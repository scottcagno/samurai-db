package com.cagnosolutions.samurai.db.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */



public final class Server {

	private static BufferedWriter AOF_BUFFERED_WRITER = null;

	public Server(String path) {
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			AOF_BUFFERED_WRITER = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void run(String host, int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ServerInitializer(AOF_BUFFERED_WRITER));

			ChannelFuture f = b.bind(host, port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException ex) {
			System.out.println("Server.catch(InterruptedException)...");
			ex.printStackTrace();
		} finally {

			if(AOF_BUFFERED_WRITER != null) {
				try {
					AOF_BUFFERED_WRITER.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

			System.out.println("Server.finally block...");
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
