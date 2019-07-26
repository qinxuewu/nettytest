package com.github.zerocopy;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OldClient {

    public static void main(String[] args) throws  Exception {
        Socket socket=new Socket("localhost",8899);

        String filename="E:\\BaiduNetdiskDownload\\aaaa.zip";

        // 获取文件输入流
        InputStream inputStream=new FileInputStream(filename);

        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
        byte[] buffer=new byte[4096];
        // 实际读到字节数
        long readCount;
        long total=0;

        long startTime=System.currentTimeMillis();

        // 如果
        while ((readCount=inputStream.read(buffer))>=0){
            total +=readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送总字节数："+total +", 耗时:"+(System.currentTimeMillis()-startTime));
        dataOutputStream.close();
        socket.close();
        inputStream.close();

    }
}
