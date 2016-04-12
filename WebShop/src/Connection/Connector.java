package Connection;

import java.util.Arrays;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterDescription;
import com.mongodb.connection.ClusterType;
import com.mongodb.connection.ServerConnectionState;
import com.mongodb.connection.ServerDescription;
import com.mongodb.connection.ServerDescription.Builder;
import com.mongodb.selector.PrimaryServerSelector;
import com.mongodb.selector.ReadPreferenceServerSelector;

import Helper.DbNames;
import Helper.ErrorClass;

public class Connector {
	MongoClient mongoClient;
	MongoDatabase db;
	String ip_db_1 = "192.168.178.60";
	String ip_db_2 = "192.168.178.61";
	String ip_db_3 = "192.168.178.62";
	
	ServerAddress sa_1 = new ServerAddress(ip_db_1,27017);
	ServerAddress sa_2 = new ServerAddress(ip_db_2,27017);
	ServerAddress sa_3 = new ServerAddress(ip_db_3,27017);
	
	protected final static String dbName = "mongodb";
	
	
	public Connector(){
		
		PrimaryServerSelector serSel = new PrimaryServerSelector();
		Builder bb_1 = new Builder().address(sa_1).state(ServerConnectionState.CONNECTED);
		Builder bb_2 = new Builder().address(sa_2).state(ServerConnectionState.CONNECTED);
		Builder bb_3 = new Builder().address(sa_3).state(ServerConnectionState.CONNECTED);
		ServerDescription serDes_1 = bb_1.build();
		ServerDescription serDes_2 = bb_2.build();
		ServerDescription serDes_3 = bb_3.build();
		
		ClusterDescription clDes = new ClusterDescription(ClusterConnectionMode.MULTIPLE, 
				ClusterType.REPLICA_SET, Arrays.asList(serDes_1,serDes_2,serDes_3));
		
		
		/*
		mongoClient = new MongoClient(Arrays.asList(
				   sa_1, sa_2, sa_3
				   ));
		
		
		*/
		mongoClient = new MongoClient(Arrays.asList(
				   sa_1));
				   
		mongoClient.setReadPreference(ReadPreference.nearest());
		serSel.select(clDes);
		
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
