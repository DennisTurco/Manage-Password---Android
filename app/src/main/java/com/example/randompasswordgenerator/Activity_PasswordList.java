package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_PasswordList extends AppCompatActivity {

    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        //TODO: ricalcolo della pagina

        //----------------------------- Back Home ------------------------------
        buttonBack = findViewById(R.id.btnHome);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openActivity_Main();  //chiamata alla funzione
                InterfaceImplementation inter = new InterfaceImplementation();
                inter.redirectActivity(Activity_PasswordList.this, MainActivity.class); //chiamata alla funzione per cambio pagina
            }
        });

    }

}