package com.example.randompasswordgenerator;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_PasswordList extends AppCompatActivity{

    private ImageButton buttonBack;
    private static InterfaceImplementation inter = new InterfaceImplementation();
    private static String User;
    private static ArrayList<DataList> dataList;

    //sono indicati come static perchè vengono utilizzati dalla funzione "removeItem che è a sua volta static"
    private static ListView listView;
    private static ArrayList<DataList> info;
    private static ListViewAdapter dataListAdapter;
    private static Context context;
    private static File file = new File(Environment.getExternalStorageDirectory(), "./RandomPasswordGenerator/DataList.txt");

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
        //Read text from file
        StringBuilder text = inter.ReadFromFile(file);

        Gson gson = new Gson();
        dataList = new ArrayList<>();

        Type type = new TypeToken<ArrayList<DataList>>() {}.getType();
        dataList = gson.fromJson(text.toString(), type);


        //----------------------------- Creazione Lista ------------------------------
        info = new ArrayList<>();
        for (int i=0; i<dataList.size(); i++){
            if (User.equals(dataList.get(i).getUsername())){ //devo prendere le password solo dell'utente loggato
                info.add(dataList.get(i));
            }
        }

        listView = findViewById(R.id.lista_dati);
        dataListAdapter = new ListViewAdapter(this, info); //R.layout.list_item si riferisce al file xml
        listView.setAdapter(dataListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog(view, info.get(position).getName(), info.get(position).getEmail(), info.get(position).getPassword());
            }
        });


    }

    public void openDialog(View view, String text, String text2, String text3){
        ViewDialog viewDialog = new ViewDialog(text, text2, text3); //oggetto della classe SaveDialog
        viewDialog.show(getSupportFragmentManager(), "example dialog");
    }

    //è definita come static perchè in questo modo è riferibile da altre classi, in particolare dalla classe ListViewAdapter
    public static void removeItem(int i){  //funzione per la rimozione di un elemento dalla lista
        info.remove(i);

        ArrayList<DataList> d = new ArrayList<>();
        for (int j=0; j<dataList.size(); j++){
            if (!User.equals(dataList.get(j).getUsername())){ //devo prendere le password solo dell'utente loggato
                d.add(dataList.get(j));
            }
        }
        d.addAll(info);

        Gson gson = new Gson();
        String json = gson.toJson(d);

        inter.WriteToFile(json ,file);

        listView.setAdapter(dataListAdapter);

        //TODO: FIXHERE -> messaggio per il feedback
        /*ClipData clip = ClipData.newPlainText("simple text", editTextPassword.getText());
        Snackbar snackbar = Snackbar.make(view, "password saved!", Snackbar.LENGTH_SHORT)
                .setAction("Dimiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.show();*/
    }

}