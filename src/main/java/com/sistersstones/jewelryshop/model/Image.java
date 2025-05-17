package com.sistersstones.jewelryshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "jewelry_id")
    private Jewelry jewelry;

    public Image() {}

    public Image(String url, Jewelry jewelry) {
        this.url = url;
        this.jewelry = jewelry;
    }

    public Long getId() { return id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Jewelry getJewelry() { return jewelry; }
    public void setJewelry(Jewelry jewelry) { this.jewelry = jewelry; }
}
