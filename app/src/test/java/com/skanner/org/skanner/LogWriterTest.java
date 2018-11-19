package com.skanner.org.skanner;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class LogWriterTest {

    LogWriter writer = null;


    @Before
    public void setUp() throws Exception {
        writer = LogWriter.getInstance();
        writer.setLogFile("log.txt");
        writer.clearLogFile();
        writer.appendEntry("first1", "first2");
    }

    @Test
    public void ShouldAppendLine() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();

        while (line != null) {
            lines++;
            // System.out.println(line);
            line = reader.readLine();
        }
        reader.close();
        assertEquals(2, lines);
    }

    @Test
    public void ShouldKeepOldLine() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("first1", lineParts[2]);
        assertEquals("first2", lineParts[3]);
    }

    @Test
    public void ShouldWriteThisDate() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();
        String[] lineParts = line.split(" ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());

        assertEquals(formattedDate, lineParts[0]);
    }

    @Test
    public void ShouldWriteThisTime() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();
        String[] lineParts = line.split(" ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        assertEquals(formattedDate, lineParts[1]);
    }

    @Test
    public void ShouldWriteFirstLineElement() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        reader.readLine(); // throws away first line
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("second1", lineParts[2]);
    }

    @Test
    public void ShouldWriteSecondLineElement() throws Exception {
        writer.appendEntry("second1", "second2");
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        reader.readLine(); // throws away first line
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("second2", lineParts[3]);
    }
}
