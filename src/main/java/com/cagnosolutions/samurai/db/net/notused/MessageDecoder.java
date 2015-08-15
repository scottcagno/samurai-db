package com.cagnosolutions.samurai.db.netty.notused;

import com.cagnosolutions.samurai.db.common.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		byte[] array = new byte[msg.readableBytes()];
		msg.getBytes(0, array);
		byte[][] message = Message.decMsg(array);
		out.add(message);
	}
}
