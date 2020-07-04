package com.example.logg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseFact extends SQLiteOpenHelper {
    public static final String DataBase_name ="invv.sqlite";
    public static final String Table_name_F="factorries";
    public static final String col_1="entreprise";
    public static final String col_2="email";
    public static final String col_3="phone";
    public static final String col_4="address";


    public DataBaseFact(@Nullable Context context) {
        super(context,DataBase_name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table_name_F+"("+
                col_1 +" varchar(20) PRIMARY KEY ,"+
                col_2 +" varchar ," +
                col_3 +" varchar(10)," +
                col_4 +" varchar )" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_F);
        onCreate(db);
    }
}
