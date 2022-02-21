package com.example.randompasswordgenerator;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavePasswordManually extends AppCompatActivity {

    private Button btnSave;
    private Button btnPasswordList;
    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private TextView txtComment;
    private String User = "";
    private ArrayList<DataList> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.save_password_manually);

        //Ottengo il dato dal Choose
        Bundle message = getIntent().getExtras();
        if(message != null){
            User = message.getString("User");
        }


        //----------------------------- Button Save ------------------------------
        editName = findViewById(R.id.textName);
        editEmail = findViewById(R.id.textEmail);
        editPassword = findViewById(R.id.textPassword);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //casi d'errore
                if(editName.getText().length() == 0 || editEmail.getText().length() == 0 || editPassword.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Error! Argument Missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create File
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "./RandomPasswordGenerator/DataList.txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //Read text from file
                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


                Gson gson = new Gson();
                DataList data = new DataList(User, editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                dataList = new ArrayList<>();

                if(!text.toString().isEmpty() && text != null){
                    Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
                    dataList = gson.fromJson(text.toString(), type);

                    for (DataList d: dataList) {
                        if(d.getPassword().equals(data.getPassword()) && d.getName().equals(data.getName()) && d.getEmail().equals(data.getEmail())){
                            Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }

                dataList.add(data);

                String json = gson.toJson(dataList);


                //write to file
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file);
                    fos.write(json.getBytes()); //nome
                    Toast.makeText(view.getContext(), "Password Saved!", Toast.LENGTH_SHORT).show();
                    fos.close();
                } catch (IOException e) {
                    Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


                //TODO: FIXHERE -> messaggio per il feedback
                ClipData clip = ClipData.newPlainText("simple text", editName.getText());
                Snackbar snackbar = Snackbar.make(view, "password saved!", Snackbar.LENGTH_SHORT)
                        .setAction("Dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();
            }
        });



        //----------------------------- Comment ------------------------------
        editPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {} //inutile
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Comment(editPassword.getText().toString()); //chiamata alla funzione Comment()
            }
        });



        //----------------------------- Button Password List ------------------------------
        btnPasswordList = (Button) findViewById(R.id.btnPasswordList);
        btnPasswordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*InterfaceImplementation inter = new InterfaceImplementation();
                inter.redirectActivity(MainActivity.this,Activity_PasswordList.class); //chiamata alla funzione per cambio pagina*/

                //TODO: aggiungere metodo alla interface e richiamarlo
                Intent in = new Intent(view.getContext(), Activity_PasswordList.class);
                in.putExtra("User", User);
                startActivity(in); //chiamata funzione cambio pagina
            }
        });


    }

    //TODO: inserirlo nell'interface in modo tale da richiamarlo
    @SuppressLint("SetTextI18n")
    public String Comment(String password) {
        editPassword = findViewById(R.id.editText);
        txtComment = findViewById(R.id.txtComment);

        String letters = "abcdefghilmnopqrstuvzxykjw";
        String numbers = "1234567890";
        String specialChars = ",._-*?!";
        int n = 0;

        //lettere minuscole e maiuscole
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<letters.length(); j++) {
                if ( (password.charAt(i) == letters.charAt(j)) || (password.charAt(i) == letters.toUpperCase().charAt(j)) ) {
                    n = n + 3;
                }
            }
        }
        //numeri
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<numbers.length(); j++) {
                if (password.charAt(i) == numbers.charAt(j)) {
                    n = n + 8;
                }
            }
        }
        //caratteri speciali
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<specialChars.length(); j++) {
                if (password.charAt(i) == specialChars.charAt(j)) {
                    n = n + 10;
                }
            }
        }

        txtComment.setVisibility(View.VISIBLE);

        if (n == 0) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Too Short!");
        }
        if (n > 0 && n < 20) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Very Weak!");
        }
        if (n >= 20 && n < 40) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Weak!");
        }
        if (n >= 40 && n < 60) {
            txtComment.setTextColor(Color.YELLOW);
            txtComment.setText("Password Good!");
        }
        if (n >= 60 && n < 80) {
            txtComment.setTextColor(Color.YELLOW);
            txtComment.setText("Password Strong!");
        }
        if (n >= 80) {
            txtComment.setTextColor(Color.GREEN);
            txtComment.setText("Password Very Strong!");
        }

        return txtComment.getText().toString();
    }
}
