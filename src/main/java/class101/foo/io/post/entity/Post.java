package class101.foo.io.post.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    @SequenceGenerator(name="post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;
    private String content;
}
