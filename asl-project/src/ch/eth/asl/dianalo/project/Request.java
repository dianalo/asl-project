package ch.eth.asl.dianalo.project;

public class Request {
	public enum Type {GET, SET, DELETE};
	private Type type; 
	private String key;
	private String value;
	
	//maybe add something for time measurements...
	
	public Type getType(){
		return type;
	}
	
	public String getKey(){
		return key;
	}
	
	public String getValue(){
		return value;
	}
	
	public Request(String type, String key) throws Exception{
		switch(type){
		case "GET":
			this.type = Type.GET;
			break;
		case "SET":
			this.type = Type.SET;
			break;
		case "DELETE":
			this.type = Type.DELETE;
			break;
		default:
			throw new Exception("Unknown request type.");
		}
		
		this.key = key;
	}
	
	public Request(String type, String key, String value) throws Exception{
		this(type, key);
		
		if(this.type == Type.SET){
			this.value = value;
		}
	}
	
	public static Request parse(String rawRequest) throws UnsupportedOperationException{
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
