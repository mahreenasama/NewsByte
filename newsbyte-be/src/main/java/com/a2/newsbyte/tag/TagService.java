package com.a2.newsbyte.tag;

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
    public List<Tag> getAllTags()
    {
        List<Tag> tags = new ArrayList<Tag>();
        tagRepository.findAllByOrderById().forEach(Tag -> tags.add(Tag));
        return tags;
    }

    public List<Tag> getEnabledTags() {
        return tagRepository.findByStatus("enabled");
    }

    public Tag getTagById(String name)
    {
        return tagRepository.findById(name).orElse(null);
    }

    public Tag createTag(Tag tag)
    {
        tag.setStatus("enabled");
        if (tagRepository.existsById(tag.getName())) {
            return null;
        }
        return tagRepository.save(tag);    //create tag
    }

    public Tag updateTagById(String name, Tag updatedTagData) {
        Optional<Tag> oldTagData = tagRepository.findById(name);
        if(oldTagData.isPresent()){
            Tag updatedTag = oldTagData.get();
            updatedTag.setName(updatedTagData.getName());
            updatedTag.setDescription(updatedTagData.getDescription());
            return tagRepository.save(updatedTag);
        }
        return null;
    }

    public void disableTagById(String name)
    {
        Optional<Tag> oldTagData = tagRepository.findById(name);
        if(oldTagData.isPresent()) {
            Tag updatedTag = oldTagData.get();
            updatedTag.setStatus("disabled");
            tagRepository.save(updatedTag);
        }
    }

    // other functions
    public Tag getTagByName(String name){
        return tagRepository.findById(name).orElse(null);
    }
}
