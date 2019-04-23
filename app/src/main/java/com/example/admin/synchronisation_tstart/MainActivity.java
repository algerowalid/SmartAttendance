package com.example.admin.synchronisation_tstart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.database.DatabaseManager;
import com.example.admin.synchronisation_tstart.synch.Maj_Distant;
import com.example.admin.synchronisation_tstart.synch.MyConnexion;
import com.example.admin.synchronisation_tstart.synch.Wilaya;
import com.example.admin.synchronisation_tstart.synch.contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    Context context = MainActivity.this;

    TextView ne , ns;



    public DatabaseManager database = new DatabaseManager(this);
    public android.app.AlertDialog DetailalertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        ne = (TextView) findViewById(R.id.netudiant);
        ns = (TextView) findViewById(R.id.nseance);


        /*
        String re = "delete from seance ";
        database.open();
        database.execu(re);
        re = "delete from rps ";
        database.execu(re);
        database.close();
*/
        int ae = database.GetCount("etudiant");
    int as = database.GetCount("seance");

        ne.setText(""+ae);
        ns.setText(""+as);

    }




    public void AfficheModule(){

        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_module, null); //tab_popup_layout

            ImageView annulerDialog = (ImageView) view.findViewById(R.id.fermer);
            Button validerDialog = (Button) view.findViewById(R.id.valider);
            final EditText des= (EditText) view.findViewById(R.id.designation);
            final EditText information = (EditText) view.findViewById(R.id.information);

            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);




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

                    if(des.getText().toString().trim().length()>0 ){

                        String de = des.getText().toString().replace("'","''");
                        String info ="";

                        if(information.getText().toString().trim().length()>0 ){
                            info =information.getText().toString().replace("'","''");
                        }


                        database.open();
                        database.addModule(de,info);
                        database.close();
                        finalDetailalertDialog.dismiss();
                    }
                }
            });





        } catch (Exception e) {
            e.toString();
        }
    }





    public void statistique(View tr){
        Intent launchactivity = new Intent(context, Statistiques.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();


    }







    public void seance(View tr){
            popup_init(context,DetailalertDialog);
    }



    public  void popup_init(final Context context, android.app.AlertDialog DetailalertDialog){


        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_initialize, null); //tab_popup_layout

            ImageView annulerDialog = (ImageView) view.findViewById(R.id.fermer);
            Button validerDialog = (Button) view.findViewById(R.id.valider);
            final EditText msg = (EditText) view.findViewById(R.id.designation);
            final Spinner spinner= (Spinner) view.findViewById(R.id.spin);

            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);



            database.open();
            List<String> malist = database.FillSpinner(context,"programme",spinner);
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

                    if(msg.getText().toString().trim().length()>0 ){

                        String des = msg.getText().toString().replace("'","''");

                        SimpleDateFormat sdt = new SimpleDateFormat("HH:mm");
                        String currentTime = sdt.format(new Date());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateandTime = sdf.format(new Date());


                        String md = spinner.getSelectedItem().toString();
                        String[] ab = md.split(Pattern.quote("."));
                        int idp = Integer.parseInt(ab[0]);


                        database.open();
                        long idcr = database.addSeance(idp,currentDateandTime,currentTime,"",des);
                        database.close();

                        int idc = (int) idcr;

                        finalDetailalertDialog.dismiss();

                        Intent launchactivity = new Intent(context, Attendance_seance.class);
                        launchactivity.putExtra("ids",idc);
                        context.startActivity(launchactivity);



                    }
                }
            });





        } catch (Exception e) {
            e.toString();
        }

    }


    public void lister(View tr){

        try {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

            View view = ((Activity)context).getLayoutInflater().inflate(R.layout.pop_choice, null); //tab_popup_layout

            TextView annulerDialog = (TextView) view.findViewById(R.id.fermer);
            Button moduleDialog = (Button) view.findViewById(R.id.module);
            Button programmeDialog = (Button) view.findViewById(R.id.programme);
            Button inscritDialog = (Button) view.findViewById(R.id.inscrits);
            builder.setView(view);
            DetailalertDialog = builder.create();

            DetailalertDialog.show();
            DetailalertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            DetailalertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            DetailalertDialog.setCanceledOnTouchOutside(false);




            final android.app.AlertDialog finalDetailalertDialog = DetailalertDialog;





            annulerDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    finalDetailalertDialog.dismiss();

                }
            });

            moduleDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    finalDetailalertDialog.dismiss();
                    AfficheModule();

                }
            });


            inscritDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    Intent launchactivity = new Intent(context, Gestion_contacts.class);
                    context.startActivity(launchactivity);
                    ((Activity)context).finish();


                }
            });

            programmeDialog.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent launchactivity = new Intent(context, Gestion_programme.class);
                    context.startActivity(launchactivity);
                    ((Activity)context).finish();

                }
            });


        } catch (Exception e) {
            e.toString();
        }

    }



    // Ancienne interface


    public void afficher(View tr){
        String re = "select count(id) as id from wilaya";
        database.open();
        int a = database.getInt(re,"id");
        database.close();
        Toast.makeText(context,"Total wilaya:"+a,Toast.LENGTH_LONG).show();


    }


    public void download(View tr){
        boolean k = MyConnexion.isOnlinePing();

        if(k==true) {

            new Wilaya(this,database).execute();
        }else{
            Toast.makeText(context, "Veuillez vérifier  votre connexion internet ", Toast.LENGTH_LONG).show();
        }

    }



    public void uploadt(View tr){
        boolean k = MyConnexion.isOnlinePing();

        if(k==true) {

            new Maj_Distant(this,database).execute();
        }else{
            Toast.makeText(context, "Veuillez vérifier  votre connexion internet ", Toast.LENGTH_LONG).show();
        }

    }


    public static ArrayList<contact> ListContact(DatabaseManager db){
        ArrayList<contact> myList = new ArrayList<contact>();
        myList.clear();
        String req = "select * from contact";

        // Select All Query

        db.open();
        Cursor cursor = db.getCurs(req);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nm= cursor.getString(1);
                String pr= cursor.getString(2);
                String te= cursor.getString(3);
                String em= cursor.getString(4);

                contact vend = new contact(id,nm,pr,te,em);
                myList.add(vend);


            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();

        db.close();


        return myList;}


    public void fermer(View tr){
        Intent launchactivity = new Intent(context, Login.class);
        context.startActivity(launchactivity);
        ((Activity)context).finish();

    }

}
