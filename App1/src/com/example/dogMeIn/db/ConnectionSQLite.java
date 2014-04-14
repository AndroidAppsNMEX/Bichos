package com.example.dogMeIn.db;

import android.content.Context;

import com.example.dogMeIn.models.Owner;

public interface ConnectionSQLite {

	int insertUserSQLite(Context context, Owner owner);
}
