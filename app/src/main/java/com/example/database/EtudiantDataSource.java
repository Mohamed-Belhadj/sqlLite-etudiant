package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EtudiantDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public EtudiantDataSource(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<Etudiant> getListEtudiants() {
        String sql = "select * from " + dbHelper.TABLE_ETUDIANTS;
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        List<Etudiant> listEtudiants = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String nom = cursor.getString(1);
                String prenom = cursor.getString(2);
                int presence = cursor.getInt(3);
                listEtudiants.add(new Etudiant(nom, prenom, presence));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listEtudiants;
    }

    public void addEtudiant(Etudiant etudiant) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NOM, etudiant.getNom());
        values.put(DBHelper.COLUMN_PRENOM, etudiant.getPrenom());
        values.put(DBHelper.COLUMN_PRESENCE, etudiant.getPresence());

        try {
            // Insert the new student into the database
            database.insert(DBHelper.TABLE_ETUDIANTS, null, values);
        } catch (SQLiteException e) {
            Log.e("EtudiantDataSource", "Error adding student: " + e.getMessage());
        }
    }

    // New method to delete student and retrieve updated list
    public void deleteEtudiant(int id) {
        String selection = DBHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        // Delete the student with the specified ID
        database.delete(DBHelper.TABLE_ETUDIANTS, selection, selectionArgs);
    }

    public void close() {
        dbHelper.close();
    }
}
