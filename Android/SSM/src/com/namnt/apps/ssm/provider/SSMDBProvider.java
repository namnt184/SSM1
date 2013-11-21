package com.namnt.apps.ssm.provider;

import com.namnt.apps.ssm.provider.SSMDBContracts.StaffColums;
import com.namnt.apps.ssm.provider.SSMDBContracts.Staffs;
import com.namnt.apps.ssm.provider.SSMDBManager.Tables;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
public class SSMDBProvider extends ContentProvider {
	private SSMDBManager mDatabaseManager;
	public final static String CONTENT_AUTHORITY = "com.linhnv.apps.ssm.provider";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	private final static UriMatcher sUriMatcher = buildUriMatcher();

	private final static int STAFF = 100;
	private final static int STAFF_ID = 101;

	private static UriMatcher buildUriMatcher() {
		final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(CONTENT_AUTHORITY, "staff", STAFF);
		uriMatcher.addURI(CONTENT_AUTHORITY, "staff/*", STAFF_ID);
		return uriMatcher;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mDatabaseManager.getWritableDatabase();
		int deleteCount = 0;
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case STAFF:
			deleteCount = db.delete(Tables.staff, selection, selectionArgs);
			break;
		case STAFF_ID:
			break;
		default:
			break;
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case STAFF:
			return Staffs.CONTENT_TYPE;
		case STAFF_ID:
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mDatabaseManager.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		long rowId = 0;
		switch (match) {
		case STAFF:
			rowId = db.insert(Tables.staff, null, values);
			if (rowId > 0) {
				Uri noteUri = Staffs.buildPostUri(values.getAsString(StaffColums.ID));
				getContext().getContentResolver().notifyChange(noteUri, null);
				return noteUri;
			}
			break;
		default:
			break;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		mDatabaseManager = new SSMDBManager(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		final SQLiteDatabase db = mDatabaseManager.getReadableDatabase();
		final int match = sUriMatcher.match(uri);
		final SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
		switch (match) {
		case STAFF:
			sqLiteQueryBuilder.setTables(Tables.staff);
			break;
		
		default:
			throw new RuntimeException("Unknow uri :" + uri);
		}
		Cursor cursor = sqLiteQueryBuilder.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		final int match = sUriMatcher.match(uri);
		final SQLiteDatabase db = mDatabaseManager.getWritableDatabase();
		int updateCount = 0;
		switch (match) {
		case STAFF:
			updateCount = db.update(Tables.staff, values, selection, selectionArgs);
			break;
		
		default:
			throw new RuntimeException("Unknow uri :" + uri);
		}
		// here we need to register an Observer to notify data set changed if
		// nessecery;
		getContext().getContentResolver().notifyChange(uri, null);
		return updateCount;
	}

}
