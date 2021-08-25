package com.scribassu.tracto.dto.web;

import com.scribassu.tracto.domain.ExtramuralEvent;
import com.scribassu.tracto.domain.StudentGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExtramuralEventDto {
    private List<ExtramuralEvent> extramuralEvents;
    private StudentGroup studentGroup;

    public ExtramuralEventDto(List<ExtramuralEvent> extramuralEvents,
                              StudentGroup studentGroup) {
        this.extramuralEvents = extramuralEvents;
        this.studentGroup = studentGroup;
    }
}
