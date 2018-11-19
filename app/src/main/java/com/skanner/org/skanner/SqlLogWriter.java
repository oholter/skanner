package com.skanner.org.skanner;

import android.content.Context;

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
    public void appendEntry(String first, String second) {
        db.insertEntry(first, second);
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
}