package org.example.db;
import org.example.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class DbRepositoty {
    private static Connection conn = null;
    private static final String dbPath = "jdbc:sqlite:/home/kirill/IdeaProjects/ulernhelp/ulearnDd.db";


    public static void connect() {
        try {
            conn = DriverManager.getConnection(dbPath);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void createTableStudents() {

        String sql = "CREATE TABLE IF NOT EXISTS students (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	distance_hometown real,\n"
                + " avg_mark real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("Table 'students' created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students) {
        String sql = "INSERT INTO students(name,distance_hometown, avg_mark) VALUES(?,?,?)";

        for (var student : students) {
            try (Connection conn = DriverManager.getConnection(dbPath);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, student.getName());
                    pstmt.setDouble(2, student.getDistance());
                    pstmt.setDouble(3, student.getAvgMark());
                    pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static HashMap<String, Double[]> getStudents() {
        String sql = "SELECT name, distance_hometown, avg_mark FROM students";

        var res = new HashMap<String, Double[]>();

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                double distanceHometown = rs.getDouble("distance_hometown");
                double avgMark = rs.getDouble("avg_mark");
                res.put(name, new Double[]{distanceHometown, avgMark});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }

    public static HashMap<String, Double[]> getStudentsOrderBy() {
        String sql = "SELECT name, distance_hometown, avg_mark FROM students WHERE " +
                "distance_hometown > 0 ORDER BY avg_mark";

        var res = new HashMap<String, Double[]>();

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                double distanceHometown = rs.getDouble("distance_hometown");
                double avgMark = rs.getDouble("avg_mark");
                res.put(name, new Double[]{distanceHometown, avgMark});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }






}
