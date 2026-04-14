package com.example.ivoryledger.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IvoryDatabaseGateway extends SQLiteOpenHelper {

    private static final int LEDGER_VERSION = 1;
    private static final String LEDGER_NAME = "ivory_academy";

    private static final String INITIALIZE_SCHOLAR_TABLE =
            "create table scholars(" +
                    "scholarId INTEGER primary key autoincrement," +
                    "familyName TEXT," +
                    "givenName TEXT)";

    public IvoryDatabaseGateway(Context context) {
        super(context, LEDGER_NAME, null, LEDGER_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INITIALIZE_SCHOLAR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists scholars");
        this.onCreate(db);
    }
}