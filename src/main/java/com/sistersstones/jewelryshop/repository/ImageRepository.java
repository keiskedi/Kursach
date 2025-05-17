package com.sistersstones.jewelryshop.repository;

import com.sistersstones.jewelryshop.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByJewelryId(Long jewelryId);
}
