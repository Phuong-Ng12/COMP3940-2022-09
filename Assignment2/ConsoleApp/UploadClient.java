import java.net.*;
import java.util.Date;  
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
 

public class UploadClient {
    public static final String LINE_FEED = "\r\n";

    public UploadClient() {
    }

    public String uploadFile() {
        String listing = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input a file with path:");
        String fn = scan.nextLine();
        System.out.println("Please input file caption:");
        String cap = scan.nextLine().trim();
        System.out.println("Please input a date(yyyy-mm-dd):");
        String date = scan.nextLine();
        String charset = "UTF-8";
        File uploadFile1 = new File(fn);
        try {
            Socket socket = new Socket("localhost", 8999);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            FileInputStream inputStream = new FileInputStream(uploadFile1);
            byte[] bytes = inputStream.readAllBytes();
            
            SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");  
            Date myDate = formatter1.parse(date); 
            out.writeObject(cap);
            out.writeObject(myDate);
            out.writeObject(bytes);
            
           
            scan.close();
            socket.shutdownOutput();
            inputStream.close();
            String filename = "";
            while ((filename = in.readLine()) != null) {
                listing = listing + filename;
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return listing;
    }
}
