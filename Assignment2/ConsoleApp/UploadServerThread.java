import java.net.*;
import java.io.*;
import java.time.Clock;
import java.util.Scanner;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import java.sql.*;
import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.Part;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.*;
import java.sql.*;
import java.util.UUID;
import java.nio.*;
import org.json.simple.JSONObject; 


public class UploadServerThread extends Thread {
   private Socket socket = null;

   public UploadServerThread(Socket socket) {
      super("DirServerThread");
      this.socket = socket;
   }

   public void run() {
      try {
         
         ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
         HttpServletRequest req = new HttpServletRequest(in);
         OutputStream baos = new ByteArrayOutputStream();
         HttpServletResponse res = new HttpServletResponse(baos);
         HttpServlet httpServlet = new UploadServlet();
         httpServlet.doPost(req, res);
         OutputStream out = socket.getOutputStream();
         out.write(((ByteArrayOutputStream) baos).toByteArray());
         
         socket.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

   }
   
}