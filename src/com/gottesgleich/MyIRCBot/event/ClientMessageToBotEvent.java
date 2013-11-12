package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

public class ClientMessageToBotEvent extends IRCEvent {

	private String clientname;
	private String msg;
	private String hostmask;

	public ClientMessageToBotEvent(IRCConnection con, String clientname,
			String msg, String hostmask) {
		super(con);
		this.clientname = clientname;
		this.msg = msg;
		this.hostmask = hostmask;
	}

	public String getMessage() {
		return this.msg;
	}

	public String getClientName() {
		return this.clientname;
	}

	public String getHostmask() {
		return this.hostmask;
	}
}
