package com.scribassu.tracto.entity;

import com.scribassu.tracto.domain.FullTimeLesson;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "full-time-lesson")
public class FullTimeLessonEntity extends FullTimeLesson {
}
