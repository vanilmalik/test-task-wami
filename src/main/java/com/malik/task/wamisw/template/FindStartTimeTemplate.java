package com.malik.task.wamisw.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class FindStartTimeTemplate extends AbstractFindTimeTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindStartTimeTemplate.class);

    @Override
    public void processLogLine(String tag, LocalDateTime currentStartTime, Map<String, LocalDateTime> result) {
        if (!result.containsKey(tag)) {
            result.put(tag, currentStartTime);
        } else if (result.get(tag).isAfter(currentStartTime)) {
            LOGGER.info("The first occurrence: {} has a later result then current time : {} for tag: {}",
                    result.get(tag), currentStartTime, tag);
        }
    }
}
