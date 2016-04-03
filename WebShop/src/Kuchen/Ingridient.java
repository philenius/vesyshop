package Kuchen;

import java.math.BigDecimal;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;

public class Ingridient {
	
	final int id;
	String name;
	BigDecimal price;
	int quantity;
	String quantityType;
	String collection;
	
	Document doc;
	
	public Ingridient(int _id, String _name, int _price, int _quantity, 
			String _quantityType){
		this.id = _id;
		this.name = _name;
		this.price = new BigDecimal(_price);
		this.quantity = _quantity;
		this.quantityType = _quantityType;
		
		collection = DbNames.collection.INGRIDIENTS.toString();
	}
	
	public Document getDocument(){
		doc = new Document(DbNames.fieldIngridient.id.toString(), 		 this.id)
				.append(DbNames.fieldIngridient.name.toString(), 		 this.name)
				.append(DbNames.fieldIngridient.price.toString(), 		 this.price)
				.append(DbNames.fieldIngridient.quantity.toString(), 	 this.quantity)
				.append(DbNames.fieldIngridient.quantityType.toString(), this.quantityType);
		
		return doc;
	}
	
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
	}
	
	public void save(MongoDatabase db){
		
		FindIterable<Document> exists = Manager.getDocuments(collection, "id", this.id, db);
		
		if(exists != null){
			
		}
		
		doc = getDocument();
		
		Manager.insertDocument(doc, DbNames.collection.INGRIDIENTS.toString(), db);
	}

}
