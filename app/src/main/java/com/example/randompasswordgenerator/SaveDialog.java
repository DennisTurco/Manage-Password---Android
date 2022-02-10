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
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
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

        editTextName = view.findViewById(R.id.editName);
        editTextUsername = view.findViewById(R.id.editUsername);
        editTextPassword = view.findViewById(R.id.editPassword);
        editTextPassword.setText(Password);

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

                        //create folder
                        File folder = new File(Environment.getExternalStorageDirectory(), "/RandomPasswordGenerator");
                        if (!folder.exists()) folder.mkdir();

                        //create File
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "./RandomPasswordGenerator/DataList.txt");

                        //write to file
                        Gson gson = new Gson();
                        DataList data = new DataList(editTextName.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString());
                        String json = gson.toJson(data);
                        //json = "{\"Username\":\"Dennis\",\"Password\":\"prova\"}";

                        try {
                            FileOutputStream fos = null;
                            fos = new FileOutputStream(file);
                            fos.write(json.getBytes()); //nome
                            Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                            fos.close();
                        } catch (IOException e) {
                            Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        //TODO: messaggio per il feedback
                        ClipData clip = ClipData.newPlainText("simple text", editTextPassword.getText());
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
