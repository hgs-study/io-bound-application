package class101.foo.io.post.api;

import class101.foo.io.message.Producer;
import class101.foo.io.post.application.PostCacheService;
import class101.foo.io.post.application.PostRepository;
import class101.foo.io.post.entity.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private static final Integer PAGE_SIZE = 20;

    private final PostCacheService postCacheService;

    private final PostRepository postRepository;

    private final Producer producer;

    private final ObjectMapper objectMapper;

    // 1. 글을 작성한다.
    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) throws JsonProcessingException {
        String jsonPost = objectMapper.writeValueAsString(post);
        producer.sendTo(jsonPost);
        return post;
//        return postRepository.save(post);
    }

    // 2-1. 글 목록을 조회한다.
    @GetMapping("/posts")
    public Page<Post> getPostList(@RequestParam(defaultValue = "1") Integer page) {
        if(page.equals(1))
            return postCacheService.getFirstPostPage();
        else
            return postRepository.findAll(
                    PageRequest.of(1,PAGE_SIZE, Sort.by("id").descending())
            );
    }

//    // 2-1. 글 목록을 조회한다.
//    @GetMapping("/posts")
//    public List<Post> getPostList() {
//        return postRepository.findAll();
//    }
    
    // 2-2 글 목록을 페이징하여 반환
    
    // 3. 글 번호로 조회
    
    // 4. 글 내용으로 검색 -> 해당 내용이 포함된 모든 글

}
