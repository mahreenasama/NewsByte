package com.a2.newsbyte.tag;

import com.a2.newsbyte.news.News;
import com.a2.newsbyte.newspaper.Newspaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    List<Tag> findByStatus(String status);
    //List<Tag> findAllByOrderByName();

    @Modifying
    @Transactional
    @Query(value="update tags set name=?,description=? where name=?", nativeQuery = true)
    void updateTagByName(String name, String description, String oldName);

    @Modifying
    @Transactional
    @Query(value="update news set tag_name=null where tag_name=?", nativeQuery = true)
    void makeReleventNewsTagNull(String name);

    @Modifying
    @Transactional
    @Query(value="update news set tag_name=? where tag_name is null", nativeQuery = true)
    void assignReleventNewsUpdatedTag(String updatedTag);


}
