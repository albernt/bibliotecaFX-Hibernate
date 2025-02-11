package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "autor")  // El nombre de la tabla en la base de datos (opcional)
public class Autor {

    @Id
    @Column(name = "id")  // Especifica la columna para la clave primaria
    private Long id;  // Usamos Long para el ID (puede ser otro tipo, dependiendo de tus necesidades)

    @Column(name = "nombre", nullable = false)  // Especificamos el nombre de la columna y que no puede ser nulo
    private String nombre;

    @Column(name = "nacionalidad", nullable = true)  // Especificamos la columna para la nacionalidad
    private String nacionalidad;

    // Constructor sin parámetros
    public Autor() {}

    // Constructor con parámetros
    public Autor(Long id, String nombre, String nacionalidad) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}
