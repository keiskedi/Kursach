package com.sistersstones.jewelryshop.service;

import com.sistersstones.jewelryshop.model.*;
import com.sistersstones.jewelryshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JewelryRepository jewelryRepository;

    public void addToCart(String username, Long jewelryId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow();
        cartRepository.save(new Cart(user, jewelry));
    }

    public List<Cart> getCartForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(String username, Long jewelryId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow();
        cartRepository.deleteByUserAndJewelry(user, jewelry);
    }
}
