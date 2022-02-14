package com.example.randompasswordgenerator;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Register extends AppCompatActivity {

    private EditText textUsername;
    private EditText textPassword;
    private Button btnRegister;
    private ImageButton btnShow;
    private TextView textComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //----------------------------- TextInput1 ------------------------------


        //----------------------------- TextInput2 ------------------------------


        //----------------------------- Text Comment ------------------------------
        textPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {} //inutile
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = Comment(); //chiamata alla funzione Comment

                if (result.length() == 0){
                    textComment.setVisibility(View.INVISIBLE);
                }else{
                    textComment.setVisibility(View.VISIBLE);
                }
            }
        });

        //----------------------------- Button Register ------------------------------
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //casi d'errore
                if(textUsername.getText().length() == 0 || textPassword.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Argument Missing!", Toast.LENGTH_SHORT).show();
                    return;
                }


                File file = new File(Environment.getExternalStorageDirectory(), "./RandomPasswordGenerator/DataLogin.txt");

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
                    //You'll need to add proper error handling here
                }

                //TODO: FIXHERE -> controllare che l'utente che si vuole registrare non esisti già
                //CONTROLLO SULL'ESISTENZA DELL'UTENTE

                //Type listType = (Type) new TypeToken<ArrayList<DataLogin>>(){}.getType();
                //List<DataLogin> register = new Gson().fromJson(text.toString(), DataLogin.class);
                /*if(username.getText().toString().equals(register.getUsername()) && password.getText().toString().equals(register.getPassword())){
                    Toast.makeText(getApplicationContext(), "Logged!", Toast.LENGTH_SHORT).show();

                    InterfaceImplementation inter = new InterfaceImplementation();
                    inter.redirectActivity(Register.this, Login.class); //chiamata alla funzione campio pagina
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }*/



                //AGGIUNTA DELL'UTENTE AL FILE TXT

                //write to file
                Gson gson = new Gson();
                DataLogin data = new DataLogin(textUsername.getText().toString(), textPassword.getText().toString());
                String json = gson.toJson(data);

                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file);
                    fos.write(json.getBytes());
                    fos.close();
                    Toast.makeText(getApplicationContext(), "User Created!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                InterfaceImplementation inter = new InterfaceImplementation();
                inter.redirectActivity(Register.this, Login.class);

            }
        });

    }

    public String Comment(){
        textPassword = findViewById(R.id.textPassword);
        textComment = findViewById(R.id.txtComment2);

        String letters = "abcdefghilmnopqrstuvzxykjw";
        String numbers = "1234567890";
        String specialChars = ",._-*?!";
        String password = textPassword.getText().toString();
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

        textComment.setVisibility(View.VISIBLE);

        if (n == 0) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Too Short!");
        }
        if (n > 0 && n < 20) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Very Weak!");
        }
        if (n >= 20 && n < 40) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Weak!");
        }
        if (n >= 40 && n < 60) {
            textComment.setTextColor(Color.YELLOW);
            textComment.setText("Password Good!");
        }
        if (n >= 60 && n < 80) {
            textComment.setTextColor(Color.YELLOW);
            textComment.setText("Password Strong!");
        }
        if (n >= 80) {
            textComment.setTextColor(Color.GREEN);
            textComment.setText("Password Very Strong!");
        }

        String text = textComment.getText().toString();
        return text;
    }



}
