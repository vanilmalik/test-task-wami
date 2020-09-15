package com.malik.task.wamisw;

import com.malik.task.wamisw.template.FindFinishTimeTemplate;
import com.malik.task.wamisw.template.FindStartTimeTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WinnerFinder {

    @Value("${path.to.start}")
    private String pathToStartFile;

    @Value("${path.to.finish}")
    private String pathToFinishFile;

    @Autowired
    private FindFinishTimeTemplate findFinishTimeTemplate;

    @Autowired
    private FindStartTimeTemplate findStartTimeTemplate;

    public Map<String, Long> getWinners() {
        Map<String, Long> finishers = new HashMap<>();
        Map<String, LocalDateTime> startResults = findStartTimeTemplate.retrieveResults(pathToStartFile);
        Map<String, LocalDateTime> finishResults = findFinishTimeTemplate.retrieveResults(pathToFinishFile);

        finishResults.entrySet().stream()
                .filter(entry -> startResults.containsKey(entry.getKey()))
                .forEach(entry -> {
                    long differenceInMiliSeconds = ChronoUnit.MILLIS.between(startResults.get(entry.getKey()), entry.getValue());
                    finishers.put(entry.getKey(), differenceInMiliSeconds);
                });

        return processMap(finishers);
    }

    private static Map<String, Long> processMap(Map<String, Long> finishers) {
        return finishers.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
