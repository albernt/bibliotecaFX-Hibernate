package entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "prestamo")  // El nombre de la tabla en la base de datos (opcional)
public class Prestamo {

    @Id
    @Column(name = "id")  // Especificamos la columna para la clave primaria
    private Long id;  // Usamos Long para el ID (puede ser otro tipo, dependiendo de tus necesidades)

    @ManyToOne  // Relación muchos a uno con la clase Socio
    @JoinColumn(name = "socio_id", referencedColumnName = "id", nullable = false)  // Columna que referencia la tabla Socio
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "libro_id", referencedColumnName = "id", nullable = false)
    private Libro libro;


    @Column(name = "fecha_prestamo", nullable = false)  // Fecha del préstamo
    private Date fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = true)  // Fecha de devolución
    private Date fechaDevolucion;

    // Constructor sin parámetros
    public Prestamo() {}

    // Constructor con parámetros
    public Prestamo(Long id, Socio socio, Libro libro, Date fechaPrestamo, Date fechaDevolucion) {
        this.id = id;
        this.socio = socio;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", socio=" + socio +
                ", libro=" + libro +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}
