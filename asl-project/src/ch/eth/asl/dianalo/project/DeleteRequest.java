package ch.eth.asl.dianalo.project;

import java.net.InetSocketAddress;

public class DeleteRequest extends WriteRequest {
	public DeleteRequest(InetSocketAddress clientAddress, String key){
		super(clientAddress);
		this.key = key;
	}
}
