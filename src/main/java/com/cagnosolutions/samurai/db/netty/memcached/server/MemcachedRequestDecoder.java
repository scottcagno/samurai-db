package com.cagnosolutions.samurai.db.netty.memcached.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class MemcachedRequestDecoder extends ByteToMessageDecoder {

	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

	}
}
