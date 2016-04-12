package Kuchen;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;
import java.io.IOException;

public class Cake {

	public String id;
	public Recept recept;
	public BigDecimal price;
	public String name;
	private String image;

	int Aufschlag = 10;

	protected String collection;
	protected Document doc;

	public Cake(Recept _recept, String _name) {
		this.recept = _recept;
		this.name = _name;
		this.image = this.getRandomImage();
		this.collection = DbNames.collection.CAKES.toString();

		calculatePrice();
	}

	public Cake(String _id, Recept _recept, String _name) {
		this.id = _id;
		this.recept = _recept;
		this.name = _name;
		this.image = this.getRandomImage();

		this.collection = DbNames.collection.CAKES.toString();

		calculatePrice();
	}

	private void calculatePrice() {

		price = new BigDecimal(recept.costs);
		for (Ingridient ing : recept.ingridients) {
			price = price.add(ing.price);
		}
		price.add(new BigDecimal(Aufschlag));
	}

	public void save(MongoDatabase db) {

		Document doc = getDocument();

		Manager.insertDocument(doc, this.collection, db);
	}

	public Document getDocument() {
		Document doc;

		doc = new Document(DbNames.fieldCacke.name.toString(), this.name).append(DbNames.fieldCacke.Recept.toString(),
				this.recept.name);

		return doc;
	}

	public static Cake DocToCake(Document doc, MongoDatabase db) {
		Cake cake;
		Recept rez = Recept.getByName(doc.getString(DbNames.fieldCacke.Recept.toString()), db);

		cake = new Cake(doc.getString("id"), rez, doc.getString(DbNames.fieldCacke.name.toString()));

		return cake;
	}

	public String toString() {
		return "name:" + this.name + ", Recept:" + this.recept.toString();
	}

	public static String getAll(MongoDatabase db) throws JsonGenerationException, JsonMappingException, IOException {

		List<Cake> cakes = new ArrayList<Cake>();

		FindIterable<Document> cakeDocs = Manager.getAllDocuments(DbNames.collection.CAKES.toString(), db);

		cakeDocs.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document.toString());
				Cake gefunden = DocToCake(document, db);
				System.out.println(gefunden.toString());
				cakes.add(gefunden);
			}
		});

		String json = listToJSON(cakes);
		return json;
	}

	/**
	 * Transforms a given list of cake objects into a valid JSON array of cake
	 * objects.
	 * 
	 * @param cakes
	 *            The list of cakes.
	 * @return The JSON array of cakes.
	 */
	private static String listToJSON(List<Cake> cakes) {
		String json = "[";
		for (int i = 0; i < cakes.size(); i++) {
			json += (cakes.get(i).toJSON());

			if (i == (cakes.size() - 1)) {
				continue;
			}
			json += ",";
		}
		json += "]";
		return json;
	}

	/**
	 * Transforms a cake object into JSON.
	 * 
	 * @return Returns this as a JSON string.
	 */
	private String toJSON() {
		String json = "{";
		json += "\"name\":\"" + this.name + "\",";
		json += "\"price\":" + this.price + ",";
		json += "\"image\":\"" + this.image + "\"";
		json += "}";
		return json;
	}

	/**
	 * Creates a random image name consisting of the word "cake" + random number
	 * between 0 and 9 + ".png".
	 * 
	 * @return Returns the name of a random image.
	 */
	private String getRandomImage() {
		int number = (int) Math.floor((Math.random() * 10));
		String fileName = "cake" + String.valueOf(number) + ".png";
		return fileName;
	}

	/*
	 * public void update(){ //TODO }
	 */

}
