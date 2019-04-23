package com.example.admin.synchronisation_tstart.synch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.MainActivity;
import com.example.admin.synchronisation_tstart.R;
import com.example.admin.synchronisation_tstart.database.DatabaseManager;
import com.example.admin.synchronisation_tstart.synch.Constantes;
import com.example.admin.synchronisation_tstart.synch.contact;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;


public class Maj_Distant extends AsyncTask<String, Void, ArrayList<Vector>> {


    public ArrayList<Vector> resultat = new ArrayList<>();
    public ProgressDialog progressDialog ;

    public ArrayList<contact> myListB  = new ArrayList<contact>();

    private Context context;
    int  TYPE;
    public DatabaseManager db;

    public int IDVV;

    public Maj_Distant(Context context ,DatabaseManager data) {
        this.context = context;
        this.db= data;
        progressDialog = new ProgressDialog((Activity)context);
    }
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = progressDialog.show(context,"Envoi en cours","Veuillez patienter svp", false, false);
    }

    public ArrayList<Vector> doInBackground(String... urls) {

            String linkw = "";
            String data = "";


            db.open();
            myListB = MainActivity.ListContact(db);


            for (int i = 0; i < myListB.size(); i++) {
                try {
                    linkw = Constantes.Link+ "tstart_update.php";
                    data = URLEncoder.encode("action", "UTF-8") + "=" +
                            URLEncoder.encode("add_contact", "UTF-8");
                    data += "&" + URLEncoder.encode("nom", "UTF-8") + "=" +
                            URLEncoder.encode(myListB.get(i).getNom(), "UTF-8");
                    data += "&" + URLEncoder.encode("prenom", "UTF-8") + "=" +
                            URLEncoder.encode(myListB.get(i).getPrenom()+ "", "UTF-8");
                    data += "&" + URLEncoder.encode("telephone", "UTF-8") + "=" +
                            URLEncoder.encode(myListB.get(i).getTelephone()+ "", "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                            URLEncoder.encode(myListB.get(i).getEmail()+ "", "UTF-8");



                    URL url = new URL(linkw);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();

                    sb = new StringBuilder();
                    String  line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }


                    JSONArray jArray = null;
                    int k = 0;
                    try {
                        jArray = new JSONArray(sb.toString());
                        k = jArray.length();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } catch(Exception e){

                    Log.i("Lien error: ", e.getMessage().toString());

                }


            }







        return resultat;



    }

    protected void onPostExecute(ArrayList<Vector> result) {
        progressDialog.dismiss();


    }
}
