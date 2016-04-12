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
import Order.Booking;
import Order.ShoppingCart;
import Order.Status;

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
		
		Ingridient zucker 			= new Ingridient("Zucker", 12, 13, DbNames.QuantityType.gram.toString());
		Recept ZitronenkuchenRezept = new Recept(20, 200, "Rezept Zitronenkuchen", asList(zucker));
		Cake ZitronenKuchen 		= new Cake(ZitronenkuchenRezept, "Zitronenkuchen");
		ShoppingCart Einkaufswagen	= new ShoppingCart(asList(ZitronenKuchen),"olala123");
		User Phil 					= new User("Philipp","ozeanien", Einkaufswagen);
		
		Recept ApfelkuchenRezept = new Recept(20, 200, "Rezept Apfelkuchen", asList(zucker));
		Cake Apfelkuchen 		= new Cake(ApfelkuchenRezept, "Apfelkuchen");

		
		Booking Bestellung			= new Booking(12, Phil, new Status(Status.value.open), asList(ZitronenKuchen));
		
		zucker.save(db);
		ZitronenkuchenRezept.save(db);
		ZitronenKuchen.save(db);
		Einkaufswagen.save(db);
		Phil.save(db);
		Bestellung.save(db);
		Apfelkuchen.save(db);
		ApfelkuchenRezept.save(db);
		
		Einkaufswagen.addCacke(Apfelkuchen);
		Einkaufswagen.update(db);
		
		Ingridient.getAll(db);
		Recept.getAll(db);
		Cake.getAll(db);
		ShoppingCart.getAll(db);
		User.getAll(db);
		//Bestellung.getAll(db);
		

		

	}

}
