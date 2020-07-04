package com.example.logg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHalper extends SQLiteOpenHelper {
    public static final String DataBase_name="Registre.db";
    public static final String Table_name="employe";
    public static final String col_1="ID";
    public static final String col_2="Nom";
    public static final String col_3="Prenom";
    public static final String col_4="UserName";
    public static final String col_5="password";
    public static final String col_6="job";



    public DatabaseHalper(@Nullable Context context) {

        super(context,DataBase_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table_name+"(ID INTEGER PRIMARY KEY ,Nom varchar(10),Prenom varchar(10),UserName varchar(10) UNIQUE,password varchar(10) UNIQUE,job varchar(15))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(db);

    }
}
