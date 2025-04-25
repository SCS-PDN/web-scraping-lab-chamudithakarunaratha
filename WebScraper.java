import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


class WebScraper {
    private String headline;
    private String publicationDate;
    private String author;

    public WebScraper(String headline, String publicationDate, String author) {
        this.headline = headline;
        this.publicationDate = publicationDate;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Headline: " + headline + "\nDate: " + publicationDate + "\nAuthor: " + author + "\n";
    }

    public class WebScraper {

        public static void main(String[] args) {
            String url = "https://www.bbc.com";

            try {

                Document doc = Jsoup.connect(url).get();


                System.out.println("Page Title: " + doc.title());


                for (int i = 1; i <= 6; i++) {
                    Elements headings = doc.select("h" + i);
                    for (Element heading : headings) {
                        System.out.println("Heading (h" + i + "): " + heading.text());
                    }
                }


                Elements links = doc.select("a[href]");
                System.out.println("\nAll Links:");
                for (Element link : links) {
                    System.out.println(link.attr("abs:href") + " => " + link.text());
                }


                List<Scraper> newsList = new ArrayList<>();

                Elements newsBlocks = doc.select("div[data-entityid^=container-top-stories] a");

                for (Element news : newsBlocks) {
                    String headline = news.text();
                    String newsUrl = news.absUrl("href");


                    if (!headline.isEmpty() && newsUrl.startsWith("https://www.bbc.com/news")) {
                        try {
                            Document newsDoc = Jsoup.connect(newsUrl).get();
                            String date = newsDoc.selectFirst("time") != null ? newsDoc.selectFirst("time").attr("datetime") : "Not available";
                            String author = newsDoc.selectFirst(".ssrcss-1pjc44v-Contributor") != null ?
                                    newsDoc.selectFirst(".ssrcss-1pjc44v-Contributor").text() : "Not available";

                            newsList.add(new Scraper(headline, date, author));
                        } catch (IOException e) {
                            System.out.println("Failed to fetch article: " + newsUrl);
                        }
                    }
                }

                System.out.println("\nExtracted News Items:");
                for (Scraper item : newsList) {
                    System.out.println(item);
                }

            } catch (IOException e) {
                System.out.println("Error fetching the webpage: " + e.getMessage());
            }
        }
    }


}



