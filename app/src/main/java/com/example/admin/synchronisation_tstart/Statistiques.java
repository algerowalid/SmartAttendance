package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.affichage.Adapter_affichage;
import com.example.admin.synchronisation_tstart.affichage.Adapter_stat_module;
import com.example.admin.synchronisation_tstart.affichage.list_contact;
import com.example.admin.synchronisation_tstart.affichage.stat_module;
import com.example.admin.synchronisation_tstart.database.DatabaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Statistiques extends AppCompatActivity {
    Context context = Statistiques.this;
    ListView lista ;
    Spinner spinner, spin;

    int Total =0;
    TextView info , dt1,dt2;

    public int idm=0, spi=0;

    String date1="0000-00-00", date2="3000-12-30";

    public android.app.AlertDialog DetailalertDialog;


    public DatabaseManager database = new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);

        lista = (ListView) findViewById(R.id.list);
        spinner = (Spinner) findViewById(R.id.spinner);
        spin = (Spinner) findViewById(R.id.spin);
        info = (TextView) findViewById(R.id.information);
        dt2 = (TextView) findViewById(R.id.dt2);
        dt1 = (TextView) findViewById(R.id.dt1);

        database.open();
        List<String> malist = database.FillSpinner(context,"module",spinner);
        database.close();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinner != null && spinner.getSelectedItem() !=null) {

                    String md = spinner.getSelectedItem().toString();
                    String[] ab = md.split(Pattern.quote("."));
                    idm = Integer.parseInt(ab[0]);

                    spi = spin.getSelectedItemPosition() - 1;

                    AfficheTest(idm, spi);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(spinner != null && spinner.getSelectedItem() !=null) {
                    String md = spinner.getSelectedItem().toString();
                    String[] ab = md.split(Pattern.quote("."));
                    idm = Integer.parseInt(ab[0]);
                    spi = spin.getSelectedItemPosition() - 1;
                    AfficheTest(idm, spi);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });




    }



    public void AfficheTest(int id, int idspi ){

String re = "";

        if(idspi==-1) {
            re = "select count(s.id) as id from  seance  s" +
                    " inner join  programme p on p.id = s.idp  " +
                    "  where p.idm =" + id;
        }else {
            re = "select count(s.id) as id from  seance  s" +
                    " inner join  programme p on p.id = s.idp  " +
                    "  where p.idm ="+id+" and p.type_groupe="+idspi;

        }

        re = re + " and s.date>= '"+date1+"' and s.date<= '"+date2+"'";
        database.open();
        Total = database.getInt(re,"id");
        database.close();

        info.setText("Nombre des séances : "+Total);


        fillList(id);

    }



    public void fillList(int id ){
        ArrayList<stat_module> myList = new ArrayList<stat_module>();
        myList.clear();

        String req= "";

        if(spi<1){
            req = "select e.id as id , e.nom, e.prenom, e.telephone , e.id_nfc from etudiant e ";
        }
        else{
            req = "select e.id as id , e.nom, e.prenom, e.telephone , e.id_nfc from etudiant e where type_groupe=" +spi;
        }

        // Select All Query

        database.open();
        Cursor cursor = database.getCurs(req);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int ide = cursor.getInt(0);
                String n= cursor.getString(1);
                String p= cursor.getString(2);
                String te= cursor.getString(3);
                String car= cursor.getString(4);

                String re = "";

                if(spi==-1) {
                    re = "select count(r.id) as id from  rps r" +
                            " inner join  seance  s on s.id = r.ids " +
                            " inner join  programme  p on p.id = s.idp" +
                            "  where p.idm =" + id + " and r.ide=" + ide;

                }else{
                    re = "select count(r.id) as id from  rps r" +
                            " inner join  seance  s on s.id = r.ids " +
                            " inner join  programme  p on p.id = s.idp" +
                            "  where p.idm =" + id + " and p.type_groupe="+spi+" and r.ide=" + ide;


                }

                re = re + " and s.date>= '"+date1+"' and s.date<= '"+date2+"'";

                int pre = database.getInt(re,"id");


                stat_module st= new stat_module(id,n,p,car,te,pre,Total);
                myList.add(st);


            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        if(myList.size()>0) {
            lista.setAdapter(new Adapter_stat_module(context, myList));
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


    public void choosedt(View te){

        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_date, null); //tab_popup_layout

            final DatePicker simpleDatePicker = (DatePicker) view.findViewById(R.id.simpleDatePicker); // initiate a date picker
            Button validerDialog = (Button) view.findViewById(R.id.vld);

            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);



            simpleDatePicker.setSpinnersShown(false);


            final android.app.AlertDialog finalDetailalertDialog = DetailalertDialog;





            validerDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    int day = simpleDatePicker.getDayOfMonth();
                    int month = simpleDatePicker.getMonth();
                    int year =  simpleDatePicker.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date dd = calendar.getTime();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(dd);

                    date1= dateString;
                    dt1.setText(dateString);

                    finalDetailalertDialog.dismiss();

                    if(idm>0) {
                        AfficheTest(idm, spi);
                    }

                }
            });





        } catch (Exception e) {
            e.toString();
        }


    }





    public void choosedtr(View te){

        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_date, null); //tab_popup_layout
          Button validerDialog = (Button) view.findViewById(R.id.vld);

            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);



            final DatePicker simpleDatePicker = (DatePicker) view.findViewById(R.id.simpleDatePicker); // initiate a date picker
            simpleDatePicker.setSpinnersShown(false);


            final android.app.AlertDialog finalDetailalertDialog = DetailalertDialog;






            validerDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    int day = simpleDatePicker.getDayOfMonth();
                    int month = simpleDatePicker.getMonth();
                    int year =  simpleDatePicker.getYear();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date dd =calendar.getTime();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateString = sdf.format(dd);

                    date2= dateString;
                    dt2.setText(dateString);
                    finalDetailalertDialog.dismiss();

                    if(idm>0) {
                        AfficheTest(idm, spi);
                    }
                }
            });





        } catch (Exception e) {
            e.toString();
        }


    }

}
