package com.skanner.org.skanner;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogWriter {

    private final String LOG_FILE_NAME = "/log.txt";
    private File logFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), LOG_FILE_NAME);
    //private File logFile = new File("log.txt");
    private static LogWriter instance;

    private LogWriter() { }

    public static LogWriter getInstance() {
        if (instance == null) {
            instance = new LogWriter();
        }
        return instance;
    }

    public void setLogFile(String name) {
        logFile = new File(name);
    }

    public void appendEntry(String first, String second) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        OutputStream outStream = null;
        OutputStreamWriter writer = null;

        try {
            outStream = new FileOutputStream(logFile, true);
            writer = new OutputStreamWriter(outStream);
            writer.append(formattedDate + " " + first + " " + second + "\n");
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

    public void clearLogFile() {
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
