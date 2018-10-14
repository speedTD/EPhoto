package com.example.admin.ephoto.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.admin.ephoto.Thuvien;


public class DatabaseThuVien extends SQLiteOpenHelper {
    private static final String COL1="ID";
    private static final String COL2="NAME";
    private static final String COL3="PATH";
    private static final String COL4="DATE_CREATE";
    private static final String DBNAME="Ephoto.db";
    private static final String TABLE_NAME="THUVIEN";
    private static final int VERSION=1;
    private final String TAG="E365";
    private String CREATE_SQL="CREATE TABLE "+TABLE_NAME+" ("+
            COL1+" INTEGER PRIMARY KEY ,"+
            COL2+" TEXT,"+
            COL3+" TEXT,"+
            COL4+" INTEGER)";

    public DatabaseThuVien(Context context) {
        super(context, DBNAME, null, VERSION);
        Log.d(TAG, "DatabaseThuVien  Đã Tạo ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    //hàm Thêm Insert Nè :))
    public long InsertData(String FileName,String filePath){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2, FileName);
        cv.put(COL3, filePath);
        cv.put(COL4, System.currentTimeMillis());
        Log.d(TAG, "InsertData: Đã Thêm dữ liệu  ");
        long rowId = db.insert(TABLE_NAME, null, cv);
        return rowId;
    }


    //Hàm Lấy dữ liệu ra vào
    public Thuvien GetItem(int Postion){
        SQLiteDatabase db=getReadableDatabase();
        //Mảng chứa Các Cột
        String Arr []={COL1, COL2, COL3, COL4};
        //Thuc chất nó là Select * From Table
        Cursor cursor=db.query(TABLE_NAME,Arr,null,null,null,null,null);
        if(cursor.moveToPosition(Postion)){
            Thuvien item=new Thuvien();
            item.setId(cursor.getInt(cursor.getColumnIndex(COL1)));
            item.setName(cursor.getString(cursor.getColumnIndex(COL2)));
            item.setPath(cursor.getString(cursor.getColumnIndex(COL3)));
            item.setDate(cursor.getInt(cursor.getColumnIndex(COL4)));
            cursor.close();
            return item;
        }
        return  null;
    }

    public int getcount() {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {COL1 };
            Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, null);
            int count = c.getCount();
            c.close();
            return count;
    }
    public  void DeleteData(int ID){

       SQLiteDatabase db=getReadableDatabase();
       String [] where={String.valueOf(ID)};
       db.delete(TABLE_NAME,COL1+"=?",where);

    }
}
