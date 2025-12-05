package community.domain.model.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post_images")
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "filename")
    private String filename;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB")
    private byte[] data;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt = new Date();

    protected PostImage() { }

    public PostImage(Long postId, String filename, String contentType, byte[] data) {
        this.postId = postId;
        this.filename = filename;
        this.contentType = contentType;
        this.data = data;
        this.createdAt = new Date();
    }

    public Long getId() { return id; }
    public Long getPostId() { return postId; }
    public String getFilename() { return filename; }
    public String getContentType() { return contentType; }
    public byte[] getData() { return data; }
    public Date getCreatedAt() { return createdAt; }
}
