package Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.omg.PortableServer._ServantActivatorStub;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Connection.Manager;
import Helper.DbNames;
import Kuchen.Cake;
import Kunde.User;

public class Booking {
	
	public String id;
	BigDecimal price;
	User user;
	Status status;
	List<Cake> cakes;
	
	public Booking(String _id, String _price, User _user, Status _status, List<Cake> _cakes){
		this.id = _id;
		this.price = new BigDecimal(_price);
		this.user = _user;
		this.status = _status;
		this.cakes = _cakes;
	}
	
	public Booking(int _price, User _user, Status _status, List<Cake> _cakes){
		this.price = new BigDecimal(_price);
		this.user = _user;
		this.status = _status;
		this.cakes = _cakes;
	}
	
	public static List<Booking> getAll(MongoDatabase db){
		FindIterable<Document> orderIter;
		orderIter = Manager.getAllDocuments(DbNames.collection.ORDERS.toString(), db);
		
		List<Booking> orders = new ArrayList<Booking>();
		
		orderIter.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
	    	System.out.println(document.toString());
	    	Booking gefunden = docToBooking(document, db);
	    	orders.add(gefunden);
			}
		});
		
		return orders;
		
	}
	
	public Document getDocument(){
		Document doc;
		
		List<Document> cakeDocs = new ArrayList<Document>();
		for(Cake c : cakes){
			cakeDocs.add(c.getDocument());
		}
		
		doc = new Document(
						DbNames.fieldOrder.price.toString(), 	price.toString())
				.append(DbNames.fieldOrder.Status.toString(), 	status.now)
				.append(DbNames.fieldOrder.User.toString(), 	user.getDocument())
				.append(DbNames.fieldOrder.Cackes.toString(), 	cakeDocs);
		
		return doc;
	}
	
	public static Booking docToBooking(Document doc, MongoDatabase db){
		
		User u = User.DocToUser(doc.get(DbNames.fieldOrder.User.toString()));
		Status s = new Status(doc.getString(DbNames.fieldOrder.Status.toString()));
		List<Cake> _cakes = new ArrayList<Cake>();
		
		@SuppressWarnings("unchecked")
		List<Document> cakeDocs = (List<Document>) doc.get(DbNames.fieldOrder.Cackes.toString());
		for(Document d : cakeDocs){
			_cakes.add(Cake.DocToCake(d, db));
		}
		
		return new Booking(
				doc.getString("id"),
				doc.getString("price"), 
				u, 
				s, 
				_cakes);
	}
	
	public static Booking getById(String _id, MongoDatabase db){
		FindIterable<Document> docIter;
		docIter = Manager.getDocuments(DbNames.collection.ORDERS.toString(), "id", _id, db);
		
		return docToBooking(docIter.first(), db);
	}
	
	public void save(MongoDatabase db){
		Document doc = this.getDocument();
		Manager.insertDocument(doc, DbNames.collection.ORDERS.toString(), db);
	}

}
