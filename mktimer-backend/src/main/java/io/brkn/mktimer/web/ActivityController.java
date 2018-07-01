package io.brkn.mktimer.web;

import io.brkn.mktimer.domain.Activity;
import io.brkn.mktimer.domain.Category;
import io.brkn.mktimer.repository.ActivityRepository;
import io.brkn.mktimer.repository.CategoryRepository;
import io.brkn.mktimer.web.exceptions.NotFoundException;
import io.brkn.mktimer.web.forms.CreateActivityForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ActivityController {
    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/activity")
    public Iterable<Activity> getActivities(@RequestParam(required = false) String category,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO
                                                    .DATE_TIME) ZonedDateTime after,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO
                                                    .DATE_TIME) ZonedDateTime before) {
        if (category != null) {
            Category categoryObject = getRequiredCategory(category);

            if (before != null && after != null) {
                return activityRepository.findAllByCategoryAndStartDateTimeAfterAndStartDateTimeBefore
                        (categoryObject, after, before);
            } else if (after != null) {
                return activityRepository.findAllByCategoryAndStartDateTimeAfter(categoryObject, after);
            } else if (before != null) {
                return activityRepository.findAllByCategoryAndStartDateTimeBefore(categoryObject, before);
            }

            return activityRepository.findAllByCategory(categoryObject);
        } else {
            if (before != null && after != null) {
                return activityRepository.findAllByStartDateTimeAfterAndStartDateTimeBefore(after, before);
            } else if (after != null) {
                return activityRepository.findAllByStartDateTimeAfter(after);
            } else if (before != null) {
                return activityRepository.findAllByStartDateTimeBefore(before);
            }

            return activityRepository.findAll();
        }
    }

    @PostMapping("/activity")
    public Activity createActivity(@RequestBody @Valid CreateActivityForm form) {
        Category category = categoryRepository.findByName(form.getCategory())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        Activity newActivity = new Activity(category, form.getStart());

        if (form.getEnd() != null) {
            newActivity.setEndDateTime(form.getEnd());
        }

        activityRepository.save(newActivity);

        return newActivity;
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
