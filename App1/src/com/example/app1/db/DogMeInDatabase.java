package com.example.app1.db;

import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import com.example.dogMeIn.models.Owner;

public class DogMeInDatabase {

	private static final String DATABASE_NAME = "DogMeInLite.db";
	private static final int DATABASE_VERSION = 1;
	private static final String OWNER_TABLE = "OWNER";

	protected static final String[] COL_OWNER = { "OWNER_ID", "NICKNAME",
			"PASSWORD" };
	private final DogMeInOpenHelper mOpenHelper;
	private final HashMap<String, String> columnMapOwner = buildColumnMapOwner();

	public DogMeInDatabase(Context context) {
		mOpenHelper = new DogMeInOpenHelper(context);
	}

	private static HashMap<String, String> buildColumnMapOwner() {
		HashMap<String, String> columnMap = new HashMap<String, String>();
		for (String column : COL_OWNER) {
			columnMap.put(column, column);
		}
		columnMap.put(BaseColumns._ID, COL_OWNER[0] + " AS " + BaseColumns._ID);
		return columnMap;
	}

	protected Cursor getOwner(Owner owner, String[] columns) {
		String selection = COL_OWNER[0] + " = ?";
		String[] selectionArgs = new String[] { owner.getOwnerID().toString() };

		return query(selection, selectionArgs, columns);
	}
	
	protected Cursor checkOwner(String id, String[] columns) {
		String selection = "";
		String[] selectionArgs = new String[] { id };

		return query(selection, selectionArgs, columns);
	}

	private Cursor query(String selection, String[] selectionArgs,
			String[] columns) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(OWNER_TABLE);
		builder.setProjectionMap(columnMapOwner);

		Cursor cursor = builder.query(mOpenHelper.getReadableDatabase(),
				columns, selection, selectionArgs, null, null, COL_OWNER[0]);
		mOpenHelper.close();
		return cursor;
	}

	protected long insertOwner(Owner owner) {
		long result = 0;
		
		mOpenHelper.getWritableDatabase();
		result = mOpenHelper.addOwner(owner);
		mOpenHelper.close();
		
		return result;
	}

	public static class DogMeInOpenHelper extends SQLiteOpenHelper {

		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;

		private static final String OWNER_TABLE_CR = "CREATE TABLE DOGMEIN."
				+ OWNER_TABLE + "(" + COL_OWNER[0]
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_OWNER[1]
				+ " TEXT UNIQUE, " + COL_OWNER[2] + " TEXT);";
		private static final String PET_TABLE_CR = "";
		private static final String COMMENT_TABLE_CR = "";
		private static final String BREDD_TABLE_CR = "";

		public DogMeInOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mHelperContext = context;
		}

		public void onCreate(SQLiteDatabase _db) {
			mDatabase = _db;
			mDatabase.execSQL(OWNER_TABLE_CR);
		}

		public void onUpgrade(SQLiteDatabase _db, int old_version,
				int new_version) {

		}

		public void onDelete(SQLiteDatabase _db) {

		}

		protected long addOwner(Owner owner) {
			ContentValues values = new ContentValues();
			values.put(COL_OWNER[0], owner.getOwnerID());
			values.put(COL_OWNER[1], owner.getOwnerName());
			values.put(COL_OWNER[2], owner.getOwnerPassword());
			return mDatabase.insert(OWNER_TABLE, null, values);
		}
	}
}
