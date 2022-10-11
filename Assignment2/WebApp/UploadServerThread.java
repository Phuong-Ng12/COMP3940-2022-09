import java.net.*;
import java.io.*;
import java.time.Clock;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.Part;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import java.sql.*;
import java.util.UUID;
import java.nio.*;


public class UploadServerThread extends Thread {
   private Socket socket = null;

   public UploadServerThread(Socket socket) {
      super("DirServerThread");
      this.socket = socket;
   }

   public void run() {
      byte[] array = new byte[100];

      try {
         int index = 0;

         InputStream in = socket.getInputStream();
         HttpServletRequest req = new HttpServletRequest(in);
         in.read(array);
         String data = new String(array);

         if (data.contains("POST")) {
            index = 1;
         }

         OutputStream baos = new ByteArrayOutputStream();
         HttpServletResponse res = new HttpServletResponse(baos);
         HttpServlet httpServlet = new UploadServlet();
         byte[] content = new byte[1];
         // final String CRLF = "\n\r\n\r";
         // String responseString = " HTTP/1.1/ 200 OK"
         // + CRLF + CRLF + "\nHere is a list of photos" + CRLF + CRLF;

         // baos.write(responseString.getBytes());

         in.read(content);
         if (content[0] == 1) {
            httpServlet.doGet(req, res);
            OutputStream out1 = socket.getOutputStream();
            out1.write(((ByteArrayOutputStream) baos).toByteArray());
         }

         if (content[0] == 2) {
            httpServlet.doPost(req, res);
            OutputStream out = socket.getOutputStream();
            out.write(((ByteArrayOutputStream) baos).toByteArray());

         }

         if (index == 1) {
          
         }
         if ((content[0] != -1) && (content[0] != 2) && (content[0] != 1)) {

            File dir = new File(".");
            String[] chld = dir.list();
            for (int i = 0; i < chld.length; i++) {
               String fileName = chld[i];
               System.out.println(fileName);

            }
            ;

            OutputStream out1 = socket.getOutputStream();
            out1.write("HTTP/1.1 200 OK\r\n".getBytes());
            out1.write(("ContentType: text/html\r\n").getBytes());
            out1.write("\r\n".getBytes());

            Scanner scanner = new Scanner(new File("fileUpload.html"));
            String htmlString = scanner.useDelimiter("\\Z").next();
            scanner.close();
            out1.write(htmlString.getBytes("UTF-8"));

            out1.write("\r\n\r\n".getBytes());
            out1.flush();
            out1.close();
         }

         in.close();

         socket.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
}