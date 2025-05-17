package com.sistersstones.jewelryshop.repository;

import com.sistersstones.jewelryshop.model.Cart;
import com.sistersstones.jewelryshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    void deleteByUserAndJewelry(User user, com.sistersstones.jewelryshop.model.Jewelry jewelry);
}
