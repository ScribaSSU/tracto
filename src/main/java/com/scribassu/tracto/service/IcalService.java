package com.scribassu.tracto.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.scribassu.tracto.dto.web.IcalDto;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.Calendar;

@Service
public class IcalService {
    @Value("${tracto.save-ical-url}")
    private String pathForSavingIcal;

    private String formatDescription(String lessonType, String surname, String name, String patronymic, String subgroup) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        if (lessonType.replaceAll("\"", "").equals("PRACTICE"))
            sb.append("Практика");
        else
            sb.append("Лекция");
        sb.append(", ");
        sb.append(surname.replaceAll("\"", ""));
        sb.append(" ");
        sb.append(name.replaceAll("\"", ""));
        sb.append(" ");
        sb.append(patronymic.replaceAll("\"", ""));

        if (subgroup.replaceAll("\"", "").length() != 0) {
            sb.append(", ");
            sb.append(subgroup.replaceAll("\"", ""));
        }
        sb.append("\"");
        return sb.toString();
    }

    public IcalDto generateIcalFile(JsonObject jsonObject) {
        try {
            String file = pathForSavingIcal+"schedule.ics";
            JsonArray jsonArray = jsonObject.getAsJsonArray("lessons");
            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                JsonObject teacher = jo.getAsJsonObject("teacher");
                JsonObject lessonTime = jo.getAsJsonObject("lessonTime");
                JsonObject day = jo.getAsJsonObject("day");
                int dayNumber = day.getAsJsonPrimitive("dayNumber").getAsInt();
                dayNumber = dayNumber + 1 == 8 ? 1 : dayNumber + 1;
                Calendar startDate = Calendar.getInstance();
                startDate.set(Calendar.DAY_OF_WEEK, dayNumber);
                startDate.set(Calendar.HOUR, lessonTime.get("hourStart").getAsInt());
                startDate.set(Calendar.MINUTE, lessonTime.get("minuteStart").getAsInt());
                Calendar endDate = Calendar.getInstance();
                endDate.set(Calendar.HOUR, lessonTime.get("hourEnd").getAsInt());
                endDate.set(Calendar.MINUTE, lessonTime.get("minuteEnd").getAsInt());
                endDate.set(Calendar.DAY_OF_WEEK, dayNumber);
                String eventName = jo.get("name").toString().replaceAll("\"", "");
                DateTime start = new DateTime(startDate.getTime());
                DateTime end = new DateTime(endDate.getTime());
                Description description = new Description();
                String fotmattedDescription = formatDescription(jo.get("lessonType").toString(),
                        teacher.get("surname").toString(),
                        teacher.get("name").toString(),
                        teacher.get("patronymic").toString(),
                        jo.get("subGroup").toString());
                description.setValue(fotmattedDescription);
                VEvent meeting = new VEvent(start, end, eventName);
                meeting.getProperties().add(description);
                Location location = new Location();
                location.setValue(jo.get("place").toString());
                meeting.getProperties().add(location);
                net.fortuna.ical4j.model.Calendar calendar1 = new net.fortuna.ical4j.model.Calendar();
                calendar1.getProperties().add(CalScale.GREGORIAN);
                calendar1.getComponents().add(meeting);
                FileOutputStream fout = new FileOutputStream(file);
                CalendarOutputter calendarOutputter = new CalendarOutputter();
                calendarOutputter.output(calendar1, fout);
                fout.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new IcalDto(pathForSavingIcal+"schedule.ics");
    }
}
