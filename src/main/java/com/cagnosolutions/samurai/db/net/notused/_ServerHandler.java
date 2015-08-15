package com.cagnosolutions.samurai.db.netty.notused;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class _ServerHandler extends SimpleChannelInboundHandler<_Command> {

	private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	// this is called when a new client connects to the server
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel currentChan = ctx.channel();
		for(Channel channel : channels)
			channel.writeAndFlush(String.format("[SERVER] - %s has joined\n", currentChan.remoteAddress()));
		System.out.printf("[LOG] - %s has joined\n", currentChan.remoteAddress());
		channels.add(currentChan);
	}

	// this is called when a client disconnects from the server
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel currentChan = ctx.channel();
		for(Channel channel : channels)
			channel.writeAndFlush(String.format("[SERVER] - %s has disconnected\n", currentChan.remoteAddress()));
		System.out.printf("[LOG] - %s has disconnected\n", currentChan.remoteAddress());
		channels.remove(currentChan);
	}

	// message received
	protected void channelRead0(ChannelHandlerContext ctx, String[] msg) throws Exception {
		Channel currentChan = ctx.channel();
		channels.stream()
				.filter(channel -> channel != currentChan)
				.forEach(channel -> handleRead(channel, currentChan, msg[0]));
		System.out.printf("[LOG] - %s => %s, %s, %s\n", ctx.channel().remoteAddress(), msg[0],msg[1],msg[2]);
	}

	protected void handleRead(Channel otherChan, Channel currentChan, String msg) {
		//if(msg.equals("quit") || msg.equals("QUIT") || msg.equals("exit") || msg.equals("EXIT"))
		//	disconnect(currentChan);
		//else
			otherChan.writeAndFlush(String.format("[%s] %s\n", otherChan.remoteAddress(), msg));
	}

	protected void disconnect(Channel currentChan) {
		currentChan.writeAndFlush("[SERVER] - Goodbye!\n");
		channels.remove(currentChan);
		currentChan.close();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, _Command msg) throws Exception {

	}
}