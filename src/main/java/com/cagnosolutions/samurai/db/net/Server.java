package com.cagnosolutions.samurai.db.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public final class Server {

	public void run(String host, int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ServerInitializer());

			ChannelFuture f = b.bind(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException ex) {
			System.out.println("Server.catch(InterruptedException)...");
			ex.printStackTrace();
		} finally {
			System.out.println("Server.finally block...");
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
