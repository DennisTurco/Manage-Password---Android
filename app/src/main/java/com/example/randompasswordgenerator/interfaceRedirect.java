package com.example.randompasswordgenerator;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public interface interfaceRedirect {
    void RedirectActivity(Context packageContext, Class<?> cls2);
    void RedirectActivityPutsExtra(Context packageContext, Class<?> cls, View v, String User, String name);
    File CheckOrCreateFile(String fileName);
    StringBuilder ReadFromFile(File file);
    String GetStringJson(ArrayList<DataList> dataList, String currentUser, StringBuilder text, View view, String editTextName, String editTextUsername, String editTextPassword);
    boolean WriteToFile(String json, File file);
    String Comment(String password, TextView txtComment);
}
