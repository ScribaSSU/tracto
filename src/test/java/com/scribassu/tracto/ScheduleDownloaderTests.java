package com.scribassu.tracto;

import com.scribassu.tracto.service.ScheduleDownloaderImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ScheduleDownloaderTests {

  /*  @Autowired
    private ScheduleDownloaderImpl scheduleDownloader;

    @Test
    public void contextLoads() {
        assertThat(scheduleDownloader).isNotNull();
    }

    @Test
    public void downloadKntDo351Lesson() {
        String file = scheduleDownloader.downloadSchedule("knt", "do", "351", false);
        assertEquals("schedule_knt_do_351_lesson.xls", file);
    }

    @Test
    public void downloadKntZo151Lesson() {
        String file = scheduleDownloader.downloadSchedule("knt", "zo", "151", false);
        assertEquals("schedule_knt_zo_151_lesson.xls", file);
    }

    @Test
    public void downloadGlVo521Lesson() {
        String file = scheduleDownloader.downloadSchedule("gl", "vo", "521", false);
        assertEquals("schedule_gl_vo_521_lesson.xls", file);
    }

    @Test
    public void downloadTeacherBessonovLesson() {
        String file = scheduleDownloader.downloadSchedule(null, "teacher", "1", false);
        assertEquals("schedule_teacher_1_lesson.xls", file);
    }

    @Test
    public void downloadKntDo351Session() {
        String file = scheduleDownloader.downloadSchedule("knt", "do", "351", true);
        assertEquals("schedule_knt_do_351_session.xls", file);
    }

    @Test
    public void downloadKntZo151Session() {
        String file = scheduleDownloader.downloadSchedule("knt", "zo", "151", true);
        assertEquals("schedule_knt_zo_151_session.xls", file);
    }

    @Test
    public void downloadGlVo521Session() {
        String file = scheduleDownloader.downloadSchedule("gl", "vo", "521", true);
        assertEquals("schedule_gl_vo_521_session.xls", file);
    }

    @Test
    public void downloadTeacherBessonovSession() {
        String file = scheduleDownloader.downloadSchedule(null, "teacher", "1", true);
        assertEquals("schedule_teacher_1_session.xls", file);
    }*/
}
