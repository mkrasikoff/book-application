package com.epam.postingservice.service;

import com.epam.postingservice.entity.Post;
import com.epam.postingservice.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post save(Long authorId, String text) {
        Post post = new Post();
        post.setAuthorId(authorId);
        post.setText(text);
        post.setPostedAt(LocalDate.now());

        Post updated = postRepository.save(post);
        updateUser(authorId);

        return updated;
    }

    private void updateUser(Long authorId) {
        String address = "http://user-service-api:8091/users/"  + authorId + "/iterateNumberOfPosts";
        URI uri = URI.create(address);
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.put(uri, null);
        }
        catch (HttpClientErrorException.NotFound exception) {
            return;
        }
    }

    @Override
    public Post get(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(Long id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            postRepository.deleteById(id);

            updateUser(post.getAuthorId());
        }
        catch (EmptyResultDataAccessException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Post update(Long id, String text) {
        Post oldPost = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Post updatedPost = new Post();
        updatedPost.setId(id);
        updatedPost.setText(text);
        updatedPost.setAuthorId(oldPost.getAuthorId());
        updatedPost.setPostedAt(LocalDate.now());

        return postRepository.save(updatedPost);
    }
}
