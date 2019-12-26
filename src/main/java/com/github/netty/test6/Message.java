package com.github.netty.test6;

import java.io.Serializable;

/**
 * Netty 实现序列化传输对象数据
 * @author qxw
 * @version 1.00
 * @time  2/8/2019 上午 11:00
 */
public class Message implements Serializable{

    private static final long serialVersionUID = -1L;
    private String id;
    private String content;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
