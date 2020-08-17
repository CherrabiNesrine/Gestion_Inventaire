package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.R.layout.simple_list_item_1;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ajtproduit2 extends SidebarMenu {
    EditText nom, qntete, prix, prixS, Exp, FAB, qttmin, desc;
    DataBaseM bd;
    Button add, op1, op2, op3, opba4;
    TextInputLayout textInputLayout2, textInputLayout3, tip1, tip2, tip3, tip4;
    LinearLayout lay1, lay2, lay3, lay4;
    ConstraintLayout cnst1, cnst2, cnst3, cnst4;
    Button opba, opba2, opba3;
    FactoryE Fact= new FactoryE();
    AutoCompleteTextView Unit, coins, scoins, Categorie, Scategorie, supplier, customer;
    final Calendar myCalendar1 = Calendar.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    BottomNavigationView navigationView;
    Produit p = new Produit();
    RadioGroup rgt, RGd;
    final ArrayList<String> llist = new ArrayList<String>();
    String nomm = "";
    private String Code;
boolean djaz=false;
    ImageView image;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        //reception of code from the pervius activity
        if (intent != null) {
            String codee = "";
            String typee = "";
            String unitt = "";
            if (intent.hasExtra("hello")) {
                codee = intent.getStringExtra("hello");
            }
            Code = codee;

            //recupriate the pervious page attribute

            if (intent.hasExtra("Nom")) {
                nomm = intent.getStringExtra("Nom");

            }
            if (intent.hasExtra("type")) {
                typee = intent.getStringExtra("type");

            }
            if (intent.hasExtra("unit")) {
                unitt = intent.getStringExtra("unit");

            }

            p.setCategorie(typee);
            p.setmesure(unitt);

        }

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_ajtproduit2, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ajouter Produit ");

        /////////////////////////////
// Initialisatiom
        bd = new DataBaseM(this);
        lay1 = (LinearLayout) findViewById(R.id.layyy1);
        lay2 = (LinearLayout) findViewById(R.id.layyy2);
        lay3 = (LinearLayout) findViewById(R.id.layyy3);
        lay4 = (LinearLayout) findViewById(R.id.layyy0);
        cnst1 = (ConstraintLayout) findViewById(R.id.cnst1);
        cnst2 = (ConstraintLayout) findViewById(R.id.cnst2);
        cnst3 = (ConstraintLayout) findViewById(R.id.cnst3);
        cnst4 = (ConstraintLayout) findViewById(R.id.cnst4);
        nom = (EditText) findViewById(R.id.nom);
        qntete = (EditText) findViewById(R.id.qnt);
        prixS = (EditText) findViewById(R.id.prixS);
        prix = (EditText) findViewById(R.id.prix);
        qttmin = (EditText) findViewById(R.id.qttmin);
        supplier = (AutoCompleteTextView) findViewById(R.id.supplier);
        customer = (AutoCompleteTextView) findViewById(R.id.customer);
        coins = (AutoCompleteTextView) findViewById(R.id.coins);
        scoins = (AutoCompleteTextView) findViewById(R.id.Scoins);
        Scategorie = (AutoCompleteTextView) findViewById(R.id.Souscategorie);
        Categorie = (AutoCompleteTextView) findViewById(R.id.categorie);
        add = (Button) findViewById(R.id.btajt);
        op1 = (Button) findViewById(R.id.op1);
        op2 = (Button) findViewById(R.id.op2);
        opba2 = (Button) findViewById(R.id.opba2);
        opba3 = (Button) findViewById(R.id.opba3);
        op3 = (Button) findViewById(R.id.op3);
        opba4 = (Button) findViewById(R.id.opba4);
        image = (ImageView) findViewById(R.id.imgv);
        image.setImageResource(R.mipmap.vv);
        rgt = (RadioGroup) findViewById(R.id.rgt);
        RGd = (RadioGroup) findViewById(R.id.RGd);
        Unit = (AutoCompleteTextView) findViewById(R.id.unit);
        textInputLayout2 = (TextInputLayout) findViewById(R.id.textInputLayout2);
        textInputLayout3 = (TextInputLayout) findViewById(R.id.textInputLayout3);
        tip1 = (TextInputLayout) findViewById(R.id.tip1);
        tip2 = (TextInputLayout) findViewById(R.id.tip2);
        tip3 = (TextInputLayout) findViewById(R.id.tip3);
        tip4 = (TextInputLayout) findViewById(R.id.tip4);
        desc = (EditText) findViewById(R.id.desc);
        /////////////////////////////

        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_add);

        llist.add("add a new provider ");
        supplier.setClickable(true);
        //////////////////////////////

        Date d = null;
        try {
            d = sdf.parse("01/01/2000");
            p.setDateDel(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), sdf.format(p.getDateDel()), Toast.LENGTH_LONG).show();
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nom.getText().toString().trim().equalsIgnoreCase("")) {

                    textInputLayout2.setError("This field can not be blank");

                } else if (qntete.getText().toString().isEmpty()) {
                    textInputLayout3.setError("this field can not be blank ");
                } else {
                    textInputLayout3.setError(null);
                    textInputLayout2.setError(null);
                    lay4.setVisibility(View.GONE);
                    lay1.setVisibility(View.VISIBLE);
                    cnst3.setVisibility(View.GONE);
                    cnst1.setVisibility(View.VISIBLE);
                }
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (prix.getText().toString().trim().equalsIgnoreCase("")) {

                    tip1.setError("This field can not be blank");

                } else if (supplier.getText().toString().isEmpty()) {
                    tip2.setError("this field can not be blank ");
                }
                else if(supplier.getText().toString().equals("add a new provider ")){

                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                    final View view= LayoutInflater.from(ajtproduit2.this).inflate(R.layout.activity_factory,null);
                    final LinearLayout layy0,layy1,layy2,layy3,layy4,layy5;
                    Button nxt1,nxt2,nxt3,nxt4,startad2,startad,bk1,bk2,bk3,bk4;
                    final EditText oneNom , oneprenom,oneJob,oneAdress,OnTlf,oneEmail,facebook,LinkedIn,Twitter,entreprise,nif,rg,compaddres ,comtlf,compEmail,site,fax;
                    final AutoCompleteTextView  secteur,taille,statujur;
                    final ImageView photop,logo;
                    layy0 = (LinearLayout)view.findViewById(R.id.layy0);
                    layy1 = (LinearLayout)view.findViewById(R.id.layy1);
                    layy2 = (LinearLayout)view.findViewById(R.id.layy2);
                    layy3 = (LinearLayout)view.findViewById(R.id.layy3);
                    layy4 = (LinearLayout)view.findViewById(R.id.layy4);
                    layy5 = (LinearLayout)view.findViewById(R.id.layy5);

                    startad=(Button)view.findViewById(R.id.startad);
                    startad2=(Button)view.findViewById(R.id.startad2);
                    nxt1=(Button)view.findViewById(R.id.nxt1);
                    nxt2=(Button)view.findViewById(R.id.nxt2);
                    nxt3=(Button)view.findViewById(R.id.nxt3);
                    nxt4=(Button)view.findViewById(R.id.nxt4);

                    bk1=(Button)view.findViewById(R.id.bk1);
                    bk2=(Button)view.findViewById(R.id.bk2);
                    bk3=(Button)view.findViewById(R.id.bk3);
                    bk4=(Button)view.findViewById(R.id.bk4);

                    oneNom=(EditText)view.findViewById(R.id.OneNom);
                    oneprenom=(EditText)view.findViewById(R.id.onePrenom);
                    oneJob=(EditText)view.findViewById(R.id.oneJob);
                    oneAdress=(EditText)view.findViewById(R.id.oneAdress);
                    OnTlf=(EditText)view.findViewById(R.id.OnTlf);
                    oneEmail=(EditText)view.findViewById(R.id.oneEmail);
                    facebook=(EditText)view.findViewById(R.id.facebook);
                    LinkedIn=(EditText)view.findViewById(R.id.LinkedIn);
                    Twitter=(EditText)view.findViewById(R.id.Twitter);
                    entreprise=(EditText)view.findViewById(R.id.entreprise);
                    nif=(EditText)view.findViewById(R.id.nif);
                    rg=(EditText)view.findViewById(R.id.rg);
                    compaddres=(EditText)view.findViewById(R.id.CompAdrs);
                    comtlf=(EditText)view.findViewById(R.id.CompTlf);
                    compEmail=(EditText)view.findViewById(R.id.compEmail);
                    site=(EditText)view.findViewById(R.id.site);
                    fax=(EditText)view.findViewById(R.id.Fax);

                    secteur=(AutoCompleteTextView)findViewById(R.id.secteur);
                    taille=(AutoCompleteTextView)findViewById(R.id.taille);
                    statujur=(AutoCompleteTextView)findViewById(R.id.statusjuridique);


                    photop=(ImageView)view.findViewById(R.id.photoP);
                    logo=(ImageView)view.findViewById(R.id.logo);


                    photop.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                            final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                            TextView title = (TextView) view.findViewById(R.id.ertitle);
                            TextView message = (TextView) view.findViewById(R.id.messageer);
                            Button acc = (Button) view.findViewById(R.id.btn_acc);
                            Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                            ImageView img = (ImageView) view.findViewById(R.id.help);
                            title.setText("OPEN");
                            img.setImageResource(R.drawable.ic_action_cameraa);
                            message.setText("do you want to open camera or gallery  ");
                            dateAlt.setView(view);
                            acc.setText("CAMERA");
                            nacc.setText("GALLERY ");
                            final AlertDialog dialog = dateAlt.create();
                            acc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                                    ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                                }
                            });
                            dialog.show();

                            return false;
                        }
                    });
                    logo.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                            final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                            TextView title = (TextView) view.findViewById(R.id.ertitle);
                            TextView message = (TextView) view.findViewById(R.id.messageer);
                            Button acc = (Button) view.findViewById(R.id.btn_acc);
                            Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                            ImageView img = (ImageView) view.findViewById(R.id.help);
                            title.setText("OPEN");
                            img.setImageResource(R.drawable.ic_action_cameraa);
                            message.setText("do you want to open camera or gallery  ");
                            dateAlt.setView(view);
                            acc.setText("CAMERA");
                            nacc.setText("GALLERY ");
                            final AlertDialog dialog = dateAlt.create();
                            acc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                                    ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                                }
                            });
                            dialog.show();

                            return false;
                        }
                    });



                    startad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layy0.setVisibility(View.GONE);
                            layy1.setVisibility(View.VISIBLE);
                        }
                    });
                    nxt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layy1.setVisibility(View.GONE);
                            layy2.setVisibility(View.VISIBLE);
                        }
                    });
                    nxt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            layy2.setVisibility(View.VISIBLE);
                            if (djaz==true){
                                layy4.setVisibility(View.VISIBLE);
                            }
                            else{
                                layy3.setVisibility(View.VISIBLE);
                                djaz=true;
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
                            layy4.setVisibility(View.GONE);
                            layy5.setVisibility(View.VISIBLE);

                        }
                    });
                    nxt4.setOnClickListener(new View.OnClickListener() { //Button OK
                        @Override
                        public void onClick(View view) {

                            Fact.setName(oneNom.getText().toString());
                            Fact.setPrenom(oneprenom.getText().toString());
                            Fact.setPhone(OnTlf.getText().toString());
                            Fact.setJob(oneJob.getText().toString());
                            Fact.setAdress(oneAdress.getText().toString());
                            Fact.setEmail(oneEmail.getText().toString());
                            Fact.setFacebook(facebook.getText().toString());
                            Fact.setLinkedIn(LinkedIn.getText().toString());
                            Fact.setTwitter(Twitter.getText().toString());
                            Fact.setNom(entreprise.getText().toString());
                            Fact.setNif(nif.getText().toString());
                            Fact.setRg(rg.getText().toString());
                            Fact.setAddress(compaddres.getText().toString());
                            Fact.setPhone_num(comtlf.getText().toString());
                            Fact.setComEmail(compEmail.getText().toString());
                            Fact.setSite(site.getText().toString());
                            Fact.setFax(fax.getText().toString());
                            Fact.setStatujur(statujur.getText().toString());
                            Fact.setSecteur(secteur.getText().toString());
                            Fact.setTaille(taille.getText().toString());

                            Fact.setImage(Imageviewtobyte(photop));
                            Fact.setFactlogo(Imageviewtobyte(logo));
                            db.QueryData();
                            db.InsertDataEntreprise(Fact.getNif(),Fact.getNom(),Fact.getRg(),Fact.getSecteur(),Fact.getTaille(),Fact.getStatujur(),Fact.getComEmail(),Fact.getPhone_num(),Fact.getAddress(),Fact.getSite(),Fact.getFax(),Fact.getFactlogo());
                            db.InsertDataOperateur(Fact.getName(),Fact.getPrenom(),Fact.getNif(),Fact.getNom(),Fact.getJob(),Fact.getPhone(),Fact.getEmail(),Fact.getAdress(),Fact.getLinkedIn(),Fact.getFacebook(),Fact.getTwitter(),Fact.getImage());
                            db.InsertDataFounisseur(Fact.getName(),Fact.getPrenom());

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
                    dateAlt.setView(view);
                    final AlertDialog dialog2 = dateAlt.create();

                    dialog2.show();
                    ArrayAdapter<String> dataAda= new ArrayAdapter<String>(ajtproduit2.this, android.R.layout.simple_dropdown_item_1line, llist);
                    //ArrayAdapter<String> dataAda = ArrayAdapter.createFromResource(getApplicationContext(),llist,simple_list_item_1 );
                    dataAda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    supplier.setAdapter(dataAda);

                }
                else {
                    tip1.setError(null);
                    tip2.setError(null);
                    lay1.setVisibility(View.GONE);
                    lay2.setVisibility(View.VISIBLE);
                    cnst1.setVisibility(View.GONE);
                    cnst4.setVisibility(View.VISIBLE);
                }

            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (qntete.getText().toString().trim().equalsIgnoreCase("")) {
                    tip1.setError("This field can not be blank");

                } else if (customer.getText().toString().isEmpty()) {
                    tip2.setError("this field can not be blank ");
                }else if (customer.getText().toString().equals("")){




                }
                else {
                    lay2.setVisibility(View.GONE);
                    lay3.setVisibility(View.VISIBLE);
                    cnst4.setVisibility(View.GONE);
                    cnst2.setVisibility(View.VISIBLE);
                }

            }
        });
        opba3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay2.setVisibility(View.VISIBLE);
                lay3.setVisibility(View.GONE);
                cnst4.setVisibility(View.VISIBLE);
                cnst2.setVisibility(View.GONE);


            }
        });
        opba2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay4.setVisibility(View.VISIBLE);
                lay1.setVisibility(View.GONE);
                cnst3.setVisibility(View.VISIBLE);
                cnst1.setVisibility(View.GONE);


            }
        });
        opba4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.VISIBLE);
                lay2.setVisibility(View.GONE);
                cnst1.setVisibility(View.VISIBLE);
                cnst4.setVisibility(View.GONE);


            }
        });


        rgt.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i;
                i = rgt.getCheckedRadioButtonId();
                RadioButton catt = (RadioButton) findViewById(i);
                p.setTypePr(catt.getText().toString());


            }
        });
        RGd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i;
                i = RGd.getCheckedRadioButtonId();
                RadioButton date = (RadioButton) findViewById(i);
                if (date.getText().toString().equals("yes")) {

                    final AlertDialog.Builder dateAlert = new AlertDialog.Builder(ajtproduit2.this);
                    final View viewD = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.datecatch, null);
                    Button acc = (Button) viewD.findViewById(R.id.kkk);
                    FAB = (EditText) viewD.findViewById(R.id.datef);
                    Exp = (EditText) viewD.findViewById(R.id.dateE);
                    final TextInputLayout lay1 = (TextInputLayout) viewD.findViewById(R.id.lay1);
                    final TextInputLayout lay2 = (TextInputLayout) viewD.findViewById(R.id.lay2);


                    dateAlert.setView(viewD);
                    final AlertDialog dialog = dateAlert.create();
                    dialog.show();

                    final DatePickerDialog.OnDateSetListener datep = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            java.util.Date Date = new java.util.Date();
                            if (CompareDate(myCalendar.getTime(), Date) == 1) {
                                FAB.setError("invalide date ");
                            } else {
                                FAB.setError(null);
                                FAB.setText(sdf.format(myCalendar.getTime()));
                                p.setFab(myCalendar.getTime());

                            }
                        }

                    };
                    FAB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DatePickerDialog(ajtproduit2.this, datep, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                        }
                    });

                    final DatePickerDialog.OnDateSetListener dateEXP = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar1.set(Calendar.YEAR, year);
                            myCalendar1.set(Calendar.MONTH, monthOfYear);
                            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            p.setExp(myCalendar1.getTime());
                            Exp.setText(sdf.format(myCalendar1.getTime()));

                        }

                    };


                    Exp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v, "you should click on edit text to get the calander ", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            new DatePickerDialog(ajtproduit2.this, dateEXP, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });


                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (FAB.getText().toString().isEmpty()) {
                                lay1.setError("this field can not be blank ");
                            } else if (Exp.getText().toString().isEmpty()) {
                                lay2.setError("this field can not be blank ");
                            } else if (CompareDate(myCalendar.getTime(), myCalendar1.getTime()) == 1) {
                                Toast.makeText(getApplicationContext(), "Experation date can not be before Fabrication date", Toast.LENGTH_LONG).show();
                            } else {
                                p.setFab(myCalendar1.getTime());
                                p.setExp(myCalendar.getTime());
                                dialog.dismiss();
                            }
                        }
                    });


                }


            }
        });

        //-----------------------------------UNIT---------------------------
        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.unit, simple_list_item_1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Unit.setThreshold(0);
        Unit.setAdapter(dataAdapter);
        Unit.setText(p.getmesure());
        Unit.setActivated(false);
     /*   Unit.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                Unit.showDropDown();
                return false;
            }
        });*/

