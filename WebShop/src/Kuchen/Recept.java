package Kuchen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import static java.util.Arrays.asList;

import Connection.Connector;
import Connection.Manager;
import Helper.DbNames;

public class Recept {
	
	public String id;
	int costs;
	String name;
	int timeToComplete;
	List<Ingridient> ingridients;
	
	protected Document doc;

	public Recept(int _costs, int _time, String _name, List<Ingridient> _ingridients){
		this.costs = _costs;
		this.timeToComplete = _time;
		this.name = _name;
		this.ingridients = _ingridients;
	}
	
	public Recept(String _id, int _costs, int _time, String _name, List<Ingridient> _ingridients){
		this.id = _id;
		this.costs = _costs;
		this.timeToComplete = _time;
		this.name = _name;
		this.ingridients = _ingridients;
	}
	
	public void save(MongoDatabase db){
		
		String collection = DbNames.collection.RECEPTS.toString();
		Document doc = getDocument();
		
		Manager.insertDocument(doc, collection, db);
	}
	/*
	public void update(MongoDatabase db){
		
		Document doc = getDocument();
		
		updateSingleVal(db, 
				DbNames.fieldRecept.name.toString(), 
				this.name);
		
		updateSingleVal(db,
				DbNames.fieldRecept.timeToComplete.toString(), 
				String.valueOf(this.timeToComplete));
		
		updateSingleVal(db,
				DbNames.fieldRecept.costs.toString(),
				String.valueOf(this.costs));
		
		updateIngridients(db);
	}
	*/
	/*
	private void updateIngridients(MongoDatabase db){
		
		String collection = DbNames.collection.RECEPTS.toString();
		
		List<String> where = asList(DbNames.fieldRecept.id.toString(),String.valueOf(this.id));
		List<List<String>> where_2 = asList(where);
		
		List<List<String>> value_2 = null;
		
		for(Ingridient i : ingridients){
			List<String> value_id = asList("id", String.valueOf(i.id));
			List<String> value_quantity = asList(DbNames.fieldIngridient.quantity.toString(), 
					String.valueOf(i.quantity));
			
			value_2.add(value_id);
			value_2.add(value_quantity);
		}
		
		
		Manager.update(collection, where_2, value_2, db);
	}
	
	private void updateSingleVal(MongoDatabase db, String field, String value){
		
		String collection = DbNames.collection.RECEPTS.toString();

		Manager.update(collection, 
				DbNames.fieldRecept.id.toString(), 
				String.valueOf(this.id), 
				field, 
				value, 
				db);
	}
	*/
	private List<Document> getIngridDocs(){
		
		List<Document> ingridDocs = new ArrayList<Document>();
		
		//save ids of ingridients
		for(Ingridient i : ingridients){ 
			ingridDocs.add(new Document(DbNames.fieldIngridient.name.toString()	,i.name)
					.append(DbNames.fieldIngridient.quantity.toString(), i.quantity));
		}
		
		return ingridDocs;
	}
	
	public static Recept DocToRecept(Document doc, MongoDatabase db){
		
		List<Document> ingriDocs = (List<Document>) doc.get(DbNames.fieldRecept.Ingridients.toString());
		List<Ingridient> ings = new ArrayList<Ingridient>();
		
		for(Document d : ingriDocs){
			String theName = d.getString("name");
			ings.add(Ingridient.getByName(theName, db));
		}
		
		Recept found = new Recept(
				doc.getString("id"), 
				doc.getInteger(DbNames.fieldRecept.costs.toString()), 
				doc.getInteger(DbNames.fieldRecept.timeToComplete.toString()), 
				doc.getString(DbNames.fieldRecept.name.toString()), 
				ings); 
		
		return found;
	}
	
	public static Recept getByName(String _name, MongoDatabase db){
		FindIterable<Document> docs;
		System.out.println("Suche nach " + _name + " recept");
		docs = Manager.getDocuments(DbNames.collection.RECEPTS.toString(), DbNames.fieldRecept.name.toString(), _name, db);
		
		Document doc = docs.first();
		
		if(doc == null){
			System.out.println("Kein Rezept gefunden");
		}
				
		return DocToRecept(doc, db);
		
	}
	
	public static Recept getById(String _id, MongoDatabase db){
		FindIterable<Document> docs;
		docs = Manager.getDocuments(DbNames.collection.RECEPTS.toString(), "id", _id, db);
		
		Document doc = docs.first();
		
		return DocToRecept(doc, db);
		
	}
	
	public static List<Recept> getAll(MongoDatabase db){
		
		FindIterable<Document> docs;
		List<Recept> recepts = new ArrayList<Recept>();
		
		docs = Manager.getAllDocuments(DbNames.collection.RECEPTS.toString(), db);
		
		docs.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	System.out.println(document.toString());
		    	Recept gefunden = DocToRecept(document, db);
		    	recepts.add(gefunden);
		    }
		});
		
		return recepts;
	}
	
	public Document getDocument(){
		
		List<Document> ingridDocs = getIngridDocs();
		
		doc = new Document(DbNames.fieldRecept.name.toString(), 		this.name)
				.append(DbNames.fieldRecept.timeToComplete.toString(), 	this.timeToComplete)
				.append(DbNames.fieldRecept.costs.toString(), 			this.costs)
				.append(DbNames.fieldRecept.Ingridients.toString(), 	ingridDocs);
		
		return doc;
	}
	
	
}
