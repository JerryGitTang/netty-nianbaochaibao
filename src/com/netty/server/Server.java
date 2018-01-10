/**
 * @author rency
 * @data Jan 10, 2018 12:00:17 PM
 * @describe Server.java <br>
 *      <p>TODO</p>
 * @version 0.0.1
 */
package com.netty.server;

import java.net.InetSocketAddress;

import com.netty.handler.server.ServerHandler;
import com.netty.potocal.SmartCarDecoder;
import com.netty.potocal.SmartCarEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 处理粘包和拆包
 * @author rency
 * @data Jan 10, 2018 12:00:17 PM
 * @describe Server.java <br>
 *      <p>TODO</p>
 * @version 0.0.1
 */
public class Server {

	public Server(){}
	
	public void bind(int port) throws Exception{
		// 配置NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try{
			// 服务器辅助启动类配置  
			ServerBootstrap sbs = new ServerBootstrap();
			sbs.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
//			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChildChannelHandler())
			.option(ChannelOption.SO_BACKLOG, 1024)//设置tcp缓冲区（5）
			.childOption(ChannelOption.SO_KEEPALIVE,true);//（6）
			// 绑定端口 同步等待绑定成功  
			ChannelFuture cf = sbs.bind(port).sync();//(7)
			System.out.println("start server listen at "+port);
			// 等到服务端监听端口关闭
			cf.channel().closeFuture().sync();
		}catch (Exception e) {
			// 优雅释放线程资源  
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}
	
	 /** 
     * 网络事件处理器 :定义编解码、事件处理器
     */ 
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
		/* (non-Javadoc)
		 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
		 */
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// 添加自定义协议的编解码工具  
			ch.pipeline().addLast(new SmartCarDecoder());
			ch.pipeline().addLast(new SmartCarEncoder());
			// 处理网络IO  
			ch.pipeline().addLast(new ServerHandler());
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server().bind(8080);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
}
