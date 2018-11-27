/**
 * Dette grensesnittet definerer funksjonene til en LogWriter,
 * både SqlLogWriter og TextFileLogWriter må ha disse
 */
package com.skanner.org.skanner;

import android.content.Context;

public interface LogWriter {
    public void appendEntry(String a, String b, boolean feil);
    public void clearLog();
    public void setContext(Context context);
}
