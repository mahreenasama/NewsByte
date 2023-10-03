package com.a2.newsbyte.news;

import com.a2.newsbyte.tag.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

   /* @GetMapping("/latest")
    public ResponseEntity<Map<String, List<News>>> getLatestNews() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.getLatestNews()));
    }*/

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping
    public ResponseEntity<Map<String, List<News>>> getAllNews(@RequestParam(name = "newspaper") String newspaper) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.getAllNews(newspaper)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping("/assign-tag/{id}")
    public ResponseEntity<Map<String, News>> assignTagById(@PathVariable("id") Long id, @RequestBody Tag tag)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newsService.assignTagById(id, tag)));
    }

}


