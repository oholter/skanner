package com.skanner.org.skanner;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class TextFileLogWriterTest {

    TextFileLogWriter writer = null;


    @Before
    public void setUp() throws Exception {
        writer = TextFileLogWriter.getInstance();
        writer.setContext(null);
        writer.clearLog();
        writer.appendEntry("first1", "first2", false);
    }

    @Test
    public void ShouldAppendLine() throws Exception {
        writer.appendEntry("second1", "second2", true);
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
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("first1", lineParts[2]);
        assertEquals("first2", lineParts[3]);
    }

    @Test
    public void ShouldWriteThisDate() throws Exception {
        writer.appendEntry("second1", "second2", true);
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
        writer.appendEntry("second1", "second2", true);
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
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        reader.readLine(); // throws away first line
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("second1", lineParts[2]);
    }

    @Test
    public void ShouldWriteSecondLineElement() throws Exception {
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        reader.readLine(); // throws away first line
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("second2", lineParts[3]);
    }

    @Test
    public void LastEntryShouldBeFeil() throws Exception {
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        reader.readLine(); // throws away first line
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("FEIL", lineParts[4]);
    }

    @Test
    public void FirstEntryShouldBeOK() throws Exception {
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        int lines = 0;
        String line = reader.readLine();
        String[] lineParts = line.split(" ");

        assertEquals("OK", lineParts[4]);
    }
}
