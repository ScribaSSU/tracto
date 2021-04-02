package com.scribassu.tracto.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.scribassu.tracto.domain.StudentGroup;
import com.scribassu.tracto.dto.web.CsvDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class ParseLessonsAndCreateCsv {
    @Value("${tracto.save-csv-url}")
    private String pathForSavingCsv;

    private String formatTime(String hour, String minute) {
        StringBuilder sb = new StringBuilder();
        if (hour.length() == 1) {
            sb.append("0");
            sb.append(hour);
        } else
            sb.append(hour);
        sb.append(":");
        if (minute.length() == 1) {
            sb.append("0");
            sb.append(minute);
        } else
            sb.append(minute);
        sb.append(":");
        sb.append("0");
        sb.append("0");
        return sb.toString();
    }

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

    public CsvDto generateCsvFile(JsonObject jsonObject) {
        try {
            FileWriter writer = new FileWriter(new File(pathForSavingCsv + "schedule.csv"));
            JsonArray jsonArray = jsonObject.getAsJsonArray("lessons");
            Calendar c = Calendar.getInstance();
            Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                c.add(Calendar.DATE, 1);
            } else {
                c.add(Calendar.DATE, -dayOfWeek + 2);
            }
            Calendar cAdd = Calendar.getInstance();
            dayOfWeek = cAdd.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                cAdd.add(Calendar.DATE, 1);
            } else {
                cAdd.add(Calendar.DATE, -dayOfWeek + 2);
            }
            dayOfWeek = 2;
            Date date = c.getTime();
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = targetFormat.format(date);
            StringBuilder scheduleBuilder = new StringBuilder();
            scheduleBuilder.append("Subject");
            scheduleBuilder.append(",");
            scheduleBuilder.append("Start date");
            scheduleBuilder.append(",");
            scheduleBuilder.append("Start time");
            scheduleBuilder.append(",");
            scheduleBuilder.append("End date");
            scheduleBuilder.append(",");
            scheduleBuilder.append("End time");
            scheduleBuilder.append(",");
            scheduleBuilder.append("Description");
            scheduleBuilder.append(",");
            scheduleBuilder.append("Location");
            scheduleBuilder.append('\n');
            for (JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                JsonObject lessonTime = jo.getAsJsonObject("lessonTime");
                JsonObject day = jo.getAsJsonObject("day");
                JsonObject teacher = jo.getAsJsonObject("teacher");
                JsonPrimitive dayNumer = day.getAsJsonPrimitive("dayNumber");
                cAdd.add(Calendar.DATE, dayNumer.getAsInt() - 1);
                date = cAdd.getTime();
                formattedDate = targetFormat.format(date);
                scheduleBuilder.append(jo.get("name").toString().replaceAll("\"", ""));
                scheduleBuilder.append(",");
                scheduleBuilder.append(formattedDate);
                scheduleBuilder.append(',');
                scheduleBuilder.append(formatTime(lessonTime.get("hourStart").toString(), lessonTime.get("minuteStart").toString()));
                scheduleBuilder.append(',');
                scheduleBuilder.append(formattedDate);
                scheduleBuilder.append(',');
                scheduleBuilder.append(formatTime(lessonTime.get("hourEnd").toString(), lessonTime.get("minuteEnd").toString()));
                scheduleBuilder.append(",");
                scheduleBuilder.append(formatDescription(jo.get("lessonType").toString(),
                        teacher.get("surname").toString(),
                        teacher.get("name").toString(),
                        teacher.get("patronymic").toString(),
                        jo.get("subGroup").toString()));
                scheduleBuilder.append(",");
                scheduleBuilder.append(jo.get("place").toString());
                scheduleBuilder.append('\n');
                cAdd = Calendar.getInstance();
                dayOfWeek = cAdd.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek == 1) {
                    cAdd.add(Calendar.DATE, 1);
                } else {
                    cAdd.add(Calendar.DATE, -dayOfWeek + 2);
                }
            }
            writer.write(scheduleBuilder.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CsvDto(pathForSavingCsv + "schedule.csv");
    }
}
