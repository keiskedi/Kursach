package com.sistersstones.jewelryshop.repository;

import com.sistersstones.jewelryshop.model.Cart;
import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Пошук кошика користувача
    List<Cart> findByUser(User user);

    // Видалити конкретну прикрасу з кошика користувача
    void deleteByUserAndJewelry(User user, Jewelry jewelry);

    // ❗ Нове: Видалити всі записи з кошика, які посилаються на прикрасу (потрібно для видалення прикраси)
    void deleteByJewelryId(Long jewelryId);
}
