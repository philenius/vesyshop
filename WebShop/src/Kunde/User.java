package Kunde;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;
import Kuchen.Recept;

public class User {
	
	protected String id;
	protected String name;
	protected String password;
	
	public User(String _name, String _password){
		this.name = _name;
		this.password = _password;
	}
	
	public User(String _id, String _name, String _password){
		this.name = _name;
		this.password = _password;
		this.id = _id;
	}
	public boolean checkPw(String passw){
		return this.password.equals(passw);
	}
	public void save(MongoDatabase db){
		Document doc = new Document("name", name).append("password", password);
		
		Manager.insertDocument(doc, DbNames.collection.USERS.toString(), db);
	}
	
	
	
	private static User DocToUser(Document doc){
		return new User(doc.getString("id"),
				doc.getString("name"),
				doc.getString("password"));
	}
	
	public static List<User> getAll(MongoDatabase db){
		List<User> users = new ArrayList<User>();
		
		FindIterable<Document> docs = Manager.getAllDocuments("USERS", db);
		
		docs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	User gefunden = DocToUser(document);
		    	users.add(gefunden);
		    }
		});
		
		return users;
	}
	
	public static User getById(String _id, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.USERS.toString(), "id", _id, db);
		
		User found = DocToUser(docs.first());
		
		return found;
	}
	
	public static User getByName(String _name, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.USERS.toString(), "name", _name, db);
		
		User found = DocToUser(docs.first());
		
		return found;
		
	}

}
