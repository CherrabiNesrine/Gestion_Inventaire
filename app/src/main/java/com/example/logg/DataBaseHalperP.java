package com.example.logg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.util.Date;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.logg.DataBaseFact.Table_name_F;

public class DataBaseHalperP extends SQLiteOpenHelper {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public static final String DataBase_name ="ProductSs.sqlite";
    public static final String Table_name="prod";


    public DataBaseHalperP(@Nullable Context context) {
        super(context,DataBase_name, null, 7);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public void QueryData(){
        SQLiteDatabase db  = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name+"(ID varchar(13)PRIMARY KEY,Nom varchar(20),entreprise varchar(20),dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,prix number,discription text, image BLOG, FOREIGN KEY(entreprise) REFERENCES "+Table_name_F+"("+DataBaseFact.col_1+"))");
    }
    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);

    }
    public void Delete(String clause ,String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table_name,clause,args);
    }
    public void Update(ContentValues values,String clause,String[] args){
        SQLiteDatabase db = getWritableDatabase();
        db.update(Table_name,values,clause,args);
    }
 public void InsertDate(String id, String Nom, String entreprise, Date dateF,Date datE, String Categorie, String sousCategorie, String matiere, int quantite, double prix, String discription, byte[] image){
        SQLiteDatabase db= getWritableDatabase();
        String sql="INSERT INTO prod VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement= db.compileStatement(sql);
     statement.clearBindings();
     statement.bindString(1,id);
      statement.bindString(2,Nom);
     statement.bindString(3,entreprise);
     statement.bindString(4,sdf.format(dateF));
     statement.bindString(5,sdf.format(datE));
     statement.bindString(6,Categorie);
     statement.bindString(7,sousCategorie);
     statement.bindString(8,matiere);
     statement.bindLong(9,quantite);
     statement.bindDouble(10,prix);
     statement.bindString(11,discription);
     statement.bindBlob(12,image);

     statement.executeInsert();


 }
 public Cursor getData(String sql){
        SQLiteDatabase db= getReadableDatabase();
        return db.rawQuery(sql,null);
 }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_name+"(ID varchar(13)PRIMARY KEY,Nom varchar(20),entreprise varchar(20),dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,prix number,discription text, image BLOG, FOREIGN KEY(entreprise) REFERENCES "+Table_name_F+"("+DataBaseFact.col_1+"))");

    }
    /*
     public static final String Table_name="prdct";
    public static final String col_1="ID";
    public static final String col_2="Nom";
    public static final String col_3="entreprise";
    public static final String col_4="dateF";
    public static final String col_5="datE";
    public static final String col_6="Categorie";
    public static final String col_7="sousCategorie";
    public static final String col_8="matiere";
    public static final String col_9="quantite";
    public static final String col_10="mesure";
    public static final String col_11="prix";
    public static final String col_12="coins";
    public static final String col_13="discription";


    public DataBaseHalperP(@Nullable Context context) {
        super(context,DataBase_name, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Table_name+"(ID varchar(13),Nom varchar(20),entreprise varchar(20),dateF Date ,datE Date , categorie varchar,sousCategorie varchar,matiere varchar,quantite number,mesure varchar,prix number,coins varchar,discription text , FOREIGN KEY("+col_3 +") REFERENCES "+Table_name_F+"("+DataBaseFact.col_1+")) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(db);
    }*/
}
