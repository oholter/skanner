package com.skanner.org.skanner;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class JsonCommunicatorTest {
    JSONObject object;
    User user;
    final String userName = "USER";
    final String first = "first";
    final String second = "second";
    final boolean feil = false;

    @Before
    public void setUp() throws Exception {
        user = User.getInstance();
        user.setUserName("USER");
        object = JsonCommunicator.createJsonObject(first, second, feil);
    }

    @Test
    public void ShouldReturnJSONObject() {
        boolean isJson = false;
        if (object instanceof JSONObject) {
            isJson = true;
        }
        assertEquals(true, isJson);
    }

    @Test
    public void ShouldContainUserName() throws Exception {
        String name = object.getString("user");
        assertEquals(userName, name);
    }

    @Test
    public void ShouldContainTimeStamp() throws Exception {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        String time = object.getString("time");
        assertEquals(formattedDate, time);
    }

    @Test
    public void ShouldContainFirstEntry() throws Exception {
        String firstEntry = object.getString("first");
        assertEquals(first, firstEntry);
    }

    @Test
    public void ShouldContainSecondEntry() throws Exception {
        String secondEntry = object.getString("second");
        assertEquals(second, secondEntry);
    }

    @Test
    public void ShouldContainFeil() throws Exception {
        boolean isFeil = object.getBoolean("feil");
        assertEquals(feil, isFeil);
    }
}
