package DAO;

import entities.Libro;

import java.util.List;

public interface LibroDAO {
    // Método para agregar un nuevo libro
    void agregarLibro(Libro libro);

    // Método para modificar los datos de un libro
    void modificarLibro(Libro libro);

    // Método para eliminar un libro dado su ISBN
    void eliminarLibro(String isbn);

    // Método para obtener un libro por su ISBN
    Libro obtenerLibroPorIsbn(String isbn);

    Libro obtenerLibroPorAutor(String autor);

    Libro obtenerLibroPorTitulo(String titulo);

    // Método para obtener todos los libros
    List<Libro> obtenerTodosLosLibros();
}
