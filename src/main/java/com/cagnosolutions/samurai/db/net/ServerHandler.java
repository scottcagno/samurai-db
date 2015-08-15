package com.cagnosolutions.samurai.db.net;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<byte[]> {

	public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {

		


		System.out.printf("[");
		for(int i = 0; i < msg.length; i++)
			if(i+1 == msg.length)
				System.out.printf("%d (%c)", msg[i], msg[i]);
			else
				System.out.printf("%d (%c), ", msg[i], msg[i]);
		System.out.printf("]\n");

		/*
		String[] ss = msg.split("\\s+", 3);
		System.out.printf("got (%d): ", ss.length);
		for(int i = 0; i < ss.length; i++)
			System.out.printf("'%s' ", ss[i]);
		System.out.println();
		if ("exit".equals(msg.toLowerCase()))
			ctx.close();
		*/
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}