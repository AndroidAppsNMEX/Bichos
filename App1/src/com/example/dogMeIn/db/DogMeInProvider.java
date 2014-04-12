package com.example.dogMeIn.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.dogMeIn.models.Owner;

public class DogMeInProvider extends ContentProvider {

	String TAG = "DogMeInProvider";
	public static String AUTHORITY = "com.example.dogMeIn.db.DogMeInProvider";
	public static final Uri CONTENT_URI_OWNER = Uri.parse("content://"
			+ AUTHORITY + "/OWNER");

	public static final String MR_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.com.example.app1";
	public static final String SR_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.com.example.app1";

	private static final int GET_OWNER = 0;
	private static final int CHECK_INSERT_UPDATE_OWNER = 1;

	private DogMeInDatabase mDogMeIn;

	private static final UriMatcher sURIMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(AUTHORITY, "OWNER/#", GET_OWNER);
		matcher.addURI(AUTHORITY, "OWNER", CHECK_INSERT_UPDATE_OWNER);
		return matcher;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		switch (sURIMatcher.match(uri)) {
		case GET_OWNER:
			return SR_MIME_TYPE;
		case CHECK_INSERT_UPDATE_OWNER:
			return MR_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		switch (sURIMatcher.match(uri)) {
		case CHECK_INSERT_UPDATE_OWNER:
			Owner owner = new Owner();
			owner.setOwnerID(values.getAsInteger(DogMeInDatabase.COL_OWNER[0]));
			owner.setOwnerName(values.getAsString(DogMeInDatabase.COL_OWNER[1]));
			owner.setOwnerPassword(values.getAsString(DogMeInDatabase.COL_OWNER[2]));
			mDogMeIn.insertOwner(owner);
			break;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mDogMeIn = new DogMeInDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		switch (sURIMatcher.match(uri)) {
		case GET_OWNER:
			return getOwner(uri);
		case CHECK_INSERT_UPDATE_OWNER:
			return checkOwner();
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Cursor getOwner(Uri uri) {
		String id = uri.getLastPathSegment();
		String[] columns = DogMeInDatabase.COL_OWNER;
		return mDogMeIn.getOwner(id, columns);
	}

	private Cursor checkOwner() {
		String[] columns = DogMeInDatabase.COL_OWNER;
		return mDogMeIn.checkOwner(columns);
	}

}
