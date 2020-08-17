package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.logg.EditProfile.IdH;


public class RegisterActivity extends AppCompatActivity  {

    EditText id, Password, Name ,Prenom,Username ;
    Button Register;
    TextView login;String temp = "";
    String NameHolder, PasswordHolder,PrenomHolder,UsernameHolder,IdHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj,sqLiteDatabaseObjt;
    String SQLiteDataBaseQueryHolder ;
    DataBaseM db;
    Cursor cursor;Cursor cur;
    RadioGroup grb;
    String F_Result = "Not_Found";
    boolean isNameValid,isPasswordValid,isPrenomValid,isUserValid,isIdValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        login=(TextView) findViewById(R.id.register_caption);
        Register = (Button)findViewById(R.id.fb);
        Username= (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        Name = (EditText)findViewById(R.id.nom);
        Prenom= (EditText)findViewById(R.id.prenom);
        id= (EditText)findViewById(R.id.hhh);


        db = new DataBaseM(this);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=SetValidation();
                if (a==1)
                {
                    db.QueryData();
                    CheckEditTextStatus();
                    CheckFinalResult();
                    EmptyEditTextAfterDataInsert();}



            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);



            }
        });


    }


    public int SetValidation() {

        int b=0;

        if (Name.getText().toString().isEmpty()) {
            Name.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } if (Name.getText().length() < 3) {
            Name.setError(getResources().getString(R.string.error_invalid_nom));
            isNameValid = false;
        }else  {
            isNameValid = true;
        }
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(Name.getText().toString());
        boolean bs = ms.matches();
        if (bs == false) {Name.setError(getResources().getString(R.string.letters));
            isNameValid = false;
        }


        if (id.getText().toString().isEmpty()) {
            id.setError(getResources().getString(R.string.id_error));
            isIdValid = false;
        }else {try {
            int number = Integer.parseInt(id.getText().toString());
            isIdValid=true;
        } catch(NumberFormatException e) {
            isIdValid=false;
            id.setError(getResources().getString(R.string.error_invalid_id));
        }}



        if (Prenom.getText().toString().isEmpty()) {
            Prenom.setError(getResources().getString(R.string.prenom_error));
            isPrenomValid = false;
        } else if (Prenom.getText().length() < 3) {
            Prenom.setError(getResources().getString(R.string.error_invalid_prenom));
            isPrenomValid = false;
        }else  {
            isPrenomValid = true;
        }
        Pattern ps1 = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms1 = ps1.matcher(Prenom.getText().toString());
        boolean bl = ms1.matches();
        if (bl == false) {Prenom.setError(getResources().getString(R.string.letters));
            isPrenomValid = false;
        }

        if (Username.getText().toString().isEmpty()) {
            Username.setError(getResources().getString(R.string.error_user));
            isUserValid = false;
        } else if (Username.getText().length() < 3) {
            Username.setError(getResources().getString(R.string.error_invalid_user));
            isUserValid = false;
        }else  {
            isUserValid = true;
        }


        // Check for a valid password.
        if (Password.getText().toString().isEmpty()) {
            Password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (Password.getText().length() < 6) {
            Password.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isNameValid && isUserValid && isPrenomValid && isPasswordValid && isIdValid) {
            return b=1;
        }
        return (b);
    }

    /*public void SQLiteDataBaseBuild(){

        sqLiteDatabaseObj = openOrCreateDatabase(DataBaseM.DATABASE_NAME, Context.MODE_PRIVATE, null);

    }
    public void SQLiteTableBuild() {

        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + DataBaseM.TABLE_NAME + "(" + DataBaseM.Table_Column_ID + " INTEGER PRIMARY KEY, " + DataBaseM.Table_Column_1_Name + " VARCHAR, " + DataBaseM.Table_Column_2_Prenom + " VARCHAR, " + DataBaseM.Table_Column_3_Password + " VARCHAR, " + DataBaseM.Table_Column_4_username + " VARCHAR," + DataBaseM.Table_Column_5_job + " VARCHAR,"+DataBaseM.KEY_IMG+"blob);");

    }
*/

    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+DataBaseM.TABLE_NAME+" (id,nom,prenom,password,username,image) VALUES( '"+IdHolder+"','"+NameHolder+"', '"+PrenomHolder+"', '"+PasswordHolder+"', '"+UsernameHolder+"','"+temp+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();
            Toast.makeText(RegisterActivity.this,"sign up réussi !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(RegisterActivity.this,"SVP ,remplissez toutes les cases !", Toast.LENGTH_LONG).show();

        }

    }
    public void EmptyEditTextAfterDataInsert(){

        Name.getText().clear();

        Prenom.getText().clear();

        id.getText().clear();

        Username.getText().clear();

        Password.getText().clear();

    }

    public void CheckEditTextStatus() throws SQLException {
        NameHolder = Name.getText().toString() ;
        PrenomHolder = Prenom.getText().toString();
        PasswordHolder = Password.getText().toString();
        IdHolder = id.getText().toString();
        UsernameHolder = Username.getText().toString();




        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(IdHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(UsernameHolder)|| TextUtils.isEmpty(PrenomHolder )){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }
    public String CheckingUsernameAlreadyExistsOrNot(){
        sqLiteDatabaseObjt = db.getWritableDatabase();

        cur = sqLiteDatabaseObjt.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_4_username + "=?", new String[]{UsernameHolder}, null, null, null);

        while (cur.moveToNext()) {

            if (cur.isFirst()) {

                cur.moveToFirst();
                F_Result = "Username already exists";


            }
        }
        return F_Result;

    }

    public String CheckingidAlreadyExistsOrNot(){
        sqLiteDatabaseObj = db.getWritableDatabase();
        cursor = sqLiteDatabaseObj.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_ID + "=?", new String[]{IdHolder}, null, null, null);

        while (cursor.moveToNext()) {

            if (cursor.isFirst()) {

                cursor.moveToFirst();
                F_Result = "id already exists";

            }
        }
        return F_Result;

    }

    public void CheckFinalResult(){
        String f1=CheckingidAlreadyExistsOrNot();
        String f2=CheckingUsernameAlreadyExistsOrNot();
        if(f1.equalsIgnoreCase("id already exists"))
        {

            Toast.makeText(RegisterActivity.this,f1,Toast.LENGTH_LONG).show();

        }
        else if(f2.equalsIgnoreCase("Username already exists"))
        {

            Toast.makeText(RegisterActivity.this,f2,Toast.LENGTH_LONG).show();

        }
        else if ((f2.equalsIgnoreCase("Username already exists"))&&(f1.equalsIgnoreCase("id already exists")))

        {

            Toast.makeText(RegisterActivity.this,"Username and id already exist",Toast.LENGTH_LONG).show();

        }
        else {

            InsertDataIntoSQLiteDatabase();

        }

        F_Result = "Not_Found" ;

    }

}