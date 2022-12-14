import java.io.*;
import java.time.Clock;
import java.io.InputStream;



public class UploadServlet extends HttpServlet {
 
   

   protected void doGet(HttpServletRequest request, HttpServletResponse response) {
           
      try {
         InputStream in = request.getInputStream();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] content = new byte[1];
         int bytesRead = -1;
         while ((bytesRead = in.read(content)) != -1) {
            baos.write(content, 0, bytesRead);
         }
         PrintWriter out = new PrintWriter(response.getOutputStream(), true);
         File dir = new File(".");
         String[] chld = dir.list();
         for (int i = 0; i < chld.length; i++) {
            String fileName = chld[i];
            out.println(fileName);
            System.out.println(fileName);
         }
         
       
        
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
             
   }

  

   protected void doPost(HttpServletRequest request, HttpServletResponse response) {
      try {
         InputStream in = request.getInputStream();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] content = new byte[1];
         int bytesRead = -1;
         while ((bytesRead = in.read(content)) != -1) {
            baos.write(content, 0, bytesRead);
         }
         Clock clock = Clock.systemDefaultZone();
         long milliSeconds = clock.millis();
         OutputStream outputStream = new FileOutputStream(new File(String.valueOf(milliSeconds) + ".jpg"));
         baos.writeTo(outputStream);
         outputStream.close();
         PrintWriter out = new PrintWriter(response.getOutputStream(), true);
         File dir = new File(".");
         String[] chld = dir.list();
         for (int i = 0; i < chld.length; i++) {
            String fileName = chld[i];
            out.println(fileName);
            System.out.println(fileName);
         }
      } catch (Exception ex) {
         System.err.println(ex);
      }
   }
}