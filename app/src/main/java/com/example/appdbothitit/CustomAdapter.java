package com.example.appdbothitit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Thoitiet> arrayList;

    public CustomAdapter(Context context, ArrayList<Thoitiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view =inflater.inflate(R.layout.donglistview,null);

        Thoitiet thoitiet =arrayList.get(i);
        TextView txtDay = view.findViewById(R.id.textviewNgay);
        TextView txtStatus = view.findViewById(R.id.textviewTrangthai);
        TextView txtMaxtemp = view.findViewById(R.id.textViewMax);
        TextView txtMintemp = view.findViewById(R.id.textViewMin);
        ImageView imgStatus = (ImageView) view.findViewById(R.id.imageviewTrangthai);


        txtDay.setText(thoitiet.Day);
        txtStatus.setText(thoitiet.status);
        txtMaxtemp.setText(thoitiet.Maxtemp+"°C");
        txtMintemp.setText(thoitiet.Mintemp+"°C");

        Picasso.get().load("https://openweathermap.org/img/wn/"+thoitiet.Image+".png").into(imgStatus);



        return view;
    }
}
