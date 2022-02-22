package com.example.randompasswordgenerator;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Register extends AppCompatActivity {

    private EditText textUsername;
    private EditText textPassword;
    private Button btnRegister;
    private ImageButton btnShow;
    private TextView textComment;
    private ArrayList<DataLogin> register;

    private int STORAGE_PERMISSION_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        InterfaceImplementation inter = new InterfaceImplementation();

        textUsername = findViewById(R.id.textName);
        textPassword = findViewById(R.id.textPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);



        //richista di ottenimento dei permessi qualora non siano già stati dati
        if(ContextCompat.checkSelfPermission(Register.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(Register.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestStoragePermissions();
        }



        //----------------------------- Text Comment ------------------------------
        textComment = findViewById(R.id.txtComment2);
        textPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {} //inutile
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = inter.Comment(textPassword.getText().toString(), textComment); //chiamata alla funzione Comment

                if (result.length() == 0){
                    textComment.setVisibility(View.INVISIBLE);
                }else{
                    textComment.setVisibility(View.VISIBLE);
                }
            }
        });

        //----------------------------- Button Register ------------------------------
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //casi d'errore
                if(textUsername.getText().length() == 0 || textPassword.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Argument Missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create folder
                File folder = new File(Environment.getExternalStorageDirectory(), "/RandomPasswordGenerator");
                if (!folder.exists()) folder.mkdir();

                //create File
                File file = inter.CheckOrCreateFile("./RandomPasswordGenerator/DataLogin.txt");

                //Read text from file
                StringBuilder text = inter.ReadFromFile(file);

                //CONTROLLO SULL'ESISTENZA DELL'UTENTE
                Gson gson = new Gson();
                register = new ArrayList<>();
                DataLogin data = new DataLogin(textUsername.getText().toString(), textPassword.getText().toString());
                if(!text.toString().isEmpty() && text != null){

                    Type type = new TypeToken<ArrayList<DataLogin>>() {}.getType();
                    register = gson.fromJson(text.toString(), type);
                    for (int i=0; i<register.size(); i++){
                        if(register.get(i).getUsername().equals(data.getUsername())){
                            Toast.makeText(getApplicationContext(), "Error! Username already used!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                register.add(data);
                String json = gson.toJson(register);


                //AGGIUNTA DELL'UTENTE AL FILE TXT
                boolean correct = inter.WriteToFile(json, file);
                if (correct) Toast.makeText(v.getContext(), "User '" + textUsername.getText().toString() + "' Created!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(v.getContext(), "Error! User Creation!", Toast.LENGTH_SHORT).show();
                    //TODO:FIXHERE -> L'ho piazzato qui per ora ma non è bellissimo
                    if(!Environment.isExternalStorageManager()) {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                    return;
                }

                inter.RedirectActivity(Register.this, Login.class);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }

    void requestStoragePermissions(){
        new AlertDialog.Builder(this)
                .setTitle("Permission needed!")
                .setMessage("This permission is needed because this application have to save information on the storage to work!")
                .setPositiveButton("ok", new DialogInterface.OnClickListener(){
                    @RequiresApi(api = Build.VERSION_CODES.R)
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                    }
                })
                .create().show();
    }

}
