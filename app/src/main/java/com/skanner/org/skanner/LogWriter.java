package com.skanner.org.skanner;

import android.content.Context;

public interface LogWriter {
    public void appendEntry(String a, String b);
    public void clearLog();
    public void setContext(Context context);
}
