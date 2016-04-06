package Kuchen;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;

public class Cake {
	
	final int id;
	public Recept recept;
	public BigDecimal price;
	public String name;
	
	int Aufschlag = 10;
	
	protected String collection;
	protected Document doc;
	
	public Cake(int _id, Recept _recept, String _name){
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
		
		doc = new Document(DbNames.fieldCacke.id.toString(), this.id)
		.append(DbNames.fieldCacke.name.toString(), this.name)
		.append(DbNames.fieldCacke.Recept.toString(), this.recept.id);
		
		return doc;
	}
	
	public static List<Cake> getAll(){
		return null;
		
	}
	
	public void update(){
		//TODO
	}
	
}
