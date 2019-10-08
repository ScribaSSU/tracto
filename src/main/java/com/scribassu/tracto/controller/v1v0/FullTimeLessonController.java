package com.scribassu.tracto.controller.v1v0;

import com.scribassu.tracto.entity.FullTimeLessonEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1.0/schedule/full")
public class FullTimeLessonController {

    @GetMapping("/{groupType}/{groupNumber}")
    public List<FullTimeLessonEntity> getFullTimeLessonEntityList(@PathVariable("groupType") String groupType,
                                                                  @PathVariable("groupNumber") String groupNumber) {
        return null;
    }

    @GetMapping("/{groupType}/{groupNumber}/{dayNumber}")
    public List<FullTimeLessonEntity> getFullTimeLessonEntityListByDay(@PathVariable("groupType") String groupType,
                                                                  @PathVariable("groupNumber") String groupNumber,
                                                                  @PathVariable("dayNumber") String dayNumber) {
        return null;
    }

    @GetMapping("/{groupType}/{groupNumber}/{dayNumber}/{lessonNumber}")
    public FullTimeLessonEntity getFullTimeLessonEntity(@PathVariable("groupType") String groupType,
                                                        @PathVariable("groupNumber") String groupNumber,
                                                        @PathVariable("dayNumber") String dayNumber,
                                                        @PathVariable("lessonNumber") String lessonNumber) {
        return null;
    }
}
