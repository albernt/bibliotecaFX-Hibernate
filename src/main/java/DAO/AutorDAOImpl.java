package DAO;

import entities.Autor;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class AutorDAOImpl {

    // Método para agregar un nuevo autor
    public boolean agregarAutor(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(autor); // Guarda el autor en la base de datos
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback(); // Si hay un error, deshace la transacción
            e.printStackTrace();
            return false;
        } finally {
            session.close(); // Cierra la sesión
        }
    }

    // Método para modificar un autor existente
    public boolean modificarAutor(Autor autor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(autor); // Actualiza el autor en la base de datos
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    // Método para eliminar un autor por su ID
    public boolean eliminarAutor(Long idAutor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Autor autor = session.get(Autor.class, idAutor); // Busca el autor por su ID
            if (autor != null) {
                session.remove(autor); // Elimina el autor
                tx.commit();
                return true;
            }
            return false; // Si no se encuentra el autor, retorna false
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    // Método para buscar autores por nombre (búsqueda parcial)
    public List<Autor> buscarAutoresPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Autor> query = session.createQuery(
                    "FROM Autor WHERE nombre LIKE :nombre", Autor.class); // Consulta HQL
            query.setParameter("nombre", "%" + nombre + "%"); // Búsqueda parcial
            return query.getResultList(); // Retorna la lista de autores encontrados
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para listar todos los autores
    public List<Autor> listarTodosLosAutores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Autor", Autor.class).getResultList(); // Consulta HQL
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}