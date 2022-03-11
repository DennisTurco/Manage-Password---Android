package com.example.randompasswordgenerator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Choose extends AppCompatActivity {

    private TextView textActivity1;
    private TextView textActivity2;
    private Button btnPasswordList;
    private String User = "";
    private InterfaceImplementation inter = new InterfaceImplementation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose);

        Toolbar tb = (Toolbar) findViewById(R.id.topBar);
        setSupportActionBar(tb);

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

    //funzione per gli options menu sul ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.logout:
                inter.RedirectActivity(Choose.this, Login.class);
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
                break;
            case R.id.password_generator:
                //inter.RedirectActivityPutsExtra(MainActivity.this, MainActivity.class, , User, "User");
                Toast.makeText(getApplicationContext(), "Password Generator", Toast.LENGTH_SHORT).show();
                break;
            case R.id.insert_manually:
                //inter.RedirectActivityPutsExtra(MainActivity.this, SavePasswordManually.class, , User, "User");
                Toast.makeText(getApplicationContext(), "Insert Manually", Toast.LENGTH_SHORT).show();
                break;
            case R.id.password_list:
                //inter.RedirectActivityPutsExtra(MainActivity.this, Activity_PasswordList.class, , User, "User"););
                Toast.makeText(getApplicationContext(), "Password List", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                //per uscire
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //per prevenire di ritornare in login
    @Override
    public void onBackPressed() {
    }
}
