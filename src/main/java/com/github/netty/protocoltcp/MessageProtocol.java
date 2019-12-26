package com.github.netty.protocoltcp;


/**
 * 功能描述: 自定义协议包
 * @author: qinxuewu
 * @date: 2019/12/26 14:18
 * @since 1.0.0
 */
public class MessageProtocol {
    /**
     * 长度
     */
    private int len;
    /**
     *  内容字节
     */
    private byte[] content;
    public int getLen() {
        return len;
    }
    public void setLen(int len) {
        this.len = len;
    }
    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }
}
