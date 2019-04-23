package com.example.admin.synchronisation_tstart.affichage;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.synchronisation_tstart.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */
public class Adapter_stat_module extends BaseAdapter {

    ArrayList<stat_module> myList = new ArrayList<stat_module>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_stat_module(Context context, ArrayList<stat_module> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public stat_module getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_stat_module, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        stat_module currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.nm.setText(""+currentListData.getNom()+ "  "+currentListData.getPrenom());
        mViewHolder.ca.setText("N° carte : "+currentListData.getCarte());
        mViewHolder.te.setText("Telephone : "+currentListData.getTelephone());



        double pour =  ((double) currentListData.getPresence() / currentListData.getTotal()) *100;
        DecimalFormat df2 = new DecimalFormat("#.##");
        mViewHolder.po.setText("" + df2.format(pour) + "%" );


        if(pour>49) {
            //mViewHolder.po.setText("" + currentListData.getPresence() + "/" + currentListData.getTotal());
            mViewHolder.po.setTextColor(Color.parseColor("#05c46b"));
        }else{
            mViewHolder.po.setTextColor(Color.parseColor("#ff3f34"));

        }


        return convertView;
    }




    private class MyViewHolder {
        TextView  nm, ca , te , po;

        //  CheckBox et;
        public MyViewHolder(View item) {
            nm= (TextView) item.findViewById(R.id.nom);
            ca= (TextView) item.findViewById(R.id.carte);
            te= (TextView) item.findViewById(R.id.telephone);
            po= (TextView) item.findViewById(R.id.pourcentage);

        }
    }
}
