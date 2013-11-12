package com.gottesgleich.MyIRCBot.event;

import com.gottesgleich.MyIRCBot.IRCChannel;
import com.gottesgleich.MyIRCBot.IRCClient;
import com.gottesgleich.MyIRCBot.IRCConnection;
import com.gottesgleich.MyIRCBot.event.api.IRCEvent;

public class ClientJoinedChannelEvent extends IRCEvent {

	private IRCClient client;
	private IRCChannel channel;
	private String hostmask;

	public ClientJoinedChannelEvent(IRCConnection con, IRCClient client,
			IRCChannel channel, String hostmask) {
		super(con);
		this.client = client;
		this.channel = channel;
		this.hostmask = hostmask;
	}

	public IRCClient getClient() {
		return this.client;
	}

	public IRCChannel getChannel() {
		return this.channel;
	}

	public String getHostmask() {
		return this.hostmask;
	}
}
