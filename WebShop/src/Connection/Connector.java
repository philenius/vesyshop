package Connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import Helper.ErrorClass;

public class Connector {
	MongoClient mongoClient;
	MongoDatabase db;
	
	public Connector(){
		mongoClient = new MongoClient();
	}
	
	public MongoDatabase getDatabase(String name){
		
		try {
			db = mongoClient.getDatabase(name);
		} catch (Exception e){
			System.out.println("UPS: Error to connect to database '" + name + "'!");
			System.out.println(e.toString());
			db = null;
		} 
		
		ErrorClass.checkNull(db, "Found database '" + name + "'.", "Database '" + name + "' not found!");
		
		return db;
	}
	
	public void close(){
		mongoClient.close();
	}

}
