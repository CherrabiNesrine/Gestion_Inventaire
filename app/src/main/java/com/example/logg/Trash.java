package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Trash extends SidebarMenu {
    ListView gridView;
    ArrayList<Produit> prdcts;
    static TrashAdapter adpter = null;
    DataBaseM db;
    ArrayList<String> NOM=new ArrayList<>();
    ArrayList<String> CODE=new ArrayList<>();
    ArrayList<byte[]> Images=new ArrayList<>();
    Produit p = new Produit();
    BottomNavigationView navigationView;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<String> DelItem = new ArrayList<>();
    String nomm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        final Intent intent = getIntent();

        if (intent != null) {



            //recupriate the pervious page attribute

            if (intent.hasExtra("Nom")) {
                nomm = intent.getStringExtra("Nom");

            }


        }






        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_trash, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Trash");

        db = new DataBaseM(this);
        gridView = (ListView) findViewById(R.id.gridview);
        prdcts = new ArrayList<>();




        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_trash);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add: {
                        Intent intent = new Intent (Trash.this,AjtProduit.class);
                        intent.putExtra("Nom",nomm);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;
                    }

                    case R.id.nav_view: {
                        Intent intent = new Intent (Trash.this,ViewlistP.class);
                        intent.putExtra("Nom",nomm);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;
                    }
                }
                return false;
            }
        });
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);

        adpter = new TrashAdapter(this, NOM ,CODE ,Images);
        gridView.setAdapter(adpter);
        db.QueryData();
        Cursor cursor = db.getData("SELECT * FROM prod where dateDel <> '01/01/2000'");
        prdcts.clear();
        while (cursor.moveToNext()) {
            if(cursor.getString(16).equals(nomm)){
            String code = cursor.getString(0);
            String nom = cursor.getString(1);
            byte[] image = cursor.getBlob(21);
            NOM.add(nom);
            CODE.add(code);
            Images.add(image);
               /* if (NOM.size()!=0){
                 txt.setText("PRODUCT'S LIST "); }
                else{
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Nothing to show ");
                }*/
            adpter.notifyDataSetChanged();}
        }

        gridView.setMultiChoiceModeListener(new MultiChoiceModeListener());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Trash.this,Miseajour.class);
                intent.putExtra("Nom", NOM.get(position));
                intent.putExtra("code",CODE.get(position));
                intent.putExtra("img",Images.get(position));
                startActivity(intent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.del, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_trach){
            Toast.makeText(getApplicationContext(), "Delted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    public class MultiChoiceModeListener implements
            GridView.MultiChoiceModeListener {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.del, menu);
            mode.setTitle("Select Items");
            mode.setSubtitle("One item selected");
            ActionBar actionBar = getSupportActionBar();

            actionBar.hide();
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            mode=null;
            ActionBar actionBar = getSupportActionBar();

            actionBar.show();
            return true;
        }

        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_trach: {  final AlertDialog.Builder dateAlt = new AlertDialog.Builder(Trash.this);
                    final View view = LayoutInflater.from(Trash.this).inflate(R.layout.del, null);
                    TextView title = (TextView) view.findViewById(R.id.titledel);
                    TextView message = (TextView) view.findViewById(R.id.messageerdel);
                    Button acc = (Button) view.findViewById(R.id.btn_accc);
                    Button nacc = (Button) view.findViewById(R.id.cancel);
                    ImageView img = (ImageView) view.findViewById(R.id.help);
                    title.setText("");
                    message.setText("Do you really want to delete ALL this product definitely");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            db.QueryData();
                            for(int i =0;i<DelItem.size();i++){

                                db.Delete("prod","ID=?", new String[]{DelItem.get(i)});
                            }



                            dialog.dismiss();
                            Trash.adpter.notifyDataSetChanged();



                        }
                    });
                    nacc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();}
                break;
            }
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
            mode=null;
            ActionBar actionBar = getSupportActionBar();

            actionBar.show();

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        public void onItemCheckedStateChanged(ActionMode mode, int position,
                                              long id, boolean checked) {
            int selectCount = gridView.getCheckedItemCount();
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            DelItem.add(CODE.get(position));

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
    public class TrashAdapter extends BaseAdapter {
        private Context mContext;
        private final ArrayList<String> Itnom;
        private final ArrayList<String> Itcode;
        private final ArrayList<byte[]> imageId;

        public TrashAdapter(Context c,  ArrayList<String> Itnom , ArrayList<String> Itcode ,ArrayList<byte[]> imageId) {
            mContext = c;
            this.Itnom = Itnom;
            this.Itcode=Itcode;
            this.imageId = imageId;
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

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.trachgrid, null);
            }

            final TextView code= (TextView) convertView.findViewById(R.id.tcode);
            final TextView nom= (TextView) convertView.findViewById(R.id.tnom);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imv);
            Button drop  = (Button) convertView.findViewById(R.id.drProd);
            Button cancel  = (Button)convertView.findViewById(R.id.cancProd);
            final String CODE = code.getText().toString();

            drop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseM db = new DataBaseM(mContext);
                    db.QueryData();
                    db.Delete("prod","ID=?", new String[]{code.getText().toString()});
                    Trash.adpter.notifyDataSetChanged();


                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseM db = new DataBaseM(mContext);
                    db.QueryData();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("dateDel", "01/01/2000");
                    db.Update("prod",contentValues,"ID=?",new String[]{code.getText().toString()});
                    Trash.adpter.notifyDataSetChanged();
                    ViewlistP.adpter.notifyDataSetChanged();

                }
            });
            nom.setText(Itnom.get(position));
            code.setText(Itcode.get(position));
            Bitmap bmp= BitmapFactory.decodeByteArray(imageId.get(position),0,imageId.get(position).length);
            imageView .setImageBitmap(bmp);
            return convertView;


        }
    }


}
