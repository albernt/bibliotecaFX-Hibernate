package DAO;

import Util.HibernateUtil;
import entities.Socio;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class SocioDAOImpl {

    // Método para agregar un socio
    public void agregarSocio(Socio socio) {
        // Obtener la sesión de Hibernate desde HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();
            System.out.println("Guardando socio: " + socio.toString());

            // Guardar el objeto en la base de datos
            session.save(socio);

            // Confirmar la transacción
            transaction.commit();
            System.out.println("Socio guardado correctamente.");
        } catch (Exception e) {
            // En caso de error, hacer rollback
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error durante la transacción:");
            e.printStackTrace();
        } finally {
            // Cerramos la sesión al final
            session.close();
        }
    }


    // Método para modificar un socio
    public void modificarSocio(Socio socio) {
        // Obtener la sesión de Hibernate desde HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Actualizar el socio
            session.update(socio);

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // En caso de error, hacer rollback
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerramos la sesión al final
            session.close();
        }
    }

    // Método para eliminar un socio
    public void eliminarSocio(Long id) {
        // Obtener la sesión de Hibernate desde HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Iniciar la transacción
            transaction = session.beginTransaction();

            // Buscar el socio por ID
            Socio socio = session.get(Socio.class, id);
            if (socio != null) {
                // Eliminar el socio
                session.delete(socio);
            }

            // Confirmar la transacción
            transaction.commit();
        } catch (Exception e) {
            // En caso de error, hacer rollback
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Cerramos la sesión al final
            session.close();
        }
    }

    // Método para listar todos los socios
    public List<Socio> listarSocios() {
        // Obtener la sesión de Hibernate desde HibernateUtil
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Iniciar la transacción
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
