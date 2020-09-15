package com.malik.task.wamisw.template;

import java.time.LocalDateTime;
import java.util.Map;

public interface FindTimeTemplate {

    Map<String, LocalDateTime> retrieveResults(String pathToFile);

    void processLogLine(String tag, LocalDateTime resultTime, Map<String, LocalDateTime> result);

}
