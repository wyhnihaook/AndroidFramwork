package com.lib.net.entity;

/**
 * @author wengyiheng
 * @date 2020/5/19.
 * description：
 */
public class BaseEntity<T> {
    private String status_code;
    private String msg = "";
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public int getCode() {
        int i = -1;
        try {
            i = Integer.parseInt(String.valueOf(status_code));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    public void setCode(String status_code) {
        this.status_code = status_code;
    }


    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }
}
