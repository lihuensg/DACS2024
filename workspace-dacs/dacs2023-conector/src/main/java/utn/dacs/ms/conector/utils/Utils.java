package utn.dacs.ms.conector.utils;

public class Utils {
    
	private Utils() {
		super();
	}

	public static  boolean isNullOrEmptyStr(String str) {
		return (str == null || str.isEmpty() || str.isBlank());
	}
	
}
