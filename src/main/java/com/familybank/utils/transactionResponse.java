package com.familybank.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class ValidationResponse {
    public HashMap<String,Object> genericResponse(String value, String value1){
        HashMap<String,Object> map = new LinkedHashMap<>();
        map.put("ResponseHeader", value);
        map.put("ResponseBody", value1);

        return map;
    }

}
