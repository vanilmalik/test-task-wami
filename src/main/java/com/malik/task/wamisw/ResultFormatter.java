package com.malik.task.wamisw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ResultFormatter {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm:ss:SSS");

    @Autowired
    private WinnerFinder winnerFinder;

    @EventListener(ApplicationReadyEvent.class)
    public void formatAndShowResults() {
        Map<String, Long> firstTenWinners = winnerFinder.getWinners();
        firstTenWinners.entrySet()
                .forEach(entry -> System.out.println(makeString(entry)));
    }

    private String makeString(Map.Entry<String, Long> entry) {
        return "Participant with tag: " + entry.getKey() + " takes "
                + COUNTER.getAndIncrement() + " place, " + "with result: "
                + TIME_FORMATTER.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.getValue()), ZoneId.systemDefault()));
    }
}
