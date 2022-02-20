package com.example.randompasswordgenerator;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<DataList> dataList;

    //sono indicati come static perchè vengono utilizzati dalla funzione "removeItem che è a sua volta static"
    private static ListView listView;
    private static ArrayList<String> info;
    private static ListViewAdapter dataListAdapter;
    private static Context context;

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
        dataList = new ArrayList<>();

        Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
        dataList = gson.fromJson(text.toString(), type);


        //----------------------------- Creazione Lista ------------------------------

        info = new ArrayList<>();
        for (int i=0; i<dataList.size(); i++){
            if (User.equals(dataList.get(i).getUsername())){ //devo prendere le password solo dell'utente loggato
                info.add(dataList.get(i).getName());
            }
        }

        listView = findViewById(R.id.lista_dati);
        dataListAdapter = new ListViewAdapter(this, info); //R.layout.list_item si riferisce al file xml
        listView.setAdapter(dataListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String element = parent.getItemAtPosition(position).toString();
                openDialog(view, dataList.get(position).getName(), dataList.get(position).getEmail(), dataList.get(position).getPassword());
                Toast.makeText(getApplicationContext(), element + "\n" + dataList.get(position).getEmail() + "\n" + dataList.get(position).getPassword() , Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void openDialog(View view, String text, String text2, String text3){
        ViewDialog viewDialog = new ViewDialog(text, text2, text3); //oggetto della classe SaveDialog
        viewDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //è definita come static perchè in questo modo è riferibile da altre classi, in particolare dalla classe ListViewAdapter
    public static void removeItem(int i){  //funzione per la rimozione di un elemento dalla lista
        makeToast("Removed: " + info.get(i));
        info.remove(i);
        listView.setAdapter(dataListAdapter);
    }

    // function to make a Toast given a string
    static Toast t;

    private static void makeToast(String s) {
        if (t != null) t.cancel();
        t = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        t.show();
    }


}