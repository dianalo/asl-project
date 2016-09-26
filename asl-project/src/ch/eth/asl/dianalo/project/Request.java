package ch.eth.asl.dianalo.project;

import java.net.InetSocketAddress;

public abstract class Request {
	protected String key;
	protected InetSocketAddress clientAddress;
	
	//maybe add something for time measurements...
	
	public Request(InetSocketAddress clientAddress){
		this.clientAddress = clientAddress;
	}
		
	public String getKey(){
		return key;
	}
	
	public InetSocketAddress getClientAddress(){
		return clientAddress;
	}
				
	public static Request makeRequest(InetSocketAddress clientAddress, String rawRequest) throws Exception{
		//don't look at value yet
		String[] tokens = rawRequest.split("[\\s]+",6); //split at one or more whitespaces
		if(tokens.length == 2){
			if(tokens[0].equals("get")){
				return new GetRequest(clientAddress, tokens[1]);
			}
			else if(tokens[0].equals("delete")){
				return new DeleteRequest(clientAddress, tokens[1]);
			}
			else{
				throw new Exception("Invalid memcached request.");
			}
		}
		else if(tokens.length == 6){
			if(tokens[0].equals("set")){
				return new SetRequest(clientAddress, tokens[1], tokens[5], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
			}
			else{
				throw new Exception("Invalid memcached request.");
			}
		}
		else{
			throw new Exception("Invalid memcached request.");
		}
	}
}
