import java.net.*;
import java.io.*;
import java.time.Clock;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;


public class UploadServerThread extends Thread {
   private Socket socket = null;

   public UploadServerThread(Socket socket) {
      super("DirServerThread");
      this.socket = socket;
   }

   public void run() {
      // Class<?> newClass;
      try {
         // newClass = Class.forName("web.xml");
         InputStream in = socket.getInputStream();
         OutputStream outputStream = socket.getOutputStream();
         HttpServletRequest req = new HttpServletRequest(in);
         OutputStream baos = new ByteArrayOutputStream();
         HttpServletResponse res = new HttpServletResponse(baos);
         HttpServlet httpServlet = new UploadServlet();
         byte[] content = new byte[1];
         final String CRLF = "\n\r\n\r";
         String responseString = 
               "HTTP/1.1/ 200 OK" 
               + CRLF + CRLF + "\nHere is a list of photos" + CRLF + CRLF;
               
               
         outputStream.write(responseString.getBytes());
         in.read(content);
         if (content[0] == 1) {
            httpServlet.doGet(req, res);
            OutputStream out1 = socket.getOutputStream();
            out1.write(((ByteArrayOutputStream) baos).toByteArray());
         }

         else if (content[0] == 2) {
            httpServlet.doPost(req, res);
            OutputStream out = socket.getOutputStream();
            out.write(((ByteArrayOutputStream) baos).toByteArray());
         }

         else if (content[0] != -1) {
           
            PrintWriter out = new PrintWriter(res.getOutputStream(), true);
            File dir = new File(".");
            String[] chld = dir.list();
            for (int i = 0; i < chld.length; i++) {
               String fileName = chld[i];
               out.println(fileName);
               System.out.println(fileName);
            };
            OutputStream out1 = socket.getOutputStream();
            out1.write(((ByteArrayOutputStream) baos).toByteArray());
         }
         in.close();
         outputStream.close();
         socket.close();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
}