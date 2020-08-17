package com.example.logg;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logg.DataBaseM;


public class LoginActivity extends AppCompatActivity {
    Button LogInButton;
    TextView  RegisterButton ;
    EditText  Password,user;
    String UsernameHolder, PasswordHolder;
    CheckBox checkBox;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DataBaseM db;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static  String User = "";
    public static String Id  = "";
    public static String Nom  = "";
    public static String Prenom  = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LogInButton = (Button)findViewById(R.id.btnlog);
        RegisterButton = (TextView)findViewById(R.id.sig);
        user = (EditText)findViewById(R.id.eduser);
        Password = (EditText)findViewById(R.id.edpass);
        checkBox =(CheckBox)findViewById(R.id.checkBox);
        db= new DataBaseM(this);

        SharedPreferences preferences=getSharedPreferences("checkBox",MODE_PRIVATE);
        String check=preferences.getString("remember","");
        String userr=preferences.getString("user","");
        String pass=preferences.getString("password","");



        db.QueryData();

         if(check.equals("true")){
             user.setText(userr);
             Password.setText(pass);
             sqLiteDatabaseObj = db.getReadableDatabase();


             cursor = sqLiteDatabaseObj.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_4_username+ "=?", new String[]{userr}, null, null, null);

             while (cursor.moveToNext()) {

                 if (cursor.isFirst()) {

                     cursor.moveToFirst();


                     TempPassword = cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_3_Password));
                     Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                     User=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_4_username));
                     intent.putExtra("user", User);
                     Id=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_ID));
                     intent.putExtra("id", Id);
                     Nom=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_1_Name));
                     intent.putExtra("nom", Nom);
                     Prenom=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_2_Prenom));
                     intent.putExtra("prenom", Prenom);

                     startActivity(intent);

                     cursor.close();


                 }
             }

         }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkBox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","true");
                    editor.putString("user",user.getText().toString());
                    editor.putString("password",Password.getText().toString());
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"checked",Toast.LENGTH_LONG).show();

                }
                else if(!buttonView.isChecked()){
                    SharedPreferences preferences= getSharedPreferences("checkBox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Unchecked",Toast.LENGTH_LONG).show();

                }
            }
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CheckEditTextStatus();


                LoginFunction();


            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }


    public void LoginFunction(){

        if(EditTextEmptyHolder) {
            sqLiteDatabaseObj = db.getReadableDatabase();


            cursor = sqLiteDatabaseObj.query(DataBaseM.TABLE_NAME, null, " " + DataBaseM.Table_Column_4_username+ "=?", new String[]{UsernameHolder}, null, null, null);

            while (cursor.moveToNext()) {

                if (cursor.isFirst()) {

                    cursor.moveToFirst();


                    TempPassword = cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_3_Password));
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                    User=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_4_username));
                    intent.putExtra("user", User);
                    Id=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_ID));
                    intent.putExtra("id", Id);
                    Nom=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_1_Name));
                    intent.putExtra("nom", Nom);
                    Prenom=cursor.getString(cursor.getColumnIndex(DataBaseM.Table_Column_2_Prenom));
                    intent.putExtra("prenom", Prenom);

                    startActivity(intent);

                    cursor.close();


                }
            }


            CheckFinalResult();

        }
        else {

            Toast.makeText(LoginActivity.this,"please fill all the gaps !",Toast.LENGTH_LONG).show();

        }

    }


    public void CheckEditTextStatus(){

        UsernameHolder = user.getText().toString();
        PasswordHolder = Password.getText().toString();

        if( TextUtils.isEmpty(UsernameHolder) || TextUtils.isEmpty(PasswordHolder)){

            EditTextEmptyHolder = false ;

        }
        else {

            EditTextEmptyHolder = true ;
        }
    }

    public void CheckFinalResult(){

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {

            Toast.makeText(LoginActivity.this,"Login successfully !",Toast.LENGTH_LONG).show();

            user.getText().clear();


            Password.getText().clear();


        }
        else {

            Toast.makeText(LoginActivity.this,"your username or your password is wrong ! !.",Toast.LENGTH_LONG).show();

        }
        TempPassword = "NOT_FOUND" ;

    }

}
