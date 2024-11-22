package utn.dacs.ms.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.dacs.ms.backend.dto.UsuarioDto;
import utn.dacs.ms.backend.model.entity.Usuario;
import utn.dacs.ms.backend.model.repository.UsuarioRepository;

import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void guardarUsuario(UsuarioDto usuarioDto) {
        // Convertir el ID de Keycloak (String) a UUID
        UUID userId = UUID.fromString(usuarioDto.getUserId());

        // Crear la entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        usuario.setDisponible(true); // Valor predeterminado
        usuario.setCuentabloqueada(false); // Valor predeterminado

        // Guardar en la base de datos
        usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario findById(UUID usuarioId) {
        return usuarioRepository.findById(usuarioId).orElse(null);
    }
}