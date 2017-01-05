package com.gr.jiang.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by jiang on 2017/1/5.
 */
public class JsonUtils {
    public static <T> T json2Obj(String json, Class<T> c) {
        T o = null;
        try {
            o = new ObjectMapper().readValue(json, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
    public static String obj2Json(Object o) {
        ObjectMapper om = new ObjectMapper();
        Writer w = new StringWriter();
        String json = null;
        try {
            om.writeValue(w, o);
            json = w.toString();
            w.close();
        } catch (IOException e) {
        }
        return json;
    }
}
