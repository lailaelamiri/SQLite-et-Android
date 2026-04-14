package com.example.ivoryledger.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.ivoryledger.classes.EnrolledScholar;
import com.example.ivoryledger.util.IvoryDatabaseGateway;

public class ScholarVaultService {

    private static final String SCHOLAR_TABLE = "scholars";

    private static final String COL_ID = "scholarId";
    private static final String COL_FAMILY = "familyName";
    private static final String COL_GIVEN = "givenName";

    private static final String[] LEDGER_COLUMNS = {COL_ID, COL_FAMILY, COL_GIVEN};

    private final IvoryDatabaseGateway gatewayHelper;

    public ScholarVaultService(Context context) {
        this.gatewayHelper = new IvoryDatabaseGateway(context);
    }

    public void enrollScholar(EnrolledScholar scholar) {
        SQLiteDatabase db = this.gatewayHelper.getWritableDatabase();
        ContentValues parchment = new ContentValues();
        parchment.put(COL_FAMILY, scholar.getFamilyName());
        parchment.put(COL_GIVEN, scholar.getGivenName());

        db.insert(SCHOLAR_TABLE, null, parchment);
        Log.d("IvoryLedger_insert", scholar.getFamilyName());

        db.close();
    }

    public void amendScholar(EnrolledScholar scholar) {
        SQLiteDatabase db = this.gatewayHelper.getWritableDatabase();
        ContentValues parchment = new ContentValues();
        parchment.put(COL_ID, scholar.getScholarId());
        parchment.put(COL_FAMILY, scholar.getFamilyName());
        parchment.put(COL_GIVEN, scholar.getGivenName());

        db.update(SCHOLAR_TABLE, parchment, "scholarId = ?", new String[]{scholar.getScholarId() + ""});
        db.close();
    }

    public EnrolledScholar fetchScholarById(int scholarId) {
        EnrolledScholar scholar = null;
        SQLiteDatabase db = this.gatewayHelper.getReadableDatabase();

        Cursor scroll = db.query(
                SCHOLAR_TABLE,
                LEDGER_COLUMNS,
                "scholarId = ?",
                new String[]{String.valueOf(scholarId)},
                null,
                null,
                null,
                null
        );

        if (scroll.moveToFirst()) {
            scholar = new EnrolledScholar();
            scholar.setScholarId(scroll.getInt(0));
            scholar.setFamilyName(scroll.getString(1));
            scholar.setGivenName(scroll.getString(2));
        }

        scroll.close();
        db.close();
        return scholar;
    }

    public void removeScholar(EnrolledScholar scholar) {
        SQLiteDatabase db = this.gatewayHelper.getWritableDatabase();
        db.delete(SCHOLAR_TABLE, "scholarId = ?", new String[]{String.valueOf(scholar.getScholarId())});
        db.close();
    }

    public List<EnrolledScholar> fetchAllScholars() {
        List<EnrolledScholar> vaultCollection = new ArrayList<>();
        String fullScanQuery = "select * from " + SCHOLAR_TABLE;

        SQLiteDatabase db = this.gatewayHelper.getReadableDatabase();
        Cursor scroll = db.rawQuery(fullScanQuery, null);

        if (scroll.moveToFirst()) {
            do {
                EnrolledScholar scholar = new EnrolledScholar();
                scholar.setScholarId(scroll.getInt(0));
                scholar.setFamilyName(scroll.getString(1));
                scholar.setGivenName(scroll.getString(2));
                vaultCollection.add(scholar);
                Log.d("IvoryLedger_id", scholar.getScholarId() + "");
            } while (scroll.moveToNext());
        }

        scroll.close();
        db.close();
        return vaultCollection;
    }
}