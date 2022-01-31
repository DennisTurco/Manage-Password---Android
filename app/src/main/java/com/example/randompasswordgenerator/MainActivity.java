package com.example.randompasswordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CheckBox cb_minusc, cb_maiusc, cb_numbers, cb_special;
    private SeekBar seekbar;
    private TextView indexPassword;
    private Button buttonGenerate;
    private TextInputEditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------- Seek Bar ------------------------------
        seekbar = findViewById(R.id.seekBar);
        indexPassword = findViewById(R.id.tw_indexPassword);
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
                if ((!cb_minusc.isSelected() && !cb_maiusc.isSelected() && !cb_numbers.isSelected() && !cb_special.isSelected()) || seekbar.getProgress()/6 == 0){
                    Context context = getApplicationContext();
                    CharSequence text = "Error! Argument Missing!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                //calcolo della password
                for(int i=0; i<seekbar.getProgress()/6; i++) {

                    value = random.nextInt(4 - 1 + 1) + 1;  //questa funzione ci da un numero randomico compreso tra 1 e 4

                    if(cb_minusc.isSelected() &&  value == 1) {
                        //lettera minuscola
                        value = random.nextInt(25+ 1);
                        password += letters.substring(value, value+1);
                    }
                    else if(cb_maiusc.isSelected() &&  value == 2) {
                        //lettera maiuscola
                        value = random.nextInt(25 + 1);
                        password += letters.toUpperCase().substring(value, value + 1);
                    }
                    else if(cb_special.isSelected() &&  value == 3){
                        //carattere speciale
                        value = random.nextInt(6 + 1);
                        password += special.substring(value, value+1);
                    }
                    else if(cb_numbers.isSelected() &&  value == 4) {
                        //numero
                        value = random.nextInt(9 + 1);
                        password += numbers.substring(value, value + 1);
                    }

                    else {  //a volte capita che non entra in nessun if quindi resetto il ciclo con i--
                        i--;
                    }

                }

                textPassword = findViewById(R.id.textPassword);
                textPassword.setText(password);

            }
        });
    }
}