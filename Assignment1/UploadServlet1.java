import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.*;

@MultipartConfig
public class UploadServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dirList = getListing("C:\\tomcat\\webapps\\upload\\images");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String str = dirList;
        JSONObject json = new JSONObject();
        json.put("name", str);
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("??????? in do Post??????\n");
        Part filePart = request.getPart("File");
        String fileName = filePart.getSubmittedFileName();
        String caption = request.getParameter("caption");
        String Date = request.getParameter("date");
        if (fileName.equals("")) {
            response.setStatus(302);
            return;
        }
        System.out.println("??????? inside do Post??????\n");
        filePart.write(System.getProperty("catalina.base") + "/webapps/upload/images/" + fileName);

    }

    private String getListing(String path) {
        System.out.println("??????? in getListing??????\n");
        String dirList = "";
        File dir = new File(path);
        String[] chld = dir.list();
        for (int i = 0; i < chld.length; i++) {
            dirList += "," + chld[i];
        }
        System.out.println(dirList);
        return dirList;
    }
}
