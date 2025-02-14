package DAO;

import Util.HibernateUtil;
import entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SocioDAOImpl {

    public void agregarSocio(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            System.out.println("Guardando socio: " + socio.toString());

            session.save(socio);

            transaction.commit();
            System.out.println("Socio guardado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error durante la transacción:");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public void modificarSocio(Socio socio) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.update(socio);

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

    public void eliminarSocio(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                session.delete(socio);
            }

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

    public List<Socio> listarSocios() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            // Consultar todos los socios
            List<Socio> socios = session.createQuery("FROM Socio", Socio.class).getResultList();

            // Confirmar la transacción (si solo se consulta, esto puede no ser necesario)
            session.getTransaction().commit();

            return socios;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Cerramos la sesión al final
            session.close();
        }
    }


    // Método para buscar un socio por nombre
    public Socio buscarPorNombre(String nombre) {
        // Obtener la sesión de Hibernate desde HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Iniciar la transacción
            session.beginTransaction();

            // Buscar el socio por nombre
            Socio socio = session.createQuery("FROM Socio s WHERE s.nombre = :nombre", Socio.class)
                    .setParameter("nombre", nombre)
                    .uniqueResult();

            // Confirmar la transacción
            session.getTransaction().commit();

            return socio;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Cerramos la sesión al final
            session.close();
        }
    }
}
