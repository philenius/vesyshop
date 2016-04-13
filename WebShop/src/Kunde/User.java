package Kunde;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;
import Kuchen.Recept;
import Order.ShoppingCart;

public class User {
	
	protected String id;
	protected String name;
	protected String password;
	public ShoppingCart cart = null;
	protected static String collection = DbNames.collection.USERS.toString();
	public String session = null;
	
	public User(String _name, String _password, ShoppingCart _cart){
		this.name = _name;
		this.password = _password;
		this.cart = _cart;
		this.session = _cart.session;
	}
	
	public User(String _name, String _password){
		this.name = _name;
		this.password = _password;
	}
	
	public User(String _id, String _name, String _password, ShoppingCart _cart){
		this.name = _name;
		this.password = _password;
		this.id = _id;
		this.cart = _cart;
		this.session = _cart.session;
	}
	
	public boolean checkPw(String passw){
		return this.password.equals(passw);
	}
	
	public Document getDocument(){
		Document doc = new Document("name", name)
				.append("password", password)
				.append("cart", this.cart.getDocument());
		return doc;
	}
	
	public void save(MongoDatabase db){
		Document doc = getDocument();
		
		Manager.insertDocument(doc, DbNames.collection.USERS.toString(), db);
	}
	
	public void updateCart(MongoDatabase db){
		Manager.update(collection, "name", this.name, "cart", this.cart.getDocument(), db);
	}
	
	public static User DocToUser(Document doc, MongoDatabase db){
		
		return new User(doc.getString("id"),
				doc.getString("name"),
				doc.getString("password"),
				ShoppingCart.docToCart((Document)doc.get("cart"), db));
	}
	
	public static User DocToUser( MongoDatabase db, Object doc){
		return DocToUser((Document)doc, db);
	}
	
	public static List<User> getAll(MongoDatabase db){
		List<User> users = new ArrayList<User>();
		
		FindIterable<Document> docs = Manager.getAllDocuments("USERS", db);
		
		docs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	User gefunden = DocToUser(document, db);
		    	users.add(gefunden);
		    }
		});
		
		return users;
	}

	/*
	public static User getById(String _id, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.USERS.toString(), "id", _id, db);
		
		User found = DocToUser(docs.first(), db);
		
		return found;
	}
	*/

	public static User getByName(String _name, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.USERS.toString(), "name", _name, db);
		
		if(docs.first() == null){
			return null;
		}
		
		User found = DocToUser(docs.first(), db);
		
		return found;
		
	}
	
	public void clearSession(MongoDatabase db){
		Manager.update(collection, "session", this.session, "session", "", db);
		this.session = null;

	}

	public static User getBySID(String jSID, MongoDatabase db) {
		FindIterable<Document> docs;
		docs = Manager.getDocuments("USERS", "session", jSID, db);
		
		if(docs.first() == null){
			return null;
		}
		else {
			return DocToUser(docs.first(), db);
		}
	}

}
