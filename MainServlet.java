import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.util.UUID;

public class MainServlet extends HttpServlet {
  
  public void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
      HttpSession session = request.getSession(false);
      if (session == null) {
        response.setStatus(302);
        response.sendRedirect("login");	
      }		
      String userUUID = (String) session.getAttribute("userUUID");
      System.out.print("here in main : " + userUUID);
      response.setContentType("text/html");
      RequestDispatcher dispatcher = request.getRequestDispatcher("html/main.jsp");
      dispatcher.forward(request, response);
  }
}