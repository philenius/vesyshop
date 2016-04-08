package Connection;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import Helper.DbNames;
import Helper.ErrorClass;

public class Connector {
	MongoClient mongoClient;
	MongoDatabase db;
	
	protected final static String dbName = "mongodb";
	
	public Connector(){
		mongoClient = new MongoClient();
	}
	
	public MongoDatabase getDatabase(){
		
		try {
			db = mongoClient.getDatabase(dbName);
		} catch (Exception e){
			System.out.println("UPS: Error to connect to database '" + dbName + "'!");
			System.out.println(e.toString());
			db = null;
		} 
		
		ErrorClass.checkNull(db, "Found database '" + dbName + "'.", "Database '" + dbName + "' not found!");
		
		//createCollections();
		
		return db;
	}
	
	
	private void createCollections(){
		db.createCollection(DbNames.collection.CAKES.toString());
		db.createCollection(DbNames.collection.INGRIDIENTS.toString());
		db.createCollection(DbNames.collection.ORDERS.toString());
		db.createCollection(DbNames.collection.USERS.toString());
		db.createCollection(DbNames.collection.RECEPTS.toString());
	}
	
	public void close(){
		mongoClient.close();
	}

}
