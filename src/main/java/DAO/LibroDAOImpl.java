package DAO;

import entities.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Util.HibernateUtil;

import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void agregarLibro(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(libro);  // Guardamos el libro en la base de datos
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void modificarLibro(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(libro);  // Actualizamos el libro en la base de datos
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void eliminarLibro(String isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Libro libro = session.get(Libro.class, isbn);  // Buscamos el libro por su ISBN
            if (libro != null) {
                session.delete(libro);  // Eliminar el libro
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Libro obtenerLibroPorIsbn(String isbn) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Libro libro = null;
        try {
            libro = session.get(Libro.class, isbn);  // Obtenemos el libro por su ISBN
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return libro;
    }

    @Override
    public Libro obtenerLibroPorAutor(String autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Libro libro = null;
        try {
            libro = session.get(Libro.class, autor);  // Obtenemos el libro por su ISBN
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return libro;
    }

    @Override
    public Libro obtenerLibroPorTitulo(String titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Libro libro = null;
        try {
            libro = session.get(Libro.class, titulo);  // Obtenemos el libro por su ISBN
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return libro;
    }

    @Override
    public List<Libro> obtenerTodosLosLibros() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Libro> libros = null;
        try {
            libros = session.createQuery("from Libro", Libro.class).list();  // Obtenemos todos los libros
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return libros;
    }
}
