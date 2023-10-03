package com.a2.newsbyte.newspaper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, String> {
    List<Newspaper> findByStatus(String status);
    Newspaper findByUrlToScrap(String urlToScrap);

}
