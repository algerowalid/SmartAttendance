package com.example.admin.synchronisation_tstart.affichage;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.synchronisation_tstart.R;

import java.util.ArrayList;

/**
 * Created by Eurequat-Algerie on 25/04/2017.
 */
public class Adapter_programme extends BaseAdapter {

    ArrayList<programme> myList = new ArrayList<programme>();
    LayoutInflater inflater;
    Context context;
    int firstColorValue = Color.parseColor("#dff0f7");
    int secondColorValue = Color.parseColor("#f8fefe");
    private int[] colors = new int[] { firstColorValue, secondColorValue};


    public Adapter_programme(Context context, ArrayList<programme> myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }


    public int getCount() {
        return myList.size();
    }

    public programme getItem(int position) {
        return myList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.element_programme, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        programme currentListData = getItem(position);

        int colorPos = position % colors.length;
        convertView.setBackgroundColor(colors[colorPos]);

        mViewHolder.de.setText(""+currentListData.getDesignation());
        mViewHolder.mod.setText(""+currentListData.module);

        if(currentListData.getIdc()==0) {
            mViewHolder.hd.setText("Cours : " + currentListData.getHdd()+" -> "+currentListData.getHf());
        }else{
            mViewHolder.hd.setText("TP ou TD: " + currentListData.getHdd()+" -> "+currentListData.getHf());


        }

        return convertView;
    }




    private class MyViewHolder {
        TextView  de, mod, hd;

        //  CheckBox et;
        public MyViewHolder(View item) {
            de= (TextView) item.findViewById(R.id.designation);
            mod= (TextView) item.findViewById(R.id.module);
            hd= (TextView) item.findViewById(R.id.horaire);

        }
    }
}
