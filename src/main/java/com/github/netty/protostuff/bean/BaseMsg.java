package com.github.netty.protostuff.bean;


/**
 * 功能描述: 消息基类
 * @author: qinxuewu
 * @date: 2020/5/13 16:30
 * @since 1.0.0
 */
public class BaseMsg {
    private String channelId;


    public BaseMsg(String channelId) {
        this.channelId = channelId;
    }

    public BaseMsg() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
