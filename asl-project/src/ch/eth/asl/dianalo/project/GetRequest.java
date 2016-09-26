package ch.eth.asl.dianalo.project;

import java.net.InetSocketAddress;

public class GetRequest extends ReadRequest {
	public GetRequest(InetSocketAddress clientAddress, String key){
		super(clientAddress);
		this.key = key;
	}
}
