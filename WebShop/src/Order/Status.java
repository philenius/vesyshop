package Order;

import org.bson.Document;

import Helper.DbNames;

public class Status {
	
	//protected int id;
	public enum value{
		delivered,
		open,
		closed,
		paid
	}
	public String now;
	
	public Status(String val){
		this.now = val;
	}
	
	public Status(value val){
		this.now = val.toString();
	}
	
	public void changeTo(value val){
		this.now = val.toString();
	}
	
	public void changeTo(String val){
		this.now = val;
	}
	
	public Document getDocument(){
		Document doc = new Document(DbNames.fieldStatus.status.toString(), now);
				
		return doc;
	}
	

}
