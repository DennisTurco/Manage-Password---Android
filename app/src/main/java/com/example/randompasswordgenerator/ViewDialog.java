package com.example.randompasswordgenerator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ViewDialog extends AppCompatDialogFragment {

    private TextView txtName;
    private TextView txtEmail;
    private TextView txtPassword;
    private String Name;
    private String Email;
    private String Password;

    public ViewDialog() {}

    public ViewDialog(String text1, String text2, String text3) {
        Name = text1;
        Email = text2;
        Password = text3;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());



        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.view_dialog, null);

        txtName = view.findViewById(R.id.txtName);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtPassword = view.findViewById(R.id.textPassword);


        builder.setView(view)
                .setTitle("Save Password")
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        txtName.setText(Name);
                        txtEmail.setText(Email);
                        txtPassword.setText(Password);
                    }

                });

        return builder.create();

    }
}
