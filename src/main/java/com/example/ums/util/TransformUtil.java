package com.example.ums.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformUtil {

    private static final Logger log          = LoggerFactory.getLogger(TransformUtil.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Returns a JSON {@link String} from the <strong>obj</strong> provided using {@link ObjectMapper}
     *
     * @param obj - {@link Object}
     * @return {@link String} - JSON string
     */
    public static String toJson(Object obj) {
        try {
            if (obj != null) {
                return objectMapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException e) {
            log.error("Error in toJson(), obj: " + obj + " ; Exception: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Returns the parsed {@link Object} from the {@link String jsonString} provided using {@link ObjectMapper} - will
     * need a type cast
     *
     * @param jsonString - {@link String}
     * @param valueType - {@link Class}
     * @return {@link Object}
     */
    public static <T> T fromJson(String jsonString, Class<T> valueType) {
        try {
            if (jsonString != null) {
                return objectMapper.readValue(jsonString, valueType);
            }
        } catch (Exception e) {
            log.error("Error in fromJson(), jsonString: " + jsonString + " ; Exception: " + e.getMessage(), e);
        }
        return null;
    }
}
