import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import java.nio.*;


@MultipartConfig
public class ListOfPhotosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        boolean isLoggedIn = isLoggedIn(request);
        if (!isLoggedIn) {
            response.setStatus(302);
            response.sendRedirect("login");

        } else {
            
            HttpSession session = request.getSession(true);  
            String userUUID = (String) session.getAttribute("userUUID");
            String userName = (String) session.getAttribute("USER_ID");
            
            response.setContentType("text/html");
            

            
        }
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement prepareStatement = null;
        String photoFile = "";
        ArrayList<String> listOfPhotos = new ArrayList<String>();
        HttpSession session = request.getSession(true);  
        String userUUID = (String) session.getAttribute("userUUID");
        

        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle1");
            statement = con.createStatement();
            
            prepareStatement = con.prepareStatement("DELETE FROM PHOTOS WHERE USERID=? AND FILEN=?");
            prepareStatement.setString(1, userUUID);
            prepareStatement.setString(2, fileName);
            resultSet = prepareStatement.executeQuery();
  
            
            String SQLstatement = "SELECT FILEN FROM PHOTOS WHERE USERID='" + userUUID +"'";
            resultSet = statement.executeQuery(SQLstatement);
            
            while (resultSet.next()) {
                photoFile = resultSet.getString("FILEN");
                listOfPhotos.add(photoFile);
            }
            for (int i = 0; i < listOfPhotos.size(); i++) {
                System.out.println(listOfPhotos.get(i));
              }
            session.setAttribute("listOfPhotos", listOfPhotos);

           
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
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {}
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {}
            try {
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
            } catch (Exception e) {}
        }
        
        response.setStatus(302);
        
        PrintWriter out = response.getWriter();
        String topPart = "<!DOCTYPE html><html><body><ul>";
        String bottomPart = "</ul></body></html>";
        out.println(topPart + getListing("c:\\tomcat\\webapps\\photogallery\\images", listOfPhotos) + bottomPart);
        out.println("<form action='main' method='GET'>");
        out.println("<button class='button' id='main'>Main</button>");
        out.println("<br>");
        out.println("</form>");

    }
    
    private String getListing(String path, ArrayList<String> listOfPhotos) {
        String dirList = "";
        File dir = new File(path);
        
        for (int i = 0; i < listOfPhotos.size(); i++) {
            if ((new File(path + listOfPhotos.get(i))).isDirectory())
                dirList += "<li><button type=\"button\">" + listOfPhotos.get(i) + "</button></li>";
            else
                dirList += "<li>" + listOfPhotos.get(i) + "</li>";
        }
        return dirList;
    }

    private boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null || !req.isRequestedSessionIdValid()) {
            return false;
        } else {
            return true;
        }

    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }


}