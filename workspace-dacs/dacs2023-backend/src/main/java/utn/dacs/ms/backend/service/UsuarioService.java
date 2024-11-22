package utn.dacs.ms.backend.service;

import java.util.UUID;

import utn.dacs.ms.backend.dto.UsuarioDto;
import utn.dacs.ms.backend.model.entity.Usuario;

public interface UsuarioService {
    void guardarUsuario(UsuarioDto usuarioDto);
    Usuario findById(UUID usuarioId);
}
