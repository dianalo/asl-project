package ch.eth.asl.dianalo.project;

import java.net.InetSocketAddress;

public class SetRequest extends WriteRequest {
	public SetRequest(InetSocketAddress clientAddress, String key, String value, int flags, int ttl, int len){
		super(clientAddress);
		this.key = key;
		this.value = value;
		this.flags = flags;
		this.ttl = ttl;
		this.len = len;
	}
	
	protected String value;
	protected int flags;
	protected int ttl;
	protected int len;
	
	public String getValue(){
		return value;
	}
	
	public int getFlags(){
		return flags;
	}
	
	public int getTtl(){
		return ttl;
	}
	
	public int getLen(){
		return len;
	}
}
