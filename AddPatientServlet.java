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

@WebServlet("/addPatient")
public class AddPatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String contact = request.getParameter("contact");
        String address = request.getParameter("address");

        String sql = "INSERT INTO PATIENTS(NAME, GENDER, CONTACT, ADDRESS) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, gender);
            ps.setString(3, contact);
            ps.setString(4, address);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.getWriter().println("Patient added successfully.");
            } else {
                response.getWriter().println("Failed to add patient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
