package io.brkn.mktimer.repository;

import io.brkn.mktimer.domain.Activity;
import io.brkn.mktimer.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
    List<Activity> findAllByCategory(Category category);
    List<Activity> findAllByEndDateTimeNullAndCategory(Category category);
    List<Activity> findAllByStartDateTimeAfter(ZonedDateTime after);
    List<Activity> findAllByStartDateTimeAfterAndStartDateTimeBefore(ZonedDateTime after, ZonedDateTime before);
    List<Activity> findAllByStartDateTimeBefore(ZonedDateTime before);
    List<Activity> findAllByCategoryAndStartDateTimeAfter(Category category, ZonedDateTime after);
    List<Activity> findAllByCategoryAndStartDateTimeAfterAndStartDateTimeBefore(Category category, ZonedDateTime
            after, ZonedDateTime before);
    List<Activity> findAllByCategoryAndStartDateTimeBefore(Category category, ZonedDateTime before);
}
