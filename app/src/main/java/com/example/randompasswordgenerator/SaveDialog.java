package com.example.randompasswordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class SaveDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String Password;
    private String User = "";
    private ArrayList<DataList> dataList = null;

    public SaveDialog(){}

    public SaveDialog(String password, String user){
        Password = password;
        User = user;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        InterfaceImplementation inter = new InterfaceImplementation();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        editTextName = view.findViewById(R.id.editName);
        editTextUsername = view.findViewById(R.id.editUsername);
        editTextPassword = view.findViewById(R.id.editPassword);
        editTextPassword.setText(Password);

        builder.setView(view)
                .setTitle("Save Password")
                .setCancelable(false)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        //casi di errore
                        if(editTextName.getText().length() == 0 || editTextUsername.getText().length() == 0 || editTextPassword.getText().length() == 0){
                            Toast.makeText(view.getContext(), "Error! Argument Missing!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //create File
                        File file = inter.CheckOrCreateFile("./RandomPasswordGenerator/DataList.txt");

                        //Read text from file
                        StringBuilder text = inter.ReadFromFile(file);

                        //serialize list
                        String json = inter.GetStringJson(dataList, User, text, view, editTextName.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString());

                        //write to file
                        boolean correct = inter.WriteToFile(json, file);
                        if (correct) Toast.makeText(view.getContext(), "Password Saved!", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();


                        //TODO: FIXHERE -> messaggio per il feedback
                        ClipData.newPlainText("simple text", editTextPassword.getText());
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
