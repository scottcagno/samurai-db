package com.cagnosolutions.samurai.db.net;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.BufferedWriter;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

@ChannelHandler.Sharable
public class AppendOnlyFileHandler extends SimpleChannelInboundHandler<String> {

	private static BufferedWriter writer = null;

	public AppendOnlyFileHandler(final BufferedWriter writer) {
		AppendOnlyFileHandler.writer = writer;
	}

	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		writer.write(msg + "\n");
		writer.flush();
		ctx.fireChannelRead(msg);
	}
}
