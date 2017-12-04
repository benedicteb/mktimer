package io.brkn.mktimer.web;

import io.brkn.mktimer.domain.Activity;
import io.brkn.mktimer.domain.Category;
import io.brkn.mktimer.repository.ActivityRepository;
import io.brkn.mktimer.repository.CategoryRepository;
import io.brkn.mktimer.web.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/activity")
    public Iterable<Activity> getActivities(@RequestParam(required = false) String category) {
        Iterable<Activity> activitiesIterable;

        if (category != null) {
            Category categoryObject = getRequiredCategory(category);
            return activityRepository.findAllByCategory(categoryObject);
        } else {
            return activityRepository.findAll();
        }
    }

    @PostMapping("/activity/start")
    public Activity startActivity(@RequestParam String category) {
        Category categoryObject = getRequiredCategory(category);

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        Activity newActivity = new Activity(categoryObject, now);
        activityRepository.save(newActivity);

        return newActivity;
    }

    @PostMapping("/activity/stop")
    public List<Activity> stopActivity(@RequestParam String category) {
        Category categoryObject = getRequiredCategory(category);
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        return activityRepository.findAllByEndDateTimeNullAndCategory(categoryObject)
                .stream().peek((activity) -> activity.setEndDateTime(now))
                .peek((activityRepository::save))
                .collect(Collectors.toList());
    }

    private Category getRequiredCategory(String category) {
        return categoryRepository.findByName(category).orElseThrow(
                () -> new NotFoundException("Category not found"));
    }
}
