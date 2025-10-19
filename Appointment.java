package model;

import java.sql.*;
import java.util.Scanner;

public class Appointment {
    private Connection connection;
    private Scanner scanner;

    public Appointment(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add new appointment
    public void addAppointment() {
        try {
            System.out.print("Enter Patient ID: ");
            int patientId = scanner.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doctorId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            String date = scanner.nextLine();

            String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, patientId);
                ps.setInt(2, doctorId);
                ps.setString(3, date);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Appointment Scheduled Successfully!!" : "Failed to schedule appointment!!");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: Could not schedule appointment.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Input Error: Please enter valid data.");
        }
    }

    // View all appointments
    public void viewAppointments() {
        String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date " +
                       "FROM appointments a " +
                       "JOIN patients p ON a.patient_id = p.id " +
                       "JOIN doctors d ON a.doctor_id = d.id";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Appointments: ");
            System.out.println("+------------+--------------------+--------------------+----------------+");
            System.out.println("| Appointment| Patient Name       | Doctor Name        | Date           |");
            System.out.println("+------------+--------------------+--------------------+----------------+");
            while (rs.next()) {
                int id = rs.getInt("id");
                String patient = rs.getString("patient_name");
                String doctor = rs.getString("doctor_name");
                String date = rs.getString("appointment_date");
                System.out.printf("| %-10s | %-18s | %-18s | %-14s |\n", id, patient, doctor, date);
                System.out.println("+------------+--------------------+--------------------+----------------+");
            }

        } catch (SQLException e) {
            System.out.println("Database Error: Could not retrieve appointments.");
            e.printStackTrace();
        }
    }
}
