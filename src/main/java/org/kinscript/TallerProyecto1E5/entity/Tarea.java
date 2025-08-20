package org.kinscript.TallerProyecto1E5.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;



@Entity(name = "Tareas")
//lombook
@Data//generar los setters y getters
@NoArgsConstructor//el constructor vacio
@AllArgsConstructor//el constructor lleno
@ToString// el metodo sobrecargado toString
@EqualsAndHashCode//el metodo para trabajar con HashCode | id interno para la clase
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer codigoTarea;
    private String nombre;
    private String descripcion;
    private String fechaLimite;
    private String estado;

}
