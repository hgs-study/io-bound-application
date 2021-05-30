package class101.foo.io.post.application;

import class101.foo.io.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCacheService {

    private final PostRepository postRepository;

    private Page<Post> firstPostPage;

    //사실 이런 캐싱 전략보다는 Redis를 이용하는 것이 좋음.
    @Scheduled(cron = "* * * * * *")
     public void upadteFirstPostPage(){
        firstPostPage = postRepository.findAll(
                PageRequest.of(0,20, Sort.by("id").descending())
        );
    }

     public Page<Post> getFirstPostPage(){
        return this.firstPostPage;
     }
}
