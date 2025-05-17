package com.sistersstones.jewelryshop.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Jewelry jewelry;

    public Cart() {}

    public Cart(User user, Jewelry jewelry) {
        this.user = user;
        this.jewelry = jewelry;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Jewelry getJewelry() { return jewelry; }
    public void setJewelry(Jewelry jewelry) { this.jewelry = jewelry; }
    
    
}
