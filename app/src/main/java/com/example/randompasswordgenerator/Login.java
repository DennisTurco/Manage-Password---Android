package com.example.randompasswordgenerator;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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


                //CONTROLLO SULL'ESISTENZA DELL'UTENTE
                register = new ArrayList<>();
                register = new Gson().fromJson(text.toString(), ArrayList.class);
                DataLogin data = new DataLogin(username.getText().toString(), password.getText().toString());
                String stringaUsername = "";
                String stringaPassword = "";

                for(int i=0; i<register.size(); i++){
                    //{Password=sad , Username=asd} formattazione iniziale
                    stringaUsername = "" + register.get(i);
                    stringaUsername = stringaUsername.split(",")[1];  //metto alla fine [1] perchè voglio ottenere la seconda parte
                    stringaUsername = stringaUsername.split("=")[1];

                    stringaPassword = "" + register.get(i);
                    stringaPassword = stringaPassword.split(",")[0];  //metto alla fine [0] perchè voglio ottenere la prima parte
                    stringaPassword = stringaPassword.split("=")[1];


                    if(stringaUsername.substring(0, stringaUsername.length()-1).equals(data.getUsername())  &&  stringaPassword.equals(data.getPassword())) {
                        Toast.makeText(getApplicationContext(), "Logged!", Toast.LENGTH_SHORT).show();
                        inter.redirectActivity(Login.this, MainActivity.class); //chiamata funzione cambio pagina
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
