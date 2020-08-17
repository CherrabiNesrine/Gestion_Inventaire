package com.example.logg;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.appcompat.app.ActionBar;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.logg.LoginActivity.Id;


public class EditProfile extends SidebarMenu {

    SQLiteDatabase DB;
    EditText id, password, Name, prenom, Username;boolean isNameValid,isPasswordValid,isPrenomValid,isUserValid,isIdValid;
    Button Register,Cancel;
    static String NameH, PasswordH, PrenomH, UsernameH, IdH;byte[] profileimg=null;
    SQLiteDatabase sqLiteDatabaseObj;
    DataBaseM db;
    ImageButton Img;
    byte[] imagee = null;
    String TempPass = "", ident = "", userr = "", Nm = "", Prenm = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.edit_profile, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");
        Img = (ImageButton) findViewById(R.id.img);
        Register = (Button) findViewById(R.id.buttonSave);
        Cancel=(Button) findViewById(R.id.buttonCancel);
        Username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.Pass);
        Name = (EditText) findViewById(R.id.lastname);
        prenom = (EditText) findViewById(R.id.firstname);
        id = (EditText) findViewById(R.id.ID);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        Cursor cursor;
        db = new DataBaseM(this);
        sqLiteDatabaseObj = db.getReadableDatabase();
        db.QueryData();
        cursor = sqLiteDatabaseObj.query(db.TABLE_NAME, null, " " + db.Table_Column_ID + "=?", new String[]{Id}, null, null, null);

        while (cursor.moveToNext()) {


            if (cursor.isFirst()) {

                cursor.moveToFirst();

                profileimg = cursor.getBlob(cursor.getColumnIndex(db.KEY_IMG));

                userr = cursor.getString(cursor.getColumnIndex(db.Table_Column_4_username));

                ident = cursor.getString(cursor.getColumnIndex(db.Table_Column_ID));

                Nm = cursor.getString(cursor.getColumnIndex(db.Table_Column_1_Name));

                Prenm = cursor.getString(cursor.getColumnIndex(db.Table_Column_2_Prenom));


                TempPass = cursor.getString(cursor.getColumnIndex(db.Table_Column_3_Password));


                cursor.close();


            }
        }
        if(profileimg !=null || intent.hasExtra("image"))
        {Img.setImageBitmap(getImage(profileimg));}

        User us = new User(ident, Nm, Prenm, TempPass,userr);
        Username.setText(us.getUserName());
        Name.setText(us.getName());
        id.setText(us.getId());
        password.setText(us.getPassword());
        prenom.setText(us.getPrenom());



        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, DashboardActivity.class);
                startActivity(intent);
            }
        });


        Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleries(view);

            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable drawable = (BitmapDrawable) Img.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                NameH = Name.getText().toString();
                PrenomH = prenom.getText().toString();
                PasswordH = password.getText().toString();
                IdH = id.getText().toString();
                UsernameH = Username.getText().toString();
                int a = SetValidation();
                if (a == 1) {
                    if (imagee != null) {
                        imagee = getBytes(bitmap);
                        db.updateProfile(IdH, NameH, PrenomH, PasswordH, UsernameH, imagee);
                    } else {
                        db.updateProfile1(IdH, NameH, PrenomH, PasswordH, UsernameH);
                    }
                    Intent intent = new Intent(EditProfile.this, DashboardActivity.class);
                    startActivity(intent);
                }
            }
        });




    }

    public void openGalleries(View view) {

        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);
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


        if (prenom.getText().toString().isEmpty()) {
            prenom.setError(getResources().getString(R.string.prenom_error));
            isPrenomValid = false;
        } else if (prenom.getText().length() < 3) {
            prenom.setError(getResources().getString(R.string.error_invalid_prenom));
            isPrenomValid = false;
        }else  {
            isPrenomValid = true;
        }
        Pattern ps1 = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms1 = ps1.matcher(prenom.getText().toString());
        boolean bl = ms1.matches();
        if (bl == false) {prenom.setError(getResources().getString(R.string.letters));
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
        if (password.getText().toString().isEmpty()) {
            password.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            password.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else  {
            isPasswordValid = true;
        }

        if (isNameValid && isUserValid && isPrenomValid && isPasswordValid ) {
            return b=1;
        }
        return (b);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                Img.setImageBitmap(decodeStream);

                imagee = getBytes(decodeStream);

            } catch (Exception ex) {
                Log.e("ex", ex.getMessage());
            }

        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}

