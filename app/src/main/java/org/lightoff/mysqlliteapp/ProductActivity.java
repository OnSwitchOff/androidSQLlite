package org.lightoff.mysqlliteapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class ProductActivity extends AppCompatActivity {

    private EditText nameBox;
    private EditText countBox;
    private EditText unitBox;
    private ImageView picBox;
    static String ImagePath=null;
    private Button picButton;
    private Button saveButton;



    private DatabaseAdapter adapter;
    private int productId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getInt("id");
            ImagePath=extras.getString("selectedImgPath");
        }

//

        setContentView(R.layout.activity_product);

        nameBox = (EditText) findViewById(R.id.name);
        countBox = (EditText) findViewById(R.id.count);
        unitBox = (EditText) findViewById(R.id.unit);
        picBox = (ImageView) findViewById(R.id.pic);
        picButton = (Button) findViewById(R.id.picButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        adapter = new DatabaseAdapter(this);


        // если 0, то добавление
        if (productId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Product product = adapter.getProduct(productId);
            nameBox.setText(product.getName());
            countBox.setText(String.valueOf(product.getCount()));
            unitBox.setText(product.getUnit());
            Log.d("-",String.valueOf(product.getPic()));
            Log.d("-",String.valueOf(R.drawable.bounty));
            Bitmap bitmap = BitmapFactory.decodeFile(product.getPic());
            picBox.setImageBitmap(bitmap);
            Log.d("+","1");
            adapter.close();
        }
    }

    public void save(View view){

        String name = nameBox.getText().toString();
        int count = Integer.parseInt(countBox.getText().toString());
        String unit = unitBox.getText().toString();
        if (ImagePath==null){
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File fileB = new File(path, "Bounty.jpg");
            ImagePath = fileB.getAbsolutePath();
        }

        Product product = new Product(productId, name, count, unit, ImagePath);

        adapter.open();
        if (productId > 0) {
            adapter.update(product);
        } else {
            adapter.insert(product);
        }
        adapter.close();
        goHome();
    }

    public static void changePic(View view,String selectedImgPath){
        ImageView logoView = (ImageView) view.findViewById(R.id.pic);
        ImagePath = selectedImgPath;
        Bitmap bitmap = BitmapFactory.decodeFile(ImagePath);
        logoView.setImageBitmap(bitmap);
    }

    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void showDialog(View v) {

        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");

    }


}
