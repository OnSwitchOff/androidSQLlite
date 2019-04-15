package org.lightoff.mysqlliteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ProductActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText countBox;
    private EditText unitBox;
    private ImageView picBox;
    private Button picButton;
    private Button saveButton;

    private DatabaseAdapter adapter;
    private int productId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        nameBox = (EditText) findViewById(R.id.name);
        countBox = (EditText) findViewById(R.id.count);
        unitBox = (EditText) findViewById(R.id.unit);
        picBox = (ImageView) findViewById(R.id.pic);
        picButton = (Button) findViewById(R.id.picButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        adapter = new DatabaseAdapter(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getInt("id");
        }
        // если 0, то добавление
        if (productId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Product product = adapter.getProduct(productId);
            nameBox.setText(product.getName());
            countBox.setText(String.valueOf(product.getCount()));
            unitBox.setText(product.getUnit());
            picBox.setImageResource(product.getPic());
            adapter.close();
        }
    }

    public void save(View view){

        String name = nameBox.getText().toString();
        int count = Integer.parseInt(countBox.getText().toString());
        String unit = unitBox.getText().toString();
        int pic = picBox.getId();
        Product product = new Product(productId, name, count, unit, pic);

        adapter.open();
        if (productId > 0) {
            adapter.update(product);
        } else {
            adapter.insert(product);
        }
        adapter.close();
        goHome();
    }

    public void changePic(View view){

    }
    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
