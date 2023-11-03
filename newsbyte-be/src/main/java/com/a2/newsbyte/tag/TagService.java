package com.a2.newsbyte.tag;

import com.a2.newsbyte.news.News;
import com.a2.newsbyte.newspaper.Newspaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    // controller functions
    public List<Tag> getAllTags(String filter)
    {
        List<Tag> tags = new ArrayList<Tag>();
        if(filter.equals("all")){
            return tagRepository.findAll();
        }
        else if(filter.equals("enabled")){
            return tagRepository.findByStatus("enabled");
        }
        return null;
    }

    public Tag createTag(Tag tag)
    {
        tag.setStatus("enabled");
        if (tagRepository.existsById(tag.getName())) {
            return null;
        }
        return tagRepository.save(tag);    //create tag
    }

    public Tag updateTag(String name, String action, Tag updatedTagData) {
        Optional<Tag> oldTagData = tagRepository.findById(name);
        if(oldTagData.isPresent()){
            if(action.equals("update")) {
                //first make tag of those news null which belong to this tag
                tagRepository.makeReleventNewsTagNull(name);

                tagRepository.updateTagByName(updatedTagData.getName(), updatedTagData.getDescription(), name);

                tagRepository.assignReleventNewsUpdatedTag(updatedTagData.getName());

                return tagRepository.findById(updatedTagData.getName()).orElse(null);
            }
            else if(action.equals("enable") || action.equals("disable")){
                Tag updatedTag = oldTagData.get();
                updatedTag.setStatus(action+"d");
                return tagRepository.save(updatedTag);
            }
        }
        return null;
    }

    // other functions
    public Tag getTagByName(String name){
        return tagRepository.findById(name).orElse(null);
    }
}
