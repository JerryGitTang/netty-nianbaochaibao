/**
 * @author rency
 * @data Jan 10, 2018 12:00:35 PM
 * @describe Client.java <br>
 *      <p>TODO</p>
 * @version 0.0.1
 */
package com.netty.client;

import com.netty.handler.client.ClientHandler;
import com.netty.potocal.SmartCarDecoder;
import com.netty.potocal.SmartCarEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author rency
 * @data Jan 10, 2018 12:00:35 PM
 * @describe Client.java <br>
 *      <p>TODO</p>
 * @version 0.0.1
 */
public class Client {
	
	/** 
     * 连接服务器 
     *  
     * @param port 
     * @param host 
     * @throws Exception 
     */  
    public void connect(int port, String host) throws Exception {
        // 配置客户端NIO线程组  
        EventLoopGroup group = new NioEventLoopGroup();  
        try {  
            // 客户端辅助启动类 对客户端配置  
            Bootstrap b = new Bootstrap();  
            b.group(group)//  
                    .channel(NioSocketChannel.class)//  
                    .option(ChannelOption.TCP_NODELAY, true)//  
                    .handler(new MyChannelHandler());//  
            // 异步链接服务器 同步等待链接成功  
            ChannelFuture f = b.connect(host, port).sync();  
  
            // 等待链接关闭  
            f.channel().closeFuture().sync();  
  
        } finally {  
            group.shutdownGracefully();  
            System.out.println("客户端优雅的释放了线程资源...");  
        }  
    } 
    
    /** 
     * 网络事件处理器 
     */  
    private class MyChannelHandler extends ChannelInitializer<SocketChannel> {  
        @Override  
        protected void initChannel(SocketChannel ch) throws Exception {  
            // 添加自定义协议的编解码工具  
            ch.pipeline().addLast(new SmartCarEncoder());  
            ch.pipeline().addLast(new SmartCarDecoder());  
            // 处理网络IO  
            ch.pipeline().addLast(new ClientHandler());  
        }  
    } 
    
    public static void main(String[] args) throws Exception {  
        new Client().connect(8080, "127.0.0.1");  
    }  

}
