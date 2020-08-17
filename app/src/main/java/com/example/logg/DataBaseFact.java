package com.example.logg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.Date;

public class DataBaseFact extends SQLiteOpenHelper {
    public static final String DataBase_name ="invv.sqlite";
    public static final String Table_name_F="factorries";
    public static final String col_1="entreprise";
    public static final String col_2="email";
    public static final String col_3="phone";
    public static final String col_4="address";
    public static final String  col_5="Site";
     public static final String col_6="Nom";
     public static final String col_7="Prenom";
     public static final String col_8="job";
     public static final String col_9="phone_num";
     public static final String col_11="type";
    public static final String col_10="immagee";




    public DataBaseFact(@Nullable Context context) {
        super(context,DataBase_name, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {





    }
    public void QueryData(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("CREATE TABLE  "+Table_name_F+"("+
                col_1 +" varchar(20) PRIMARY KEY ,"+
                col_2 +" varchar ," +
                col_3 +" varchar(10)," +
                col_4 +" varchar ," +col_5+" varchar ," +
                col_6 +" varchar ," +col_7+" varchar ," +
                col_8+" varchar ," +col_9+" varchar ," +
                col_10+" BLOG, "+
                col_11+" type "
                +")");
    }


    public void InsertDataFirst(String entreprise, String email,String phone, String address,String Site,String Nom,String Prenom,String job,String phone_num,byte[] image,String type){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO "+Table_name_F+" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,entreprise);
        statement.bindString(2,email);
        statement.bindString(3,phone);
        statement.bindString(4,address);
        statement.bindString(5,Site);
        statement.bindString(6,Nom);
        statement.bindString(7,Prenom);
        statement.bindString(8,job);
        statement.bindString(9,phone_num);
        statement.bindBlob(10,image);
        statement.bindString(11,type);


        statement.executeInsert();


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name_F);
        db.execSQL("CREATE TABLE  "+Table_name_F+"("+
                col_1 +" varchar(20) PRIMARY KEY ,"+
                col_2 +" varchar ," +
                col_3 +" varchar(10)," +
                col_4 +" varchar ," +col_5+" varchar ," +
                col_6 +" varchar ," +col_7+" varchar ," +
                col_8+" varchar ," +col_9+" varchar ," +
                col_10+" BLOG, "+
                col_11+" type "
                +")");
    }







    //////////////////////////////////////////////////////////////////////////////////////
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }
    public void Delete(String clause ,String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table_name_F,clause,args);
    }
    public void Update(ContentValues values, String clause, String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.update(Table_name_F,values,clause,args);
    }
    public Cursor getData(String sql){
        SQLiteDatabase db= getReadableDatabase();
        return db.rawQuery(sql,null);
    }


}
