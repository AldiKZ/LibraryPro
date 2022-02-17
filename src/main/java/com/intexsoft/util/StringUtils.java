package com.intexsoft.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static Map<String, String> obtainParamsMap(String userInput) {
        String[] params = userInput.split(" ");
        Map<String, String> paramsMap = new HashMap<>();
        for (String s : params) {
            String[] splittedParams = s.split("=");
            paramsMap.put(splittedParams[0], splittedParams[1]);
        }
        return paramsMap;
    }
}
