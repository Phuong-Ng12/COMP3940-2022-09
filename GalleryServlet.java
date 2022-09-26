import java.io.*;
import java.sql.PreparedStatement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.text.AbstractDocument.LeafElement;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.sql.*;

public class GalleryServlet extends HttpServlet {
      private int mCount;
      String key = "times";

      public void doGet(HttpServletRequest request,
                  HttpServletResponse response)
                  throws ServletException, IOException {

            if (!isLoggedIn(request)) {
                  response.setStatus(302);
                  response.sendRedirect("login");
            }
            

            // Shows Gallery.html
            response.setContentType("text/html");
            // RequestDispatcher view = request.getRequestDispatcher("html/gallery.html");
            // view.forward(request, response);
            response.setCharacterEncoding("UTF-8");
            File dir = new File("C:\\tomcat\\webapps\\photogallery\\images"); // Remove later
            String[] chld = dir.list(); //
            HttpSession session = request.getSession(true);

            // Gets all images posted by the signed in user
            ArrayList<String> photos = new ArrayList<String>();
            String userUUID = (String) session.getAttribute("userUUID");

            try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "oracle1");
            PreparedStatement preparedStatement = con.prepareStatement("SELECT FILEN FROM PHOTOS WHERE USERID=?");
            preparedStatement.setString(1, userUUID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                 photos.add(rs.getString(1));
            }
            
            } catch (SQLException e) {
                  while (e != null) {
                        System.out.println("Message: " + e.getMessage());
                        System.out.println("SQLState: " + e.getSQLState());
                        System.out.println("ErrorCode: " + e.getErrorCode());
                        e = e.getNextException();
                        System.out.println("");
                  }
            }
            String[] photoArray = photos.toArray(new String[0]);
            PrintWriter out = response.getWriter();
            if (request.getParameterMap().containsKey("SS")) {
                  int num = (Integer) session.getAttribute(key);
                  num++;
                  session.setAttribute(key, num);
            }
            ;
            if (request.getParameterMap().containsKey("FF")) {
                  int num = (Integer) session.getAttribute(key);
                  num--;
                  if (num < 0)
                        num = 0;

                  session.setAttribute(key, num);
            }
            ;
            if (request.getParameterMap().containsKey("AA")) {
                  session.setAttribute(key, 10000);
            }
            ;
            int num1 = (Integer) session.getAttribute(key);
            if (num1 != 10000) {
                  int d = photoArray.length;
                  int num2 = num1 % d;
                  String img_src = photoArray[num2];
                  String alt_text = "SOME IMAGE";
                  
                  out.println("<html>");
                  out.println("<meta charset='UTF-8'>");
                  out.println("<body>");
                  out.println("<div>");
                  out.println("<form action='/photogallery/gallery' method='GET'>");
                  out.println("<div>");
                  out.println("<img id = \"img_src\" src=./images/" + img_src + " alt=" + alt_text
                              + " width=200 height=150>");
                  out.println("<div>");
                  out.println("<br>");
                  out.println("<div class='button'>");
                  out.println("<button class='button' id='prev' onclick='Prev()'>Prev</button>");
                  out.println("<button class='button' id='next'  onclick='Next()'>Next</button>");
                  out.println("<button class='button' id='auto'  onclick='Auto()'>Auto</button>");
                  out.println("</div></div><br>");
                  out.println("</form>");
                  out.println("<div>");
                  out.println("<form action='main' method='GET'>");
                  out.println("<button class='button' id='main'>Main</button>");
                  out.println("</div><br>");
                  out.println("</form>");
                  out.println("<script>");
                  out.println("function Next() {");
                  out.println("const xhttp = new XMLHttpRequest();");
                  out.println("var k2='key'");
                  out.println(" xhttp.open('GET','/photogallery/gallery?SS=k2');");
                  out.println("xhttp.send();}");
                  out.println("function Prev() {");
                  out.println("const xhttp = new XMLHttpRequest();");
                  out.println("var k3='key'");
                  out.println(" xhttp.open('GET','/photogallery/gallery?FF=k3');");
                  out.println("xhttp.send();}");
                  out.println("function Auto() {");
                  out.println("const xhttp = new XMLHttpRequest();");
                  out.println("var k4='key'");
                  out.println(" xhttp.open('GET','/photogallery/gallery?AA=k4');");
                  out.println("xhttp.send();}");
                  out.println("</script>");
                  out.println("</body></html>");
            } else {
                  int d = photoArray.length;
                  String alt_text = "SOME IMAGE";
                  for (int k = 0; k < d; k++) {
                        String img_src = photoArray[k];
                        
                        out.println("<html>");
                        out.println("<meta charset='UTF-8'>");
                        out.println("<body>");
                        out.println("<div>");
                        out.println("<img id = \"img_src\" src=./images/" + img_src + " alt=" + alt_text
                                    + " width=200 height=150>");
                        out.println("</div>");
                        out.println("</body>");
                        out.println("</body></html>");
                       
                  }

            }

            out.close();
      }

      private boolean isLoggedIn(HttpServletRequest req) {
            HttpSession session = req.getSession(false);

            if (session == null || !req.isRequestedSessionIdValid()) {
                  return false;
            } else {
                  return true;
            }

      }
}