package com.prj4.reviewer.reporsitory;

import com.prj4.reviewer.entity.FeedbackWebsite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FeedbackRepository extends CrudRepository<FeedbackWebsite, String> {

}
