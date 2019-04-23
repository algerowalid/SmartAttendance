package com.example.admin.synchronisation_tstart.synch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.synchronisation_tstart.database.DatabaseManager;
import com.example.admin.synchronisation_tstart.synch.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Vector;

public class Wilaya extends AsyncTask<String, Void, ArrayList<Vector>> {


    public ArrayList<Vector> resultat = new ArrayList<>();
    public ProgressDialog progressDialog ;


    public DatabaseManager d;
    private Context context;

    public Wilaya(Context context, DatabaseManager data ) {
        this.context = context;
        this.d = data;

        progressDialog = new ProgressDialog((Activity)context);
    }

    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = progressDialog.show(context,"Chargement en cours","Veuillez patiener svp", false, false);
    }

    public ArrayList<Vector> doInBackground(String... urls) {
        try{
            String linkw = Constantes.Link+"/list_wilaya.php";

            URL url = new URL(linkw);
            URLConnection conn = url.openConnection();

            String data="";
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            Vector k = new Vector();
            k.add("");
            resultat.add(k);

            Vector vect = new Vector();
            vect.add(sb.toString());
            resultat.add(vect);





            return resultat;

        } catch(Exception e){

            Log.i("Lien error: ", e.getMessage().toString());
            Vector vect = new Vector();
            vect.add(e.getMessage().toString());
            resultat.add(vect);
            return resultat;
        }

    }

    protected void onPostExecute(ArrayList<Vector> result) {


        if(result.size()>1){

            JSONObject json = null;
            String str = result.get(1).get(0).toString();

            JSONArray jArray = null;
            int k = 0;
            try {
                jArray = new JSONArray(str);
                k = jArray.length();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (k == 0) {
                Toast.makeText(context, "Erreur paramétre", Toast.LENGTH_LONG).show();
            } else {

                d.open();
                for(int i=0;i<k;i++) {

                try {
                    json = jArray.getJSONObject(i);
                    String id1= json.getString(Constantes.wilaya_commune.get(1).toString());
//                    d.addWilaya(id1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }
                d.close();

            }




        }else{
            Toast.makeText(context,"Aucune information",Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();

    }
}
