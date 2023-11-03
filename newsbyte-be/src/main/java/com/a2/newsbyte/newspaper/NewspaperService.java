package com.a2.newsbyte.newspaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewspaperService {
    @Autowired
    private NewspaperRepository newspaperRepository;

    // controller functions
    public List<Newspaper> getAllNewspapers() {
        List<Newspaper> newspapers = new ArrayList<>();
        newspaperRepository.findAll().forEach(Newspaper -> newspapers.add(Newspaper));
        return newspapers;
    }

    public List<Newspaper> getEnabledNewspapers() {
        List<Newspaper> newspapers = new ArrayList<>();
        return newspaperRepository.findByStatus("enabled");
    }

    public Newspaper createNewspaper(Newspaper newspaper)
    {
        newspaper.setStatus("enabled");
        if (newspaperRepository.existsById(newspaper.getName())) {
            return null;
        }
        return newspaperRepository.save(newspaper);
    }

    public Newspaper updateNewspaperByName(String name, Newspaper updatedNewspaperData) {
        Optional<Newspaper> oldNewspaperData = newspaperRepository.findById(name);
        if(oldNewspaperData.isPresent()){
            Newspaper updatedNewspaper = oldNewspaperData.get();
            updatedNewspaper.setName(updatedNewspaperData.getName());
            updatedNewspaper.setType(updatedNewspaperData.getType());
            updatedNewspaper.setLogoUrl(updatedNewspaperData.getLogoUrl());
            updatedNewspaper.setSiteUrl(updatedNewspaperData.getSiteUrl());
            updatedNewspaper.setUrlToScrap(updatedNewspaperData.getUrlToScrap());
            return newspaperRepository.save(updatedNewspaper);
        }
        return null;
    }

    public void disableNewspaperByName(String name)
    {
        Optional<Newspaper> oldNewspaperData = newspaperRepository.findById(name);
        if(oldNewspaperData.isPresent()) {
            Newspaper updatedNewspaper = oldNewspaperData.get();
            updatedNewspaper.setStatus("disabled");
            newspaperRepository.save(updatedNewspaper);
        }
    }

    // other functions
    public Newspaper getNewspaperByScrappingUrl(String urlToScrap){
        return newspaperRepository.findByUrlToScrap(urlToScrap);
    }

    public Newspaper getNewspaperByName(String name) {
        return newspaperRepository.findById(name).orElse(null);
    }

    public List<Newspaper> getNewspapersByType(String type) {
        return newspaperRepository.findByType(type);
    }

}
