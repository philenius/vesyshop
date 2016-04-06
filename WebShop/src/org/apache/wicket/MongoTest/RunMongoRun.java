package org.apache.wicket.MongoTest;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Connection.Manager;
import Kuchen.Cake;
import Kuchen.Ingridient;
import Kuchen.Recept;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import static java.util.Arrays.asList;

public class RunMongoRun {

	public static void main(String[] args) {

		String dbName = "mongodb";
		MongoDatabase database;
		String collection = "INGRIDIENTS";
		
		Connector con = new Connector();
		database = con.getDatabase(dbName);
		
		Ingridient zutat = new Ingridient(001, "Zucker", 10, 20, "gram");
		//zutat.save(database);
		
		Ingridient gefunden = Ingridient.getById(001, database);
		Ingridient gefunden2 = Ingridient.getById(1, database);
		
		//System.out.println(gefunden2.toString());
		
		
	}

}
