package ru.netology.service.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class DBUtils {
    private static final String URL = System.getProperty("db.url");
    private static final String USER = "app";
    private static final String PASSWORD = "pass";

    public static String getValidVerificationStatusApproved() {
        String query = "SELECT status FROM payment_entity WHERE status = 'APPROVED' ORDER BY created DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статуса верификации", e);
        }
    }

    public static String getValidVerificationStatusDeclined() {
        String query = "SELECT status FROM payment_entity WHERE status = 'DECLINED' ORDER BY created DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            QueryRunner runner = new QueryRunner();
            return runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении статуса верификации", e);
        }
    }
}

