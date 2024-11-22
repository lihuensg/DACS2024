package utn.dacs.ms.bff.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.dacs.ms.bff.api.client.MsApiBackendClient;
import utn.dacs.ms.bff.dto.UsuarioDto;


@RestController
@RequestMapping("/backend")
public class UsuarioController {

    private final MsApiBackendClient backendClient;

    public UsuarioController(MsApiBackendClient backendClient) {
        this.backendClient = backendClient;
    }

    @GetMapping("/usuario/guardar")
    public String guardarUsuario(@AuthenticationPrincipal Jwt jwt) {
        // Extrae el ID del usuario desde el token JWT
    	String userId = jwt.getClaimAsString("sub");

        // Envía el ID al backend
        UsuarioDto usuarioDto = new UsuarioDto(userId);
        backendClient.guardarUsuario(usuarioDto);

        return "ID de usuario guardado: " + userId;
    }
}