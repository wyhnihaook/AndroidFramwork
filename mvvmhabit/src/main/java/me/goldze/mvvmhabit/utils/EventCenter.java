package me.goldze.mvvmhabit.utils;

/**
 * @author wengyiheng
 * @date 2021/8/25.
 * description：
 */
public class EventCenter <T> {

    /**
     * reserved data
     */
    public T data;

    /**
     * this code distinguish between different events
     */
    public int eventCode = -1;

    public EventCenter(int eventCode) {
        this(eventCode, null);
    }

    public EventCenter(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    /**
     * get event code
     *
     * @return
     */
    public int getEventCode() {
        return this.eventCode;
    }

    /**
     * get event reserved data
     *
     * @return
     */
    public T getData() {
        return this.data;
    }
}

