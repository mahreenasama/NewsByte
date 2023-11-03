package com.a2.newsbyte.tag;


import com.a2.newsbyte.newspaper.Newspaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<Map<String, List<Tag>>> getAllTags(@RequestParam(name = "filter") String filter) {
        List<Tag> tags = tagService.getAllTags(filter);
        if (tags == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("tags", tags));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/{name}")
    public ResponseEntity<Map<String, Tag>> getTag(@PathVariable("name") String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("tag", tagService.getTagByName(name)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping
    public ResponseEntity<Map<String, Tag>> createTag(@RequestBody Tag tag)
    {
        Tag createdTag = tagService.createTag(tag);
        if (createdTag == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("content", createdTag));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/{name}")
    public ResponseEntity<Map<String, Tag>> updateTag(@PathVariable("name") String name, @RequestParam(name = "action") String action, @RequestBody Tag updatedTagData)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("tag", tagService.updateTag(name, action, updatedTagData)));
    }


}
