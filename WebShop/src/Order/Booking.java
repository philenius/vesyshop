package Order;

import java.math.BigDecimal;
import java.util.List;

import Kuchen.Cacke;
import Kunde.User;

public class Booking {
	
	final int id;
	BigDecimal price;
	User user;
	Status status;
	List<Cacke> cackes;
	
	public Booking(int _id){
		this.id = _id;
	}

}
