package ch.eth.asl.dianalo.project;

public abstract class Request {
	protected String key;
	
	//maybe add something for time measurements...
		
	public String getKey(){
		return key;
	}
				
	public static Request parse(String rawRequest) throws Exception{
		//don't look at value yet
		String[] tokens = rawRequest.split("[\\s]+",6); //split at one or more whitespaces
		if(tokens.length == 2){
			if(tokens[0].equals("get")){
				return new GetRequest(tokens[1]);
			}
			else if(tokens[0].equals("delete")){
				return new DeleteRequest(tokens[1]);
			}
			else{
				throw new Exception("Invalid memcached request.");
			}
		}
		else if(tokens.length == 6){
			if(tokens[0].equals("set")){
				return new SetRequest(tokens[1], tokens[5], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
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
