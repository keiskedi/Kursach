package com.sistersstones.jewelryshop.service;

import com.sistersstones.jewelryshop.model.Cart;
import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.model.User;
import com.sistersstones.jewelryshop.repository.CartRepository;
import com.sistersstones.jewelryshop.repository.JewelryRepository;
import com.sistersstones.jewelryshop.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    // Додати прикрасу до кошика користувача
    public void addToCart(String username, Long jewelryId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow();

        // Перевірка — якщо товар вже в кошику, не додаємо вдруге
        boolean alreadyExists = cartRepository.findByUser(user).stream()
                .anyMatch(cart -> cart.getJewelry().getId().equals(jewelryId));

        if (!alreadyExists) {
            cartRepository.save(new Cart(user, jewelry));
        }
    }

    // Отримати список товарів у кошику користувача
    public List<Cart> getCartForUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartRepository.findByUser(user);
    }

    // Видалити товар з кошика користувача
    @Transactional
    public void removeFromCart(String username, Long jewelryId) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow();
        cartRepository.deleteByUserAndJewelry(user, jewelry);
    }
}
