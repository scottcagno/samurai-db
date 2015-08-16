package com.cagnosolutions.samurai.db.net;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

	public void channelRead0(ChannelHandlerContext ctx, String msg) {
		handleRead(ctx, msg);
		ctx.write(msg + "\n");
	}


	private void handleRead(ChannelHandlerContext ctx, String msg) {

		String[] ss = msg.split("\\s+", 3);

		System.out.printf("got (%d): ", ss.length);
		for(int i = 0; i < ss.length; i++)
			System.out.printf("'%s' ", ((i + 1 <= ss.length) ? ss[i] : "\n"));
		if ("exit".equals(msg.toLowerCase()))
			ctx.close();
	}

	public void channelReadComplete(ChannelHandlerContext ctx) {
		// flushes all pending messages to the remote peer
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
				// closes the channel after the operation is complete
				.addListener(ChannelFutureListener.CLOSE);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}