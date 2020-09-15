package com.malik.task.wamisw.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class FindFinishTimeTemplate extends AbstractFindTimeTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(FindFinishTimeTemplate.class);

    @Override
    public void processLogLine(String tag, LocalDateTime currentFinishTime, Map<String, LocalDateTime> result) {
        if (nonNull(result.get(tag)) && result.get(tag).isBefore(currentFinishTime)) {
            LOGGER.info("The last occurrence: {} has an earlier result then current time : {} for tag: {}",
                    result.get(tag), currentFinishTime, tag);
        }

        result.put(tag, currentFinishTime);
    }
}
