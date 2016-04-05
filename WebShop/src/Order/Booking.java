package Order;

import java.math.BigDecimal;
import java.util.List;

import Kuchen.Cake;
import Kunde.User;

public class Booking {
	
	final int id;
	BigDecimal price;
	User user;
	Status status;
	List<Cake> cackes;
	
	public Booking(int _id){
		this.id = _id;
	}

}
