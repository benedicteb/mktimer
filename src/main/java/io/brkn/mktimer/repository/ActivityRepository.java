package io.brkn.mktimer.repository;

import io.brkn.mktimer.domain.Activity;
import io.brkn.mktimer.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    List<Activity> findAllByCategory(Category category);
    List<Activity> findAllByEndDateTimeNullAndCategory(Category category);
}
