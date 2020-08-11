package com.scribassu.tracto.service.scheduled;

import com.scribassu.tracto.repository.ScheduleParserStatusRepository;
import com.scribassu.tracto.repository.UpdatedRowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpiredDataRemover {

    private final UpdatedRowRepository updatedRowRepository;

    private final ScheduleParserStatusRepository scheduleParserStatusRepository;

    @Autowired
    public ExpiredDataRemover(UpdatedRowRepository updatedRowRepository,
                              ScheduleParserStatusRepository scheduleParserStatusRepository) {
        this.updatedRowRepository = updatedRowRepository;
        this.scheduleParserStatusRepository = scheduleParserStatusRepository;
    }

    /*@Scheduled(cron = "")
    public void removeExpiredData() {
        //TODO :)
    }*/
}
