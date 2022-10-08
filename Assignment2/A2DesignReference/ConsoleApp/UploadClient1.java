import java.io.*;
import java.net.*;
public class UploadClient1 {
    public UploadClient1() { }
    public String uploadFile() {
        String listing = "";
        try {
            Socket socket = new Socket("localhost", 8999);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream();
             out.write(2);
            
           
            FileInputStream fis = new FileInputStream("AndroidLogo.png");
            byte[] bytes = fis.readAllBytes();
            out.write(bytes);
            socket.shutdownOutput();
            fis.close();
            System.out.println("Came this far\n");
            String filename = "";
            while ((filename = in.readLine()) != null) {
                listing =listing+ filename;
            }
            
    //        socket.shutdownInput();
        } catch (Exception e) {
            System.err.println(e);
        }
        return listing;
    }
}