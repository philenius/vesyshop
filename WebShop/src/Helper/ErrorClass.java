package Helper;

public class ErrorClass {
	
	public static void checkNull(Object ob, String success, String failure){
		
		if(ob != null){
			System.out.println("SUCCESS: \t" + success);
		} else {
			System.out.println("FAIL:\t" + failure);
		}
	}


}
