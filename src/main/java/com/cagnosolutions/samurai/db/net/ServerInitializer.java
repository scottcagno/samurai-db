package com.cagnosolutions.samurai.db.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedWriter;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	private static final ByteBuf DELIMITER = Unpooled.wrappedBuffer(new byte[]{'\r','\n'});
	private static final int KB = 1024;
	private static final int MB = 1024*KB;

	private static BufferedWriter writer = null;

	public ServerInitializer(final BufferedWriter writer) {
		ServerInitializer.writer = writer;
	}

	public void initChannel(SocketChannel ch) {
		ChannelPipeline pl = ch.pipeline();
		pl.addLast(new DelimiterBasedFrameDecoder(256*MB, DELIMITER));
		pl.addLast(new StringDecoder(CharsetUtil.UTF_8));
		pl.addLast(new AppendOnlyFileHandler(writer));
		pl.addLast(new StringEncoder(CharsetUtil.UTF_8));
		pl.addLast(new ServerHandler());
	}
}
