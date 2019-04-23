package com.example.admin.synchronisation_tstart.affichage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.synchronisation_tstart.R;
import com.example.admin.synchronisation_tstart.affichage.list_contact;

import java.util.ArrayList;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */
public class Adapter_affichage extends BaseAdapter {

    ArrayList<list_contact> myList = new ArrayList<list_contact>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_affichage(Context context, ArrayList<list_contact> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public list_contact getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_contact, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        list_contact currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.no.setText(""+currentListData.getNom2()+" "+currentListData.getPrenom() );
        mViewHolder.te.setText(""+currentListData.getTelephone());
        mViewHolder.ad.setText(""+currentListData.getAdresse());
        mViewHolder.carte.setText("ID carte : "+currentListData.getIdcarte());
        mViewHolder.grp.setText("Groupe : "+currentListData.getGroup());


        return convertView;
    }




    private class MyViewHolder {
        TextView  no, te, ad, carte, grp;

        //  CheckBox et;
        public MyViewHolder(View item) {
            no = (TextView) item.findViewById(R.id.nom);
            te= (TextView) item.findViewById(R.id.telephone);
            ad= (TextView) item.findViewById(R.id.adresse);
            carte= (TextView) item.findViewById(R.id.idc);
            grp= (TextView) item.findViewById(R.id.grp);

        }
    }
}