//------------------------------------------- COINS AND SCOINS ---------------------------------------
        ArrayAdapter<CharSequence> dataAdapter2 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coins, simple_list_item_1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        coins.setAdapter(dataAdapter2);
        coins.setThreshold(1);
        coins.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                coins.showDropDown();
                return false;
            }
        });

        //-----------------scoins(sale coins)
        ArrayAdapter<CharSequence> dataAdapter3 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.coins, simple_list_item_1);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        scoins.setAdapter(dataAdapter3);
        scoins.setThreshold(1);
        scoins.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scoins.showDropDown();
                return false;
            }
        });
//------------------------------------------Categorie ------------------------------------------------
        ArrayAdapter<CharSequence> dataAdapter4 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.categorie, simple_list_item_1);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        Categorie.setThreshold(0);
        Categorie.setAdapter(dataAdapter4);
        Categorie.setText(p.getCategorie());
        Categorie.setActivated(false);


        //////////////////////////////////////////////
        String cat = Categorie.getText().toString();
        if (cat != null) {
            if (cat.equals("Consumable")) {

                ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.listCon, simple_list_item_1);
                dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                Scategorie.setThreshold(1);
                Scategorie.setAdapter(dataAdapter5);
                Scategorie.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Scategorie.showDropDown();
                        return false;
                    }
                });


            } else if (cat.equals("Not consumable")) {
                ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.listNcons, simple_list_item_1);
                dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                Scategorie.setThreshold(1);
                Scategorie.setAdapter(dataAdapter5);
            } else if (cat.equals("Spare parts")) {
                ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
                dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                Scategorie.setThreshold(1);
                Scategorie.setAdapter(dataAdapter5);
            } else if (cat.equals("Raw materials")) {

            } else {
                ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
                dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                Scategorie.setThreshold(1);
                Scategorie.setAdapter(dataAdapter5);
            }
        } else {
            ArrayAdapter<CharSequence> dataAdapter5 = ArrayAdapter.createFromResource(getApplicationContext(), R.array.SpareLit, simple_list_item_1);
            dataAdapter5.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            Scategorie.setThreshold(1);
            Scategorie.setAdapter(dataAdapter5);
            Scategorie.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Scategorie.showDropDown();
                    return false;
                }
            });
        }


        ///////////////////////////////////////////////////


