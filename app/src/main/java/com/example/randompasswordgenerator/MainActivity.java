package com.example.randompasswordgenerator;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CheckBox cb_minusc, cb_maiusc, cb_numbers, cb_special;
    private SeekBar seekbar;
    private TextView indexPassword;
    private Button buttonGenerate;
    private EditText textPassword;
    private MediaPlayer generateSound;
    private ImageButton btnSave, btnCopy;
    private static final String FILE_NAME = "example.txt";

    public void openDialog(View view){
        SaveDialog saveDialog = new SaveDialog(); //oggetto della classe SaveDialog
        saveDialog.show(getSupportFragmentManager(), "example dialog");
    }

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
                btnSave = (ImageButton) findViewById(R.id.btnSave);
                btnSave.setVisibility(1);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //save
                        openDialog(v);  //chiamata alla funzione

                        String text = textPassword.getText().toString();
                        FileOutputStream fos = null;
                        try{
                            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                            fos.write(text.getBytes());

                            textPassword.getText().clear();
                            Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null){
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        //messaggio per il feedback
                        ClipData clip = ClipData.newPlainText("simple text", textPassword.getText());
                        Snackbar snackbar = Snackbar.make(v, "password saved!", Snackbar.LENGTH_SHORT)
                                .setAction("Dimiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();
                    }
                });


            }
        });

    }




    public void applyTexts(String name, String username, String password) {

        //textViewUsername.setText(username);
        //textViewPassword.setText(password);
    }
}

