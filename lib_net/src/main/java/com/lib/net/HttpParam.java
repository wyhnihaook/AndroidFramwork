package com.lib.net;


import java.util.HashMap;

/**
 * @author wengyiheng
 * @date 2020/6/18.
 * description：用于Okhttp的参数传输
 */
public class HttpParam extends HashMap<String, Object> {

    @Override
    public HttpParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}