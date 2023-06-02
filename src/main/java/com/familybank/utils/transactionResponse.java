package com.familybank.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
public class transactionResponse {
    public HashMap<Object,Object> genericResponse(Object header, Object body){
        HashMap<Object,Object> map = new LinkedHashMap<>();
        map.put("ResponseHeader", header);
        map.put("ResponseBody", body);

        return map;
    }

}
