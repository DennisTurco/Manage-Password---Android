package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Login extends AppCompatActivity{

    private String Username;
    private String Password;

    private EditText username;
    private EditText password;
    private Button btnLogin;

    public Login(String text1, String text2){
        Username = text1;
        Password = text2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);


        //----------------------------- Button Login ------------------------------
        username = findViewById(R.id.textUsername);
        password = findViewById(R.id.textPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
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

            }
        });

    }


}
