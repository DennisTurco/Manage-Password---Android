package com.example.randompasswordgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Choose extends AppCompatActivity {

    private TextView textActivity1;
    private TextView textActivity2;
    private String User = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

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
                Intent in = new Intent(v.getContext(), MainActivity.class);
                in.putExtra("User", User);
                startActivity(in); //chiamata funzione cambio pagina
            }
        });

        //----------------------------- Activity 2 ------------------------------
        textActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), SavePasswordManually.class);
                in.putExtra("User", User);
                startActivity(in); //chiamata funzione cambio pagina
            }
        });


    }
}
