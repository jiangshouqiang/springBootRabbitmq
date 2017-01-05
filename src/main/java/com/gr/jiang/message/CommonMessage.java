package com.gr.jiang.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gr.jiang.util.JsonUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by jiang on 2017/1/4.
 */
public class CommonMessage {

    private String className;

    private String methodName;

    private Object[] args;

    private Class[] parameterTypes;

    public CommonMessage() {

    }

    public CommonMessage(String className, String methodName, Object... args) {
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        parameterTypes = new Class[args.length];
        int i = 0;
        for(Object obj : args){
            parameterTypes[i] = obj.getClass();
            i ++;
        }
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        Object[] objs = new Object[args.length];
        for(int i = 0 ; i < args.length ; i ++){
            String json = JsonUtils.obj2Json(args[i]);
            objs[i] = JsonUtils.json2Obj(json,parameterTypes[i]);
        }
        return objs;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

}
