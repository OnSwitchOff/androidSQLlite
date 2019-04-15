package org.lightoff.mysqlliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView productList;
    ArrayAdapter<Product> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("1","1");

        productList = (ListView)findViewById(R.id.list);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product =arrayAdapter.getItem(position);
                if(product!=null) {
                    Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                    intent.putExtra("id", product.getId());
                    intent.putExtra("click", 25);
                    startActivity(intent);
                }
            }
        });
        Log.d("2","2");
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseAdapter adapter = new DatabaseAdapter(this);
        adapter.open();

        List<Product> products = adapter.getProducts();


        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        productList.setAdapter(arrayAdapter);
        adapter.close();
    }
    // по нажатию на кнопку запускаем ProductActivity для добавления данных
    public void add(View view){
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }
}
