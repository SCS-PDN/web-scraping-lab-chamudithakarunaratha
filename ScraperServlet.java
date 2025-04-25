import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/ScrapeServlet")
public class ScrapeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = request.getParameter("url");
        String[] options = request.getParameterValues("options");

        List<Map<String, String>> scrapedData = Scraper.scrapeWebsite(url, options);

        // Save to session
        HttpSession session = request.getSession();
        session.setAttribute("scrapedData", scrapedData);

        // Convert to JSON
        Gson gson = new Gson();
        String json = gson.toJson(scrapedData);

        // Output as JSON
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    public class Scraper {
        public static List<Map<String, String>> scrapeWebsite(String url, String[] options) {
            List<Map<String, String>> results = new ArrayList<>();

            // Mock Data for demo
            Map<String, String> data = new HashMap<>();
            if (options != null) {
                for (String opt : options) {
                    switch (opt) {
                        case "title":
                            data.put("title", "Example Title");
                            break;
                        case "links":
                            data.put("link", "https://example.com");
                            break;
                        case "images":
                            data.put("image", "https://example.com/image.jpg");
                            break;
                    }
                }
            }
            results.add(data);
            return results;
        }
    }





}
