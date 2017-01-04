package com.gr.jiang.message;

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
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class[] getParameterTypes() {
        parameterTypes = new Class[args.length];
        int i = 0;
        for(Object obj : args){
            parameterTypes[i] = obj.getClass();
            i ++;
        }
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
