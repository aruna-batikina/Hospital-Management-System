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

@WebServlet("/addDoctor")
public class AddDoctorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String specialization = request.getParameter("specialization");
        String contact = request.getParameter("contact");

        String sql = "INSERT INTO DOCTORS(NAME, SPECIALIZATION, CONTACT) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, specialization);
            ps.setString(3, contact);

            int result = ps.executeUpdate();
            if (result > 0) {
                response.getWriter().println("Doctor added successfully.");
            } else {
                response.getWriter().println("Failed to add doctor.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        }
    }
}
