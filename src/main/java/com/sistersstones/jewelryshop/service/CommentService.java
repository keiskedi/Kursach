package com.sistersstones.jewelryshop.service;

import com.sistersstones.jewelryshop.model.Comment;
import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.repository.CommentRepository;
import com.sistersstones.jewelryshop.repository.JewelryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private JewelryRepository jewelryRepository;

    public List<Comment> getCommentsForJewelry(Long jewelryId) {
        return commentRepository.findByJewelryId(jewelryId);
    }

    public void addComment(Long jewelryId, String text, String username) {
        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow();
        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthorName(username);
        comment.setJewelry(jewelry);
        commentRepository.save(comment);
    }
}
