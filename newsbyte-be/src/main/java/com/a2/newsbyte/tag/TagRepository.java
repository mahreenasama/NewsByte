package com.a2.newsbyte.tag;

import com.a2.newsbyte.newspaper.Newspaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    List<Tag> findByStatus(String status);
    List<Tag> findAllByOrderById();

}
