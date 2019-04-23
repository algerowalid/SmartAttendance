package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.affichage.Adapter_affichage;
import com.example.admin.synchronisation_tstart.affichage.list_contact;
import com.example.admin.synchronisation_tstart.database.DatabaseManager;
import com.sun.mail.util.LineInputStream;

import java.util.ArrayList;

public class Gestion_contacts extends AppCompatActivity {

    Context context = Gestion_contacts.this;
    ListView lista ;

    public  DatabaseManager database = new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_contacts);


   lista = (ListView) findViewById(R.id.list);

        fillList();

    }





    public void fillList(){
        ArrayList<list_contact> myList = new ArrayList<list_contact>();
        myList.clear();
        String req = "select * from "+database.TABLE_ETUDIANT;

        // Select All Query

        database.open();
        Cursor cursor = database.getCurs(req);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String n= cursor.getString(1);
                String p= cursor.getString(2);
                String te= cursor.getString(3);
                String ad= cursor.getString(4);
                String cart= cursor.getString(5);
                int gr= cursor.getInt(6);

                list_contact vend = new list_contact(id,n,p,ad,te,cart, gr);
                myList.add(vend);


            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        if(myList.size()>0) {
            lista.setAdapter(new Adapter_affichage(context, myList));
        }else{
            Toast.makeText(context,"Votre lste est vide",Toast.LENGTH_LONG).show();

        }
        database.close();


    }


    public void fermer(View tr){
        Intent launchactivity = new Intent(context, MainActivity.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }

    public void ajouterp(View rr){

        Intent launchactivity = new Intent(context, Inscription.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }


}
