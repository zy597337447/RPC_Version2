package client;

import common.RPCRequest;
import common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * author:ZhangYu
 * date:2022/6/2916:13
 * description:这里负责底层与服务端的通信，发送的Request，接受的是Response对象
 *              客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
 *              这里的request是封装好的（上层进行封装），不同的service需要进行不同的封装，
 *              客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
 **/
public class IOClient {
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            Socket socket = new Socket(host, port);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();



//            Object o =   objectInputStream.readObject();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();

            //System.out.println(response.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }

    }

}
