package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author:ZhangYu
 * date:2022/6/2917:11
 * description:
 **/
public class SimpleRPCServer implements RPCServer{
    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");

            //BIO方式监听
            while(true) {
                Socket socket = serverSocket.accept();
                //开启一个新线程去处理
                new Thread(new WorkThread(socket,serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("stop");
    }


}
