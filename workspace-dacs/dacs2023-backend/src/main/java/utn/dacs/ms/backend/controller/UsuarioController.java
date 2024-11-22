package utn.dacs.ms.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.dacs.ms.backend.dto.UsuarioDto;
import utn.dacs.ms.backend.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/guardar")
    public String guardarUsuario(@RequestBody UsuarioDto usuarioDto) {
        usuarioService.guardarUsuario(usuarioDto);
        return "Usuario guardado correctamente!";
    }
}