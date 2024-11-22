package utn.dacs.ms.backend.model.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@NoArgsConstructor
public class Usuario {
    @Id
    private UUID id; // ID de Keycloak
    private Boolean disponible; // Puedes dejarlo aquí si es necesario
    private Boolean cuentabloqueada; // Puedes dejarlo aquí si es necesario

    // Constructor que acepta un UUID
    public Usuario(UUID id) {
        this.id = id;
    }
    
 // Relación bidireccional opcional
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Evitar la serialización de la relación
    private List<Libro> libros;
}