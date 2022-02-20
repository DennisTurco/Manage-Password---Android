package com.example.randompasswordgenerator;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_PasswordList extends AppCompatActivity{

    private ImageButton buttonBack;
    private String User;
    private ListView listView;
    private ArrayList<DataList> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        //TODO: ricerca con lente d'ingrandimento


        //Ottengo il dato dal Login
        Bundle message = getIntent().getExtras();
        if(message != null){
            User = message.getString("User");
        }

        //OTTENGO GLI ELEMENTI DELLA LISTA
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "./RandomPasswordGenerator/DataList.txt");

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
        //DataList data = new DataList(User, editTextName.getText().toString(), editTextUsername.getText().toString(), editTextPassword.getText().toString());
        dataList = new ArrayList<>();

        Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
        dataList = gson.fromJson(text.toString(), type);


        //----------------------------- Creazione Lista ------------------------------

        ArrayList<String> info = new ArrayList<>();
        for (int i=0; i<dataList.size(); i++){
            info.add(dataList.get(i).getName());
        }

        listView = findViewById(R.id.lista_dati);
        ArrayAdapter<String> dataListAdapter = new ArrayAdapter<>(this, R.layout.list_item, info); //R.layout.list_item si riferisce al file xml
        listView.setAdapter(dataListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), element, Toast.LENGTH_SHORT).show();
            }
        });


    }


}