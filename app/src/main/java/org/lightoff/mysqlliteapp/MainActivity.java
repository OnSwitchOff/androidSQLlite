package org.lightoff.mysqlliteapp;

import android.Manifest;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productList;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
        ExternalStorageImageManager im = new ExternalStorageImageManager(this);
        im.createExternalStoragePublicPicture(R.drawable.mars,"Mars.jpg");
        im.createExternalStoragePublicPicture(R.drawable.bounty,"Bounty.jpg");
        im.createExternalStoragePublicPicture(R.drawable.nuts,"Nuts.jpg");
        setContentView(R.layout.activity_main);

        productList = (ListView)findViewById(R.id.list);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = productAdapter.getItem(position);
                if(product!=null) {
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<Product> products = adapter.getProducts();


        productAdapter = new ProductAdapter(this, R.layout.product_item, products);
        productList.setAdapter(productAdapter);
        adapter.close();
    }
    // по нажатию на кнопку запускаем ProductActivity для добавления данных
    public void add(View view){
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

}
