package model;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add new doctor
    public void addDoctor() {
        try {
            scanner.nextLine(); // consume leftover newline
            System.out.print("Enter Doctor Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Doctor Specialty: ");
            String specialty = scanner.nextLine();
            System.out.print("Enter Doctor Experience (years): ");
            int experience = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String query = "INSERT INTO doctors(name, specialty, experience) VALUES(?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setString(2, specialty);
                ps.setInt(3, experience);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Doctor Added Successfully!!" : "Failed to add Doctor!!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: Could not add doctor.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Input Error: Please enter valid data.");
        }
    }

    // View all doctors
    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+--------------------+------------+");
            System.out.println("| Doctor Id  | Name               | Specialty          | Experience |");
            System.out.println("+------------+--------------------+--------------------+------------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialty = rs.getString("specialty");
                int exp = rs.getInt("experience");
                System.out.printf("| %-10s | %-18s | %-18s | %-10s |\n", id, name, specialty, exp);
                System.out.println("+------------+--------------------+--------------------+------------+");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: Could not retrieve doctors.");
            e.printStackTrace();
        }
    }

    // Check if doctor exists by ID
    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Database Error: Could not check doctor.");
            e.printStackTrace();
        }
        return false;
    }
}
