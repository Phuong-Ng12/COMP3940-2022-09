import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

import java.sql.*;
import java.util.Date;
import java.util.UUID;
import java.text.*;
import java.nio.*;

public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>\n" + "<head><title>" + "Login" + "</title></head>\n" + "<body>\n"
                + "<h1 align=\"center\">" + "Login" + "</h1>\n" + "<form action=\"login\" method=\"POST\">\n"
                + "Username: <input type=\"text\" name=\"user_id\">\n" + "<br />\n"
                + "Password: <input type=\"password\" name=\"password\" />\n" + "<br />\n"
                + "<input type=\"submit\" value=\"Sign up\" />\n" + "</form>\n"
                + "</form>\n" + "<br />\n" + "<br />\n"
                + "<form action=\"signin\" method=\"POST\">\n"
                + "Username: <input type=\"text\" name=\"user_id\">\n" + "<br />\n"
                + "Password: <input type=\"password\" name=\"password\" />\n" + "<br />\n"
                + "<input type=\"submit\" value=\"logn in\" />\n" + "</form>\n"
                + "</form>\n" + "</body>\n</html\n");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String title = "Logged in as: ";
        String username = request.getParameter("user_id");
        String password = request.getParameter("password");
        HttpSession session = request.getSession(true);
        session.setAttribute("USER_ID", username);

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
                    .prepareStatement("INSERT INTO staff (ID,name, password) VALUES (?,?,?)");
            UUID uuid = UUID.randomUUID();
            preparedStatement.setBytes(1, asBytes(uuid));
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            // File file = new File("C:\\duck.jpg");
            // FileInputStream fin = new FileInputStream(file);
            // preparedStatement.setBinaryStream(4, fin);

            // java.util.Date date = new java.util.Date();
            // long t = date.getTime();
            // java.sql.Date sqlDate = new java.sql.Date(t);
            // preparedStatement.setDate(5, sqlDate);
            int row = preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException ex) {

            while (ex != null) {
                System.out.println("Message: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("ErrorCode: " + ex.getErrorCode());
                ex = ex.getNextException();
                System.out.println("");
            }
        }

        response.setStatus(302);
        response.sendRedirect("main");
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
