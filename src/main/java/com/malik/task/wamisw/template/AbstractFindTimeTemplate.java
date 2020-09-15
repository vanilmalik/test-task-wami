package com.malik.task.wamisw.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

public abstract class AbstractFindTimeTemplate implements FindTimeTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFindTimeTemplate.class);

    public static final int BEGIN_INDEX_TIMESTAMP = 20;
    public static final int END_INDEX_TIMESTAMP = 32;
    public static final int BEGIN_INDEX_TAG = 4;
    public static final int END_INDEX = 16;
    public static final int ERROR_TIME_STAMP = 999999999;

    @Override
    public Map<String, LocalDateTime> retrieveResults(String pathToFile) {
        HashMap<String, LocalDateTime> tagWithResult = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(pathToFile)))) {
            String logLine;
            while (isNotEmpty((logLine = bufferedReader.readLine()))) {
                String tag = getTagFromLogLine(logLine);
                LocalDateTime timeStamp = getLocalDateTimeFromLogLine(logLine);
                processLogLine(tag, timeStamp, tagWithResult);
            }
        } catch (IOException e) {
            LOGGER.error("Error while reading file", e);
        }

        return tagWithResult;
    }

    protected LocalDateTime getLocalDateTimeFromLogLine(String logLine) {
        long timeStamp;
        String timeStampString = logLine.substring(BEGIN_INDEX_TIMESTAMP, END_INDEX_TIMESTAMP);

        try {
            timeStamp = Long.parseLong(timeStampString);
        } catch (NumberFormatException e) {
            timeStamp = ERROR_TIME_STAMP;
            LOGGER.error("Error while retrieving timestamp from : {}", timeStampString);
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), TimeZone.getDefault().toZoneId());
    }

    protected String getTagFromLogLine(String logLine) {
        return logLine.substring(BEGIN_INDEX_TAG, END_INDEX);
    }
}
