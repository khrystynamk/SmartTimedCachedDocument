package com.lab10;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

public class TimedDocument extends SmartDocument {
    public TimedDocument(String gcsPath) {
        super(gcsPath);
    }

    public String parse() {
        Instant start = Instant.now();
        String result = super.parse();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(MessageFormat.format("Parsing of image took {0}.", timeElapsed));
        return result;
    }
}