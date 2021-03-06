package com.prj4.reviewer.reporsitory;

import com.prj4.reviewer.entity.RatingPost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingPostRepository extends CrudRepository<RatingPost, String> {
    RatingPost findByIdRatingProduct(String idRatingProduct);
    RatingPost findByIdReviewer(String idReviewer);
    RatingPost findByIdProduct(String idProduct);
    RatingPost findByIdProductAndIdReviewer(String idProduct, String idReviewer);

    @Query("Select count(c) from RatingPost c")
    int getCountRatingPosts();
}
