package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Choose extends AppCompatActivity {

    private TextView textActivity1;
    private TextView textActivity2;
    private Button btnPasswordList;
    private String User = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        InterfaceImplementation inter = new InterfaceImplementation();

        //Ottengo il dato dal Login
        Bundle message = getIntent().getExtras();
        if(message != null){
            User = message.getString("User");
        }

        textActivity1 = findViewById(R.id.textView4);
        textActivity2 = findViewById(R.id.textView5);

        //----------------------------- Activity 1 ------------------------------
        textActivity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.RedirectActivityPutsExtra(Choose.this, MainActivity.class, v, User, "User");
            }
        });

        //----------------------------- Activity 2 ------------------------------
        textActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.RedirectActivityPutsExtra(Choose.this, SavePasswordManually.class, v, User, "User");
            }
        });

        //----------------------------- Button Password List ------------------------------
        btnPasswordList = (Button) findViewById(R.id.btnPasswordList);
        btnPasswordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.RedirectActivityPutsExtra(Choose.this, Activity_PasswordList.class, v, User, "User");
            }
        });




    }
}
