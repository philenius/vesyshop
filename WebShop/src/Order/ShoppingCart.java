package Order;

import java.math.BigDecimal;
import java.util.List;

import Kuchen.Cacke;

public class ShoppingCart {
	protected int id;
	public List<Cacke> cackes;
	BigDecimal price;
	
	public ShoppingCart(List<Cacke> _cackes, int _id){
		this.cackes = _cackes;
		this.id = _id;
		calculatePrice();
	}
	
	private void calculatePrice(){
		for(Cacke c : cackes){
			price = price.add(c.price);
		}
	}
	
	public void addCackes(List<Cacke> cs){
		this.cackes.addAll(cs);
	}
	
	public void addCacke(Cacke c){
		this.cackes.add(c);
	}
	
	public void removeCacke(Cacke c){
		this.cackes.remove(c);
	}
	
	public void clear(){
		this.cackes.clear();
	}

}
