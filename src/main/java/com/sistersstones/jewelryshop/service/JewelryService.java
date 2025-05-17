package com.sistersstones.jewelryshop.service;

import com.sistersstones.jewelryshop.model.Jewelry;
import com.sistersstones.jewelryshop.repository.JewelryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JewelryService {

    private final JewelryRepository jewelryRepository;

    @Autowired
    public JewelryService(JewelryRepository jewelryRepository) {
        this.jewelryRepository = jewelryRepository;
    }

    public List<Jewelry> getAllJewelry() {
        return jewelryRepository.findAll();
    }

    public List<Jewelry> getSortedByPriceAsc() {
        return jewelryRepository.findAllByOrderByPriceAsc();
    }

    public List<Jewelry> getSortedByPriceDesc() {
        return jewelryRepository.findAllByOrderByPriceDesc();
    }

    public Jewelry getJewelryById(Long id) {
        return jewelryRepository.findById(id).orElse(null);
    }
}
