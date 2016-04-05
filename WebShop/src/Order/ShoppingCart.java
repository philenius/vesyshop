package Order;

import java.math.BigDecimal;
import java.util.List;

import Kuchen.Cake;

public class ShoppingCart {
	protected int id;
	public List<Cake> cackes;
	BigDecimal price;
	
	public ShoppingCart(List<Cake> _cackes, int _id){
		this.cackes = _cackes;
		this.id = _id;
		calculatePrice();
	}
	
	private void calculatePrice(){
		for(Cake c : cackes){
			price = price.add(c.price);
		}
	}
	
	public void addCackes(List<Cake> cs){
		this.cackes.addAll(cs);
	}
	
	public void addCacke(Cake c){
		this.cackes.add(c);
	}
	
	public void removeCacke(Cake c){
		this.cackes.remove(c);
	}
	
	public void clear(){
		this.cackes.clear();
	}

}
