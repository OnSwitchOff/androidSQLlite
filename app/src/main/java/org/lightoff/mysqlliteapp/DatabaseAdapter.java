package org.lightoff.mysqlliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter {

    private DataBaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DataBaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {
                DataBaseHelper.COLUMN_ID,
                DataBaseHelper.COLUMN_NAME,
                DataBaseHelper.COLUMN_COUNT,
                DataBaseHelper.COLUMN_UNIT,
                DataBaseHelper.COLUMN_PIC
        };
        return  database.query(DataBaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public List<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
                int count = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_COUNT));
                String unit = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_UNIT));
                int pic = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PIC));
                products.add(new Product(id,name,count,unit,pic));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  products;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DataBaseHelper.TABLE);
    }

    public Product getProduct(int id){
        Product product = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DataBaseHelper.TABLE, DataBaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            int count = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_COUNT));
            String unit = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_UNIT));
            int pic = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PIC));
            product = new Product(id,name,count,unit,pic);
        }
        cursor.close();
        return  product;
    }

    public long insert(Product product){

        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, product.getName());
        cv.put(DataBaseHelper.COLUMN_COUNT, product.getCount());
        cv.put(DataBaseHelper.COLUMN_UNIT, product.getUnit());
        cv.put(DataBaseHelper.COLUMN_PIC, product.getPic());

        return  database.insert(DataBaseHelper.TABLE, null, cv);
    }

    public long delete(int productId){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(productId)};
        return database.delete(DataBaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Product product){

        String whereClause = DataBaseHelper.COLUMN_ID + "=" + String.valueOf(product.getId());
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.COLUMN_NAME, product.getName());
        cv.put(DataBaseHelper.COLUMN_COUNT, product.getCount());
        cv.put(DataBaseHelper.COLUMN_UNIT, product.getUnit());
        cv.put(DataBaseHelper.COLUMN_PIC, product.getPic());

        return database.update(DataBaseHelper.TABLE, cv, whereClause, null);
    }
}
