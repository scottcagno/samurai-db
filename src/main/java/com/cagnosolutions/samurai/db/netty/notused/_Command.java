package com.cagnosolutions.samurai.db.netty.notused;

import com.cagnosolutions.samurai.db.engine.common.BytesKey;
import com.cagnosolutions.samurai.db.engine.common.BytesVal;

/**
 * Created by Scott Cagno.
 * Copyright Cagno Solutions. All rights reserved.
 */

public class _Command {

	private Cmd cmd;
	private BytesKey key;
	private BytesVal val;

	public _Command(Cmd cmd, BytesKey key, BytesVal val) {
		this.cmd = cmd;
		this.key = key;
		this.val = val;
	}

	public Cmd getCmd() {
		return cmd;
	}

	public void setCmd(Cmd cmd) {
		this.cmd = cmd;
	}

	public BytesKey getKey() {
		return key;
	}

	public void setKey(BytesKey key) {
		this.key = key;
	}

	public BytesVal getVal() {
		return val;
	}

	public void setVal(BytesVal val) {
		this.val = val;
	}
}
