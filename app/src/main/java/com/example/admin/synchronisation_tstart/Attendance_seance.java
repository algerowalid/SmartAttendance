package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.affichage.Adapter_affichage;
import com.example.admin.synchronisation_tstart.affichage.list_contact;
import com.example.admin.synchronisation_tstart.database.DatabaseManager;

import java.util.ArrayList;

public class Attendance_seance extends AppCompatActivity {

    Context context = Attendance_seance.this;
    ArrayList<list_contact> myList ;
    public DatabaseManager database = new DatabaseManager(this);
    EditText carte;
    TextView presence ;

    ListView lista ;

    public int IDS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_seance);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            IDS = extras.getInt("ids");
        }

        carte = (EditText) findViewById(R.id.carte);
        presence= (TextView) findViewById(R.id.presence);

        lista = (ListView) findViewById(R.id.listview);

        myList = new ArrayList<list_contact>();


        carte.setFocusableInTouchMode(true);
        carte.setFocusable(true);


        carte.requestFocus();

        carte.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {

                if((keyevent.getAction() != KeyEvent.ACTION_DOWN)) {

                    if (keyCode == KeyEvent.KEYCODE_ENTER){


                        if (carte.getText().toString().trim().length() > 0) {

                            String crt = carte.getText().toString();

                            int testCarte=0;
                            for (int i = 0; i < myList.size(); i++) {

                                if(myList.get(i).getIdcarte().equals(crt)){
                                    testCarte=1;
                                    i=myList.size();
                                }
                            }

                            if(testCarte==0) {
                                String re = "select * from etudiant where id_nfc='" + crt + "'";
                                database.open();
                                String nm = database.getString(re, "nom");
                                String pr = database.getString(re, "prenom");
                                int id = database.getInt(re, "id");
                                database.close();
                                carte.setText("");


                                if(id>0) {
                                    list_contact a = new list_contact(id, nm, pr, "", "", crt,0);
                                    myList.add(a);
                                    FillList();

                                }

                                carte.requestFocus();

                            }else{
                                Toast.makeText(context,"Carte déja utilisé",Toast.LENGTH_LONG).show();
                                carte.requestFocus();
                                carte.setText("");

                            }

                        }
                        carte.requestFocus();
                        return true;
                    }

                }else{
                    carte.requestFocus();

                }

                return false;


            }
        });


    }


    public void FillList(){

        if(myList.size()>0) {
            lista.setAdapter(new Adapter_affichage(context, myList));
            carte.requestFocus();
        }else{
            Toast.makeText(context,"Votre lste est vide",Toast.LENGTH_LONG).show();

        }
    }

    public void fseance(View tr){

database.open();
        for(int i=0; i<myList.size(); i++){
            database.addRPS(IDS,myList.get(i).getId());
        }
database.close();

        Intent launchactivity = new Intent(context, MainActivity.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }
}
