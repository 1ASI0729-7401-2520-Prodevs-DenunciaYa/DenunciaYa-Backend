package community.domain.model.aggregates;

import com.denunciayabackend.community.domain.model.commands.CreatePostCommand;
import com.denunciayabackend.community.domain.model.entities.Comment;
import com.denunciayabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post extends AuditableAbstractAggregateRoot<Post> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String author;

    @Column(length = 5000, nullable = false)
    private String content;


    @Lob
    @Column
    private String imageUrl;

    @Column(nullable = false)
    private Integer likes = 0;


    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();


    protected Post() { }

    public Post(CreatePostCommand command) {
        this.author = command.author();
        this.userId = command.userId();
        this.content = command.content();
        this.imageUrl = command.imageUrl();
        this.likes = (command.likes() != null) ? command.likes() : 0;
    }



    public void addComment(Comment comment) {
        if (comment == null) throw new IllegalArgumentException("Comment cannot be null");
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        if (comment == null) return;
        this.comments.remove(comment);
        comment.setPost(null);
    }

    public void like() {
        this.likes++;
    }
    public void incrementLikes() {
        this.likes++;
    }
    public void unlike() {
        if (this.likes > 0) this.likes--;
    }

    public int commentCount() {
        return this.comments.size();
    }

    // Nuevo setter para imageUrl (necesario para actualizar la URL después de crear el post)
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
