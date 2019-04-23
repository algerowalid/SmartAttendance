package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.affichage.Adapter_affichage;
import com.example.admin.synchronisation_tstart.affichage.Adapter_programme;
import com.example.admin.synchronisation_tstart.affichage.list_contact;
import com.example.admin.synchronisation_tstart.affichage.programme;
import com.example.admin.synchronisation_tstart.database.DatabaseManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Gestion_programme extends AppCompatActivity {

    Context context = Gestion_programme.this;

    ListView lista ;
    public DatabaseManager database = new DatabaseManager(this);


    public android.app.AlertDialog DetailalertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_programme);


        lista = (ListView) findViewById(R.id.list);

        fillList();

    }





    public void fillList(){
        ArrayList<programme> myList = new ArrayList<programme>();
        myList.clear();
        String req = "select p.id, p.designation , p.heured, p.heuref , m.designation , m.id  from "+database.TABLE_PROGRAMME+" p " +
                " inner join module m on m.id = p.idm " ;

        // Select All Query

        database.open();
        Cursor cursor = database.getCurs(req);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String d= cursor.getString(1);
                String hd= cursor.getString(2);
                String hf= cursor.getString(3);
                String mo= cursor.getString(4);
                int  idm= cursor.getInt(5);

                programme vend = new programme(id,idm,mo,d,hd,hf);
                myList.add(vend);


            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        if(myList.size()>0) {
            lista.setAdapter(new Adapter_programme(context, myList));
        }else{
            Toast.makeText(context,"Votre lste est vide",Toast.LENGTH_LONG).show();

        }
        database.close();


    }









    public void ajouterp(View tr ){


        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_programme, null); //tab_popup_layout

            ImageView annulerDialog = (ImageView) view.findViewById(R.id.fermer);
            Button validerDialog = (Button) view.findViewById(R.id.valider);

            final EditText des= (EditText) view.findViewById(R.id.designation);
            final EditText hrd= (EditText) view.findViewById(R.id.hd);
            final EditText hrf= (EditText) view.findViewById(R.id.hf);
            final Spinner sp= (Spinner) view.findViewById(R.id.spin);
            final Spinner spt= (Spinner) view.findViewById(R.id.spint);

            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);




            database.open();
            List<String> malist = database.FillSpinner(context,"module",sp);
            database.close();




            final android.app.AlertDialog finalDetailalertDialog = DetailalertDialog;





            annulerDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    finalDetailalertDialog.dismiss();

                }
            });

            validerDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub



                    if(des.getText().toString().trim().length()>0  &&
                            hrd.getText().toString().trim().length()>0 &&
                            hrf.getText().toString().trim().length()>0 ){


                        String de = des.getText().toString().replace("'","''");
                        String hd = hrd.getText().toString().replace("'","''");
                        String hf = hrf.getText().toString().replace("'","''");


                        String md = sp.getSelectedItem().toString();
                        String[] ab = md.split(Pattern.quote("."));
                        int idm = Integer.parseInt(ab[0]);

                        int idt = spt.getSelectedItemPosition();

                        database.open();
                        database.addProgramme(idm,de,hd,hf,idt);
                        database.close();

                        finalDetailalertDialog.dismiss();
                        fillList();




                    }else{
                        Toast.makeText(context, "Veuillez saisir Designation, heure début, heure fin ",
                                Toast.LENGTH_LONG).show();

                    }



                }
            });





        } catch (Exception e) {
            e.toString();
        }

    }
























    public void fermer(View tr){
        Intent launchactivity = new Intent(context, MainActivity.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }


}
