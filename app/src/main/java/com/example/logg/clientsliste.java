package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static java.lang.Double.parseDouble;
import static java.lang.Math.floor;

public class clientsliste extends SidebarMenu {
    BottomNavigationView navigationView;
    ArrayList<String> Nom = new ArrayList<>();
    ArrayList<String> Prenom = new ArrayList<>();
    ArrayList<String> Entreprise = new ArrayList<>();
    ArrayList<String> TYPPE = new ArrayList<>();
    ArrayList<byte[]> Images = new ArrayList<>();
    static clientslisteAdp adpter = null;
    ListView listView;
    ImageView imv ;
    TextView empt;
    Button addF;
    private ArrayList<String> selectedStrings;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<String> DelItemN = new ArrayList<>();
    ArrayList<String> DelItemP = new ArrayList<>();
    boolean djaz = false;
    FactoryE Fact= new FactoryE();
    final int REQUEST_CODE_GALLERY = 999;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
            super.onCreate(savedInstanceState);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //inflate your activity layout here!
            View contentView = inflater.inflate(R.layout.activity_clientsliste, null, false);
            drawer.addView(contentView, 0);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Costumers List ");


            listView = (ListView) findViewById(R.id.lstClient);
            selectedStrings = new ArrayList<>();


            adpter = new clientsliste.clientslisteAdp(this, Nom, Prenom, Entreprise,TYPPE, Images);
            listView.setAdapter(adpter);
            listView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
            db.QueryData();

            Cursor cursor = db.getData("SELECT * FROM client");

        if (cursor==null || cursor.getCount()<=0){
            imv =(ImageView)findViewById(R.id.empty);
            empt =(TextView)findViewById(R.id.emptyTxt);
            imv.setVisibility(View.VISIBLE);
            empt.setVisibility(View.VISIBLE);
            empt.setText("no customer found  ");
            listView.setVisibility(View.GONE);
        }
        else {
            while (cursor.moveToNext()) {

                String nom = cursor.getString(2);

                String prenom = cursor.getString(3);

                String typpe = cursor.getString(4);


                Nom.add(nom);
                Prenom.add(prenom);
                TYPPE.add(typpe);

                final Cursor[] cursor1 = {db.getData("select * from OPERATEUR where nomOp = '" + nom + "' and  prenomOp = '" + prenom + "'")};
                while (cursor1[0].moveToNext()) {
                    byte[] image = cursor1[0].getBlob(11);
                    Images.add(image);
                    String entre = cursor1[0].getString(3);
                    Entreprise.add(entre);

                }
                adpter.notifyDataSetChanged();
            }

        }
            listView.setMultiChoiceModeListener(new clientsliste.MultiChoiceModeListener());

            navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
            navigationView.setSelectedItemId(R.id.cli);
            navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    if (menuItem.getItemId() == R.id.fourn) {

                        Intent intent = new Intent(clientsliste.this, factoriesList.class);

                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;
                    }


