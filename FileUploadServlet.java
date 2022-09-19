import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.StringBuilder;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.Map.Entry;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.text.*;
import java.nio.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = isLoggedIn(request);
        if (!isLoggedIn) {
            response.setStatus(302);
            response.sendRedirect("login");

        } else {
            PrintWriter writer = response.getWriter();
            writer.append("<!DOCTYPE html>\r\n")
                    .append("<html>\r\n")
                    .append("    <head>\r\n")
                    .append("        <title>File Upload Form</title>\r\n")
                    .append("    </head>\r\n")
                    .append("    <body>\r\n");
            writer.append("<h1>Upload file</h1>\r\n");
            writer.append("<form method=\"POST\" action=\"upload\" ")
                    .append("enctype=\"multipart/form-data\">\r\n");
            writer.append("<input type=\"file\" name=\"fileName\"/><br/><br/>\r\n");
            writer.append("Caption: <input type=\"text\" name=\"caption\"<br/><br/>\r\n");
            writer.append("<br />\n");
            writer.append("Date: <input type=\"date\" name=\"date\"<br/><br/>\r\n");
            writer.append("<br />\n");
            writer.append("<input type=\"submit\" value=\"Submit\"/>\r\n");
            writer.append("</form>\r\n");
            writer.append("</body>\r\n").append("</html>\r\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Part filePart = request.getPart("fileName");
        String captionName = request.getParameter("caption");
        String formDate = request.getParameter("date");
        String fileName = filePart.getSubmittedFileName();

        Connection con = null;

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
            PreparedStatement preparedStatement = con
                    .prepareStatement("INSERT INTO IMAGE1 (PICID,PICTURE,STARTDATE,fileN,CAPTION) VALUES (?,?,?,?,?)");
            UUID uuid = UUID.randomUUID();
            preparedStatement.setBytes(1, asBytes(uuid));
            String file = "C:\\tomcat\\webapps\\photogallery\\images\\" + fileName;
            File dir = new File(file);
            FileInputStream fin = new FileInputStream(file);
            preparedStatement.setBinaryStream(2, fin);
            // formDate=formDate+",00:00:00";
            // DateTimeFormatter formatter =
            // DateTimeFormatter.ofPattern("yyyy-mm-dd,HH:mm:ss");
            // LocalDateTime date = LocalDateTime.parse(formDate, formatter);

            // // java.util.Date date = new java.util.Date();
            // ZoneId zoneId = ZoneId.systemDefault();
            // long t = date.now().atZone(zoneId).toEpochSecond();
            // ;

            java.util.Date date = new java.util.Date();
            long t = date.getTime();
            java.sql.Date sqlDate = new java.sql.Date(t);
            // try {
            // SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD",Locale.ENGLISH);
            // Date parsed = format.parse("2020-10-25");
            // java.sql.Date sql = new java.sql.Date(parsed.getTime());
            // preparedStatement.setDate(3, sql);
            // } catch (ParseException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // ;

            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, YYYY MM DD",
            // Locale.ENGLISH);
            // LocalDate dateTime = LocalDate.parse(formDate, formatter);
            // long t = dateTime.getTime();
            // java.sql.Date sqlDate = new java.sql.Date(t);

            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, fileName);
            preparedStatement.setString(5, captionName);
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

        PrintWriter out = response.getWriter();
        String topPart = "<!DOCTYPE html><html><body><ul>";
        String bottomPart = "</ul></body></html>";
        out.println(topPart + getListing("c:\\tomcat\\webapps\\photogallery\\images") + bottomPart);
    }

    private String getListing(String path) {
        String dirList = null;
        File dir = new File(path);
        String[] chld = dir.list();
        for (int i = 0; i < chld.length; i++) {
            if ((new File(path + chld[i])).isDirectory())
                dirList += "<li><button type=\"button\">" + chld[i] + "</button></li>";
            else
                dirList += "<li>" + chld[i] + "</li>";
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

    public long createTimestamp() {
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }

}
