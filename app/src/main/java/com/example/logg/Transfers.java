package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Transfers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Transfers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transfers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    String Etat="";
    private String mParam2;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private OnFragmentInteractionListener mListener;

    public Transfers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Transfers.
     */
    // TODO: Rename and change types and number of parameters
    public static Transfers newInstance(String param1, String param2) {
        Transfers fragment = new Transfers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView= inflater.inflate(R.layout.fragment_transfers, container, false);
        final ArrayList<String> wrh1=new ArrayList<>();
        final ArrayList<String> wrh2=new ArrayList<>();
        final ArrayList<String> dates=new ArrayList<>();
        final ArrayList<String> CODE=new ArrayList<>();
        final ArrayList<byte[]> Images=new ArrayList<>();
        ImageView imv ;
        TextView empt;
        ListView listView = (ListView)rootView.findViewById(R.id.lstDocSale);
        final TrnsfAdp adpter = new TrnsfAdp(rootView.getContext(), wrh1,wrh2,CODE ,Images,dates);
        listView.setAdapter(adpter);
        final DataBaseM db= new DataBaseM(rootView.getContext());
        db.QueryData();

        Cursor cursor = db.getData("SELECT * FROM transfert ");
        if (cursor==null || cursor.getCount()<=0){
            imv =(ImageView)rootView.findViewById(R.id.empty);
            empt =(TextView)rootView.findViewById(R.id.emptyTxt);
            imv.setVisibility(View.VISIBLE);
            empt.setVisibility(View.VISIBLE);
            empt.setText("No forwards operation  found ");
            listView.setVisibility(View.GONE);
        }
        else {
            while (cursor.moveToNext()) {
                String code = cursor.getString(1);
                String warh1 = cursor.getString(4);
                String warh2 = cursor.getString(5);
                String dattte= cursor.getString(3);
                Cursor[] cursor2 = {db.getData("SELECT * FROM prod where ID='"+code+"'")};
                while (cursor2[0].moveToNext()) {
                    byte[] image = cursor2[0].getBlob(21);
                    Images.add(image);
                    wrh1.add(warh1);
                    wrh2.add(warh2);
                    CODE.add(code);
                    dates.add(dattte);
                    adpter.notifyDataSetChanged();
                }

            }
        }
        Button btn = (Button)rootView.findViewById(R.id.trnsfADD);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder addTrans = new AlertDialog.Builder(rootView.getContext());
                final View view3 = LayoutInflater.from(rootView.getContext()).inflate(R.layout.trnsfralert, null);
                addTrans.setView(view3);
                final AlertDialog dialog2 = addTrans.create();
                dialog2.show();
                final AutoCompleteTextView prd = (AutoCompleteTextView) view3.findViewById(R.id.prd);
                final EditText qnt=(EditText) view3.findViewById(R.id.prdQnt);

                final AutoCompleteTextView wareh1 = (AutoCompleteTextView) view3.findViewById(R.id.wareH1);
                final AutoCompleteTextView wareh2 = (AutoCompleteTextView) view3.findViewById(R.id.wareH2);
                Button kk = (Button) view3.findViewById(R.id.btnOK);
                TextView DateTR = (TextView)view3.findViewById(R.id.DateTrn);
                final TextInputLayout lay1 = (TextInputLayout) view3.findViewById(R.id.trlay1);
                final TextInputLayout lay2 = (TextInputLayout) view3.findViewById(R.id.trlay2);
                final TextInputLayout lay3 = (TextInputLayout) view3.findViewById(R.id.trlay3);
                final TextInputLayout lay4 = (TextInputLayout) view3.findViewById(R.id.trlayqnt);
                final Spinner EtatP = (Spinner)view3.findViewById(R.id.etatP);

                final Date TRNFR = new Date();
                String STRNFR= sdf.format(TRNFR);
                DateTR.setText(STRNFR);
                ArrayList<String> CODE = new ArrayList<>();
                final ArrayList<String> MAG1 = new ArrayList<>();
                final ArrayList<String> MAG2 = new ArrayList<>();
                db.QueryData();
                Cursor cursor = db.getData("SELECT * FROM prod where dateDel = '01/01/2000' ");

                while(cursor.moveToNext()){
                    String code= cursor.getString(0);
                    String mag1=cursor.getString(16);


                    CODE.add(code);
                    MAG1.add(mag1);
                }

                ArrayAdapter<CharSequence> EtatADP = ArrayAdapter.createFromResource(rootView.getContext(),R.array.EtatP,android.R.layout.simple_spinner_item);
                EtatADP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                EtatP.setAdapter(EtatADP);
                Etat=EtatP.getSelectedItem().toString();



                final ArrayAdapter<String> dataAda = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, CODE);
                dataAda.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                prd.setThreshold(0);
                prd.setAdapter(dataAda);
                prd.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        prd.showDropDown();
                        return false;
                    }
                });
                prd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        wareh1.setText(MAG1.get(i));
                        wareh1.setEnabled(false);
                    }
                });
                prd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        wareh1.setText(MAG1.get(i));
                        wareh1.setEnabled(false);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                Cursor cursor1 = db.getData("SELECT * FROM mag");
                while (cursor1.moveToNext()){
                    String mag2=cursor1.getString(0);
                    MAG2.add(mag2);
                }
                final ArrayAdapter<String> dataAda2 = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, MAG2);
                dataAda2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

                wareh2.setAdapter(dataAda2);
                wareh2.setThreshold(0);
                wareh2.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        wareh2.showDropDown();
                        return false;
                    }
                });


                kk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String vrf= prd.getText().toString();
                        boolean valide1= false;
                        boolean valide2= false;
                        boolean valide3= false;
                        long qnttt = 0;
                        if (qnt.getText().toString().isEmpty()){
                            qnt.setText("1");
                        }

                        Cursor cursorr = db.getData("SELECT * FROM prod where ID = '"+vrf+"'");
                        while (cursorr.moveToNext()){
                            qnttt= cursorr.getLong(7);
                        }
                        if (Long.parseLong(qnt.getText().toString())> qnttt){
                            lay4.setError("bigger then quantity in warehouse");
                        }
                        else {
                            String warehouse1 = wareh1.getText().toString();
                            String Warehouse2 = wareh2.getText().toString();
                            String typwr1 = "";
                            String typwr2 = "";
                            Cursor cursorr2 = db.getData("SELECT * from mag where nomMAg= '" + warehouse1 + "'");
                            while (cursorr2.moveToNext()) {
                                typwr1 = cursorr2.getString(1);

                            }
                            Cursor cursorr3 = db.getData("SELECT * from mag where nomMAg= '" + Warehouse2 + "'");
                            while (cursorr3.moveToNext()) {
                                typwr2 = cursorr3.getString(1);

                            }
                            if (!typwr1.equals(typwr2)) {
                                lay3.setError("warehouse type not match ");
                            } else if (!prd.getText().toString().isEmpty()) {
                                db.InsertDataTranfert(prd.getText().toString(), Long.parseLong(qnt.getText().toString()), TRNFR, warehouse1, Warehouse2,Etat);
                                CODE.add(prd.getText().toString());
                                wrh1.add(warehouse1);
                                wrh2.add(Warehouse2);
                                dates.add(sdf.format(TRNFR));
                                adpter.notifyDataSetChanged();
                                dialog2.dismiss();
                                adpter.notifyDataSetChanged();

                                Toast.makeText(rootView.getContext(), "done", Toast.LENGTH_LONG).show();
                            }
                           else Toast.makeText(rootView.getContext(),"erreur",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
