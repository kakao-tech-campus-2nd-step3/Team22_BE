package io.github.eappezo.soundary.core;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String stackTraceOf(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));

        return stringWriter.toString();
    }
}
