package com.reddit.repository;

import com.reddit.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p join p.community c where c.communityName ILIKE :name and p.postId = :postId")
    Optional<Post> findPostsByCommunityName(@Param("name") String communityName,
                                            @Param("postId") Long postId);

    @Query("select p from Post p join p.user u where (u.username ILIKE :name) and (p.postId = :postId)")
    Optional<Post> findPostsByUsername(@Param("name") String username,
                                       @Param("postId") Long postId);

    @Query("SELECT p FROM Post p LEFT JOIN p.community c WHERE " +
            "(c.isPrivate = FALSE AND c.isRestrict = FALSE) OR " +
            "(p.community = NULL)")
    Page<Post> findPostsOrderByPublishedAt(Pageable pageable);
}
