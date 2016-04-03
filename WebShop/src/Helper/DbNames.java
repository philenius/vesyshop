package Helper;

public class DbNames {
	
	public static enum collection{
		CACKES,
		INGRIDIENTS,
		USERS,
		ORDERS,
		RECEPTS
	}
	
	public static enum field{
		id,
		price,
	}
	
	public static enum fieldOrder{
		id,
		price,
		User,
		Status,
		Cackes
	}
	
	public static enum fieldRecept{
		id,
		costs,
		name,
		timeToComplete,
		Ingridients
	}
	
	public static enum fieldIngridient{
		id,
		name,
		price,
		quantity,
		quantityType
	}
	
	public static enum fieldCacke{
		id, 
		name, 
		Recept,
		price
	}

}
