package com.cagnosolutions.samurai.db.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class RequestDecoder extends LineBasedFrameDecoder {

	public RequestDecoder(int maxLength) {
		super(maxLength);
	}

	public RequestDecoder(int maxLength, boolean stripDelimiter, boolean failFast) {
		super(maxLength, stripDelimiter, failFast);
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		ByteBuf buf = (ByteBuf) super.decode(ctx, buffer); // run newline decocder
		if(buf == null) // check to see if the buffered line was properly terminated
			return null; // ...because if not, then we don't have a line to further decode
		if(buf.readableBytes() < 7)
			return null;
		byte opcode = buf.readByte();
		int keylen = decLen(buf.readBytes(3).array());
		int vallen = decLen(buf.readBytes(3).array());
		if(buf.isReadable(keylen + vallen)) {
			byte[] body = new byte[1 + keylen + vallen];
			body[0] = opcode;
			buf.readBytes(body, 1, keylen + vallen);
			return body;
		}
		return null;
	}

	public static int decLen(byte[] blen) {
		int b3 = blen[2] >= 0 ? blen[2] : 256 + blen[2];
		int b2 = blen[1] >= 0 ? blen[1] : 256 + blen[1];
		int b1 = blen[0] >= 0 ? blen[0] : 256 + blen[0];
		return b1 | (b2 << 8) | (b3 << 16);
	}

}