// programmation du fournisseur


        db.QueryData();

        supplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {
                if(supplier.getText().toString().equals("add a new provider ")){

                        final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                        final View view= LayoutInflater.from(ajtproduit2.this).inflate(R.layout.activity_factory,null);
                        final LinearLayout layy0,layy1,layy2,layy3,layy4,layy5;
                        Button nxt1,nxt2,nxt3,nxt4,startad2,startad,bk1,bk2,bk3,bk4;
                        final EditText oneNom , oneprenom,oneJob,oneAdress,OnTlf,oneEmail,facebook,LinkedIn,Twitter,entreprise,nif,rg,compaddres ,comtlf,compEmail,site,fax;
                        final AutoCompleteTextView  secteur,taille,statujur;
                        final ImageView photop,logo;
                        layy0 = (LinearLayout)view.findViewById(R.id.layy0);
                        layy1 = (LinearLayout)view.findViewById(R.id.layy1);
                        layy2 = (LinearLayout)view.findViewById(R.id.layy2);
                        layy3 = (LinearLayout)view.findViewById(R.id.layy3);
                        layy4 = (LinearLayout)view.findViewById(R.id.layy4);
                        layy5 = (LinearLayout)view.findViewById(R.id.layy5);

                        startad=(Button)view.findViewById(R.id.startad);
                        startad2=(Button)view.findViewById(R.id.startad2);
                        nxt1=(Button)view.findViewById(R.id.nxt1);
                        nxt2=(Button)view.findViewById(R.id.nxt2);
                        nxt3=(Button)view.findViewById(R.id.nxt3);
                        nxt4=(Button)view.findViewById(R.id.nxt4);

                        bk1=(Button)view.findViewById(R.id.bk1);
                        bk2=(Button)view.findViewById(R.id.bk2);
                        bk3=(Button)view.findViewById(R.id.bk3);
                        bk4=(Button)view.findViewById(R.id.bk4);

                        oneNom=(EditText)view.findViewById(R.id.OneNom);
                        oneprenom=(EditText)view.findViewById(R.id.onePrenom);
                        oneJob=(EditText)view.findViewById(R.id.oneJob);
                        oneAdress=(EditText)view.findViewById(R.id.oneAdress);
                        OnTlf=(EditText)view.findViewById(R.id.OnTlf);
                        oneEmail=(EditText)view.findViewById(R.id.oneEmail);
                        facebook=(EditText)view.findViewById(R.id.facebook);
                        LinkedIn=(EditText)view.findViewById(R.id.LinkedIn);
                        Twitter=(EditText)view.findViewById(R.id.Twitter);
                        entreprise=(EditText)view.findViewById(R.id.entreprise);
                        nif=(EditText)view.findViewById(R.id.nif);
                        rg=(EditText)view.findViewById(R.id.rg);
                        compaddres=(EditText)view.findViewById(R.id.CompAdrs);
                        comtlf=(EditText)view.findViewById(R.id.CompTlf);
                        compEmail=(EditText)view.findViewById(R.id.compEmail);
                        site=(EditText)view.findViewById(R.id.site);
                        fax=(EditText)view.findViewById(R.id.Fax);

                        secteur=(AutoCompleteTextView)findViewById(R.id.secteur);
                        taille=(AutoCompleteTextView)findViewById(R.id.taille);
                        statujur=(AutoCompleteTextView)findViewById(R.id.statusjuridique);


                        photop=(ImageView)view.findViewById(R.id.photoP);
                        logo=(ImageView)view.findViewById(R.id.logo);


                        photop.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                                final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                                TextView title = (TextView) view.findViewById(R.id.ertitle);
                                TextView message = (TextView) view.findViewById(R.id.messageer);
                                Button acc = (Button) view.findViewById(R.id.btn_acc);
                                Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                                ImageView img = (ImageView) view.findViewById(R.id.help);
                                title.setText("OPEN");
                                img.setImageResource(R.drawable.ic_action_cameraa);
                                message.setText("do you want to open camera or gallery  ");
                                dateAlt.setView(view);
                                acc.setText("CAMERA");
                                nacc.setText("GALLERY ");
                                final AlertDialog dialog = dateAlt.create();
                                acc.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                                        ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                                    }
                                });
                                dialog.show();

                                return false;
                            }
                        });
                        logo.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                                final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                                TextView title = (TextView) view.findViewById(R.id.ertitle);
                                TextView message = (TextView) view.findViewById(R.id.messageer);
                                Button acc = (Button) view.findViewById(R.id.btn_acc);
                                Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                                ImageView img = (ImageView) view.findViewById(R.id.help);
                                title.setText("OPEN");
                                img.setImageResource(R.drawable.ic_action_cameraa);
                                message.setText("do you want to open camera or gallery  ");
                                dateAlt.setView(view);
                                acc.setText("CAMERA");
                                nacc.setText("GALLERY ");
                                final AlertDialog dialog = dateAlt.create();
                                acc.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                                        ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                                    }
                                });
                                dialog.show();

                                return false;
                            }
                        });



                        startad.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layy0.setVisibility(View.GONE);
                                layy1.setVisibility(View.VISIBLE);
                            }
                        });
                        nxt1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layy1.setVisibility(View.GONE);
                                layy2.setVisibility(View.VISIBLE);
                            }
                        });
                        nxt2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                layy2.setVisibility(View.VISIBLE);
                                if (djaz==true){
                                    layy4.setVisibility(View.VISIBLE);
                                }
                                else{
                                    layy3.setVisibility(View.VISIBLE);
                                    djaz=true;
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
                                layy4.setVisibility(View.GONE);
                                layy5.setVisibility(View.VISIBLE);

                            }
                        });
                        nxt4.setOnClickListener(new View.OnClickListener() { //Button OK
                            @Override
                            public void onClick(View view) {

                                Fact.setName(oneNom.getText().toString());
                                Fact.setPrenom(oneprenom.getText().toString());
                                Fact.setPhone(OnTlf.getText().toString());
                                Fact.setJob(oneJob.getText().toString());
                                Fact.setAdress(oneAdress.getText().toString());
                                Fact.setEmail(oneEmail.getText().toString());
                                Fact.setFacebook(facebook.getText().toString());
                                Fact.setLinkedIn(LinkedIn.getText().toString());
                                Fact.setTwitter(Twitter.getText().toString());
                                Fact.setNom(entreprise.getText().toString());
                                Fact.setNif(nif.getText().toString());
                                Fact.setRg(rg.getText().toString());
                                Fact.setAddress(compaddres.getText().toString());
                                Fact.setPhone_num(comtlf.getText().toString());
                                Fact.setComEmail(compEmail.getText().toString());
                                Fact.setSite(site.getText().toString());
                                Fact.setFax(fax.getText().toString());
                                Fact.setStatujur(statujur.getText().toString());
                                Fact.setSecteur(secteur.getText().toString());
                                Fact.setTaille(taille.getText().toString());

                                Fact.setImage(Imageviewtobyte(photop));
                                Fact.setFactlogo(Imageviewtobyte(logo));
                                db.QueryData();
                                db.InsertDataEntreprise(Fact.getNif(),Fact.getNom(),Fact.getRg(),Fact.getSecteur(),Fact.getTaille(),Fact.getStatujur(),Fact.getComEmail(),Fact.getPhone_num(),Fact.getAddress(),Fact.getSite(),Fact.getFax(),Fact.getFactlogo());
                                db.InsertDataOperateur(Fact.getName(),Fact.getPrenom(),Fact.getNif(),Fact.getNom(),Fact.getJob(),Fact.getPhone(),Fact.getEmail(),Fact.getAdress(),Fact.getLinkedIn(),Fact.getFacebook(),Fact.getTwitter(),Fact.getImage());
                                db.InsertDataFounisseur(Fact.getName(),Fact.getPrenom());

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
                        dateAlt.setView(view);
                        final AlertDialog dialog2 = dateAlt.create();

                        dialog2.show();



                    ArrayAdapter<String> dataAda= new ArrayAdapter<String>(ajtproduit2.this, android.R.layout.simple_dropdown_item_1line, llist);
                    //ArrayAdapter<String> dataAda = ArrayAdapter.createFromResource(getApplicationContext(),llist,simple_list_item_1 );
                    dataAda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    supplier.setAdapter(dataAda);

                }
            }
        });

