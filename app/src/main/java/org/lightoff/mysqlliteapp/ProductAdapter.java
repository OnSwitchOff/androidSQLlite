package org.lightoff.mysqlliteapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView picView = (ImageView) view.findViewById(R.id.itemPic);
        TextView titleView = (TextView) view.findViewById(R.id.itemTitle);
        TextView countView = (TextView) view.findViewById(R.id.itemCount);

        Product product = products.get(position);
        Log.d("--", String.valueOf(product.getPic())+"="+String.valueOf(R.drawable.bounty));
        picView.setImageResource(R.drawable.bounty);
        titleView.setText(product.getName());
        countView.setText(String.valueOf(product.getCount()));

        return view;
    }
}
