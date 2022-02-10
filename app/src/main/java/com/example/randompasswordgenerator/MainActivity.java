package com.example.randompasswordgenerator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CheckBox cb_minusc, cb_maiusc, cb_numbers, cb_special;
    private SeekBar seekbar;
    private TextView indexPassword;
    private Button buttonGenerate;
    private Button buttonPasswordList;
    public EditText textPassword;
    private TextView textComment;
    private MediaPlayer generateSound;
    private ImageButton btnSave, btnCopy;
    private static final String FILE_NAME = "example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        generateSound = MediaPlayer.create(MainActivity.this, R.raw.spin);  // per il suono del dado

        //----------------------------- Seek Bar ------------------------------
        seekbar = findViewById(R.id.seekBar);
        seekbar.setProgress(45); //settata al centro come default
        indexPassword = findViewById(R.id.tw_indexPassword);
        indexPassword.setText("" + seekbar.getProgress()/6);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                indexPassword.setText("" + seekbar.getProgress()/6);
            }
            @SuppressLint("SetTextI18n")
            public void onStartTrackingTouch(SeekBar seekBar) {
                indexPassword.setText("" + seekbar.getProgress()/6);
            }

            @SuppressLint("SetTextI18n")
            public void onStopTrackingTouch(SeekBar seekBar) {
                indexPassword.setText("" + seekbar.getProgress()/6);
            }
        });

        //----------------------------- Password List ------------------------------
        buttonPasswordList = (Button) findViewById(R.id.btnPasswordList);
        buttonPasswordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity_passwordList();  //chiamata alla funzione
            }
        });

        //----------------------------- Button Generate ------------------------------
        buttonGenerate = findViewById(R.id.btnGenerate);
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                cb_minusc = findViewById(R.id.cb_minusc);
                cb_maiusc = findViewById(R.id.cb_maiusc);
                cb_numbers = findViewById(R.id.cb_numbers);
                cb_special = findViewById(R.id.cb_special);

                String letters = "abcdefghijklmnopqrstuvwxyz";
                String numbers = "1234567890";
                String special = ",.-_!?#";
                String password = "";

                Random random = new Random();
                int value;

                //casi d'errore
                if ((!cb_minusc.isChecked() && !cb_maiusc.isChecked() && !cb_numbers.isChecked() && !cb_special.isChecked()) || seekbar.getProgress()/6 == 0 ){
                    Toast toast = Toast.makeText(getApplicationContext(), "Error! Argument Missing!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                textPassword = findViewById(R.id.editText);
                textPassword.setVisibility(1);

                //riproduzione souno
                generateSound.start();

                //calcolo della password
                for(int i=0; i<seekbar.getProgress()/6; i++) {

                    value = random.nextInt(4 - 1 + 1) + 1;  //questa funzione ci da un numero randomico compreso tra 1 e 4

                    if(cb_minusc.isChecked() &&  value == 1) {
                        //lettera minuscola
                        value = random.nextInt(25+ 1);
                        password += letters.substring(value, value+1);
                    }
                    else if(cb_maiusc.isChecked() &&  value == 2) {
                        //lettera maiuscola
                        value = random.nextInt(25 + 1);
                        password += letters.toUpperCase().substring(value, value + 1);
                    }
                    else if(cb_special.isChecked() &&  value == 3){
                        //carattere speciale
                        value = random.nextInt(6 + 1);
                        password += special.substring(value, value+1);
                    }
                    else if(cb_numbers.isChecked() &&  value == 4) {
                        //numero
                        value = random.nextInt(9 + 1);
                        password += numbers.substring(value, value + 1);
                    }

                    else {  //a volte capita che non entra in nessun if quindi resetto il ciclo con i--
                        i--;
                    }

                }

                textPassword.setText(password);
                textPassword.addTextChangedListener(new TextWatcher() {
                    public void afterTextChanged(Editable s) {} //inutile
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {} //inutile
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        Comment(); //chiamata alla funzione Comment
                    }
                });


                //----------------------------- Button Copy ------------------------------
                btnCopy = (ImageButton) findViewById(R.id.btnCopy);
                btnCopy.setVisibility(1);
                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //copy
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("EditText", textPassword.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        //messaggio per il feedback
                        Snackbar snackbar = Snackbar.make(v, "password copied!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });

                //----------------------------- Button Save ------------------------------
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                }

                btnSave = (ImageButton) findViewById(R.id.btnSave);
                btnSave.setVisibility(1);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //save
                        openDialog(v, textPassword.getText().toString());  //chiamata alla funzione


                    }
                });


            }
        });

    }

    private void saveTextAsFile(String filename, String content){

        /*String fileName = filename + ".txt";

        //create File
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);

        //write to file
        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not Found!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Saving!", Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted!", Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void openDialog(View view, String text){
        SaveDialog saveDialog = new SaveDialog(text); //oggetto della classe SaveDialog
        saveDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openActivity_passwordList(){
        Intent intent = new Intent(this, Activity_PasswordList.class);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    public void Comment() {
        textPassword = findViewById(R.id.editText);
        textComment = findViewById(R.id.txtComment);

        String letters = "abcdefghilmnopqrstuvzxykjw";
        String numbers = "1234567890";
        String specialChars = ",._-*?!";
        String password = textPassword.getText().toString();
        int n = 0;

        //lettere minuscole e maiuscole
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<letters.length(); j++) {
                if ( (password.charAt(i) == letters.charAt(j)) || (password.charAt(i) == letters.toUpperCase().charAt(j)) ) {
                    n = n + 3;
                }
            }
        }
        //numeri
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<numbers.length(); j++) {
                if (password.charAt(i) == numbers.charAt(j)) {
                    n = n + 8;
                }
            }
        }
        //caratteri speciali
        for (int i=0; i<password.length(); i++){
            for (int j=0; j<specialChars.length(); j++) {
                if (password.charAt(i) == specialChars.charAt(j)) {
                    n = n + 10;
                }
            }
        }

        textComment.setVisibility(View.VISIBLE);

        if (n == 0) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Too Short!");
        }
        if (n > 0 && n < 20) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Very Weak!");
        }
        if (n >= 20 && n < 40) {
            textComment.setTextColor(Color.RED);
            textComment.setText("Password Weak!");
        }
        if (n >= 40 && n < 60) {
            textComment.setTextColor(Color.YELLOW);
            textComment.setText("Password Good!");
        }
        if (n >= 60 && n < 80) {
            textComment.setTextColor(Color.YELLOW);
            textComment.setText("Password Strong!");
        }
        if (n >= 80) {
            textComment.setTextColor(Color.GREEN);
            textComment.setText("Password Very Strong!");
        }
    }
}

