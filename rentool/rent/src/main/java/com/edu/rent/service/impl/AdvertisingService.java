package com.edu.rent.service.impl;

import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.Advertising;
import com.edu.rent.repository.AdvertisingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisingService {

    private final AdvertisingRepository advertisingRepository;

    public List<Advertising> getAllItems(PageRequest pageRequest) {
        return advertisingRepository.findAll(pageRequest).getContent();
    }

    public Advertising getById(Long id) {
        return advertisingRepository.findById(id).orElseThrow(() -> new NotFoundException("Реклама по такому id не найдена"));
    }

    public void delete(Long id) {
        advertisingRepository.deleteById(id);
    }

    public void save(Advertising item) {
        advertisingRepository.save(item);
    }

    public Advertising update(Long id, Advertising item) {
        Advertising ad = getById(id);
        ad.setName(item.getName());
        ad.setImageUrl(item.getImageUrl());
        return advertisingRepository.save(ad);
    }
}
