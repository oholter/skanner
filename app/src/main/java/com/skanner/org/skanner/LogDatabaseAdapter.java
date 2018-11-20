package com.skanner.org.skanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogDatabaseAdapter {
    static final String DATABASE_NAME = "database.db";
    String ok = "OK";
    static final int DATABASE_VERSION = 5;
    public static String getPassword = "";
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    static final String DATABASE_CREATE = "create table LOG( ID integer primary key autoincrement, DATE text, FIRST text, SECOND  text, USERNAME text, FEIL int); ";
    public static SQLiteDatabase db;
    private final Context context;
    private static DataBaseHelper dbHelper;

    public LogDatabaseAdapter(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to open the Database
    public LogDatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }


    public void close() {
        db.close();
    }


    public SQLiteDatabase getInstance() {
        return db;
    }


    // method to insert a record in Table
    public String insertEntry(String first, String second, boolean feil) {
        try {
            ContentValues newValues = new ContentValues();
            User user = User.getInstance();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            // Assign values for each column.
            newValues.put("FIRST", first);
            newValues.put("SECOND", second);
            newValues.put("DATE", formattedDate);
            newValues.put("USERNAME", user.getUserName());
            if (feil) {
                newValues.put("FEIL", 1);
            } else {
                newValues.put("FEIL", 0);
            }

            // Insert the row into your table
            db = dbHelper.getWritableDatabase();
            long result = db.insert("LOG", null, newValues);
            System.out.print(result);
            //Toast.makeText(context, "LOG entry created", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            System.out.println("Exceptions " + ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }

    // method to delete a Record of UserName
    public void deleteEntry(String UserName) {

    }

    // method to get the password  of userName
    public String getLastEntry() {
        String toReturn = "";
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"USERNAME", "DATE", "FIRST", "SECOND", "FEIL"};
        Cursor cursor = db.query("LOG", columns, null, null, null, null, "ID DESC");
        if (cursor.getCount() < 1) // UserName Not Exist
            return "NOT EXIST";
        try {
            cursor.moveToNext();
            toReturn = cursor.getString(cursor.getColumnIndex("USERNAME"));
            toReturn += ", ";
            toReturn += cursor.getString(cursor.getColumnIndex("DATE"));
            toReturn += ", ";
            toReturn += cursor.getString(cursor.getColumnIndex("FIRST"));
            toReturn += ", ";
            toReturn += cursor.getString(cursor.getColumnIndex("SECOND"));
            toReturn += ", ";
            if (cursor.getInt(cursor.getColumnIndex("FEIL")) == 1) {
                toReturn += "FEIL";
            } else {
                toReturn += "RIKTIG";
            }
            Log.e("FEIL", toReturn);
        } finally {
            cursor.close();
        }
        return toReturn;
    }

    public String getResults(String fra, String til, boolean feil) {
        String toReturn = "";
        db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{"USERNAME", "DATE", "FIRST", "SECOND", "FEIL"};
        String[] args = null;
        Cursor cursor = null;
        try {
            if (feil) {
                args = new String[]{fra, til, "1"};
                //cursor = db.query("LOG", columns, "DATE >= ? AND DATE <= ? AND FEIL = ?", args, null, null, "ID ASC");
                String query = "SELECT USERNAME, DATE, FIRST, SECOND, FEIL FROM LOG WHERE " +
                        " DATE >= '" + fra + " 00:00:00'" +
                        " AND DATE <= '" + til + " 23:59:59'" +
                        " AND FEIL = 1";

                cursor = db.rawQuery(query, null);
                Log.e("query:", query);
            } else {
                args = new String[]{fra, til};
                //cursor = db.query("LOG", columns, "DATE >= ? AND DATE <= ?", args, null, null, "ID ASC");
                //cursor = db.query("LOG", columns, null, null, null, null, "ID DESC");
                String query = "SELECT USERNAME, DATE, FIRST, SECOND, FEIL FROM LOG WHERE " +
                        " DATE >= '" + fra + " 00:00:00'" +
                        " AND DATE <= '" + til + " 23:59:59'";
                cursor = db.rawQuery(query, null);
                Log.e("query:", query);
            }
            cursor.moveToFirst();
            toReturn = "";
            while (!cursor.isAfterLast()) {
                toReturn += cursor.getString(cursor.getColumnIndex("USERNAME"));
                toReturn += ", ";
                toReturn += cursor.getString(cursor.getColumnIndex("DATE"));
                toReturn += ", ";
                toReturn += cursor.getString(cursor.getColumnIndex("FIRST"));
                toReturn += ", ";
                toReturn += cursor.getString(cursor.getColumnIndex("SECOND"));
                toReturn += ", ";
                if (cursor.getInt(cursor.getColumnIndex("FEIL")) == 1) {
                    toReturn += "FEIL";
                } else {
                    toReturn += "RIKTIG";
                }

                toReturn += "\n";
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        Log.e("query", toReturn);
        return toReturn;
    }

    // Method to Update an Existing
    public void updateEntry(String userName, String password) {

    }

    public void clear() {
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from LOG");
    }
}