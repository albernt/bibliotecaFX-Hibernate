package DAO;

import entities.Prestamo;
import entities.Libro;
import entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import Util.HibernateUtil;

import java.util.List;
import java.util.Date;

public class PrestamoDAOImpl {

    // Registrar un préstamo
    public void registrarPrestamo(Long libroId, Long socioId, Date fechaPrestamo, Date fechaDevolucion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Obtener los objetos Libro y Socio a partir de sus IDs
            Libro libro = obtenerLibroPorId(libroId, session);
            Socio socio = obtenerSocioPorId(socioId, session);

            // Verificar que los objetos existen
            if (libro == null || socio == null) {
                System.out.println("El libro o el socio no existen.");
                return;
            }

            // Crear un nuevo objeto Prestamo y asignar los valores
            Prestamo prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setSocio(socio);
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);

            // Guardar el préstamo en la base de datos
            session.save(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Método para obtener un libro por su ID
    private Libro obtenerLibroPorId(Long libroId, Session session) {
        return session.get(Libro.class, libroId);
    }

    // Método para obtener un socio por su ID
    private Socio obtenerSocioPorId(Long socioId, Session session) {
        return session.get(Socio.class, socioId);
    }

    // Listar todos los préstamos activos (no devueltos)
    public List<Prestamo> listarPrestamosActivos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = null;
        try {
            String hql = "FROM Prestamo WHERE fechaDevolucion IS NULL";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            prestamos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prestamos;
    }

    // Listar historial de préstamos de un socio
    public List<Prestamo> listarHistorialPrestamos(Long idSocio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Prestamo> prestamos = null;
        try {
            String hql = "FROM Prestamo WHERE socio.id = :idSocio";
            Query<Prestamo> query = session.createQuery(hql, Prestamo.class);
            query.setParameter("idSocio", idSocio);
            prestamos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return prestamos;
    }
}
