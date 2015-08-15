package com.cagnosolutions.samurai.db.netty.notused;

import com.cagnosolutions.samurai.db.engine.common.BytesKey;
import com.cagnosolutions.samurai.db.engine.common.BytesVal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class _MessageDecoder extends LineBasedFrameDecoder {

	private static final byte D = (byte)':';
	private static int delim = 0;

	public _MessageDecoder(int maxLength) {
		super(maxLength);
	}

	protected _Command decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
		ByteBuf m = (ByteBuf) super.decode(ctx, buffer);
		if(m == null) return null;
		Cmd cmd = null;
		BytesKey key = null;
		BytesVal val = null;
		int marker = 0;
		while(m.isReadable()) {
			if(m.readByte() == D) {
				if(delim == 0) cmd = new Cmd(m.slice(0, marker).array());
				else if(delim == 1) key = new BytesKey(m.slice(marker, m.readerIndex()).array());
				else if(delim == 2) val = new BytesVal(m.slice(marker, m.readableBytes()).array());
				delim++;
				marker = m.readerIndex();
			}
		}
		return new _Command(cmd, key, val);
	}



	protected Object _decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
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
		byte[] cmd = m.readBytes(n[0]).array(); // read the first n[0] bytes
		byte[] key = m.skipBytes(1).readBytes(n[1]).array(); // skip the first deliminator, then read first n[1] bytes
		byte[] val = m.skipBytes(1).readBytes(m.readableBytes()).array(); // skip the second deliminator, then read remaining bytes
		return new String[]{
				new String(cmd), new String(key), new String(val)
		}; // create a new array, passing in the newly parsed buffers
	}
}
