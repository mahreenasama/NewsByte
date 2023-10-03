package com.a2.newsbyte.newspaper;

import com.a2.newsbyte.news.News;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "newspapers")
public class Newspaper {
    @Id
    private Long id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    private String type;        //localOrInternational
    @Column(name = "logo_url")
    private String logoUrl;
    @Column(name = "site_url")
    private String siteUrl;
    @Column(name = "url_to_scrap")
    private String urlToScrap;
    private String status;
    @JsonIgnore
    @OneToMany(mappedBy = "newspaper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<News> news;

    public Newspaper(){}
    public Newspaper(Newspaper newspaper) {
        this.id=newspaper.id;
        this.name=newspaper.name;
        this.type=newspaper.type;
        this.logoUrl=newspaper.logoUrl;
        this.siteUrl=newspaper.siteUrl;
        this.urlToScrap = newspaper.urlToScrap;
        this.status = newspaper.status;
        this.news=newspaper.news;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getUrlToScrap() {
        return urlToScrap;
    }

    public void setUrlToScrap(String urlToScrap) {
        this.urlToScrap = urlToScrap;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<News> getNews() {
        return Collections.unmodifiableList(news);
    }

    public void setNews(List<News> news) {
        this.news = new ArrayList<>(news);
    }

}
