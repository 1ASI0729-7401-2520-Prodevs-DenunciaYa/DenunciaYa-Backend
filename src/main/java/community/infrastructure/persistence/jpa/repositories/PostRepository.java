package community.infrastructure.persistence.jpa.repositories;

import com.denunciayabackend.community.domain.model.aggregates.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor(String author);

    List<Post> findByUserId(String userId);

    List<Post> findByContentContainingIgnoreCase(String keyword);

    boolean existsByIdAndAuthor(Long id, String author);

    Optional<Post> findByImageUrl(String imageUrl);
}
