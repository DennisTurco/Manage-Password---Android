package com.example.randompasswordgenerator;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Login extends AppCompatActivity{

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private TextView txtRegister;
    private ArrayList<DataLogin> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        InterfaceImplementation inter = new InterfaceImplementation();


        //create folder
        File folder = new File(Environment.getExternalStorageDirectory(), "/RandomPasswordGenerator");
        if (!folder.exists()) folder.mkdir();


        //----------------------------- Button Login ------------------------------
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO: controllo errori immissione campi
                //controllo errori input
                if (password.length() == 0 || username.length() == 0){
                    Toast.makeText(getApplicationContext(), "Error! Input missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //controllo esistenza file
                File file = new File(Environment.getExternalStorageDirectory(), "./RandomPasswordGenerator/DataLogin.txt");
                if (!file.exists()) {
                    Toast.makeText(getApplicationContext(), "Error! User don't exists or password is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }


                Gson gson = new Gson();


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


                //CONTROLLO SULL'ESISTENZA DELL'UTENTE
                register = new ArrayList<>();
                Type type = new TypeToken<ArrayList<DataLogin>>() {}.getType();
                register = gson.fromJson(text.toString(), type);
                DataLogin data = new DataLogin(username.getText().toString(), password.getText().toString());

                for(int i=0; i<register.size(); i++){
                    if(register.get(i).getUsername().equals(data.getUsername())  &&  register.get(i).getPassword().equals(data.getPassword())) {
                        Toast.makeText(getApplicationContext(), "Logged!", Toast.LENGTH_SHORT).show();

                        //TODO: aggiungere metodo alla interface e richiamarlo
                        Intent in = new Intent(v.getContext(), MainActivity.class);
                        in.putExtra("User", register.get(i).getUsername());
                        startActivity(in); //chiamata funzione cambio pagina

                        return;
                    }

                }

                //caso in cui non lo user non è presente
                Toast.makeText(getApplicationContext(), "Error! User don't exists or password is wrong", Toast.LENGTH_SHORT).show();
                return;

            }
        });

        //----------------------------- Text Register ------------------------------
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setPaintFlags(txtRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); //per mettere sottolineatura sulla scritta
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.redirectActivity(Login.this, Register.class); //chiamata alla funzione campio pagina
            }
        });
    }



}
