package DAO;

import entities.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import Util.HibernateUtil;

import java.util.List;

public class LibroDAOImpl implements LibroDAO {

    @Override
    public void agregarLibro(Libro libro) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(libro); // Inserta el objeto
            transaction.commit();
            System.out.println("Libro guardado: " + libro.getTitulo());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al guardar libro: " + e.getMessage());
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
            session.update(libro);  // Actualiza el libro en la BD
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
            // Como 'isbn' no es la clave primaria, buscamos el libro mediante una consulta HQL.
            Query<Libro> query = session.createQuery("FROM Libro WHERE isbn = :isbn", Libro.class);
            query.setParameter("isbn", isbn);
            Libro libro = query.uniqueResult();
            if (libro != null) {
                session.delete(libro);  // Elimina el libro encontrado
            }
            transaction.commit();
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
            libro = session.createQuery("FROM Libro WHERE isbn = :isbn", Libro.class)
                    .setParameter("isbn", isbn)
                    .uniqueResult();
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
            libro = session.createQuery("FROM Libro WHERE autor = :autor", Libro.class)
                    .setParameter("autor", autor)
                    .uniqueResult();
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
            libro = session.createQuery("FROM Libro WHERE titulo = :titulo", Libro.class)
                    .setParameter("titulo", titulo)
                    .uniqueResult();
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
            libros = session.createQuery("FROM Libro", Libro.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return libros;
    }
}