supplier.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view1) {
        Cursor[] cursor = new Cursor[]{db.getData("SELECT * FROM fournisseur")};
        if (cursor[0] != null) {
            while (cursor[0].moveToNext()) {
                String NOm = cursor[0].getColumnName(1);
                String Prenom = cursor[0].getColumnName(2);
                llist.add(NOm + " " + Prenom);
            }
        }
        else {
            final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
            final View view= LayoutInflater.from(ajtproduit2.this).inflate(R.layout.activity_factory,null);
            final LinearLayout layy0,layy1,layy2,layy3,layy4,layy5;
            Button nxt1,nxt2,nxt3,nxt4,startad2,startad,bk1,bk2,bk3,bk4;
            final EditText oneNom , oneprenom,oneJob,oneAdress,OnTlf,oneEmail,facebook,LinkedIn,Twitter,entreprise,nif,rg,compaddres ,comtlf,compEmail,site,fax;
            final AutoCompleteTextView  secteur,taille,statujur;
            final ImageView photop,logo;
            layy0 = (LinearLayout)view.findViewById(R.id.layy0);
            layy1 = (LinearLayout)view.findViewById(R.id.layy1);
            layy2 = (LinearLayout)view.findViewById(R.id.layy2);
            layy3 = (LinearLayout)view.findViewById(R.id.layy3);
            layy4 = (LinearLayout)view.findViewById(R.id.layy4);
            layy5 = (LinearLayout)view.findViewById(R.id.layy5);

            startad=(Button)view.findViewById(R.id.startad);
            startad2=(Button)view.findViewById(R.id.startad2);
            nxt1=(Button)view.findViewById(R.id.nxt1);
            nxt2=(Button)view.findViewById(R.id.nxt2);
            nxt3=(Button)view.findViewById(R.id.nxt3);
            nxt4=(Button)view.findViewById(R.id.nxt4);

            bk1=(Button)view.findViewById(R.id.bk1);
            bk2=(Button)view.findViewById(R.id.bk2);
            bk3=(Button)view.findViewById(R.id.bk3);
            bk4=(Button)view.findViewById(R.id.bk4);

            oneNom=(EditText)view.findViewById(R.id.OneNom);
            oneprenom=(EditText)view.findViewById(R.id.onePrenom);
            oneJob=(EditText)view.findViewById(R.id.oneJob);
            oneAdress=(EditText)view.findViewById(R.id.oneAdress);
            OnTlf=(EditText)view.findViewById(R.id.OnTlf);
            oneEmail=(EditText)view.findViewById(R.id.oneEmail);
            facebook=(EditText)view.findViewById(R.id.facebook);
            LinkedIn=(EditText)view.findViewById(R.id.LinkedIn);
            Twitter=(EditText)view.findViewById(R.id.Twitter);
            entreprise=(EditText)view.findViewById(R.id.entreprise);
            nif=(EditText)view.findViewById(R.id.nif);
            rg=(EditText)view.findViewById(R.id.rg);
            compaddres=(EditText)view.findViewById(R.id.CompAdrs);
            comtlf=(EditText)view.findViewById(R.id.CompTlf);
            compEmail=(EditText)view.findViewById(R.id.compEmail);
            site=(EditText)view.findViewById(R.id.site);
            fax=(EditText)view.findViewById(R.id.Fax);

            secteur=(AutoCompleteTextView)findViewById(R.id.secteur);
            taille=(AutoCompleteTextView)findViewById(R.id.taille);
            statujur=(AutoCompleteTextView)findViewById(R.id.statusjuridique);


            photop=(ImageView)view.findViewById(R.id.photoP);
            logo=(ImageView)view.findViewById(R.id.logo);


            photop.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                    final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                    TextView title = (TextView) view.findViewById(R.id.ertitle);
                    TextView message = (TextView) view.findViewById(R.id.messageer);
                    Button acc = (Button) view.findViewById(R.id.btn_acc);
                    Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView) view.findViewById(R.id.help);
                    title.setText("OPEN");
                    img.setImageResource(R.drawable.ic_action_cameraa);
                    message.setText("do you want to open camera or gallery  ");
                    dateAlt.setView(view);
                    acc.setText("CAMERA");
                    nacc.setText("GALLERY ");
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                            ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                        }
                    });
                    dialog.show();

                    return false;
                }
            });
            logo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                    final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                    TextView title = (TextView) view.findViewById(R.id.ertitle);
                    TextView message = (TextView) view.findViewById(R.id.messageer);
                    Button acc = (Button) view.findViewById(R.id.btn_acc);
                    Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView) view.findViewById(R.id.help);
                    title.setText("OPEN");
                    img.setImageResource(R.drawable.ic_action_cameraa);
                    message.setText("do you want to open camera or gallery  ");
                    dateAlt.setView(view);
                    acc.setText("CAMERA");
                    nacc.setText("GALLERY ");
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                            ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                        }
                    });
                    dialog.show();

                    return false;
                }
            });



            startad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy0.setVisibility(View.GONE);
                    layy1.setVisibility(View.VISIBLE);
                }
            });
            nxt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy1.setVisibility(View.GONE);
                    layy2.setVisibility(View.VISIBLE);
                }
            });
            nxt2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layy2.setVisibility(View.VISIBLE);
                    if (djaz==true){
                        layy4.setVisibility(View.VISIBLE);
                    }
                    else{
                        layy3.setVisibility(View.VISIBLE);
                        djaz=true;
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
                    layy4.setVisibility(View.GONE);
                    layy5.setVisibility(View.VISIBLE);

                }
            });
            nxt4.setOnClickListener(new View.OnClickListener() { //Button OK
                @Override
                public void onClick(View view) {

                    Fact.setName(oneNom.getText().toString());
                    Fact.setPrenom(oneprenom.getText().toString());
                    Fact.setPhone(OnTlf.getText().toString());
                    Fact.setJob(oneJob.getText().toString());
                    Fact.setAdress(oneAdress.getText().toString());
                    Fact.setEmail(oneEmail.getText().toString());
                    Fact.setFacebook(facebook.getText().toString());
                    Fact.setLinkedIn(LinkedIn.getText().toString());
                    Fact.setTwitter(Twitter.getText().toString());
                    Fact.setNom(entreprise.getText().toString());
                    Fact.setNif(nif.getText().toString());
                    Fact.setRg(rg.getText().toString());
                    Fact.setAddress(compaddres.getText().toString());
                    Fact.setPhone_num(comtlf.getText().toString());
                    Fact.setComEmail(compEmail.getText().toString());
                    Fact.setSite(site.getText().toString());
                    Fact.setFax(fax.getText().toString());
                    Fact.setStatujur(statujur.getText().toString());
                    Fact.setSecteur(secteur.getText().toString());
                    Fact.setTaille(taille.getText().toString());

                    Fact.setImage(Imageviewtobyte(photop));
                    Fact.setFactlogo(Imageviewtobyte(logo));
                    db.QueryData();
                    db.InsertDataEntreprise(Fact.getNif(),Fact.getNom(),Fact.getRg(),Fact.getSecteur(),Fact.getTaille(),Fact.getStatujur(),Fact.getComEmail(),Fact.getPhone_num(),Fact.getAddress(),Fact.getSite(),Fact.getFax(),Fact.getFactlogo());
                    db.InsertDataOperateur(Fact.getName(),Fact.getPrenom(),Fact.getNif(),Fact.getNom(),Fact.getJob(),Fact.getPhone(),Fact.getEmail(),Fact.getAdress(),Fact.getLinkedIn(),Fact.getFacebook(),Fact.getTwitter(),Fact.getImage());
                    db.InsertDataFounisseur(Fact.getName(),Fact.getPrenom());

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
            dateAlt.setView(view);
            final AlertDialog dialog2 = dateAlt.create();

            dialog2.show();

        }

        ArrayAdapter<String> dataAda= new ArrayAdapter<String>(ajtproduit2.this, android.R.layout.simple_dropdown_item_1line, llist);
        //ArrayAdapter<String> dataAda = ArrayAdapter.createFromResource(getApplicationContext(),llist,simple_list_item_1 );
        dataAda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        supplier.setAdapter(dataAda);
    }
});



        supplier.setThreshold(1);
        supplier.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                supplier.showDropDown();
                return false;
            }
        });






























