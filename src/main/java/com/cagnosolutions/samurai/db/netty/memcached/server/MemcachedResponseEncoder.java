package com.cagnosolutions.samurai.db.netty.memcached.server;

import com.cagnosolutions.samurai.db.netty.memcached.MemcachedResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class MemcachedResponseEncoder extends MessageToByteEncoder<MemcachedResponse> {

	protected void encode(ChannelHandlerContext ctx, MemcachedResponse msg, ByteBuf out) throws Exception {

	}
}
