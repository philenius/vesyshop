package Order;

public class Status {
	
	protected int id;
	public enum value{
		delivered,
		open,
		closed,
		paid
	}
	public String now;
	
	public Status(int _id, String val){
		this.id = _id;
		this.now = val;
	}
	
	public Status(int _id, value val){
		this.id = _id;
		this.now = val.toString();
	}
	
	public void changeTo(value val){
		this.now = val.toString();
	}
	
	public void changeTo(String val){
		this.now = val;
	}
	

}
