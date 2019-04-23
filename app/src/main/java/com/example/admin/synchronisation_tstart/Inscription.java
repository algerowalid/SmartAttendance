package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.database.DatabaseManager;

public class Inscription extends AppCompatActivity {

    Context context = Inscription.this;
    EditText n, p, t,e, nfc;
    Spinner spinner;

    public DatabaseManager database = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        n = (EditText) findViewById(R.id.nom);
        p = (EditText) findViewById(R.id.prenom);
        e = (EditText) findViewById(R.id.email);
        t = (EditText) findViewById(R.id.telephone);
        nfc = (EditText) findViewById(R.id.ncarte);
        spinner = (Spinner) findViewById(R.id.spint);

        n.requestFocus();

    }

    public void sauvgarder(View tr){
        if(n.getText().toString().trim().length()>0 && p.getText().toString().trim().length()>0 &&
        e.getText().toString().trim().length()>0 && nfc.getText().toString().trim().length()>0 ){

            String nom="", prenom="", telephone="", email="", ncart="";
            int gr =1;

            nom = n.getText().toString().replace("'","''");
            prenom= p.getText().toString().replace("'","''");
            email= e.getText().toString().replace("'","''");
            ncart= nfc.getText().toString().replace("'","''");

            gr = spinner.getSelectedItemPosition()+1;


            if(t.getText().toString().trim().length()>0) {
                telephone= t.getText().toString().replace("'", "''");
            }


            database.open();
            database.addEtudiant(nom,prenom,telephone,email,ncart,gr);
            database.close();

            reinit();

        }else{
            Toast.makeText(context,"Nom, Prénom et Email sont des champs obligatoires", Toast.LENGTH_LONG).show();
        }


    }

    public void fermer(View tr){
        Intent launchactivity = new Intent(context, Gestion_contacts.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }


    public void reinit(){
        n.setText("");
        p.setText("");
        e.setText("");
        t.setText("");
        nfc.setText("");


        n.requestFocus();
    }

}
