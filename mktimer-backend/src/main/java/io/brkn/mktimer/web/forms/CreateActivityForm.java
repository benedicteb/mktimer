package io.brkn.mktimer.web.forms;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class CreateActivityForm {
    @NotNull
    private ZonedDateTime start;

    private ZonedDateTime end;

    @NotNull
    private String category;

    public CreateActivityForm() {
    }

    public CreateActivityForm(ZonedDateTime start, String category) {
        this.start = start;
        this.category = category;
    }

    public CreateActivityForm(ZonedDateTime start, ZonedDateTime end, String category) {
        this.start = start;
        this.end = end;
        this.category = category;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CreateActivityForm{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", category=" + category +
                '}';
    }
}