                    return false;
                }
            });


    }
    public class MultiChoiceModeListener implements
            ListView.MultiChoiceModeListener {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.del, menu);
            mode.setTitle("Select Items");
            mode.setSubtitle("One item selected");
            ActionBar actionBar = getSupportActionBar();

            actionBar.hide();
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode = null;
            ActionBar actionBar = getSupportActionBar();

            actionBar.show();
            return true;
        }

        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_trach: {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(clientsliste.this);
                    final View view = LayoutInflater.from(clientsliste.this).inflate(R.layout.del, null);
                    TextView title = (TextView) view.findViewById(R.id.titledel);
                    TextView message = (TextView) view.findViewById(R.id.messageerdel);
                    Button acc = (Button) view.findViewById(R.id.btn_accc);
                    Button nacc = (Button) view.findViewById(R.id.cancel);
                    title.setText("");
                    message.setText("Do you really want to delete this provider ");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date d = new Date();
                            ContentValues values = new ContentValues();
                            values.put("dateDel", sdf.format(d));
                            for (int i = 0; i < DelItemN.size(); i++) {
                                db.Delete("client", "nomOp=? and prenomOp=?", new String[]{DelItemN.get(i), DelItemP.get(i)});

                            }
                            dialog.dismiss();
                            adpter.notifyDataSetChanged();


                        }
                    });
                    nacc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;


            }
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode = null;
            ActionBar actionBar = getSupportActionBar();

            actionBar.show();

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            int selectCount = listView.getCheckedItemCount();
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            DelItemN.add(Nom.get(position));
            DelItemP.add(Prenom.get(position));

            switch (selectCount) {
                case 1:
                    mode.setSubtitle("One item selected");
                    break;
                default:
                    mode.setSubtitle("" + selectCount + " items selected");
                    break;
            }
        }


    }

    public class CheckableLayout extends FrameLayout implements Checkable {
        private boolean mChecked;

        public CheckableLayout(Context context) {
            super(context);
        }

        @SuppressLint("ResourceAsColor")
        @SuppressWarnings("deprecation")
        public void setChecked(boolean checked) {
            mChecked = checked;
            setBackgroundColor(R.color.required);
        }

        public boolean isChecked() {
            return mChecked;
        }

        public void toggle() {
            setChecked(!mChecked);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Toast.makeText(getApplicationContext(), "New Consumer ", Toast.LENGTH_SHORT).show();

            final AlertDialog.Builder dateAlt = new AlertDialog.Builder(clientsliste.this);
            final View view = LayoutInflater.from(clientsliste.this).inflate(R.layout.activity_factory, null);
            final LinearLayout layy0, layy1, layy2, layy3, layy4, layy5;
            Button nxt1, nxt2, nxt3, nxt4, startad2, startad, bk1, bk2, bk3, bk4;
            final EditText tauxred, oneNom, oneprenom, oneJob, oneAdress, OnTlf, oneEmail, facebook, LinkedIn, Twitter, entreprise, nif, rg, compaddres, comtlf, compEmail, site, fax;
            final AutoCompleteTextView secteur, taille, statujur;
            final TextInputLayout in1, in2, in3, in4, in5, in6, in7, in8, in9, in10,taux;
            final ImageView svr;
            final RadioGroup RGC;
            TextView operaComp,opera;

            svr=(ImageView)view.findViewById(R.id.svr);


            layy0 = (LinearLayout) view.findViewById(R.id.layy0);
            layy1 = (LinearLayout) view.findViewById(R.id.layy1);
            layy2 = (LinearLayout) view.findViewById(R.id.layy2);
            layy3 = (LinearLayout) view.findViewById(R.id.layy3);
            layy4 = (LinearLayout) view.findViewById(R.id.layy4);
            layy5 = (LinearLayout) view.findViewById(R.id.layy5);
            startad = (Button) view.findViewById(R.id.startad);
            startad2 = (Button) view.findViewById(R.id.startad2);
            nxt1 = (Button) view.findViewById(R.id.nxt1);
            nxt2 = (Button) view.findViewById(R.id.nxt2);
            nxt3 = (Button) view.findViewById(R.id.nxt3);
            nxt4 = (Button) view.findViewById(R.id.nxt4);

            final LinearLayout  conss1 =(LinearLayout) view.findViewById(R.id.lil);


            RGC=(RadioGroup)view.findViewById(R.id.rgc);

            bk1 = (Button) view.findViewById(R.id.bk1);
            bk2 = (Button) view.findViewById(R.id.bk2);
            bk3 = (Button) view.findViewById(R.id.bk3);
            bk4 = (Button) view.findViewById(R.id.bk4);

            oneNom = (EditText) view.findViewById(R.id.OneNom);
            oneprenom = (EditText) view.findViewById(R.id.onePrenom);
            oneJob = (EditText) view.findViewById(R.id.oneJob);
            oneAdress = (EditText) view.findViewById(R.id.oneAdress);
            OnTlf = (EditText) view.findViewById(R.id.OnTlf);
            oneEmail = (EditText) view.findViewById(R.id.oneEmail);
            facebook = (EditText) view.findViewById(R.id.facebook);
            LinkedIn = (EditText) view.findViewById(R.id.LinkedIn);
            Twitter = (EditText) view.findViewById(R.id.Twitter);
            entreprise = (EditText) view.findViewById(R.id.entreprise);
            nif = (EditText) view.findViewById(R.id.nif);
            rg = (EditText) view.findViewById(R.id.rg);
            compaddres = (EditText) view.findViewById(R.id.CompAdrs);
            comtlf = (EditText) view.findViewById(R.id.CompTlf);
            compEmail = (EditText) view.findViewById(R.id.compEmail);
            site = (EditText) view.findViewById(R.id.site);
            fax = (EditText) view.findViewById(R.id.Fax);

            secteur = (AutoCompleteTextView) view.findViewById(R.id.secteur);
            taille = (AutoCompleteTextView) view.findViewById(R.id.taille);
            statujur = (AutoCompleteTextView) view.findViewById(R.id.statusjuridique);


            in1 = (TextInputLayout) view.findViewById(R.id.in1);
            in2 = (TextInputLayout) view.findViewById(R.id.in2);
            in3 = (TextInputLayout) view.findViewById(R.id.in3);
            in4 = (TextInputLayout) view.findViewById(R.id.in4);
            in5 = (TextInputLayout) view.findViewById(R.id.in5);
            in6 = (TextInputLayout) view.findViewById(R.id.in6);
            in7 = (TextInputLayout) view.findViewById(R.id.in7);
            in8 = (TextInputLayout) view.findViewById(R.id.in8);
            in9 = (TextInputLayout) view.findViewById(R.id.in9);
            in10 = (TextInputLayout) view.findViewById(R.id.in10);


            dateAlt.setView(view);
            final AlertDialog dialog2 = dateAlt.create();

            dialog2.show();
            Snackbar.make(view, "Every time you meet * means the field can not be blank  ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            opera=(TextView)view.findViewById(R.id.opera);
            operaComp=(TextView)view.findViewById(R.id.operaCom);
            opera.setText("Let's add a new consumer ! ");
            operaComp.setText("Time for consumer's company !");
            oneNom.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (oneNom.getText().toString().trim().equalsIgnoreCase("")) {
                        in1.setError("This field can not be blank");

                    } else {
                        in1.setError(null);
                    }

                }
            });
            oneprenom.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (oneprenom.getText().toString().trim().equalsIgnoreCase("")) {
                        in2.setError("This field can not be blank");

                    } else {
                        in2.setError(null);
                    }

                }
            });
            oneEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (oneEmail.getText().toString().trim().equalsIgnoreCase("")) {
                        in4.setError("This field can not be blank");

                    } else {
                        in4.setError(null);
                    }
                    if (isEmailValid(oneEmail.getText().toString()) == false) {
                        in4.setError("email should be like : exp@exp.exp ");
                    }

                }
            });
            OnTlf.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (OnTlf.getText().toString().length() != 10) {
                        in3.setError("your Number should have 10 numbers  ");

                    } else {
                        in3.setError(null);
                    }

                }
            });

            entreprise.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (entreprise.getText().toString().trim().equalsIgnoreCase("")) {
                        in5.setError("This field can not be blank");

                    } else {
                        in5.setError(null);
                    }

                }
            });
            nif.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (nif.getText().toString().length() != 13) {
                        in6.setError("NIF should have 13 numbers ");

                    } else {
                        in6.setError(null);
                    }

                }
            });
            rg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (rg.getText().toString().length() != 14) {
                        in7.setError("NIF should have 14 numbers ");

                    } else {
                        in7.setError(null);
                    }

                }
            });
            comtlf.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (comtlf.getText().toString().length() != 10) {
                        in9.setError("your Number should have 10 numbers  ");

                    } else {
                        in9.setError(null);
                    }

                }
            });
            compaddres.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (compaddres.getText().toString().isEmpty()) {
                        in8.setError("your Number should have 10 numbers  ");

                    } else {
                        in8.setError(null);
                    }

                }
            });
            compEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isEmailValid(compEmail.getText().toString()) == false) {
                        in10.setError("email should be like : exp@exp.exp");

                    } else {
                        in10.setError(null);
                    }

                }
            });


            startad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {



                    layy0.setVisibility(View.GONE);
                    layy1.setVisibility(View.VISIBLE);
                    conss1.setVisibility(View.VISIBLE);
                }
            });
            nxt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view2) {
                    if (oneNom.getText().toString().isEmpty()) {
                        in1.setError("this field can not be blank ");
                    }

                    if (oneprenom.getText().toString().isEmpty()) {
                        in2.setError("this field can not be blank ");
                    } else if (!oneNom.getText().toString().isEmpty() && !oneprenom.getText().toString().isEmpty()) {

                        layy1.setVisibility(View.GONE);
                        layy2.setVisibility(View.VISIBLE);

                    }

                }
            });
            nxt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (OnTlf.getText().toString().isEmpty()) {
                        in3.setError("this field can not be blank ");
                    } else if (OnTlf.getText().toString().length() != 10) {
                        in3.setError("your Number should have 10 numbers  ");
                    }
                    if (oneEmail.getText().toString().isEmpty()) {
                        in4.setError("this field can not be blank ");
                    } else if (isEmailValid(oneEmail.getText().toString()) == false) {
                        in4.setError("email should be like : exp@exp.exp ");
                    } else {
                        layy2.setVisibility(View.GONE);
                        if (djaz == true) {

                            layy4.setVisibility(View.VISIBLE);
                        } else {
                            layy3.setVisibility(View.VISIBLE);
                            djaz = true;
                        }
                    }

                }
            });
            startad2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    layy3.setVisibility(view.GONE);
                    layy4.setVisibility(View.VISIBLE);
                }
            });
            nxt3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (entreprise.getText().toString().isEmpty()) {
                        in5.setError("this field can not be blank ");
                    } else if (nif.getText().toString().length() != 13) {
                        in5.setError("NIF should have 13 numbers ");
                    }
                    if (rg.getText().toString().isEmpty()) {
                        in6.setError("this field can not be blank ");
                    } else {
                        layy4.setVisibility(View.GONE);
                        layy5.setVisibility(View.VISIBLE);
                    }

                }
            });
            nxt4.setOnClickListener(new View.OnClickListener() { //Button OK
                @Override
                public void onClick(View view) {





                    if (comtlf.getText().toString().isEmpty()) {
                        in9.setError("this field can not be blank ");
                    } else if (comtlf.getText().toString().length() != 10) {
                        in9.setError("your Number should have 10 numbers  ");
                    }
                    if (compEmail.getText().toString().isEmpty()) {
                        in10.setError("this field can not be blank ");
                    } else if (isEmailValid(compEmail.getText().toString()) == false) {
                        in10.setError("email should be like : exp@exp.exp ");
                    } else if (compaddres.getText().toString().isEmpty()) {
                        in8.setError("this field can not be blank ");
                    } else {


                        dialog2.dismiss();

                        Fact.setJob(oneJob.getText().toString());
                        Fact.setAdress(oneAdress.getText().toString());
                        Fact.setFacebook(facebook.getText().toString());
                        Fact.setLinkedIn(LinkedIn.getText().toString());
                        Fact.setTwitter(Twitter.getText().toString());
                        if (oneEmail.getText().toString().isEmpty()) {
                            oneEmail.setError("please it can not be blank");


                        } else {
                            oneEmail.setError(null);
                            Fact.setEmail(oneEmail.getText().toString());


                        }
                        if (OnTlf.getText().toString().isEmpty()) {
                            OnTlf.setError("please it can not be blank");


                        } else {
                            OnTlf.setError(null);
                            Fact.setPhone(OnTlf.getText().toString());
                        }
                        if (oneNom.getText().toString().isEmpty()) {
                            oneNom.setError("please it can not be blank");


                        } else {
                            oneNom.setError(null);
                            Fact.setName(oneNom.getText().toString());

                        }
                        if (oneprenom.getText().toString().isEmpty()) {
                            oneprenom.setError("please it can not be blank");


                        } else {
                            oneprenom.setError(null);
                            Fact.setPrenom(oneprenom.getText().toString());

                        }
                        if (entreprise.getText().toString().isEmpty()) {
                            entreprise.setError("please it can not be blank");
                        } else {
                            entreprise.setError(null);
                            Fact.setNom(entreprise.getText().toString());

                        }
                        if (nif.getText().toString().toString().isEmpty()) {
                            nif.setError("please it can not be blank ");
                        } else if (nif.getText().toString().length() != 13) {
                            nif.setError("NIF must have 13 letters  ");
                        } else {
                            nif.setError(null);
                            Fact.setNif(nif.getText().toString());

                        }
                        if (rg.getText().toString().isEmpty()) {
                            rg.setError("please it can not be blank ");
                        } else {
                            rg.setError(null);
                            Fact.setRg(rg.getText().toString());
                        }
                        if (compaddres.getText().toString().isEmpty()) {
                            compaddres.setError("please you should know the companu address");
                        } else {
                            compaddres.setError(null);
                            Fact.setAddress(compaddres.getText().toString());
                        }
                        if (comtlf.getText().toString().isEmpty()) {
                            comtlf.setError("please you should know the companu address");
                        } else {
                            comtlf.setError(null);
                            Fact.setPhone_num(comtlf.getText().toString());
                        }
                        if (compEmail.getText().toString().isEmpty()) {
                            compEmail.setError("please you should know the companu address");
                        } else {
                            compEmail.setError(null);
                            Fact.setComEmail(compEmail.getText().toString());

                        }
                        RGC.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int i;
                                i = RGC.getCheckedRadioButtonId();
                                RadioButton TYYP = (RadioButton) findViewById(i);
                                Fact.setType(TYYP.getText().toString());


                            }
                        });
                        svr.setImageResource(R.mipmap.vv);
                        Fact.setFactlogo(Imageviewtobyte(svr));
                        Fact.setImage(Imageviewtobyte(svr));
                        Fact.setSite(site.getText().toString());
                        Fact.setFax(fax.getText().toString());
                        Fact.setStatujur(statujur.getText().toString());
                        Fact.setSecteur(secteur.getText().toString());
                        Fact.setTaille(taille.getText().toString());

                        db.QueryData();
                        db.InsertDataEntreprise(Fact.getNif(), Fact.getNom(), Fact.getRg(), Fact.getSecteur(), Fact.getTaille(), Fact.getStatujur(), Fact.getComEmail(), Fact.getPhone_num(), Fact.getAddress(), Fact.getSite(), Fact.getFax(), Fact.getFactlogo());
                        db.InsertDataOperateur(Fact.getName(), Fact.getPrenom(), Fact.getNif(), Fact.getNom(), Fact.getJob(), Fact.getPhone(), Fact.getEmail(), Fact.getAdress(), Fact.getLinkedIn(), Fact.getFacebook(), Fact.getTwitter(), Fact.getImage());
                        db.InsertDataClient(Fact.getReduction(), Fact.getName(), Fact.getPrenom(), Fact.getType());
                        Nom.add(Fact.getName());
                        Prenom.add(Fact.getPrenom());
                        Entreprise.add(Fact.getNom());
                        TYPPE.add(Fact.getType());
                        Images.add(Fact.getImage());
                        adpter.notifyDataSetChanged();
                    }
                }
            });
            bk1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy1.setVisibility(View.GONE);
                    layy0.setVisibility(View.VISIBLE);

                }
            });
            bk2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy2.setVisibility(View.GONE);
                    layy1.setVisibility(View.VISIBLE);
                    conss1.setVisibility(View.VISIBLE);

                }
            });
            bk3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy4.setVisibility(View.GONE);
                    layy2.setVisibility(View.VISIBLE);

                }
            });
            bk4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy5.setVisibility(View.GONE);
                    layy4.setVisibility(View.VISIBLE);

                }
            });

            ArrayAdapter<CharSequence> dataAdapte = ArrayAdapter.createFromResource(getApplicationContext(), R.array.statujur, simple_list_item_1);
            dataAdapte.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            statujur.setAdapter(dataAdapte);
            statujur.setThreshold(1);
            statujur.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    statujur.showDropDown();
                    return false;
                }
            });

            ArrayAdapter<CharSequence> dataAdapte1 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.Secteur, simple_list_item_1);
            dataAdapte1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            secteur.setAdapter(dataAdapte1);
            secteur.setThreshold(1);
            secteur.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    secteur.showDropDown();
                    return false;
                }
            });

            ArrayAdapter<CharSequence> dataAdapte2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.size, simple_list_item_1);
            dataAdapte2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            taille.setAdapter(dataAdapte2);
            taille.setThreshold(1);
            taille.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    taille.showDropDown();
                    return false;
                }
            });















        }
        return true;
    }

    public class clientslisteAdp extends BaseAdapter {
        private Context mContext;
        private final ArrayList<String> Itnom;
        private final ArrayList<String> Itprenom;
        private final ArrayList<String> Itentreprise;
        private final ArrayList<String> TYPPE;

        private final ArrayList<byte[]> imageId;


        public clientslisteAdp(Context c, ArrayList<String> Itnom, ArrayList<String> Itprenom, ArrayList<String> Itentreprise, ArrayList<String> TYPPE, ArrayList<byte[]> imageId) {
            mContext = c;
            this.Itnom = Itnom;
            this.Itprenom = Itprenom;
            this.Itentreprise = Itentreprise;
            this.imageId = imageId;
            this.TYPPE = TYPPE;

        }

        @Override
        public int getCount() {
            return Itnom.size();
        }

        @Override
        public Object getItem(int position) {
            return Itnom.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // CheckableLayout l;


            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.lst, null);
            }

            EditText nom = (EditText) convertView.findViewById(R.id.Mname);
            image = (ImageView) convertView.findViewById(R.id.Ma);
            EditText entreprises = (EditText) convertView.findViewById(R.id.Mfact);
            EditText type=(EditText)convertView.findViewById(R.id.cons);
            final Button mdel=(Button)convertView.findViewById(R.id.mdel);
            final Button mcons=(Button)convertView.findViewById(R.id.mcons);

             type.setVisibility(View.VISIBLE);
