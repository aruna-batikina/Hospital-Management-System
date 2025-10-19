package servlet;

import dao.DBconnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addAppointment")
public class AddAppointmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int patientId = Integer.parseInt(request.getParameter("patientId"));
        int doctorId = Integer.parseInt(request.getParameter("doctorId"));
        String appointmentDate = request.getParameter("appointmentDate"); // format: yyyy-mm-dd

        String sql = "INSERT INTO APPOINTMENTS(PATIENT_ID, DOCTOR_ID, APPOINTMENT_DATE) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, appointmentDate);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.getWriter().println("Appointment scheduled successfully.");
            } else {
                response.getWriter().println("Failed to schedule appointment.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
