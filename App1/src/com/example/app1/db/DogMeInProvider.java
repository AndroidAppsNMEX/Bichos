package com.example.app1.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.dogMeIn.models.Owner;

public class DogMeInProvider extends ContentProvider {

	String TAG = "DogMeInProvider";
	public static String AUTHORITY = "com.example.app1.db.DogMeInProvider";
	public static final Uri CONTENT_URI_OWNER = Uri.parse("content://"
			+ AUTHORITY + "/OWNER");

	public static final String MR_MIME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.com.example.app1";
	public static final String SR_MIME_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.com.example.app1";

	private static final int GET_OWNER = 0;
	private static final int CHECK_OWNER = 1;

	private DogMeInDatabase mDogMeIn;

	private static final UriMatcher sURIMatcher = buildUriMatcher();

	private static UriMatcher buildUriMatcher() {
		UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

		matcher.addURI(AUTHORITY, "OWNER/#", GET_OWNER);
		matcher.addURI(AUTHORITY, "OWNER", CHECK_OWNER);
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
		case CHECK_OWNER:
			return MR_MIME_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URL " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		switch(sURIMatcher.match(uri)){
			
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
			Owner o = new Owner(selectionArgs[0]);
			return getOwner(o);
		case CHECK_OWNER:
			return checkOwner(uri);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Cursor getOwner(Owner owner) {
		String[] columns = DogMeInDatabase.COL_OWNER;
		return mDogMeIn.getOwner(owner, columns);
	}

	private Cursor checkOwner(Uri uri) {
		String id = uri.getLastPathSegment();
		String[] columns = DogMeInDatabase.COL_OWNER;
		return mDogMeIn.checkOwner(id, columns);
	}

}
