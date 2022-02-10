package com.example.randompasswordgenerator;

import android.content.Intent;
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
                openActivity_Main();  //chiamata alla funzione
            }
        });

    }

    public void openActivity_Main(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}