package Connection;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import Helper.ErrorClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import static java.util.Arrays.asList;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.ascending;

public class Manager {
	
	// INSERT
	public static void insertDocument(Document doc, String collection, MongoDatabase db){
		try {
			db.getCollection(collection).insertOne(doc);
			System.out.println("SUCCESS: \tInserted data..");
		} catch (Exception e){
			System.out.println("FAIL: \tInserting data went wrong.");
			System.out.println(e);
		}
	}
	
	// GET 
	public static FindIterable<Document> getAllDocuments(String collection, MongoDatabase db){
		
		FindIterable<Document> iterable = null;
		
		getDocuments(collection, null, null, db);
		
		return iterable;
	}
	
	public static FindIterable<Document> getDocuments(String collection, String field, 
			int value, MongoDatabase db){
		return getDocuments(collection, field, String.valueOf(value), db);
	}
	
	public static FindIterable<Document> getDocuments(String collection, String field, 
			String value, MongoDatabase db){
		
		FindIterable<Document> iterable = null;
		
		try {
			
			if(field.equals(null) && value.equals(null)){
				System.out.println("Search for all documents in collection '" + collection + "'");
				iterable = db.getCollection(collection).find();
			} else {
				System.out.println("Search for document with <" + value + "> in field <" + field +">");
				iterable = db.getCollection(collection).find(eq(field,value));
			}
			
		} catch (Exception e){
			System.out.println("FAIL: Something went wrong trying to get documents.");
			System.out.println(e.toString());
		}
		
		
		ErrorClass.checkNull(iterable, "Found documents", "No documents found");
		
		return iterable;
	}
	
	// SHOW [toString()]
	public static void showDocuments(FindIterable<Document> docs){
		
		docs.forEach(new Block<Document>() {
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
	}
	
	public static void showAllDocuments(String collection, MongoDatabase db){
		FindIterable<Document> iterable = getAllDocuments(collection, db);
		
		showDocuments(iterable);
	}

	// UPDATE
	public static void update(String collection, String whereField, String whereValue,
			String newField, String newValue, MongoDatabase db){
		
		UpdateResult result = null;
		
		try {
			
			result = db.getCollection(collection).updateMany(new Document(whereField, whereValue),
			        new Document("$set", new Document(newField, newValue)));
			
		}catch (Exception e){
			System.out.println("FAIL: \tUpdating data went wrong!");
			System.out.println(e);
		}
		
		long rows = result.getModifiedCount();
		System.out.println("INFO: \t"+rows +" row(s) modified.");
	}
	
	public static void update(String collection, List<List<String>> where,
			List<List<String>> values, MongoDatabase db){
		
		UpdateResult result = null;
		Document whereDoc = null;
		Document newValDoc = null;
		
		for(List<String> twoVal : where){
			whereDoc.append(twoVal.get(0), twoVal.get(1));
		}
		
		for(List<String> twoVal : values){
			newValDoc.append(twoVal.get(0), twoVal.get(1));
		}
		
		try {
			
			result = db.getCollection(collection).updateMany(whereDoc,
					new Document("$set", newValDoc));
			
		}catch (Exception e){
			System.out.println("FAIL: \tUpdating data went wrong!");
			System.out.println(e);
		}
		
		long rows = result.getModifiedCount();
		System.out.println("INFO: \t"+rows +" row(s) modified.");
		
	}
	
}
