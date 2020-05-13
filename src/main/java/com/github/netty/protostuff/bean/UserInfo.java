package com.github.netty.protostuff.bean;


/**
 * 功能描述: 用户消息
 * @author: qinxuewu
 * @date: 2020/5/13 16:31
 * @since 1.0.0
 */
public class UserInfo extends  BaseMsg {
    private String username;

    public UserInfo(String channelId, String username) {
        super(channelId);
        this.username = username;
    }

    public UserInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
