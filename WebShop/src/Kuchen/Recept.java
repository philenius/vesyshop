package Kuchen;

import java.math.BigDecimal;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import static java.util.Arrays.asList;

import Connection.Manager;
import Helper.DbNames;

public class Recept {
	
	final int id;
	int costs;
	String name;
	int timeToComplete;
	List<Ingridient> ingridients;
	
	protected Document doc;

	public Recept(int _id, int _costs, int _time, String _name, List<Ingridient> _ingridients){
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
	
	private List<Document> getIngridDocs(){
		
		List<Document> ingridDocs = null;
		
		//save ids of ingridients
		for(Ingridient i : ingridients){ 
			ingridDocs.add(new Document("id",i.id)
					.append(DbNames.fieldIngridient.quantity.toString(), i.quantity));
		}
		
		return ingridDocs;
	}
	
	public Document getDocument(){
		
		List<Document> ingridDocs = getIngridDocs();
		
		doc = new Document(DbNames.fieldRecept.id.toString(), this.id)
				.append(DbNames.fieldRecept.name.toString(), this.name)
				.append(DbNames.fieldRecept.timeToComplete.toString(), this.timeToComplete)
				.append(DbNames.fieldRecept.costs.toString(), this.costs)
				.append(DbNames.fieldRecept.Ingridients.toString(), ingridDocs);
		
		return doc;
	}
	
	
}
