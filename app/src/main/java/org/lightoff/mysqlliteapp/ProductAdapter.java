package org.lightoff.mysqlliteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
        TextView unitView = (TextView) view.findViewById(R.id.itemUnit);
        final TextView itemId = (TextView) view.findViewById(R.id.itemId);

        Product product = products.get(position);
        //Log.d("--", String.valueOf(product.getPic())+"="+String.valueOf(R.drawable.bounty));
//        picView.setImageResource(R.drawable.bounty);
        Bitmap bitmap = BitmapFactory.decodeFile(product.getPic());
        picView.setImageBitmap(bitmap);
        titleView.setText(product.getName());
        countView.setText(String.valueOf(product.getCount()));
        unitView.setText(product.getUnit());
        itemId.setText(String.valueOf(product.getId()));


        Button removeButton = (Button) view.findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("--", itemId.getText().toString());
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click","item");
                Intent intent = new Intent(getContext().getApplicationContext(), ProductActivity.class);
                intent.putExtra("id", Integer.valueOf(itemId.getText().toString()));
                intent.putExtra("click", 25);
                getContext().startActivity(intent);
            }
        });


        return view;
    }
}
