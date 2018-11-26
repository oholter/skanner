package com.skanner.org.skanner;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;



import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SqlLogWriterTest {
    Context context;
    SqlLogWriter writer;

    @Before
    public void setUp() throws Exception {
        User user = User.getInstance();
        user.setUserName("TEST");
        context = RuntimeEnvironment.application;
        writer = SqlLogWriter.getInstance();
        writer.setContext(context);
        writer.clearLog();
        writer.appendEntry("first1", "first2", false);
    }

    @Test
    public void ShouldAppendRow() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String result = writer.getResults("00-00-2000", "31-12-2050", false);
        String[] lines = result.split("\n");
        assertEquals(2, lines.length);
    }

    @Test
    public void ShouldKeepFirstRow() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[0].split(" ");

        assertEquals("first1", lineParts[3]);
        assertEquals("first2", lineParts[4]);
    }

    @Test
    public void ShouldWriteUserNameTest() {
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[0].split(" ");

        assertEquals("TEST", lineParts[0]);
    }

    @Test
    public void ShouldWriteThisDate() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[0].split(" ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());

        assertEquals(formattedDate, lineParts[1]);
    }

    @Test
    public void ShouldWriteThisTime() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[0].split(" ");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        assertEquals(formattedDate, lineParts[2]);
    }

    @Test
    public void ShouldWriteFirstLineElement() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String res = writer.getResults("00-00-2000", "31-12-2050", false);
        String[] lines = res.split("\n");
        String[] lineParts = lines[1].split(" ");
        assertEquals("second1", lineParts[3]);
    }

    @Test
    public void ShouldWriteSecondLineElement() throws Exception {
        writer.appendEntry("second1", "second2", true);
        BufferedReader reader = new BufferedReader(new FileReader("log.txt"));
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[1].split(" ");

        assertEquals("second2", lineParts[4]);
    }

    @Test
    public void LastEntryShouldBeFeil() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[1].split(" ");

        assertEquals("FEIL", lineParts[5]);
    }

    @Test
    public void FirstEntryShouldBeOK() throws Exception {
        writer.appendEntry("second1", "second2", true);
        String[] lines = writer.getResults("00-00-2000", "31-12-2050", false).split("\n");
        String[] lineParts = lines[0].split(" ");

        assertEquals("RIKTIG", lineParts[5]);
    }
}
