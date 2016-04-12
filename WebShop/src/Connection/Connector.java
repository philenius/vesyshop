package Connection;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ClusterType;
import com.mongodb.connection.ServerDescription;
import com.mongodb.connection.ServerDescription.Builder;
import com.mongodb.selector.PrimaryServerSelector;

import Helper.DbNames;
import Helper.ErrorClass;

public class Connector {
	MongoClient mongoClient;
	MongoDatabase db;
	String ip_db_1 = "192.168.178.60";
	String ip_db_2 = "192.168.178.61";
	String ip_db_3 = "192.168.178.62";
	
	protected final static String dbName = "mongodb";
	
	
	public Connector(){
		mongoClient = new MongoClient(//new MongoClientURI("mongodb://192.168.178.60:27017/mongodb"));
				//ip_db_1, 27017);
				Arrays.asList(
				   new ServerAddress(ip_db_1, 27017)
				  // , new ServerAddress(ip_db_2, 27017)
				  // , new ServerAddress(ip_db_3, 27017)
				   ));
		
		PrimaryServerSelector serSel = new PrimaryServerSelector();
		ServerAddress sa_1 = new ServerAddress(ip_db_1);
		ServerAddress sa_2 = new ServerAddress(ip_db_2);
		ServerAddress sa_3 = new ServerAddress(ip_db_3);
		Builder bb_1 = new Builder().address(sa_1);
		Builder bb21 = new Builder().address(sa_2);
		Builder bb_3 = new Builder().address(sa_3);
		ClusterType type = ClusterType.SHARDED;
		//ServerDescription serDes = new ServerDescription();
		//serSel.select(new ClusterDescription(ClusterConnectionMode.SINGLE, ClusterType.UNKNOWN, Arrays.asList(serDes)));
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
