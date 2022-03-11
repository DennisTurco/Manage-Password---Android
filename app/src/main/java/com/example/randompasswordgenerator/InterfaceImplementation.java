package com.example.randompasswordgenerator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class InterfaceImplementation implements interfaceRedirect {

    @Override
    public void RedirectActivity(Context packageContext, Class<?> cls2) {
        Intent intent = new Intent(packageContext, cls2);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        packageContext.startActivity(intent);
    }

    @Override
    public void RedirectActivityPutsExtra(Context packageContext, Class<?> cls, View v, String User, String name) {
        Intent in = new Intent(v.getContext(), cls);
        in.putExtra(name, User);
        packageContext.startActivity(in); //chiamata funzione cambio pagina
    }

    @Override
    public File CheckOrCreateFile(String nameFile){
        //create File
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), nameFile);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
        }
        return file;
    }

    @Override
    public StringBuilder ReadFromFile(File file) {
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
            Log.e("error", e.toString());
        }
        return text;
    }

    @Override
    public String GetStringJson(ArrayList<DataList> dataList, String currentUser, StringBuilder text, View view, String editTextName, String editTextUsername, String editTextPassword) {
        Gson gson = new Gson();
        DataList data = new DataList(currentUser, editTextName, editTextUsername, editTextPassword);
        dataList = new ArrayList<>();

        if(!text.toString().isEmpty() && text != null){
            Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
            dataList = gson.fromJson(text.toString(), type);

            ArrayList<DataList> info = new ArrayList<>();
            for (DataList d: dataList){
                if (currentUser.equals(d.getUsername())){ //devo prendere le password solo dell'utente loggato
                    info.add(d);
                }
            }

            for (DataList d: info) {
                if(d.getName().equals(data.getName()) && d.getEmail().equals(data.getEmail())){
                    Toast.makeText(view.getContext(), "Error! Email and Name already inserted", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        }

        dataList.add(data);

        return gson.toJson(dataList);
    }

    @Override
    public boolean WriteToFile(String json, File file) {
        boolean correct;
        try {
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            fos.write(json.getBytes()); //nome
            correct = true;
            fos.close();
        } catch (IOException e) {
            correct = false;
            Log.e("error", e.toString());
            e.printStackTrace();
        }
        return correct;
    }

    public String Comment(String password, TextView txtComment){

        String letters = "abcdefghilmnopqrstuvzxykjw";
        String numbers = "1234567890";
        String specialChars = ",._-*?!";
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

        txtComment.setVisibility(View.VISIBLE);

        if (n == 0) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Too Short!");
        }
        if (n > 0 && n < 20) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Very Weak!");
        }
        if (n >= 20 && n < 40) {
            txtComment.setTextColor(Color.RED);
            txtComment.setText("Password Weak!");
        }
        if (n >= 40 && n < 60) {
            txtComment.setTextColor(Color.YELLOW);
            txtComment.setText("Password Good!");
        }
        if (n >= 60 && n < 80) {
            txtComment.setTextColor(Color.YELLOW);
            txtComment.setText("Password Strong!");
        }
        if (n >= 80) {
            txtComment.setTextColor(Color.GREEN);
            txtComment.setText("Password Very Strong!");
        }

        return txtComment.getText().toString();
    }


}
