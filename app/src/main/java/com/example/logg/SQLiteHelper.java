package com.example.logg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "UserDataBase";

    public static final String TABLE_NAME = "JserTable";

    public static final String Table_Column_ID = "id";

    public static final String Table_Column_1_Name = "nom";

    public static final String Table_Column_2_Prenom = "prenom";

    public static final String Table_Column_3_Password = "password";

    public static final String Table_Column_4_username = "username";

    public static final String Table_Column_5_job = "job";

    public static final String KEY_IMG = "image";


    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, 4);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY, " + Table_Column_1_Name + " VARCHAR, " + Table_Column_2_Prenom + " VARCHAR, " + Table_Column_3_Password + " VARCHAR  ," + Table_Column_4_username + " VARCHAR ," + Table_Column_5_job + " VARCHAR ," + KEY_IMG + " blob)";
        database.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    public String getName() throws SQLException {
        String name = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_1_Name},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return name;
    }

    public String getUser() throws SQLException {
        String user = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_4_username},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                user = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return user;
    }

    public String getSurname() throws SQLException {
        String prenom = "";
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_2_Prenom},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                prenom = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return prenom;
    }

    public int getId() throws SQLException {
        int id = 0;
        Cursor cursor = this.getReadableDatabase().query(
                TABLE_NAME, new String[]{Table_Column_ID},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return id;
    }

    public boolean updateProfile(String id, String nom, String prenom, String password, String username, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table_Column_ID, id);
        values.put(Table_Column_1_Name, nom);
        values.put(Table_Column_2_Prenom, prenom);
        values.put(Table_Column_3_Password, password);
        values.put(Table_Column_4_username, username);
        values.put(KEY_IMG, image);
        db.update(TABLE_NAME, values, Table_Column_ID + "=?", new String[]{id});
        db.close();
        return true;
    }

    public boolean updateProfile1(String id, String nom, String prenom, String password, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table_Column_ID, id);
        values.put(Table_Column_1_Name, nom);
        values.put(Table_Column_2_Prenom, prenom);
        values.put(Table_Column_3_Password, password);
        values.put(Table_Column_4_username, username);
        db.update(TABLE_NAME, values, Table_Column_ID + "=?", new String[]{id});
        db.close();
        return true;
    }
    SQLiteDatabase sqLiteDatabaseObj;
    SQLiteHelper sqLiteHelper;

    public User getUserByID(String UsernameHolder) {

        Cursor cursor;

        sqLiteDatabaseObj = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabaseObj.query(SQLiteHelper.TABLE_NAME, null, " " + SQLiteHelper.Table_Column_4_username + "=?", new String[]{UsernameHolder}, null, null, null);
        String TempPassword = "", id = "", Usr = "", Nom = "", Prenom = "", Job = "", password = "";
        while (cursor.moveToNext()) {


            if (cursor.isFirst()) {

                cursor.moveToFirst();




                Usr = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_4_username));

                id = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_ID));

                Nom = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                Prenom = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_Prenom));

                Job = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_5_job));

                password = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password));


                cursor.close();


            }


        }
        User usr = new User(id, Nom, Prenom, password, Usr, Job);
         return usr;
    }
}