package com.sparta.blog.service;

import com.sparta.blog.dto.PostRequestDto;
import com.sparta.blog.dto.PostResponseDto;
import com.sparta.blog.entity.Post;
import com.sparta.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto createPost(PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(requestDto);

        // DB 저장
        Post savePost = postRepository.save(post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(post);

        return postResponseDto;
    }

    public List<PostResponseDto> getPosts() {
        // DB 조회
        return postRepository.findAll().stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public Long updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        if(post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        } else {
            new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return id;
    }

    public Long deletePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);
        if (post.getPassword().equals(requestDto.getPassword())) {
            postRepository.delete(post);
        } else {
            new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return id;
    }

    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        PostResponseDto responseDto = new PostResponseDto(post);
        return responseDto;
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }


}
