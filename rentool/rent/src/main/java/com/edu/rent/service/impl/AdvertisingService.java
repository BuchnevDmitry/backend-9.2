package com.edu.rent.service.impl;

import com.edu.rent.exception.NotFoundException;
import com.edu.rent.model.Advertising;
import com.edu.rent.repository.AdvertisingRepository;
import com.edu.rent.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisingService {

    private final AdvertisingRepository advertisingRepository;
    private final ImageService imageService;

    public List<Advertising> getAllItems(PageRequest pageRequest) {
        return advertisingRepository.findAll(pageRequest).getContent();
    }

    public Advertising getById(Long id) {
        return advertisingRepository.findById(id).orElseThrow(() -> new NotFoundException("Реклама по такому id не найдена"));
    }

    public void delete(Long id) {
        advertisingRepository.deleteById(id);
    }

    public void save(Advertising item, MultipartFile image) {
        String imageUrl = imageService.upload(image);
        item.setImageUrl(imageUrl);
        advertisingRepository.save(item);
    }

    public Advertising update(Long id, Advertising item) {
        Advertising ad = getById(id);
        ad.setName(item.getName());
        ad.setImageUrl(item.getImageUrl());
        return advertisingRepository.save(ad);
    }
}
