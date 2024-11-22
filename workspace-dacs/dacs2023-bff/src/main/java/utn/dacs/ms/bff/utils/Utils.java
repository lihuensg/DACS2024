package utn.dacs.ms.bff.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class Utils {
    
	private Utils() {
		super();
	}

	public static  boolean isNullOrEmptyStr(String str) {
		return (str == null || str.isEmpty() || str.isBlank());
	}
	
    /**
     * @return cuit de usuario obtenido desde el token de acceso.
     */
    public static String extractCuitToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((Jwt) authentication.getPrincipal()).getClaimAsString("CUIT");
    }
}
