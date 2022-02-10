package com.example.randompasswordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;


public class SaveDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText textPassword;
    private String Password;


    public SaveDialog(){}

    public SaveDialog(String text){
        Password = text;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        textPassword = view.findViewById(R.id.editPassword);
        textPassword.setText(Password);

        builder.setView(view)
                .setTitle("Save Password")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        //TODO: Salvattaggio informazioni passandole nel file system

                        //create File
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "prova.txt");

                        //write to file
                        /*try {
                            FileOutputStream fos = null;
                            fos = new FileOutputStream(file);
                            fos.write(editTextName.getText().toString().getBytes()); //nome
                            fos.write(editTextUsername.getText().toString().getBytes()); //email
                            fos.write(editTextPassword.getText().toString().getBytes()); //password
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                        JSONObject jasonObj = new JSONObject();
                        try {
                            jasonObj.put("Name", editTextName.getText().toString());
                            jasonObj.put("Email", editTextUsername.getText().toString());
                            jasonObj.put("Password", editTextPassword.getText().toString());
                            StringWriter out = new StringWriter();

                            String jsonText = out.toString();
                            
                            try {
                                FileOutputStream fos = null;
                                fos = new FileOutputStream(file);
                                fos.write(jsonText.getBytes());
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                                
                            System.out.print(jsonText);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //messaggio per il feedback
                        ClipData clip = ClipData.newPlainText("simple text", textPassword.getText());
                        Snackbar snackbar = Snackbar.make(view, "password saved!", Snackbar.LENGTH_SHORT)
                                .setAction("Dimiss", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();

                    }
                });

        return builder.create();


    }
}
