package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        //----------------------------- Button Register ------------------------------
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                Type listType = (Type) new TypeToken<ArrayList<DataLogin>>(){}.getType();
                List<DataLogin> register = new Gson().fromJson(text.toString(), DataLogin.class);
                /*if(username.getText().toString().equals(register.getUsername()) && password.getText().toString().equals(register.getPassword())){
                    Toast.makeText(getApplicationContext(), "Logged!", Toast.LENGTH_SHORT).show();

                    InterfaceImplementation inter = new InterfaceImplementation();
                    inter.redirectActivity(Register.this, Login.class); //chiamata alla funzione campio pagina
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                }*/



            }
        });

    }



}
