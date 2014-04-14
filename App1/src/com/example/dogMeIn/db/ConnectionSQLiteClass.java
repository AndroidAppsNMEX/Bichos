package com.example.dogMeIn.db;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.dogMeIn.models.Owner;

public class ConnectionSQLiteClass implements ConnectionSQLite {

	public int insertUserSQLite(Context context, Owner owner) {
		int result = 0;
		ContentValues values = new ContentValues();
		values.put(DogMeInDatabase.COL_OWNER[0], owner.getOwnerID());
		values.put(DogMeInDatabase.COL_OWNER[1], owner.getOwnerName());
		values.put(DogMeInDatabase.COL_OWNER[2], owner.getOwnerPassword());
		Uri uri = context.getContentResolver().insert(
				DogMeInProvider.CONTENT_URI_OWNER, values);
		if (uri == null) {
			result = -1;
		}
		return result;
	}
}
