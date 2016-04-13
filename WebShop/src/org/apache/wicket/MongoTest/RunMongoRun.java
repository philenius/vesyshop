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
		/*
		Manager.deleteAllDocsIn(db, DbNames.collection.INGRIDIENTS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.ORDERS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.RECEPTS.toString());
		Manager.deleteAllDocsIn(db, DbNames.collection.USERS.toString());
		*/
		Ingridient zucker 			= new Ingridient("Zucker", 12, 13, DbNames.QuantityType.gram.toString());
		zucker.save(db);
		Ingridient zitronen 		= new Ingridient("Zitronen", 8, 100, DbNames.QuantityType.gram.toString());
		zitronen.save(db);
		Ingridient mehl 			= new Ingridient("Mehl", 1, 600, DbNames.QuantityType.gram.toString());
		mehl.save(db);
		Ingridient alcohol			= new Ingridient("Schnaps", 17, 200, DbNames.QuantityType.ml.toString());
		alcohol.save(db);
		Ingridient kaese			= new Ingridient("Kaese", 4, 450, DbNames.QuantityType.gram.toString());
		kaese.save(db);
		Ingridient apfel			= new Ingridient("Aepfel", 3, 700, DbNames.QuantityType.gram.toString());
		apfel.save(db);
		
		Recept ZitronenkuchenRezept = new Recept(20, 200, "Rezept für Zitronenkuchen", asList(zucker, zitronen, mehl));
		ZitronenkuchenRezept.save(db);
		Cake ZitronenKuchen 		= new Cake(ZitronenkuchenRezept, "Zitronenkuchen");
		ZitronenKuchen.save(db);
		
		Recept ApfelkuchenRezept 	= new Recept(20, 200, "Rezept für Apfelkuchen", asList(apfel, zucker, mehl));
		ApfelkuchenRezept.save(db);
		Cake Apfelkuchen 			= new Cake(ApfelkuchenRezept, "Apfelkuchen");
		Apfelkuchen.save(db);
		
		Recept SchwarzwaelderRezept = new Recept(27, 200, "Rezept für Schwarzwaelder Kirschtorte", asList(alcohol, zucker, mehl));
		SchwarzwaelderRezept.save(db);
		Cake Schwarzwaelder 		= new Cake(SchwarzwaelderRezept, "Schwarzwaelder Kirschtorte");
		Schwarzwaelder.save(db);
		
		Recept KaeseKuchenRezept 	= new Recept(15, 120, "Rezept für Kaesekuchen", asList(kaese, zucker, mehl));
		KaeseKuchenRezept.save(db);
		Cake KaeseKuchen			= new Cake(KaeseKuchenRezept, "Kaesekuchen");
		KaeseKuchen.save(db);		
		
//		Ingridient.getAll(db);
//		Recept.getAll(db);
//		ShoppingCart.getAll(db);
//		User.getAll(db);
	}

}
