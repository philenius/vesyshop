package org.apache.wicket.MongoTest;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Connection.Manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import static java.util.Arrays.asList;

public class RunMongoRun {

	public static void main(String[] args) {

		String dbName = "test";
		MongoDatabase database;
		String collection = "restaurants";
		
		Connector con = new Connector();
		database = con.getDatabase(dbName);
		
		/*DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		Document doc = null;
		try {
			doc = new Document("address",
			        new Document()
			        .append("street", "2 Avenue")
			        .append("zipcode", "10075")
			        .append("building", "1480")
			        .append("coord", asList(-73.9557413, 40.7720266)))
			.append("borough", "Manhattan")
			.append("cuisine", "Italian")
			.append("grades", asList(
			        new Document()
			                .append("date", format.parse("2014-10-01T00:00:00Z"))
			                .append("grade", "A")
			                .append("score", 11),
			        new Document()
			                .append("date", format.parse("2014-01-16T00:00:00Z"))
			                .append("grade", "B")
			                .append("score", 17)))
			.append("name", "Vella")
			.append("restaurant_id", "41704620");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Manager.insertDocument(doc, collection, database); */
		
		//Manager.showAllDocuments(collection, database);
		
		//FindIterable<Document> docs = Manager.getDocuments(collection, "address.building", "1480", database);
		
		Manager.update(collection, "name", "Vella", "cuisine", "FINDE", database);
		
		Manager.showDocuments(Manager.getDocuments(collection, "name", "Vella", database));
		

		
	}

}
