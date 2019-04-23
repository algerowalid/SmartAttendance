package com.example.admin.synchronisation_tstart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.example.admin.synchronisation_tstart.synch.contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {




    public String table_contact= "contact";

    public static final  String TABLE_ETUDIANT= "etudiant";
    public static final  String TABLE_PROGRAMME= "programme";
    public static final  String TABLE_MODULE= "module";
    public static final String TABLE_SEANCE= "seance";
    public static final String TABLE_RPS= "rps";




    public static final String KEY_ID= "id";
    public static final String KEY_NOM= "nom";
    public static final String KEY_PRENOM= "prenom";
    public static final String KEY_TELEPHONE= "telephone";
    public static final String KEY_EMAIL= "email";
    public static final String KEY_ID_NFC= "id_nfc";
    public static final String KEY_TYPE_GROUPE= "type_groupe";
    public static final String KEY_FLAG_SUPP= "flag_supp";

    public static final String KEY_DESIGNATION= "designation";
    public static final String KEY_INFORMATION= "information";
    public static final String KEY_DATE= "date";
    public static final String KEY_HEURE_D= "heured";
    public static final String KEY_HEURE_F= "heuref";
    public static final String KEY_IDP= "idp";
    public static final String KEY_IDM= "idm";
    public static final String KEY_IDS= "ids";
    public static final String KEY_IDE= "ide";
    public static final String KEY_HEURE_E= "heuree";



    ///////////////////////////////////////////////////////// les creations de table

    // Bon

    public static final String CREATE_TABLE_ETUDIANT= "CREATE TABLE IF NOT EXISTS " + TABLE_ETUDIANT+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_NOM+ " TEXT," +
            " " + KEY_PRENOM+ " TEXT ," +
            " " + KEY_TELEPHONE+ " TEXT DEFAULT ''," +
            " " + KEY_EMAIL+ " TEXT DEFAULT '', " +
            " " + KEY_ID_NFC+ " TEXT DEFAULT '', " +
            " " + KEY_TYPE_GROUPE+ " INTEGER DEFAULT 1 , " +
            " " + KEY_FLAG_SUPP+ " integer DEFAULT 0 " +
            ");";



    public static final String CREATE_TABLE_MODULE= "CREATE TABLE IF NOT EXISTS " + TABLE_MODULE+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_DESIGNATION+ " TEXT," +
            " " + KEY_INFORMATION+ " TEXT DEFAULT '' " +
            ");";




    public static final String CREATE_TABLE_PROGRAMME= "CREATE TABLE IF NOT EXISTS " + TABLE_PROGRAMME+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_DESIGNATION+ " TEXT," +
            " " + KEY_IDM + " integer ," +
            " " + KEY_HEURE_D+ " TEXT," +
            " " + KEY_HEURE_F+ " TEXT," +
            " " + KEY_TYPE_GROUPE+ " INTEGER" +
            ");";






    public static final String CREATE_TABLE_SEANCE= "CREATE TABLE IF NOT EXISTS " + TABLE_SEANCE+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_IDP+ " integer," +
            " " + KEY_DATE+ " TEXT ," +
            " " + KEY_HEURE_D+ " TEXT DEFAULT ''," +
            " " + KEY_HEURE_F+ " TEXT DEFAULT '', " +
            " " + KEY_DESIGNATION+ " TEXT DEFAULT ''" +
            ");";



    public static final String CREATE_TABLE_RPS= "CREATE TABLE IF NOT EXISTS " + TABLE_RPS+
            " (" +
            " " + KEY_ID + " integer primary key autoincrement," +
            " " + KEY_IDS + " integer ," +
            " " + KEY_IDE + " integer ," +
            " " + KEY_HEURE_E+ " TEXT" +
            ");";





    //Fin Bon





    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;


    /**
     * ************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     * **************************************************************************************************************************************************************
     */


    // Constructeur
    public DatabaseManager(Context context) {
        maBaseSQLite = new MySQLite(context);
    }

    /**
     * **************************** Ouverture et fermeture de la base de donnÃ©es ****************************************************************
     */
    public void open() {
        //on ouvre la table en lecture/-Ã©criture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accÃ¨s Ã  la BDD
        db.close();
    }

    /************************************************************ Les Methodes **********************************************************/


    public void execu(String sql) {
            db.execSQL(sql);
    }

    // Get
    public Cursor getCurs(String sql) {
    // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery(sql, null);
    }


    public String getString(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        String info="";

        if (at.moveToFirst()) {
            do {
                info= at.getString(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }



    public  int getInt(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        int info=0;

        if (at.moveToFirst()) {
            do {
                info= at.getInt(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }


    public  double getDouble(String sql, String column){

        Cursor at = db.rawQuery(sql, null);
        double info=0;

        if (at.moveToFirst()) {
            do {
                info= at.getDouble(at.getColumnIndex(column));
            } while (at.moveToNext());
            at.close();
        }
        return info;
    }




    public List<String> FillSpinner(Context context, String table,Spinner spinner){
        List<String> labels = new ArrayList<String>();

        // Select All Query

        String selectQuery="";

            selectQuery = "SELECT  * FROM " + table;

            Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getInt(0)+"."+cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        //db.close();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labels);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // returning lables
        return labels;
    }

    public int GetCount(String table){

        String re = "select count(id) as ct from "+table;
        open();
        int nee = getInt(re,"ct");
        close();


    return nee;}

    /**
     * ************************ temporaire *************************************
     */


    public long addRPS( int ids ,int ide ) {
        // Ajout d'un enregistrement dans la table
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_IDS, ids);
        values.put(KEY_IDE, ide);
        values.put(KEY_HEURE_E, "");
        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        result = db.insert(TABLE_RPS, null, values);
        return result;
    }


    public long addSeance( int idp , String dt , String hd, String hf , String des) {
        // Ajout d'un enregistrement dans la table
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DESIGNATION, des);
        values.put(KEY_HEURE_D, hd);
        values.put(KEY_HEURE_F, hf);
        values.put(KEY_IDP, idp);
        values.put(KEY_DATE, dt);
        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_SEANCE, null, values);
        return result;
    }



    public long addProgramme( int idm, String des, String hd, String hf, int idt) {
        // Ajout d'un enregistrement dans la table
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DESIGNATION, des);
        values.put(KEY_HEURE_D, hd);
        values.put(KEY_HEURE_F, hf);
        values.put(KEY_IDM, idm);
        values.put(KEY_TYPE_GROUPE, idt);
        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_PROGRAMME, null, values);
        return result;
    }

    public long addModule( String des, String info) {
        // Ajout d'un enregistrement dans la table
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_DESIGNATION, des);
        values.put(KEY_INFORMATION, info);
        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_MODULE, null, values);
        return result;
    }


    public long addEtudiant( String nom, String pren ,  String phone, String email , String idnfc, int typeg) {
        // Ajout d'un enregistrement dans la table
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, nom);
        values.put(KEY_PRENOM, pren);
        values.put(KEY_TELEPHONE, phone);
        values.put(KEY_ID_NFC, idnfc);
        values.put(KEY_EMAIL, email);
        values.put(KEY_TYPE_GROUPE, typeg);
        // insert() retourne l'id du nouvel enregistrement insÃ©rÃ©, ou -1 en cas d'erreur
        result = db.insert(TABLE_ETUDIANT, null, values);
        return result;
    }





    // modifier  Gestion_client
    public int modEtudiant(int id, String nom, String pren ,  String phone, String email , int typeg) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la requÃªte

        int ap = 0;
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, nom);
        values.put(KEY_PRENOM, pren);
        values.put(KEY_TELEPHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_TYPE_GROUPE,typeg);

        String where = KEY_ID + "=" + id;
        ap = db.update(TABLE_ETUDIANT, values, where, null);

        return ap;
    }


    // Get all DETAIL
    public Cursor getAllEtudiant() {
        // sÃ©lection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM " + TABLE_ETUDIANT, null);
    }


    public int SuppEtudiant(int id) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectÃ©es par la clause WHERE, 0 sinon

        int a = 0;
        String where = KEY_ID + "=" + id;
        a = db.delete(TABLE_ETUDIANT, where, null);

        return a;
    }





}