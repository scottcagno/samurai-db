package com.cagnosolutions.samurai.db.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	public void initChannel(SocketChannel ch) {
		ChannelPipeline pl = ch.pipeline();

		pl.addLast(new RequestDecoder(1024*6));

		//pl.addLast(new DelimiterBasedFrameDecoder(8192, Unpooled.wrappedBuffer(new byte[]{'\r','\n'})));
		//pl.addLast(new ByteArrayDecoder());
		//pl.addLast(new ByteArrayEncoder());
		pl.addLast(new ServerHandler());
	}
}
