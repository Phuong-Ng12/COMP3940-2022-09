import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

import java.sql.*;
import java.util.UUID;
import java.nio.*;

public class SigninServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String title = "Logged in as: ";
        String username = request.getParameter("user_id");
        String password = request.getParameter("password");
        String userUUID;
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PrintWriter out = response.getWriter();
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (Exception ex) {
            System.out.println("Message: " + ex.getMessage());
            return;
        }
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "1206");
            preparedStatement = con.prepareStatement("SELECT * FROM USERS WHERE NAME=? AND PASSWORD=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            
            statement = con.createStatement();
            String SQLstatement = "SELECT ID FROM USERS WHERE name='" + username +"' AND password='" + password + "'";
            resultSet = statement.executeQuery(SQLstatement);
     
            
            while (rs.next()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("USER_ID", username);
                int num = 0;
                String key="times";
                session.setAttribute(key, 0);
                response.setStatus(302);
            }

            while (resultSet.next()) {
                userUUID = resultSet.getString("ID");
                System.out.println("userUUID here:::: " + userUUID);
                HttpSession session = request.getSession(true);
                session.setAttribute("userUUID", userUUID);
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
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {}
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {}
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {}
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {}
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (Exception e) {}
        }

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
