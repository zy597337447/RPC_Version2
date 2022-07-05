package client;


import common.Blog;
import common.User;
import service.BlogService;
import service.UserService;


/**
 * author:ZhangYu
 * date:2022/6/2916:41
 * description:
 **/
public class RPCClient {
    public static void main(String[] args) {

        ClientProxy rpcClientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService userService = rpcClientProxy.getProxy(UserService.class);

        User userByUserId = userService.getUserById(10);
        System.out.println("从服务端得到的user为：" + userByUserId);

        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("向服务端插入数据："+integer);

        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}