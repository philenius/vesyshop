package Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;
import Kuchen.Cake;
import Kunde.User;

public class ShoppingCart {
	protected String id;
	public List<Cake> cakes;
	BigDecimal price;
	
	public ShoppingCart(List<Cake> _cakes){
		this.cakes = _cakes;
		calculatePrice();
	}
	
	public ShoppingCart(List<Cake> _cakes, String _id, String _price){
		this.cakes = _cakes;
		this.id = _id;
		price = new BigDecimal(Integer.valueOf(_price));
	}
	
	private void calculatePrice(){
		/*
		for(Cake c : cakes){
			price = price.add(c.price);
		}*/ //TODO
		
		price = new BigDecimal(20);
	}
	
	public void addCackes(List<Cake> cs){
		this.cakes.addAll(cs);
	}
	
	public void addCacke(Cake c){
		this.cakes.add(c);
	}
	
	public void removeCacke(Cake c){
		this.cakes.remove(c);
	}
	
	public void clear(){
		this.cakes.clear();
	}
	
	public void save(MongoDatabase db){
		List<Document> cakeDocs = new ArrayList<Document>();
		
		for(Cake c : cakes){
			cakeDocs.add(c.getDecument());
		}
		
		Document doc = new Document("price", price.toString()).
				append(DbNames.fieldCart.cakes.toString(),cakeDocs); 
		
		Manager.insertDocument(doc, DbNames.collection.CARTS.toString(), db);
	}
	
	
	
	private static ShoppingCart DocToCart(Document doc){
		
		List<Document> cakeDocs = (List<Document>) doc.get("cakes");
		List<Cake> cakes = new ArrayList<Cake>();
		
		
		for(Document d : cakeDocs){
			cakes.add(Cake.DocToCake(doc));
		}
		
		ShoppingCart sc = new ShoppingCart(cakes,
				doc.getString("id"),
				doc.getString("price"));
		
		return sc;
	}
	
	public static List<ShoppingCart> getAll(MongoDatabase db){
		List<ShoppingCart> carts = new ArrayList<ShoppingCart>();
		
		FindIterable<Document> docs = Manager.getAllDocuments(DbNames.collection.CARTS.toString(), db);
		
		docs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	ShoppingCart gefunden = DocToCart(document);
		    	carts.add(gefunden);
		    }
		});
		
		return carts;
	}
	
	public static ShoppingCart getById(String _id, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.CARTS.toString(), "id", _id, db);
		
		ShoppingCart found = DocToCart(docs.first());
		
		return found;
	}

}
