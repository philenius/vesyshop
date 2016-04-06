package org.apache.wicket.MongoTest;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Connector;
import Connection.Manager;
import Helper.DbNames;
import Kuchen.Cake;
import Kuchen.Ingridient;
import Kuchen.Recept;
import Kunde.User;
import Order.ShoppingCart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.bson.Document;

import static java.util.Arrays.asList;

public class RunMongoRun {

	public static void main(String[] args) {

		MongoDatabase db;
		
		Connector con = new Connector();
		db = con.getDatabase();
		
		Manager.deleteAllDocsIn(db, DbNames.collection.CAKES.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.CARTS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.INGRIDIENTS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.ORDERS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.RECEPTS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.USERS.toString());
		
		Ingridient zucker 			= new Ingridient(null, "Zucker", 12, 13, "Gramm");
		Recept ZitronenkuchenRezept = new Recept(null, 20, 200, "Rezept Zitronenkuchen", asList(zucker));
		Cake ZitronenKuchen 		= new Cake(null, ZitronenkuchenRezept, "Zitronenkuchen");
		User Phil 					= new User("Philipp","ozeanien");
		ShoppingCart Einkaufswagen	= new ShoppingCart(asList(ZitronenKuchen));
		
		zucker.save(db);
		ZitronenkuchenRezept.save(db);
		ZitronenKuchen.save(db);
		Einkaufswagen.save(db);
		Phil.save(db);
		
		Ingridient.getAll(db);
		Recept.getAll(db);
		Cake.getAll(db);
		ShoppingCart.getAll(db);
		User.getAll(db);
		
	}

}