mcons.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(mContext,ContactP.class);
        intent.putExtra("in","client");
        intent.putExtra("nom",Itnom.get(position));
        intent.putExtra("prenom",Itprenom.get(position));
        intent.putExtra("entreprisee",Itentreprise.get(position));
        startActivity(intent);
    }
});

             mdel.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view2) {
                     mdel.setFocusableInTouchMode(true);
                     mdel.setFocusable(true);
                     Toast.makeText(mContext,"huhuhu",Toast.LENGTH_LONG).show();
                     final androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext).create();
                     alertDialog.setTitle("Alert Dialog");
                     alertDialog.setMessage("do you really want to delete this warehouse 0_0 ");
                     //alertDialog.setIcon(R.drawable.welcome);
                     alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             DataBaseM db = new DataBaseM(mContext);
                             db.QueryData();
                             db.Delete("client","Nom=? and Prenom=?", new String[]{Itnom.get(position),Itprenom.get(position)});
                             clientsliste.adpter.notifyDataSetChanged();
                             mdel.setFocusableInTouchMode(true);
                             mdel.setFocusable(true);

                         }
                     });
                     alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             Toast.makeText(mContext, "here we are ", Toast.LENGTH_SHORT).show();

                             alertDialog.dismiss();

                         }

                     });
                     alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                         @SuppressLint("ResourceAsColor")
                         @Override
                         public void onShow(DialogInterface arg0) {
                             alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.required);
                         }
                     });


                     alertDialog.show();




                 }
             });





            if (Itnom.size() != 0 && Itprenom.size() != 0) {
                nom.setText(Itnom.get(position) + " " + Itprenom.get(position));
            }
            if (TYPPE.size() != 0 ) {
                type.setText(TYPPE.get(position));
            }
            if (Itentreprise.size() !=0) {
                entreprises.setText(Itentreprise.get(position));
            }
            if (imageId.size() != 0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageId.get(position), 0, imageId.get(position).length);
                bmp = ImageViewHalper.ImageFromDrawable(clientsliste.this, bmp);
                image.setImageBitmap(bmp);
            }
            image.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(clientsliste.this);
                    final View view = LayoutInflater.from(clientsliste.this).inflate(R.layout.ipic_ask, null);
                    TextView title = (TextView) view.findViewById(R.id.ertitle);
                    TextView message = (TextView) view.findViewById(R.id.messageer);
                    Button acc = (Button) view.findViewById(R.id.btn_acc);
                    Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                    title.setText("OPEN");
                    message.setText("do you want to open camera or gallery  ");
                    dateAlt.setView(view);
                    acc.setText("CAMERA");
                    nacc.setText("GALLERY ");
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(clientsliste.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(clientsliste.this, new String[]{
                                        Manifest.permission.CAMERA}, 100);

                            }
                            dialog.dismiss();

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 100);

                        }
                    });
                    nacc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            ActivityCompat.requestPermissions(clientsliste.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                        }
                    });
                    dialog.show();

                    return false;
                }
            });
            //  l = (CheckableLayout) convertView;
            //ResolveInfo info = mApps.get(position);


            return convertView;


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = ImageViewHalper.ImageFromDrawable(clientsliste.this, bitmap);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = ImageViewHalper.ImageFromDrawable(clientsliste.this, bitmap);
            image.setImageBitmap(bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] Imageviewtobyte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
