package com.example.logg;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Sales.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Sales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sales extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Sales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sales.
     */
    // TODO: Rename and change types and number of parameters
    public static Sales newInstance(String param1, String param2) {
        Sales fragment = new Sales();
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
        final View rootView = inflater.inflate(R.layout.fragment_sales, container, false);
        final ArrayList<String> NOM=new ArrayList<>();
        final ArrayList<String> CODE=new ArrayList<>();
        final ArrayList<Long> QNTT=new ArrayList<>();
        final ArrayList<byte[]> Images=new ArrayList<>();
        ArrayList<String> dateeE=new ArrayList<>();
         DcAdapter adpter = null;
        ListView listView = (ListView)rootView.findViewById(R.id.lstDocSale);
        ImageView imv ;
        TextView empt;
        adpter = new DcAdapter(rootView.getContext(), NOM ,CODE ,Images,0,dateeE);
       listView.setAdapter(adpter);
        DataBaseM db= new DataBaseM(rootView.getContext());
        db.QueryData();

        Cursor cus =db.getData("select * from sold ");
        if (cus==null || cus.getCount()<=0){
            imv =(ImageView)rootView.findViewById(R.id.empty);
            empt =(TextView)rootView.findViewById(R.id.emptyTxt);
            imv.setVisibility(View.VISIBLE);
            empt.setVisibility(View.VISIBLE);
            empt.setText("no sale operation found ");
            listView.setVisibility(View.GONE);
        }
        else {
            while (cus.moveToNext()) {
                String code = cus.getString(0);
                long qntt = cus.getLong(2);
                QNTT.add(qntt);
                Cursor c = db.getData("select * from prod where ID = '" + code + "'");
                while (c.moveToNext()) {
                    String nom = c.getString(1);
                    byte[] image = c.getBlob(21);
                    dateeE.add(c.getString(10));
                    Images.add(image);
                    NOM.add(nom);
                    CODE.add(code);
                    adpter.notifyDataSetChanged();
                }


            }

        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(rootView.getContext(),Miseajour.class);
                intent.putExtra("Nomm", NOM.get(i));
                intent.putExtra("code",CODE.get(i));
                intent.putExtra("img",Images.get(i));
                intent.putExtra("where","sales");
                intent.putExtra("qntt",String.valueOf(QNTT.get(i)));

                startActivity(intent);
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
