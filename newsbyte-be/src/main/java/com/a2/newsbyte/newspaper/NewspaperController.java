package com.a2.newsbyte.newspaper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newspapers")
public class NewspaperController {
    @Autowired
    private NewspaperService newspaperService;

    @GetMapping("/latest")
    public ResponseEntity<Map<String, List<Newspaper>>> getAllNewsWebsites() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("news", newspaperService.getAllNewspapers()));
    }
}
