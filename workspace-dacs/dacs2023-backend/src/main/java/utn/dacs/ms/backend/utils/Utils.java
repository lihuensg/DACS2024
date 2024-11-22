package utn.dacs.ms.backend.utils;

public class Utils {
    
	private Utils() {
		super();
	}

	public static  boolean isNullOrEmptyStr(String str) {
		return (str == null || str.isEmpty() || str.isBlank());
	}
	
}
