package com.a2.newsbyte.news;

import com.a2.newsbyte.newspaper.Newspaper;
import com.a2.newsbyte.newspaper.NewspaperService;
import com.a2.newsbyte.tag.Tag;
import com.a2.newsbyte.tag.TagService;
import com.rometools.rome.feed.synd.SyndEntry;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewspaperService newspaperService;


    public List<News> getAllNews(String newspaper) {
        return newsRepository.getNewsByNewspaperId(newspaperService.getNewspaperByName(newspaper).getId());
    }

    public News assignTagById(Long id, Tag tag) {
        Optional<News> news = newsRepository.findById(id);
        if(news.isPresent()) {
            News oldNews = news.get();
            oldNews.setTag(tag);
            return newsRepository.save(oldNews);
        }
        return null;
    }

    //------------------------------------------------ scrapping functions -----------------------------------------
    // run scrapper
    public void runScrapper() throws IOException {
        this.saveNewsInDatabase(this.getScrappedNews());
    }

    private List<News> getScrappedNews() throws IOException {
        List<News> news = new ArrayList<>();

        List<Newspaper> newspapers = newspaperService.getAllNewspapers();
        System.out.println("totla papers: "+newspapers.size());

        for (Newspaper newspaper: newspapers) {
            System.out.println("paper: "+newspaper.getName());

            if(newspaper.getName().equals("Dawn")) {
                news.addAll(this.fetchDawnNews(newspaper.getUrlToScrap()));
            }
            else if(newspaper.getName().equals("92 News")) {
                news.addAll(this.fetchNews92(newspaper.getUrlToScrap()));
                System.out.println("came in 92: ");
            }
            else {
                news.addAll(this.fetchNewsByRssFeedUrl(newspaper.getUrlToScrap()));
                System.out.println("cam ein else: ");
            }
        }
        System.out.println("totla newssss: "+news.size());
        return news;
    }

    public void saveNewsInDatabase(List<News> newsList) {
        // saving in reverse to save earlier news first and latest news later in database
        for(int i=newsList.size()-1; i>=0; i--) {
            newsRepository.save(newsList.get(i));
        }
    }

    // news fetching functions
    private List<News> fetchDawnNews(String urlToScrap) throws IOException {
        Newspaper newspaper = newspaperService.getNewspaperByScrappingUrl(urlToScrap);

        Document doc = Jsoup.connect("https://www.dawn.com/latest-news").get();
        Elements newsItems = doc.select("div#all article");

        List<News> newsList = new ArrayList<>();

        for (Element item : newsItems) {
            String imgSrc = item.select("img.lazyload").attr("data-src");
            String title = item.select("a.story__link").html();
            String detailsUrl = item.select("a.story__link").attr("href");
            String description = item.select("div.story__excerpt").html();
            String publishedAt = item.select("span.timestamp__calendar").html() + " " + item.select("span.timestamp__time").html();

            if (imgSrc.equals("") || imgSrc.equals(null) || imgSrc.equals("null")) {
                imgSrc = newspaper.getLogoUrl();
            }
            if (title.equals("") || title.equals(null) || title.equals("null")) {
                title = newspaper.getName();
            }
            if (detailsUrl.equals("") || detailsUrl.equals(null) || detailsUrl.equals("null")) {
                detailsUrl = newspaper.getSiteUrl();
            }
            if (description.equals("") || description.equals(null) || description.equals("null")) {
                description = title;
            }
            if (publishedAt.equals("") || publishedAt.equals(null) || publishedAt.equals("null")) {
                publishedAt = LocalDateTime.now().toString();
            }
            // check if this news is already in database
            News latestNews = newsRepository.getLatestNewsByNewspaperId(newspaper.getId());

            if(latestNews != null) {
                if (latestNews.getDetailsUrl().equals(detailsUrl)) {
                    break;
                }
            }
            newsList.add(new News(title.trim(), description.trim(), publishedAt.trim(), imgSrc.trim(), detailsUrl.trim(), LocalDate.now().toString(), tagService.getTagById(1L), newspaper));
        }
        return newsList;
    }

    private List<News> fetchNews92(String urlToScrap) throws IOException {
        Newspaper newspaper = newspaperService.getNewspaperByScrappingUrl(urlToScrap);

        Document document = Jsoup.connect(urlToScrap).get();
        Elements newsItems = document.select(".post-item");

        List<News> newsList = new ArrayList<>();

        for (Element item : newsItems) {
            String imgSrc = "https://92newshd.tv/" + item.select(".post_img").attr("data-src");
            String title = item.select(".title").html();
            String detailsUrl = "https://92newshd.tv" + item.select(".post_link").attr("href");
            String description = title;
            String publishedAt = item.select(".published_time").html();
            /*System.out.println("-------------------92--------------------");
            System.out.println("imgSrc: " + imgSrc);
            System.out.println("Title: " + title);
            System.out.println("detailsUrl: " + detailsUrl);
            System.out.println("Description: " + description);
            System.out.println("publishedAt: " + publishedAt);
            System.out.println("-------------------92--------------------");*/

            if (imgSrc.equals("") || imgSrc.equals(null) || imgSrc.equals("null")) {
                imgSrc = newspaper.getLogoUrl();
            }
            if (title.equals("") || title.equals(null) || title.equals("null")) {
                title = newspaper.getName();
            }
            if (detailsUrl.equals("") || detailsUrl.equals(null) || detailsUrl.equals("null")) {
                detailsUrl = newspaper.getSiteUrl();
            }
            if (description.equals("") || description.equals(null) || description.equals("null")) {
                description = title;
            }
            if (publishedAt.equals("") || publishedAt.equals(null) || publishedAt.equals("null")) {
                publishedAt = LocalDateTime.now().toString();
            }
            // check if this news is already in database
            News latestNews = newsRepository.getLatestNewsByNewspaperId(newspaper.getId());
            if(latestNews != null) {
                if (latestNews.getDetailsUrl().equals(detailsUrl)) {
                    break;
                }
            }
            newsList.add(new News(title.trim(), description.trim(), publishedAt.trim(), imgSrc.trim(), detailsUrl.trim(), LocalDate.now().toString(), tagService.getTagById(1L), newspaper));
        }
        return newsList;
    }


    public List<News> fetchNewsByRssFeedUrl(String urlToScrap) throws MalformedURLException {
        Newspaper newspaper = newspaperService.getNewspaperByScrappingUrl(urlToScrap);

        URL url = new URL(urlToScrap);
        List<News> newsList = new ArrayList<>();
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                System.out.println("---------------------------------------");
                String title = entry.getTitle();
                String detailsUrl = entry.getLink();
                String description = null;
                String imgSrc = null;
                String publishedAt = null;

                // --------------------------------------------- setting description -----------------------------------
                if(entry.getDescription() != null) {
                    description = entry.getDescription().getValue();
                    if(description.contains("<")) {
                        Document doc = Jsoup.parse(description);
                        if(description.contains("img")) {
                            imgSrc = doc.selectFirst("img").attr("src").toString();
                            description = doc.selectFirst("body").text();
                        }
                        else{
                            description = title;
                        }
                    }
                }
                else {
                    System.out.println("desc was null");
                    description = title;
                }

                // --------------------------------------------- setting image -----------------------------------------
                List<org.jdom2.Element> foreignMarkup = entry.getForeignMarkup();
                for (org.jdom2.Element content : foreignMarkup) {
                    if (content.getName().equals("content")) {
                        imgSrc = content.getAttributeValue("url");          // <media:content rul="img-url" />
                    }
                    else if (content.getName().equals("group")) {
                        imgSrc = content.getChildren().get(0).getAttributeValue("url"); // <media:group> <media:content url="img-url" /> <media:group>
                    }
                    else if (content.getName().equals("thumbnail")) {
                        imgSrc = content.getAttributeValue("url");
                    }
                }
                if(imgSrc == null) {
                    imgSrc = newspaper.getLogoUrl();
                }

                // --------------------------------------------- setting pubDate ---------------------------------------
                if(entry.getPublishedDate() != null) {
                    publishedAt = entry.getPublishedDate().toString();
                }
                else{
                    System.out.println("pub at was null");
                    publishedAt = LocalDateTime.now().toString();
                }

                /*System.out.println("--------------------------------------");
                System.out.println("imgSrc: " + imgSrc);
                System.out.println("Title: " + title);
                System.out.println("detailsUrl: " + detailsUrl);
                System.out.println("Description: " + description);
                System.out.println("publishedAt: " + publishedAt);
                System.out.println("------------------------------------");*/

                // check if this news is already in database
                News latestNews = newsRepository.getLatestNewsByNewspaperId(newspaper.getId());
                if(latestNews != null) {
                    if (latestNews.getDetailsUrl().equals(detailsUrl)) {
                        break;
                    }
                }
                newsList.add(new News(title.trim(), description.trim(), publishedAt.trim(), imgSrc.trim(), detailsUrl.trim(), LocalDate.now().toString(), tagService.getTagById(1L), newspaper));
            }
            return newsList;

        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }


        return newsList;
    }

}
