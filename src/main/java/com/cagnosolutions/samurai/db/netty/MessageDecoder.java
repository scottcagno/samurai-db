package com.cagnosolutions.samurai.db.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class MessageDecoder extends LineBasedFrameDecoder {

	private static final byte D = (byte)':';

	public MessageDecoder(int maxLength) {
		super(maxLength);
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		ByteBuf m = (ByteBuf) super.decode(ctx, buffer); // run newline decoder
		if (m == null) // check to see if the buffered line was properly terminated ('\n' || '\r','\n' )
			return null; // ...because if not, then we don't have a line to further decode
		int[] n = new int[2]; // array to save deliminator positions
		int d = 0; // deliminator counter
		while(m.isReadable()) // iterate array backing the buffer
			if(m.readByte() == D) { // look for a deliminator
				int i = m.readerIndex();  // found deliminator, save current index in buffer
				n[d] = (d == 0) ? i-1 : (i-2) - n[0];  // if first deliminator, save (index-1) to position n[0],
				// otherwise save (index-2)-n[0] to position n[1]
				d++;  // increment deliminator count
			}
		if(d != 2) // check to see if we counted at least 2 deliminators
			return null; // ...because if we didn't, then we don't have valid data
		m.resetReaderIndex(); // reset index pointer on array, so we can start to read the byte chunks into new buffers
		ByteBuf cmd = m.readBytes(n[0]); // read the first n[0] bytes
		ByteBuf set = m.skipBytes(1).readBytes(n[1]); // skip the first deliminator, then read first n[1] bytes
		ByteBuf dat = m.skipBytes(1).readBytes(m.readableBytes()); // skip the second deliminator, then read remaining bytes
		return new ByteBuf[]{ cmd, set, dat };
	}
}
