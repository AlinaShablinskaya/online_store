package by.it.academy.onlinestore.dao;

import by.it.academy.onlinestore.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

class TestConfig {
    private final static String SCRIPT_SQL = "src/test/resources/schema.sql";

    public void createTables() throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createNativeQuery(retrieveSql());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    private String retrieveSql() throws IOException {
        return new String(Files.readAllBytes(Paths.get(SCRIPT_SQL)), StandardCharsets.UTF_8);
    }
}
