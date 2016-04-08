package Kuchen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;

public class Ingridient {
	
	public String id;
	String name;
	BigDecimal price;
	int quantity;
	String quantityType;
	static String collection = DbNames.collection.INGRIDIENTS.toString();
		
	Document doc;
	
	public Ingridient(String _name, int _price, int _quantity, 
			String _quantityType){
		this.name = _name;
		this.price = new BigDecimal(_price);
		this.quantity = _quantity;
		this.quantityType = _quantityType;
	}
	
	public Ingridient(String _id, String _name,  String _price, int _quantity, 
			String _quantityType){
		
		this.id = _id;
		this.name = _name;
		this.price = new BigDecimal(_price);
		this.quantity = _quantity;
		this.quantityType = _quantityType;
	}

	
	public static Ingridient getById(int _id, MongoDatabase db){
		FindIterable<Document> ings;
		ings = Manager.getDocuments(collection, "id", _id, db);
		
		Document one;
		Ingridient found;
		
		
		//System.out.println("ehrlich jetzt?!");
			
		while(ings.iterator().hasNext()) {
			System.out.println(ings.iterator().next().toString());
		}

		ings.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    }
		});
		
		one = ings.first();
		found = DocToIngridient(one);
	
		
		return found;
	}
	
	public static Ingridient DocToIngridient(Document doc){
		
		Ingridient found = new Ingridient(
				doc.getString("id"), 
				doc.getString(DbNames.fieldIngridient.name.toString()), 
				doc.getString(DbNames.fieldIngridient.price.toString()), 
				doc.getInteger(DbNames.fieldIngridient.quantity.toString()), 
				doc.getString(DbNames.fieldIngridient.quantityType.toString()));
		
		return found;
	}
	
	public static Ingridient getByName(String _name, MongoDatabase db){
		FindIterable<Document> docs;
		docs = Manager.getDocuments(DbNames.collection.INGRIDIENTS.toString(), 
				DbNames.fieldIngridient.name.toString(), _name, db);
		Document doc = docs.first();
		
		return Ingridient.DocToIngridient(doc);
	}
	
	public static List<Ingridient> getAll(MongoDatabase db){
		
		FindIterable<Document> ings = Manager.getAllDocuments(collection, db);

		List<Ingridient> allIng = new ArrayList<Ingridient>();;
		
		ings.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	Ingridient gefunden = DocToIngridient(document);
		    	allIng.add(gefunden);
		    }
		});
		
		return allIng;
		
	}
	
 	public Document getDocument(){
		doc = new Document(DbNames.fieldIngridient.name.toString(), 	 this.name)
				.append(DbNames.fieldIngridient.price.toString(), 		 this.price.toString())
				.append(DbNames.fieldIngridient.quantity.toString(), 	 this.quantity)
				.append(DbNames.fieldIngridient.quantityType.toString(), this.quantityType);
		
		return doc;
	}
	/*
	public void update(MongoDatabase db){
		
		updateSingeVal(DbNames.fieldIngridient.name.toString(), 		this.name, db);
		updateSingeVal(DbNames.fieldIngridient.price.toString(), 		String.valueOf(this.price), db);
		updateSingeVal(DbNames.fieldIngridient.quantity.toString(), 	String.valueOf(this.quantity), db);
		updateSingeVal(DbNames.fieldIngridient.quantityType.toString(), this.quantityType, db);
		
	}
	
	private void updateSingeVal(String field, String value, MongoDatabase db){
				
		Manager.update(collection, 
				"id", 
				String.valueOf(this.id), 
				field, 
				value, 
				db);
	}*/
	
	public void save(MongoDatabase db){
		
		//FindIterable<Document> exists = Manager.getDocuments(collection, "id", this.id, db);
		
		//if(exists != null){
			
		//}
		
		doc = getDocument();
		
		Manager.insertDocument(doc, DbNames.collection.INGRIDIENTS.toString(), db);
	}

}
