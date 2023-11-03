package com.a2.newsbyte.news;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> getNewsByNewspaperName(String newspaperName);

    List<News> getNewsByTagName(String tagName);

    @Query(value="select * from news where id = (select max(id) from news where newspaper_name = ?)", nativeQuery = true)
    News getLatestNewsByNewspaperName(String name);

    /*@Modifying
    @Transactional
    @Query(value = "DELETE FROM news WHERE added_on != ?", nativeQuery = true)
    void deletePrevDatesNews(String todaysDate);*/

}
