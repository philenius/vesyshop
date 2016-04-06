package Kuchen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.Mongo;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;

public class Cake {
	
	final String id;
	public Recept recept;
	public BigDecimal price;
	public String name;
	
	int Aufschlag = 10;
	
	protected String collection;
	protected Document doc;
	
	public Cake(String _id, Recept _recept, String _name){
		this.id = _id;
		this.recept = _recept;
		this.name = _name;
		
		this.collection = DbNames.collection.CAKES.toString();
		
		calculatePrice();
	}
	
	private void calculatePrice(){
		
		price = new BigDecimal(recept.costs);
		for (Ingridient ing : recept.ingridients){
			price = price.add(ing.price);
		}
		price.add(new BigDecimal(Aufschlag));
	}

	public void save(MongoDatabase db){
		
		Document doc = getDecument();
		
		Manager.insertDocument(doc, this.collection, db);
	}
	
	public Document getDecument(){
		Document doc = null;
		
		doc = new Document(DbNames.fieldCacke.name.toString(), this.name)
		.append(DbNames.fieldCacke.Recept.toString(), this.recept.id);
		
		return doc;
	}
	
	private static Cake DocToCake(Document doc, MongoDatabase db){
		Cake cake;
		
		cake = new Cake(
				doc.getString("id"), 
				new Recept("2b", 12, 200, "Irgendwas", Ingridient.getAll(db)), 
				doc.getString(DbNames.fieldCacke.name.toString()));
		
		return cake;
	}
	
	public static List<Cake> getAll(MongoDatabase db){
		
		List<Cake> cakes = new ArrayList<Cake>();
		
		FindIterable<Document> cakeDocs = Manager.getAllDocuments(DbNames.collection.CAKES.toString(), db);
		
		cakeDocs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	Cake gefunden = DocToCake(document, db);
		    	//System.out.println(gefunden.toString());
		    	cakes.add(gefunden);
		    }
		});
		
		return cakes;
		
	}
	/*
	public void update(){
		//TODO
	}
	*/
}
