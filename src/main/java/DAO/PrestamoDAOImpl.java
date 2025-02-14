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

    public void registrarPrestamo(Long libroId, Long socioId, Date fechaPrestamo, Date fechaDevolucion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Libro libro = obtenerLibroPorId(libroId, session);
            Socio socio = obtenerSocioPorId(socioId, session);

            if (libro == null || socio == null) {
                System.out.println("El libro o el socio no existen.");
                return;
            }

            Prestamo prestamo = new Prestamo();
            prestamo.setLibro(libro);
            prestamo.setSocio(socio);
            prestamo.setFechaPrestamo(fechaPrestamo);
            prestamo.setFechaDevolucion(fechaDevolucion);

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

    private Libro obtenerLibroPorId(Long libroId, Session session) {
        return session.get(Libro.class, libroId);
    }

    private Socio obtenerSocioPorId(Long socioId, Session session) {
        return session.get(Socio.class, socioId);
    }


    public List<Prestamo> listarPrestamosActivos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT p FROM Prestamo p "
                    + "LEFT JOIN FETCH p.socio "
                    + "LEFT JOIN FETCH p.libro "
                    + "WHERE p.fechaDevolucion IS NULL";
            return session.createQuery(hql, Prestamo.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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
