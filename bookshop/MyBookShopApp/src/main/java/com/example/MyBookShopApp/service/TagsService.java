package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.data.Tags;
import com.example.MyBookShopApp.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsService {

    private TagsRepository tagsRepository;

    @Autowired
    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public Tags getTag(Integer tagId) {
        return tagsRepository.findTagsById(tagId);
    }

}
