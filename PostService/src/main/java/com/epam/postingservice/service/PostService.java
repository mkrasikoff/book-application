package com.epam.postingservice.service;

import com.epam.postingservice.entity.Post;

public interface PostService {

    Post save(Long authorId, String text);

    Post get(Long id);

    void delete(Long id);

    Post update(Long id, String text);
}
