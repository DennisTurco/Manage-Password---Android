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
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SaveDialog extends AppCompatDialogFragment {
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private String Password;
    private String User;
    private ArrayList<DataList> dataList;

    public SaveDialog(){}

    public SaveDialog(String text, String text2){
        Password = text;
        User = text2;
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

                        //casi d'errore
                        if(editTextName.getText().length() == 0 || editTextUsername.getText().length() == 0 || editTextPassword.getText().length() == 0){
                            Toast.makeText(view.getContext(), "Error! Argument Missing!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //create File
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "./RandomPasswordGenerator/DataList.txt");
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        //Read text from file
                        StringBuilder text = new StringBuilder();

                        try {
                            BufferedReader br = new BufferedReader(new FileReader(file));
                            String line;

                            while ((line = br.readLine()) != null) {
                                text.append(line);
                                text.append('\n');
                            }
                            br.close();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }


                        Gson gson = new Gson();
                        DataList data = new DataList(User, editTextName.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString());
                        dataList = new ArrayList<>();

                        if(!text.toString().isEmpty() && text != null){
                            Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
                            dataList = gson.fromJson(text.toString(), type);

                            for (DataList d: dataList) {
                                if(d.getPassword().equals(data.getPassword()) && d.getName().equals(data.getName()) && d.getEmail().equals(data.getEmail())){
                                    Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                        }

                        dataList.add(data);

                        String json = gson.toJson(dataList);


                        //write to file
                        try {
                            FileOutputStream fos = null;
                            fos = new FileOutputStream(file);
                            fos.write(json.getBytes()); //nome
                            Toast.makeText(view.getContext(), "Password Saved!", Toast.LENGTH_SHORT).show();
                            fos.close();
                        } catch (IOException e) {
                            Toast.makeText(view.getContext(), "Operation Failed!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                        //TODO: FIXHERE -> messaggio per il feedback
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
