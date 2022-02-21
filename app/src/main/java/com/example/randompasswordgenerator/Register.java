package com.example.randompasswordgenerator;

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
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private EditText textUsername;
    private EditText textPassword;
    private Button btnRegister;
    private ImageButton btnShow;
    private TextView textComment;
    private ArrayList<DataLogin> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        InterfaceImplementation inter = new InterfaceImplementation();

        textUsername = findViewById(R.id.textName);
        textPassword = findViewById(R.id.textPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);


        //----------------------------- Text Comment ------------------------------
        textComment = findViewById(R.id.txtComment2);
        textPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {} //inutile
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = inter.Comment(textPassword.getText().toString(), textComment); //chiamata alla funzione Comment

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

                //create folder
                File folder = new File(Environment.getExternalStorageDirectory(), "/RandomPasswordGenerator");
                if (!folder.exists()) folder.mkdir();

                //create File
                File file = inter.CheckOrCreateFile("./RandomPasswordGenerator/DataLogin.txt");

                //Read text from file
                StringBuilder text = inter.ReadFromFile(file);

                //CONTROLLO SULL'ESISTENZA DELL'UTENTE
                Gson gson = new Gson();
                register = new ArrayList<>();
                DataLogin data = new DataLogin(textUsername.getText().toString(), textPassword.getText().toString());
                if(!text.toString().isEmpty() && text != null){

                    Type type = new TypeToken<ArrayList<DataLogin>>() {}.getType();
                    register = gson.fromJson(text.toString(), type);
                    for (int i=0; i<register.size(); i++){
                        if(register.get(i).getUsername().equals(data.getUsername())){
                            Toast.makeText(getApplicationContext(), "Error! Username already used!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                register.add(data);
                String json = gson.toJson(register);


                //AGGIUNTA DELL'UTENTE AL FILE TXT
                boolean correct = inter.WriteToFile(json, file);
                if (correct) Toast.makeText(v.getContext(), "User Created!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(v.getContext(), "Error! User Creation!", Toast.LENGTH_SHORT).show();

                inter.RedirectActivity(Register.this, Login.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }




}
