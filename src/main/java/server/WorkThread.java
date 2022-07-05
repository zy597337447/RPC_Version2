package server;


import common.RPCRequest;
import common.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * author:ZhangYu
 * date:2022/6/2917:23
 * description:从服务端代码中抽离出来，简化服务端代码
 **/
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            //读取客户端传来的Request
            RPCRequest request = (RPCRequest) ois.readObject();


            //反射调用方法获得返回值
            RPCResponse response = getResponse(request);

            //写入客户端
            oos.writeObject(response);
            oos.flush();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public RPCResponse getResponse(RPCRequest request) {
        //得到服务名
        String interfaceName = request.getInterfaceName();
        //得到响应的服务实现类
        Object service = serviceProvider.getService(interfaceName);
        //反射调用、
        Method method = null;
        try {
            method = service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            Object invoke = method.invoke(service,request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }
    }
}