/*supplier.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Intent intent = new Intent(ajtproduit2.this, factoriesList.class);
        startActivity(intent);
        return false;
    }
});*/

        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_ask, null);
                TextView title = (TextView) view.findViewById(R.id.ertitle);
                TextView message = (TextView) view.findViewById(R.id.messageer);
                Button acc = (Button) view.findViewById(R.id.btn_acc);
                Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                ImageView img = (ImageView) view.findViewById(R.id.help);
                title.setText("OPEN");
                img.setImageResource(R.drawable.ic_action_cameraa);
                message.setText("do you want to open camera or gallery  ");
                dateAlt.setView(view);
                acc.setText("CAMERA");
                nacc.setText("GALLERY ");
                final AlertDialog dialog = dateAlt.create();
                acc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(ajtproduit2.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{
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
                        ActivityCompat.requestPermissions(ajtproduit2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);

                    }
                });
                dialog.show();

                return false;
            }
        });
        //programming name edit text
        nom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nom.getText().toString().trim().equalsIgnoreCase("")) {

                    textInputLayout2.setError("This field can not be blank");

                } else {
                    textInputLayout2.setError(null);
                }

            }
        });


//    Quantity edit text
        qntete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (qntete.getText().toString().trim().equalsIgnoreCase("")) {
                    qntete.setError("This field can not be blank");
                } else {
                    qntete.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (qntete.getText().toString().trim().equalsIgnoreCase("")) {
                    qntete.setError("This field can not be blank");
                } else {
                    qntete.setError(null);
                }
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_view: {
                        Intent intent = new Intent(ajtproduit2.this, ViewlistP.class);
                        intent.putExtra("Nom", nomm);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.nav_trash: {
                        Intent intent = new Intent(ajtproduit2.this, Trash.class);
                        intent.putExtra("Nom", nomm);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        // price edit text
        prix.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (prix.getText().toString().trim().equalsIgnoreCase("")) {
                    prix.setError("This field can not be blank");
                } else {
                    prix.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (prix.getText().toString().trim().equalsIgnoreCase("")) {
                    prix.setError("This field can not be blank");
                } else {
                    prix.setError(null);
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((nom.getText().toString().isEmpty()) || (qntete.getText().toString().isEmpty()) || (prix.getText().toString().isEmpty())) {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ajtproduit2.this);
                    final View view = LayoutInflater.from(ajtproduit2.this).inflate(R.layout.ipic_alrt_neg, null);
                    TextView title = (TextView) view.findViewById(R.id.ertitlen);
                    TextView message = (TextView) view.findViewById(R.id.messageern);
                    Button acc = (Button) view.findViewById(R.id.btn_acc);
                    Button nacc = (Button) view.findViewById(R.id.btn_nacc);
                    ImageView img = (ImageView) view.findViewById(R.id.help);
                    title.setText("Empty Fileds");
                    message.setText("Please  fill all fields ! ");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else {
                    byte[] bytes = Imageviewtobyte(image);
                    p.setCode(Code);
                    p.setQuantite(Integer.parseInt(qntete.getText().toString()));
                    p.setName(nom.getText().toString());
                    p.setPrice(parseDouble(prix.getText().toString()));
                    p.setClient(customer.getText().toString());
                    p.setFournisseur(supplier.getText().toString());
                    p.setmesure(Unit.getText().toString());
                    p.setCoin(coins.getText().toString());
                    p.setCoins(scoins.getText().toString());
                    p.setCategorie(Categorie.getText().toString());
                    p.setSous(Scategorie.getText().toString());
                    p.setPrixS(parseDouble(prixS.getText().toString()));
                    p.setDescription(desc.getText().toString());
                    p.setQttmin(Integer.parseInt(qttmin.getText().toString()));
                    Date ENTTR = new Date();
                    bd.InsertDataProduit(p.getCode(), p.getName(), p.getFab(), p.getExp(), p.getCategorie(), p.getSous(), p.getMatiere(), p.getQuantite(), p.getPrice(), p.getTypePr(), ENTTR, p.getCoin(), p.getCoins(), p.getmesure(), p.getPrixS(), p.getDescription(), p.getFournisseur(), p.getClient(), nomm, p.getQttmin(), p.getDateDel(), bytes);

                    Toast.makeText(getApplicationContext(), "ADDING DONE ", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "you don'thave permission", Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = ImageViewHalper.ImageFromDrawable(ajtproduit2.this, bitmap);
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = ImageViewHalper.ImageFromDrawable(ajtproduit2.this, bitmap);
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

    public static int CompareDate(java.util.Date d1, java.util.Date d2) {
        /* if d1 apres d2 : return 1
           if d1 avant d2   : return 2
           if d1=d2 : return 0
         */
        if (d1.getYear() > d2.getYear()) {
            return 1;
        } else if (d1.getYear() < d2.getYear()) {
            return 2;
        } else {
            if (d1.getMonth() > d2.getMonth()) {
                return 1;
            } else if (d1.getMonth() < d2.getMonth()) {
                return 2;
            } else {
                if (d1.getDate() > d2.getDate()) {
                    return 1;
                } else if (d1.getDate() < d2.getDate()) {
                    return 2;
                } else {
                    return 0;
                }

            }
        }

    }
}
