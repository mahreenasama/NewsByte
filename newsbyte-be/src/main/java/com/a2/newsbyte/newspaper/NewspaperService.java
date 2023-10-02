package com.a2.newsbyte.newspaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewspaperService {
    @Autowired
    private NewspaperRepository newspaperRepository;


    public List<Newspaper> getAllNewspapers(){
        List<Newspaper> newspapers = new ArrayList<>();
        newspaperRepository.findAll().forEach(Newspaper -> newspapers.add(Newspaper));
        return newspapers;
    }

    public Newspaper getNewspaperByScrappingUrl(String urlToScrap){
        return newspaperRepository.findByUrlToScrap(urlToScrap);
    }

    public Newspaper getNewspaperByName(String name){
        return newspaperRepository.findByName(name);
    }

    public Newspaper getNewspaperByType(String type){
        return newspaperRepository.findByType(type);
    }

}
