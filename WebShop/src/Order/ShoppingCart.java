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

public class ShoppingCart {
	protected String id;
	public List<Cake> cakes = new ArrayList<Cake>();
	BigDecimal price;
	public String session;
	
	public ShoppingCart(List<Cake> _cakes, String _session){
		this.cakes.addAll(_cakes);
		price = new BigDecimal(0);
		calculatePrice();
		this.session = _session;
	}
	
	public ShoppingCart(List<Cake> _cakes, String _id, String _price, String _session){
		this.cakes.addAll(_cakes);
		this.id = _id;
		price = new BigDecimal(Integer.valueOf(_price));
		this.session = _session;
	}
	
	public void addCakeByName(String cakeName, MongoDatabase db){
		Cake newCake = Cake.getByName(cakeName, db);
		this.addCacke(newCake);
		update(db);
	}
	
	public boolean removeCakeByName(String cakeName, MongoDatabase db){
		
		if(cakes == null)
		{
			return false;
		}
		
		for(Cake c : this.cakes){
			if(c.name.equals(cakeName)){
				cakes.remove(c);
				update(db);
				return true;
			}
		}
		
		
		
		return false;
	}
	
	public void delete(MongoDatabase db){
		Manager.deleteSingleObject(DbNames.collection.CARTS.toString(), "session", this.session, db);
	}
	
	private void calculatePrice(){
		
		for(Cake c : cakes){
			price = price.add(c.price);
		}

	}
	
	public void addCackes(List<Cake> cs){
		this.cakes.addAll(cs);
	}
	
	public void addCacke(Cake c){
		cakes.add(c);
	}
	
	public void removeCacke(Cake c){
		this.cakes.remove(c);
	}
	
	public void clear(){
		this.cakes.clear();
	}
	
	public Document getDocument(){
		List<Document> cakeDocs = new ArrayList<Document>();
		
		for(Cake c : cakes){
			cakeDocs.add(c.getDocument());
		}
		
		Document doc = new Document("price", price.toString()).
				append(DbNames.fieldCart.cakes.toString(),cakeDocs)
				.append("session", this.session); 
		
		return doc;
	}
	
	
	public void save(MongoDatabase db){
		
		Document doc = getDocument();
		
		Manager.insertDocument(doc, DbNames.collection.CARTS.toString(), db);
	}
	
	public static ShoppingCart docToCart(Document doc, MongoDatabase db){
		
		@SuppressWarnings("unchecked")
		List<Document> cakeDocs = (List<Document>) doc.get("cakes");
		
		if(cakeDocs == null){
			return null;
		}
		
		List<Cake> cakes = new ArrayList<Cake>();
		
		
		for(Document d : cakeDocs){
			
			if(d == null){
				return null;
			}
			
			cakes.add(Cake.DocToCake(d, db));
		}
		
		ShoppingCart sc = new ShoppingCart(cakes,
				doc.getString("id"),
				doc.getString("price"),
				doc.getString("session"));
		
		return sc;
	}
	
	
	public static List<ShoppingCart> getAll(MongoDatabase db){
		List<ShoppingCart> carts = new ArrayList<ShoppingCart>();
		
		FindIterable<Document> docs = Manager.getAllDocuments(DbNames.collection.CARTS.toString(), db);
		
		docs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	ShoppingCart gefunden = docToCart(document, db);
		    	carts.add(gefunden);
		    }
		});
		
		return carts;
	}
	
	public void update(MongoDatabase db) {
		
		List<Document> cakeDocs = new ArrayList<Document>();
		
		for(Cake c : cakes){
			cakeDocs.add(c.getDocument());
		}
		
		Manager.update("CARTS", "session", this.session, "cakes", cakeDocs, db);
	}
	
	
	public static ShoppingCart getBySessionId(String _id, MongoDatabase db){
		FindIterable<Document> docs = Manager.
				getDocuments(DbNames.collection.CARTS.toString(), "session", _id, db);
		
		if(docs == null || docs.first() == null){
			return null;
		}
		
		ShoppingCart found = docToCart(docs.first(), db);
		
		System.out.println(docs.first().toString());
				
		return found;
	}
	
	/**
	 * Transforms this cart object into JSON.
	 * 
	 * @return Returns this as a JSON string.
	 */
	public String toJSON() {
		
		calculatePrice();
		
		String json = "{";
		json += "\"price\":\"" + this.price + "\",";
		json += "\"count\":\"" + this.cakes.size() + "\",";
		json += "\"cakes\":" + Cake.listToJSON(this.cakes); 
		json += "}";
		return json;
	}

}
