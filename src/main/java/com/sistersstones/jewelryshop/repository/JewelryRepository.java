package com.sistersstones.jewelryshop.repository;

import com.sistersstones.jewelryshop.model.Jewelry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JewelryRepository extends JpaRepository<Jewelry, Long> {

    // Для сортування за ціною
    List<Jewelry> findAllByOrderByPriceAsc();
    List<Jewelry> findAllByOrderByPriceDesc();
}
