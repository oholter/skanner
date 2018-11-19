package com.skanner.org.skanner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogWriter {

    private final String LOG_FILE_NAME = "log.txt";
    private File logFile = new File(LOG_FILE_NAME);

    private static LogWriter instance;

    private LogWriter() { }

    public static LogWriter getInstance() {
        if (instance == null) {
            instance = new LogWriter();
        }
        return instance;
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
            writer.close();
        }
        catch (Exception e) {
            // handle exception
        }
    }

    public void clearLogFile() {
        PrintWriter out = null;
        try {
            out = new PrintWriter(logFile);
        }
        catch (Exception e) {
            // handle exception
        }
        finally {
            out.close();
        }
    }
}
