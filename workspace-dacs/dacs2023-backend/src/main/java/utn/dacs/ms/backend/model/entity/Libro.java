package utn.dacs.ms.backend.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Libro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;          
    private String isbn;
    private String portada;          
    private String resumen;          
    private Integer idapi;           
    private String nombreautor;      
    private Boolean archivado;
    private Boolean sepuedecompartir;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // Evitar la serialización de la relación
    private Usuario usuario;
    
 // Relación con HistorialTransacciones
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<HistorialTransacciones> historialTransacciones = new ArrayList<>();

}