package Util;

import entities.Autor;
import entities.Prestamo;
import entities.Socio;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import entities.Libro;  // Importa las entidades

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            System.out.println("Cargando configuración de Hibernate...");

            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")  // Carga el archivo de configuración
                    .addAnnotatedClass(Libro.class)  // Asegúrate de añadir la clase de entidad
                    .addAnnotatedClass(Autor.class)  // Si tienes más clases, añádelas aquí
                    .addAnnotatedClass(Prestamo.class)
                    .addAnnotatedClass(Socio.class)
                    .buildSessionFactory();

            sessionFactory.getMetamodel().getEntities().forEach(e -> System.out.println("Entidad registrada: " + e.getName()));

            System.out.println("Configuración cargada exitosamente.");
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
