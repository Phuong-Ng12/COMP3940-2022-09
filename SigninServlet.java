import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

import java.sql.*;
import java.util.Date;
import java.util.UUID;
import java.text.*;
import java.nio.*;

public class SigninServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String title = "Logged in as: ";
        String username = request.getParameter("user_id");
        String password = request.getParameter("password");

        Connection con = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception ex) {
            System.out.println("Message: " + ex.getMessage());
            return;
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle1");
            PreparedStatement preparedStatement = con
                    .prepareStatement("SELECT * FROM STAFF WHERE NAME=? AND PASSWORD=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            PrintWriter out = response.getWriter();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                // preparedStatement.close();
                // con.close();
                HttpSession session = request.getSession(true);
                session.setAttribute("USER_ID", username);
                response.setStatus(302);
                response.sendRedirect("main");
            }

            out.println("<html>\n" + "<head><title>" + "Login" + "</title></head>\n" + "<body>\n"
                    + "<h1 align=\"center\">" + "Username/Password is error" + "</h1>\n" + "</body>\n</html\n");

        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }
        }

        // response.setStatus(302);
        // response.sendRedirect("main");
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }
}
