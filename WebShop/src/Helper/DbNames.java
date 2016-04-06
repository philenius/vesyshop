package Helper;

public class DbNames {
	
	public static enum collection{
		CAKES,
		INGRIDIENTS,
		USERS,
		ORDERS,
		RECEPTS
	}
	
	public static enum field{
		price,
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
