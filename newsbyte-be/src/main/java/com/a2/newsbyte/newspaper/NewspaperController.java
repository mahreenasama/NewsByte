package com.a2.newsbyte.newspaper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/newspapers")
public class NewspaperController {
    @Autowired
    private NewspaperService newspaperService;

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping
    public ResponseEntity<Map<String, List<Newspaper>>> getAllNewspapers() {
        List<Newspaper> newspapers = newspaperService.getAllNewspapers();
        if (newspapers == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("newspapers", newspapers));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/enabled")
    public ResponseEntity<Map<String, List<Newspaper>>> getEnabledNewspapers() {
        List<Newspaper> newspapers = newspaperService.getEnabledNewspapers();
        if (newspapers == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("newspapers", newspapers));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @GetMapping("/{name}")
    public ResponseEntity<Map<String, Newspaper>> getNewspaperByName(@PathVariable("name") String name)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("newspaper", newspaperService.getNewspaperByName(name)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PostMapping
    public ResponseEntity<Map<String, Newspaper>> createNewspaper(@RequestBody Newspaper newspaper)
    {
        Newspaper createdNewspaper = newspaperService.createNewspaper(newspaper);
        if (createdNewspaper == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("newspaper", createdNewspaper));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @PutMapping("/{name}")
    public ResponseEntity<Map<String, Newspaper>> updateNewspaperByName(@PathVariable("name") String name, @RequestBody Newspaper updatedNewspaperData)
    {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("newspaper", newspaperService.updateNewspaperByName(name, updatedNewspaperData)));
    }

    @PreAuthorize("hasAuthority('EDITOR')")
    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteNewspaperByName(@PathVariable("name") String name)
    {
        newspaperService.disableNewspaperByName(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
