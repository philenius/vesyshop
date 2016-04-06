package Helper;

public class DbNames {
	
	public static enum collection{
		CAKES,
		INGRIDIENTS,
		USERS,
		ORDERS,
		RECEPTS,
		CARTS
	}
	
	public static enum field{
		price,
	}
	
	public static enum fieldCart{
		price,
		cakes
	}
	
	public static enum fieldOrder{
		price,
		User,
		Status,
		Cackes
	}
	
	public static enum fieldRecept{
		costs,
		name,
		timeToComplete,
		Ingridients
	}
	
	public static enum fieldIngridient{
		name,
		price,
		quantity,
		quantityType
	}
	
	public static enum fieldCacke{
		name, 
		Recept,
		price
	}

}
