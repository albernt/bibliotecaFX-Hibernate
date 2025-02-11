package entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "socio")  // El nombre de la tabla en la base de datos
public class Socio {

    @Id
    @Column(name = "id")  // Especificamos la columna para la clave primaria
    private Long id;  // Usamos Long para el ID (puede ser otro tipo, dependiendo de tus necesidades)

    @Column(name = "nombre", nullable = false, length = 100)  // Nombre del socio
    private String nombre;

    @Column(name = "direccion", nullable = false, length = 255)  // Dirección del socio
    private String direccion;

    @Column(name = "telefono", nullable = true, length = 15)  // Número de teléfono del socio (opcional)
    private String telefono;

    // Constructor sin parámetros
    public Socio() {}

    // Constructor con parámetros
    public Socio(Long id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
