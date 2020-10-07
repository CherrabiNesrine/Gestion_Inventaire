package com.example.logg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewlistP extends SidebarMenu {
    ListView gridView;
    ArrayList<Produit> prdcts;
    static GridAdapter adpter = null;
    DataBaseM db;
    String typee = "";
    String unitt = "";
    ArrayList<String> NOM = new ArrayList<>();
    ArrayList<String> CODE = new ArrayList<>();
    ArrayList<byte[]> Images = new ArrayList<>();
    ArrayList<String> dateeE = new ArrayList<>();
    private ArrayList<String> selectedStrings;
    Produit p = new Produit();
    ImageView imv ;
    TextView empt;
    BottomNavigationView navigationView;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<String> DelItem = new ArrayList<>();
    String nomm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final Intent intent = getIntent();

        if (intent != null) {


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


        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_viewlist_p, null, false);
        drawer.addView(contentView, 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Product's list");


        db = new DataBaseM(this);
        gridView = (ListView) findViewById(R.id.gridview);
        prdcts = new ArrayList<>();


        navigationView = (BottomNavigationView) findViewById(R.id.Bottom_nav);
        navigationView.setSelectedItemId(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add: {

                        Intent intent = new Intent(ViewlistP.this, AjtProduit.class);
                        intent.putExtra("Nom", nomm);
                        intent.putExtra("type", typee);
                        intent.putExtra("unit", unitt);
                        startActivity(intent);

                        overridePendingTransition(0, 0);
                        return true;
                    }


                }
                return false;
            }
        });
        selectedStrings = new ArrayList<>();

        adpter = new GridAdapter(this, NOM, CODE, Images, dateeE);
        gridView.setAdapter(adpter);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        db.QueryData();
        Toast.makeText(getApplicationContext(), nomm, Toast.LENGTH_LONG).show();

        Cursor cursor = db.getData("SELECT * FROM prod where dateDel ='01/01/2000'");
        prdcts.clear();
        if (cursor==null || cursor.getCount()<=0){
            imv =(ImageView)findViewById(R.id.empty);
            empt =(TextView)findViewById(R.id.emptyTxt);
            imv.setVisibility(View.VISIBLE);
            empt.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
        else {
            while (cursor.moveToNext()) {
                if (cursor.getString(16).equals(nomm)) {
                    String code = cursor.getString(0);
                    String nom = cursor.getString(1);
                    byte[] image = cursor.getBlob(21);
                    NOM.add(nom);
                    CODE.add(code);
                    Images.add(image);
                    dateeE.add(cursor.getString(10));

               /* if (NOM.size()!=0){
                 txt.setText("PRODUCT'S LIST "); }
                else{
                    txt.setVisibility(View.VISIBLE);
                    txt.setText("Nothing to show ");
                }*/
                    adpter.notifyDataSetChanged();
                }
            }
        }


        gridView.setMultiChoiceModeListener(new MultiChoiceModeListener());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent = new Intent(ViewlistP.this, Miseajour.class);
                intent.putExtra("Nomm", NOM.get(position));
                intent.putExtra("code", CODE.get(position));
                intent.putExtra("img", Images.get(position));
                intent.putExtra("Nom", nomm);
                intent.putExtra("type", typee);
                intent.putExtra("unit", unitt);
                startActivity(intent);
            }
        });


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
            mode = null;
            ActionBar actionBar = getSupportActionBar();

            actionBar.show();
            return true;
        }

        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_trach: {
                    final AlertDialog.Builder dateAlt = new AlertDialog.Builder(ViewlistP.this);
                    final View view = LayoutInflater.from(ViewlistP.this).inflate(R.layout.del, null);
                    TextView title = (TextView) view.findViewById(R.id.titledel);
                    TextView message = (TextView) view.findViewById(R.id.messageerdel);
                    Button acc = (Button) view.findViewById(R.id.btn_accc);
                    Button nacc = (Button) view.findViewById(R.id.cancel);
                    title.setText("");
                    message.setText("Do you really want to delete this product ");
                    dateAlt.setView(view);
                    final AlertDialog dialog = dateAlt.create();
                    acc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date d = new Date();
                            ContentValues values = new ContentValues();
                            values.put("dateDel", sdf.format(d));
                            for (int i = 0; i < DelItem.size(); i++) {
                                db.Delete("purchase","Id=?",new String[]{DelItem.get(i)});
                                db.Delete("sold","Id=?",new String[]{DelItem.get(i)});
                                db.Delete("prod","ID=?",new String[]{DelItem.get(i)});
                                NOM.remove(i);
                                CODE.remove(i);
                                Images.remove(i);
                                dateeE.remove(i);
                                adpter.notifyDataSetChanged();

                            }


                            dialog.dismiss();
                            ViewlistP.adpter.notifyDataSetChanged();


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
            Toast.makeText(getApplicationContext(), "add new item ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ViewlistP.this, AjtProduit.class);
            intent.putExtra("Nom", nomm);
            intent.putExtra("type", typee);
            intent.putExtra("unit", unitt);
            startActivity(intent);
        }
        return true;
    }

    public class GridAdapter extends BaseAdapter {
        private Context mContext;
        private final ArrayList<String> Itnom;
        private final ArrayList<String> Itcode;
        private final ArrayList<byte[]> imageId;
        private final ArrayList<String> DateE;


        public GridAdapter(Context c, ArrayList<String> Itnom, ArrayList<String> Itcode, ArrayList<byte[]> imageId, ArrayList<String> dateE) {
            mContext = c;
            this.Itnom = Itnom;
            this.Itcode = Itcode;
            this.imageId = imageId;
            this.DateE = dateE;

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
        public View getView(int position, View convertView, ViewGroup parent) {
            // CheckableLayout l;


            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.grid, null);
            }

            TextView nom = (TextView) convertView.findViewById(R.id.tnom);
            TextView dtentr = (TextView) convertView.findViewById(R.id.dtentr);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imv);
            TextView code = (TextView) convertView.findViewById(R.id.tcode);

            if (Itnom.size() != 0) {
                nom.setText(Itnom.get(position));
            }
            if (Itcode.size() != 0) {
                code.setText(Itcode.get(position));
            }

            if (DateE.size() != 0) {
                try {
                    Date d = sdf.parse(DateE.get(position));
                    dtentr.setText(d.getDate() + "," + conv(d.getMonth() + 1));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (imageId.size() != 0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(imageId.get(position), 0, imageId.get(position).length);
                bmp = ImageViewHalper.ImageFromDrawable(ViewlistP.this, bmp);
                imageView.setImageBitmap(bmp);
            }

            //  l = (CheckableLayout) convertView;
            //ResolveInfo info = mApps.get(position);


            return convertView;


        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(ViewlistP.this, AjtProduit.class);
            intent.putExtra("Nom", nomm);
            intent.putExtra("type", typee);
            intent.putExtra("unit", unitt);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public String conv(int i) {
        switch (i) {
            case 1:
                return "january";
            case 2:
                return "february";
            case 3:
                return "marsh";
            case 4:
                return "april";
            case 5:
                return "mai";
            case 6:
                return "june";
            case 7:
                return "july";
            case 8:
                return "august";
            case 9:
                return "september";
            case 10:
                return "october";
            case 11:
                return "november";
            case 12:
                return "december";
            default:
                return "wrong the year have 12 months ! ";
        }
    }

}