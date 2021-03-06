/**
 * Implementerer logging som skriving til tekst-fil. Kan brukes i stedet for database
 * Filen legges i DCIM-mappen på android-enheten
 *
 * Må huske på å gi tillatelse til appen til å åpne og skrive til filer
 */

package com.skanner.org.skanner;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TextFileLogWriter implements LogWriter {

    private final String LOG_FILE_NAME = "/log.txt";

    private File logFile;

    private static TextFileLogWriter instance;
    private Context context;

    private TextFileLogWriter() { }

    public static TextFileLogWriter getInstance() {
        if (instance == null) {
            instance = new TextFileLogWriter();
        }
        return instance;
    }

    public void setLogFile(String name) {
        logFile = new File(name);
    }

    @Override
    public void setContext(Context context) {
        if (context == null) { // for testing purposes
            logFile = new File("log.txt");
        } else {
            logFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), LOG_FILE_NAME);
        }this.context = context;
    }

    @Override
    public void appendEntry(String first, String second, boolean feil) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        OutputStream outStream = null;
        OutputStreamWriter writer = null;

        try {
            outStream = new FileOutputStream(logFile, true);
            writer = new OutputStreamWriter(outStream);
            if (feil) {
                writer.append(formattedDate + " " + first + " " + second + " FEIL" + "\n");
            }
            else {
                writer.append(formattedDate + " " + first + " " + second + " OK" + "\n");
            }
        }
        catch (Exception e) {
            // handle exception
            Log.e("FEIL", e.getMessage());
        }
        finally {
            try {
                writer.close();
            }
            catch (IOException e) {
                //  handle exception
            }
        }
    }

    @Override
    public void clearLog() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(logFile);
        }
        catch (Exception e) {
            Log.e("Feil: ", e.getMessage());
            // handle exception
        }
        finally {
            out.close();
        }
    }
}
