package com.example.randompasswordgenerator;

import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class SavePasswordManually extends AppCompatActivity {

    private Button btnSave;
    private Button btnPasswordList;
    private EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private TextView txtComment;
    private String User = "";
    private ArrayList<DataList> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.save_password_manually);

        InterfaceImplementation inter = new InterfaceImplementation();

        //Ottengo il dato dal Choose
        Bundle message = getIntent().getExtras();
        if(message != null){
            User = message.getString("User");
        }


        //----------------------------- Button Save ------------------------------
        editName = findViewById(R.id.textName);
        editEmail = findViewById(R.id.textEmail);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPassword = findViewById(R.id.textPassword);

                //casi d'errore
                if(editName.getText().length() == 0 || editEmail.getText().length() == 0 || editPassword.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "Error! Argument Missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create File
                File file = inter.CheckOrCreateFile("./RandomPasswordGenerator/DataList.txt");

                //Read text from file
                StringBuilder text = inter.ReadFromFile(file);

                //serialize list
                String json = inter.GetStringJson(dataList, User, text, view, editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());

                //write to file
                boolean correct = inter.WriteToFile(json, file);
                if (correct) Toast.makeText(view.getContext(), "Password Saved!", Toast.LENGTH_SHORT).show();
                else Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();


                //TODO: FIXHERE -> messaggio per il feedback
                ClipData clip = ClipData.newPlainText("simple text", editName.getText());
                Snackbar snackbar = Snackbar.make(view, "password saved!", Snackbar.LENGTH_SHORT)
                        .setAction("Dimiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                snackbar.show();
            }
        });



        //----------------------------- Comment ------------------------------
        editPassword = findViewById(R.id.editText);
        txtComment = findViewById(R.id.txtComment);
        /*editPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {} //inutile
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Comment(editPassword.getText().toString()); //chiamata alla funzione Comment()
            }
        });*/



        //----------------------------- Button Password List ------------------------------
        btnPasswordList = (Button) findViewById(R.id.btnPasswordList);
        btnPasswordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inter.RedirectActivityPutsExtra(SavePasswordManually.this, Activity_PasswordList.class, view, User, "User");
            }
        });


    }

}
