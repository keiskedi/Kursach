package com.sistersstones.jewelryshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String authorName;

    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;

    public Comment() {}

    public Comment(String text, String authorName, Jewelry jewelry) {
        this.text = text;
        this.authorName = authorName;
        this.jewelry = jewelry;
    }

    // Геттери і сеттери
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Jewelry getJewelry() { return jewelry; }
    public void setJewelry(Jewelry jewelry) { this.jewelry = jewelry; }
}
