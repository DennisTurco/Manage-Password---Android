package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_PasswordList extends AppCompatActivity {

    private ImageButton buttonBack;
    private String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        //TODO: ricalcolo della pagina
        //Ottengo il dato dal Login
        Bundle message = getIntent().getExtras();
        if(message != null){
            User = message.getString("User");
        }

        //----------------------------- Back Home ------------------------------
        buttonBack = findViewById(R.id.btnHome);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InterfaceImplementation inter = new InterfaceImplementation();
                inter.redirectActivity(Activity_PasswordList.this, MainActivity.class); //chiamata alla funzione per cambio pagina
            }
        });

    }

}