package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.ExtramuralEvent;
import com.scribassu.tracto.domain.Teacher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TeacherExtramuralEventDto {

    private List<ExtramuralEvent> extramuralEvents;
    private Teacher teacher;

    public TeacherExtramuralEventDto(List<ExtramuralEvent> extramuralEvents,
                                     Teacher teacher) {
        this.extramuralEvents = extramuralEvents;
        this.teacher = teacher;
    }
}
