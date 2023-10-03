package com.a2.newsbyte.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags()
    {
        List<Tag> tags = new ArrayList<Tag>();
        tagRepository.findAllByOrderById().forEach(Tag -> tags.add(Tag));
        return tags;
    }

    public Tag getTagById(Long id)
    {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag createTag(Tag tag)
    {
        tag.setStatus("enabled");
        if (tagRepository.existsById(tag.getId())) {
            return null;
        }
        return tagRepository.save(tag);    //create tag
    }

    public Tag updateTagById(Long id, Tag updatedTagData) {
        Optional<Tag> oldTagData = tagRepository.findById(id);
        if(oldTagData.isPresent()){
            Tag updatedTag = oldTagData.get();
            updatedTag.setName(updatedTagData.getName());
            updatedTag.setDescription(updatedTagData.getDescription());
            return tagRepository.save(updatedTag);
        }
        return null;
    }
    public Tag disableTagById(Long id)
    {
        Optional<Tag> oldTagData = tagRepository.findById(id);
        if(oldTagData.isPresent()) {
            Tag updatedTag = oldTagData.get();
            updatedTag.setStatus("disabled");
            return tagRepository.save(updatedTag);
        }
        return null;
    }
    public Tag getTagByName(String name){
        return tagRepository.findByName(name);
    }
}
