package com.sistersstones.jewelryshop.repository;

import com.sistersstones.jewelryshop.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByJewelryId(Long jewelryId);
}
