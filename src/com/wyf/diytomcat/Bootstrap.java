package com.wyf.diytomcat;

import cn.hutool.core.util.NetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Bootstrap {

    public static void main(String[] args) {

        try {
            int port = 18080;

            if (!NetUtil.isUsableLocalPort(port)) {
                System.out.println(port + " 端口已经被占用了");
                return;
            }
            ServerSocket ss = new ServerSocket(port);

            for (; ; ) {
                Socket s = ss.accept();
                InputStream is = s.getInputStream();
                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                is.read(buffer);
                String requestString = new String(buffer, StandardCharsets.UTF_8);
                System.out.println("浏览器的输入信息： \r\n" + requestString);

                OutputStream os = s.getOutputStream();
                String responseHead = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n\r\n";
                String responseString = "Hello DIY Tomcat from how2j.cn";
                responseString = responseHead + responseString;
                os.write(responseString.getBytes());
                os.flush();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}