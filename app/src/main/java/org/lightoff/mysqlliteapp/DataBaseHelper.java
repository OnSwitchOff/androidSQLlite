package org.lightoff.mysqlliteapp;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "productstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "products"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_PIC = "pic";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_COUNT + " INTEGER, "
                + COLUMN_UNIT + " TEXT, "
                + COLUMN_PIC + " INTEGER"
                +");");

        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" ("
                + COLUMN_NAME + ", "
                + COLUMN_COUNT  + ", "
                + COLUMN_UNIT + ", "
                + COLUMN_PIC
                +") VALUES (" +
                "'Bounty', 19, 'unit',"+ R.drawable.bounty+");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
