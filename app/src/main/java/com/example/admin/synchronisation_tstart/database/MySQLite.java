package com.example.admin.synchronisation_tstart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by USER on 21/09/2015.
 */
public class MySQLite extends SQLiteOpenHelper {



    private static final String DATABASE_NAME = "tstart.sqlite";
    private static final int DATABASE_VERSION =1;

    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Création de la base de données
        // on exêcute ici les requetes de cr?ation des tables
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_ETUDIANT); // création table "employe"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_MODULE); // création table "WILAYA"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_PROGRAMME); // création table "WILAYA"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_SEANCE); // création table "WILAYA"
        sqLiteDatabase.execSQL(DatabaseManager.CREATE_TABLE_RPS); // création table "WILAYA"

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Mise ? jour de la base de donn?es
        // m?thode appel?e sur incr?mentation de DATABASE_VERSION
        // on peut faire ce qu'on veut ici, comme recr?er la base :
        onCreate(sqLiteDatabase);
    }
}
