package org.khk.robotctf.robotctfcontroller.util;

/**
 * Created by Joe Dailey on 4/10/2015.
 */
public class malformedJSON extends Exception{
    private static final long serialVersionUID = -1976415057769292607L;
    private final String expected;
    private final String instead;
    malformedJSON(String expected, String instead){
        this.expected = expected;
        this.instead = instead;
    }
    malformedJSON(String expected, char instead){
        this.expected = expected;
        this.instead = instead + "";
    }
    @Override
    public String getMessage() {
        return "Expected " + expected + ", instead found " + instead;
    }

}
