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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.nio.*;


@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        boolean isLoggedIn = isLoggedIn(request);
        if (!isLoggedIn) {
            response.setStatus(302);
            response.sendRedirect("login");

        } else {
            RequestDispatcher view = request.getRequestDispatcher("html/fileUpload.html");
            view.forward(request, response);
            
        }
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("fileName");
        String captionName = request.getParameter("caption");
        String formDate = request.getParameter("date");
        String fileName = filePart.getSubmittedFileName();
        
        HttpSession session = request.getSession(true);
        
        String userUUID = (String) session.getAttribute("userUUID");
        
        DateTimeFormatter dateTimeft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(formDate, dateTimeft);

        Connection con = null;
        PreparedStatement preparedStatement = null;
        
        Statement statement = null;
        ResultSet resultSet = null;
        String photoFile = "";
        ArrayList<String> listOfPhotos = new ArrayList<String>();

        if (fileName.equals("")) {
            response.setStatus(302);
            response.sendRedirect("upload");
            return;
        }

        if (formDate.equals(""))
            formDate = "2020-10-10";
        if (captionName.equals(""))
            captionName = "No caption";
        filePart.write(System.getProperty("catalina.base") + "/webapps/photogallery/images/" + fileName);
        response.setContentType("text/html");

        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle1");
            preparedStatement = con.prepareStatement("INSERT INTO photos (PICID, PICTURE, STARTDATE, fileN, CAPTION, USERID) VALUES (?,?,?,?,?,?)");
            UUID uuid = UUID.randomUUID();
            preparedStatement.setBytes(1, asBytes(uuid));
            String file = "C:\\tomcat\\webapps\\photogallery\\images\\" + fileName;
            File dir = new File(file);
            FileInputStream fin = new FileInputStream(file);
            preparedStatement.setBinaryStream(2, fin);
            preparedStatement.setObject(3, localDate);
            preparedStatement.setString(4, fileName);
            preparedStatement.setString(5, captionName);
            preparedStatement.setString(6, userUUID);
            int row = preparedStatement.executeUpdate();
            
            statement = con.createStatement();
            
            String SQLstatement = "SELECT FILEN FROM PHOTOS WHERE USERID='" + userUUID +"'";
            resultSet = statement.executeQuery(SQLstatement);
            
            while (resultSet.next()) {
                photoFile = resultSet.getString("FILEN");
                listOfPhotos.add(photoFile);
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
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {}
        }
        
        response.setStatus(302);
        PrintWriter out2 = response.getWriter();
        String topPart = "<!DOCTYPE html><html><body><ul>";
        String bottomPart = "</ul></body></html>";
        out.println(topPart + getListing("c:\\tomcat\\webapps\\photogallery\\images", listOfPhotos) + bottomPart);
        out.println("<form action='main' method='GET'>");
        out.println("<button class='button' id='main'>Main</button>");
        out.println("<br>");
        out.println("</form>");
        out.println("<form method=\"POST\" action=\"list\" " + "enctype=\"multipart/form-data\">\r\n");
        out.println("Filename of photo you want to delete: <input type=\"text\" name=\"fileName\"<br/><br/>\r\n");
        out.println("<br />\n");
        out.println("<input type=\"submit\" value=\"Delete\"/>\r\n");
        out.println("</form>\r\n");
        
        
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