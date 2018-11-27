/**
 * Denne klassen kalles i CheckEquality-klassen og er et grensesnitt mellom denne og
 * LogDatabaseAdaptor
 */
package com.skanner.org.skanner;

import android.content.Context;

import com.skanner.org.skanner.LogDatabaseAdapter;

public class SqlLogWriter implements LogWriter {
    private Context context;
    private LogDatabaseAdapter db;
    private static SqlLogWriter instance;

    public static SqlLogWriter getInstance() {
        if (instance == null) {
            instance = new SqlLogWriter();
        }
        return instance;
    }

    @Override
    public void appendEntry(String first, String second, boolean feil) {
        db.insertEntry(first, second, feil);
    }

    @Override
    public void clearLog() {
        db.clear();
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
        db = new LogDatabaseAdapter(context);
    }

    public String getLastEntry() {
        return db.getLastEntry();
    }

    public String getResults(String first, String last, boolean feil) {
        return db.getResults(first, last, feil);
    }
}
