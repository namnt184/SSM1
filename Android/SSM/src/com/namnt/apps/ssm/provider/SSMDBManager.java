package com.namnt.apps.ssm.provider;

import com.namnt.apps.ssm.provider.SSMDBContracts.StaffColums;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SSMDBManager extends SQLiteOpenHelper {
	public final static String DATABASE_NAME = "ssm.db";
	private final static int DATABASE_VERSION = 1;

	private final Context mContext;

	public interface Tables {
		String staff = "staff";
	}

	public SSMDBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public SSMDBManager(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + Tables.staff + " ( " + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + StaffColums.ID + "  TEXT NOT NULL,"
				+ StaffColums.AVATAR + " TEXT ," + StaffColums.IMEI + " TEXT NOT NULL ,"
				+ StaffColums.SERIAL + " TEXT ," + StaffColums.LOCALE + " TEXT NOT NULL ,"
				+ StaffColums.NAME + "  TEXT ,"

				+ "UNIQUE (" + BaseColumns._ID + ") ON CONFLICT REPLACE)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if (oldVersion != DATABASE_VERSION) {
			db.execSQL("DROP TABLE IF EXISTS " + Tables.staff);
			onCreate(db);
		}
	}

}
