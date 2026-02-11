import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/Employee")
public class EmployeeServlet extends HttpServlet {

    @WebServlet("/Employee")
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String dept = req.getParameter("dept");
        String salary = req.getParameter("salary");

        out.print("<html><body>");

        // Show form first time
        if (id == null) {
            out.print("<h2>Employee Management System</h2>");
            out.print("<form method='post' action='Employee'>");

            out.print("Employee ID: <input type='number' name='id' required><br/><br/>");
            out.print("Name: <input type='text' name='name' required><br/><br/>");
            out.print("Department: <input type='text' name='dept' required><br/><br/>");
            out.print("Salary: <input type='number' name='salary' required><br/><br/>");

            out.print("<input type='submit' value='Add Employee'>");
            out.print("</form>");
        }
        // Insert into database
        else {
            try {
                Connection con = DBconnection.getConnection();

                String sql = "INSERT INTO employeee VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, Integer.parseInt(id));
                ps.setString(2, name);
                ps.setString(3, dept);
                ps.setDouble(4, Double.parseDouble(salary));

                ps.executeUpdate();

                out.print("<h3 style='color:green;'>Employee Added Successfully!</h3>");

                ps.close();
                con.close();

            } catch (Exception e) {
                out.print("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
            }

            out.print("<br/><a href='Employee'>Add Another Employee</a>");
        }

        out.print("</body></html>");
    }
}

