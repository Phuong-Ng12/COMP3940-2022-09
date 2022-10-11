import java.net.*;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
        String cap = scan.nextLine();
        System.out.println("Please input a date(yyyy-mm-dd):");
        String date = scan.nextLine();
        String charset = "UTF-8";
        File uploadFile1 = new File(fn);
        try {
            Socket socket = new Socket("localhost", 8999);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, charset),
                    true);
            out.write(2);

            FileInputStream inputStream = new FileInputStream(uploadFile1);
            byte[] bytes = inputStream.readAllBytes();
            out.write(bytes);
            inputStream.close();

            writer.append("Content-Disposition: form-data; name=\"" + "filename" + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(fn).append(LINE_FEED);
            writer.flush();

            writer.append("Content-Disposition: form-data; name=\"" + "caption" + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(cap).append(LINE_FEED);
            writer.flush();

            writer.append("Content-Disposition: form-data; name=\"" + "date" + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(date).append(LINE_FEED);
            writer.flush();

            writer.append(LINE_FEED);
            writer.flush();

            scan.close();
            socket.shutdownOutput();

            System.out.println("Came this far\n");
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
