package com.dtb;

import java.util.ArrayList;
import android.content.*;
import android.database.*;
import android.util.Log;
import java.lang.Override;
import java.lang.Exception;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class dtbs
{
	private static final String Row_word = "Kata";
	
	private static final String Nama_DB = "DatabaseKataku";
	private static final String Nama_Tabel = "Kataku";
	private static final int DB_VERSION = 1;
	private static final String Create_Table = "create table "+Nama_Tabel+" ("+Row_word+" text)";
	private final Context context;
	private DatabaseOpenHelper dbHelper;
	private SQLiteDatabase db;
	
	public dtbs(Context ctx)
	{
		this.context = ctx;
		dbHelper = new DatabaseOpenHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	private static class DatabaseOpenHelper extends SQLiteOpenHelper
	{
		public DatabaseOpenHelper(Context context)
		{
			super(context,Nama_DB,null,DB_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(Create_Table);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db,int oldVer,int newVer)
		{
			db.execSQL("DROP TABLE IF EXISTS "+Nama_DB);
			onCreate(db);
		}
	}
	public void close()
	{
		dbHelper.close();
	}
	public void addRow(String kata)
	{
		ContentValues values = new ContentValues();
		values.put(Row_word,kata);
		try
		{
			db.insert(Nama_Tabel,null,values);
		}
		catch (Exception e)
		{
			Log.e("DB ERROR",e.toString());
			e.printStackTrace();
		}
	}
	public ArrayList<ArrayList<Object>> ambilSemuaBaris()
	{
		ArrayList<ArrayList<Object>> dataArray = new ArrayList<ArrayList<Object>>();
		Cursor cur;
		try 
		{
			cur = db.query(Nama_Tabel,new String[]{Row_word},null,null,null,null,null);
			cur.moveToFirst();
			if(!cur.isAfterLast())
			{
			do
			{
				ArrayList<Object> dataList = new ArrayList<Object>();
				dataList.add(cur.getString(0));
				dataArray.add(dataList);
			}
			while(cur.moveToNext());
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("DEBE ERROR",e.toString());
		}
		return dataArray;
	}
}
