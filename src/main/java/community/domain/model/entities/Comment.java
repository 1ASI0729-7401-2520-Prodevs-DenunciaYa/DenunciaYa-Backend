package community.domain.model.entities;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import com.denunciayabackend.community.domain.model.valueobjects.UserId;
import com.denunciayabackend.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Entity
public class Comment extends AuditableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Embedded
    private UserId userId;

    @NotNull
    @Column(nullable = false)
    private String author;

    @NotNull
    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Comment() { }

    public Comment(Post post, UserId userId, String author, String content) {
        if (post == null) throw new IllegalArgumentException("Post must not be null");
        if (userId == null) throw new IllegalArgumentException("UserId must not be null");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("Author must not be blank");
        if (content == null || content.isBlank()) throw new IllegalArgumentException("Content must not be blank");

        this.post = post;
        this.userId = userId;
        this.author = author;
        this.content = content;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
