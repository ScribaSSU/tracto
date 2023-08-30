package com.scribassu.tracto.startup;

import com.scribassu.tracto.entity.schedule.*;
import com.scribassu.tracto.repository.DayRepository;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.ExamPeriodMonthRepository;
import com.scribassu.tracto.repository.LessonTimeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TablesDataInitializer {
    private final DayRepository dayRepository;
    private final LessonTimeRepository lessonTimeRepository;
    private final DepartmentRepository departmentRepository;
    private final ExamPeriodMonthRepository examPeriodMonthRepository;
    
    public void init() {
        initDays();
        initTimes();
        initDepartments();
        initExamPeriodMonths();
    }

    private void initDays() {
        log.info("Start init days");
        Day monday = dayRepository.findByDayNumber(1);
        if (monday == null) {
            monday = new Day();
            monday.setDayNumber(1);
            monday.setWeekDay(WeekDay.MONDAY);
            dayRepository.save(monday);
            log.info("Init monday");
        } else {
            log.info("No need to init monday");
        }

        Day tuesday = dayRepository.findByDayNumber(2);
        if (tuesday == null) {
            tuesday = new Day();
            tuesday.setDayNumber(2);
            tuesday.setWeekDay(WeekDay.TUESDAY);
            dayRepository.save(tuesday);
            log.info("Init tuesday");
        } else {
            log.info("No need to init tuesday");
        }

        Day wednesday = dayRepository.findByDayNumber(3);
        if (wednesday == null) {
            wednesday = new Day();
            wednesday.setDayNumber(3);
            wednesday.setWeekDay(WeekDay.WEDNESDAY);
            dayRepository.save(wednesday);
            log.info("Init wednesday");
        } else {
            log.info("No need to init wednesday");
        }

        Day thursday = dayRepository.findByDayNumber(4);
        if (thursday == null) {
            thursday = new Day();
            thursday.setDayNumber(4);
            thursday.setWeekDay(WeekDay.THURSDAY);
            dayRepository.save(thursday);
            log.info("Init thursday");
        } else {
            log.info("No need to init thursday");
        }

        Day friday = dayRepository.findByDayNumber(5);
        if (friday == null) {
            friday = new Day();
            friday.setDayNumber(5);
            friday.setWeekDay(WeekDay.FRIDAY);
            dayRepository.save(friday);
            log.info("Init friday");
        } else {
            log.info("No need to init friday");
        }

        Day saturday = dayRepository.findByDayNumber(6);
        if (saturday == null) {
            saturday = new Day();
            saturday.setDayNumber(6);
            saturday.setWeekDay(WeekDay.SATURDAY);
            dayRepository.save(saturday);
            log.info("Init saturday");
        } else {
            log.info("No need to init saturday");
        }

        Day sunday = dayRepository.findByDayNumber(7);
        if (sunday == null) {
            sunday = new Day();
            sunday.setDayNumber(7);
            sunday.setWeekDay(WeekDay.SUNDAY);
            dayRepository.save(sunday);
            log.info("Init sunday");
        } else {
            log.info("No need to init sunday");
        }

        log.info("End init days");
    }

    private void initTimes() {
        log.info("Start init lesson times");
        LessonTime lessonFirst = lessonTimeRepository.findByLessonNumber(1);
        if (lessonFirst == null) {
            lessonFirst = new LessonTime();
            lessonFirst.setLessonNumber(1);
            lessonFirst.setHourStart(8);
            lessonFirst.setMinuteStart(20);
            lessonFirst.setHourEnd(9);
            lessonFirst.setMinuteEnd(50);
            lessonTimeRepository.save(lessonFirst);
            log.info("Init lesson first");
        } else {
            log.info("No need to init lesson first");
        }

        LessonTime lessonSecond = lessonTimeRepository.findByLessonNumber(2);
        if (lessonSecond == null) {
            lessonSecond = new LessonTime();
            lessonSecond.setLessonNumber(2);
            lessonSecond.setHourStart(10);
            lessonSecond.setMinuteStart(0);
            lessonSecond.setHourEnd(11);
            lessonSecond.setMinuteEnd(35);
            lessonTimeRepository.save(lessonSecond);
            log.info("Init lesson second");
        } else {
            log.info("No need to init lesson second");
        }

        LessonTime lessonThird = lessonTimeRepository.findByLessonNumber(3);
        if (lessonThird == null) {
            lessonThird = new LessonTime();
            lessonThird.setLessonNumber(3);
            lessonThird.setHourStart(12);
            lessonThird.setMinuteStart(5);
            lessonThird.setHourEnd(13);
            lessonThird.setMinuteEnd(40);
            lessonTimeRepository.save(lessonThird);
            log.info("Init lesson third");
        } else {
            log.info("No need to init lesson third");
        }

        LessonTime lessonFourth = lessonTimeRepository.findByLessonNumber(4);
        if (lessonFourth == null) {
            lessonFourth = new LessonTime();
            lessonFourth.setLessonNumber(4);
            lessonFourth.setHourStart(13);
            lessonFourth.setMinuteStart(50);
            lessonFourth.setHourEnd(15);
            lessonFourth.setMinuteEnd(25);
            lessonTimeRepository.save(lessonFourth);
            log.info("Init lesson fourth");
        } else {
            log.info("No need to init lesson fourth");
        }

        LessonTime lessonFifth = lessonTimeRepository.findByLessonNumber(5);
        if (lessonFifth == null) {
            lessonFifth = new LessonTime();
            lessonFifth.setLessonNumber(5);
            lessonFifth.setHourStart(15);
            lessonFifth.setMinuteStart(35);
            lessonFifth.setHourEnd(17);
            lessonFifth.setMinuteEnd(10);
            lessonTimeRepository.save(lessonFifth);
            log.info("Init lesson fifth");
        } else {
            log.info("No need to init lesson fifth");
        }

        LessonTime lessonSix = lessonTimeRepository.findByLessonNumber(6);
        if (lessonSix == null) {
            lessonSix = new LessonTime();
            lessonSix.setLessonNumber(6);
            lessonSix.setHourStart(17);
            lessonSix.setMinuteStart(20);
            lessonSix.setHourEnd(18);
            lessonSix.setMinuteEnd(40);
            lessonTimeRepository.save(lessonSix);
            log.info("Init lesson six");
        } else {
            log.info("No need to init lesson six");
        }

        LessonTime lessonSeventh = lessonTimeRepository.findByLessonNumber(7);
        if (lessonSeventh == null) {
            lessonSeventh = new LessonTime();
            lessonSeventh.setLessonNumber(7);
            lessonSeventh.setHourStart(18);
            lessonSeventh.setMinuteStart(45);
            lessonSeventh.setHourEnd(20);
            lessonSeventh.setMinuteEnd(5);
            lessonTimeRepository.save(lessonSeventh);
            log.info("Init lesson seventh");
        } else {
            log.info("No need to init lesson seventh");
        }

        LessonTime lessonEighth = lessonTimeRepository.findByLessonNumber(8);
        if (lessonEighth == null) {
            lessonEighth = new LessonTime();
            lessonEighth.setLessonNumber(8);
            lessonEighth.setHourStart(20);
            lessonEighth.setMinuteStart(10);
            lessonEighth.setHourEnd(21);
            lessonEighth.setMinuteEnd(30);
            lessonTimeRepository.save(lessonEighth);
            log.info("Init lesson eighth");
        } else {
            log.info("No need to init lesson eighth");
        }

        LessonTime lessonFirstCollegeCre = lessonTimeRepository.findByLessonNumber(11);
        if (lessonFirstCollegeCre == null) {
            lessonFirstCollegeCre = new LessonTime();
            lessonFirstCollegeCre.setLessonNumber(11);
            lessonFirstCollegeCre.setHourStart(8);
            lessonFirstCollegeCre.setMinuteStart(45);
            lessonFirstCollegeCre.setHourEnd(10);
            lessonFirstCollegeCre.setMinuteEnd(15);
            lessonTimeRepository.save(lessonFirstCollegeCre);
            log.info("Init lesson first CollegeCre");
        } else {
            log.info("No need to init lesson first CollegeCre");
        }

        LessonTime lessonSecondCollegeCre = lessonTimeRepository.findByLessonNumber(22);
        if (lessonSecondCollegeCre == null) {
            lessonSecondCollegeCre = new LessonTime();
            lessonSecondCollegeCre.setLessonNumber(22);
            lessonSecondCollegeCre.setHourStart(10);
            lessonSecondCollegeCre.setMinuteStart(25);
            lessonSecondCollegeCre.setHourEnd(11);
            lessonSecondCollegeCre.setMinuteEnd(55);
            lessonTimeRepository.save(lessonSecondCollegeCre);
            log.info("Init lesson second CollegeCre");
        } else {
            log.info("No need to init lesson second CollegeCre");
        }

        LessonTime lessonThirdCollegeCre = lessonTimeRepository.findByLessonNumber(33);
        if (lessonThirdCollegeCre == null) {
            lessonThirdCollegeCre = new LessonTime();
            lessonThirdCollegeCre.setLessonNumber(33);
            lessonThirdCollegeCre.setHourStart(12);
            lessonThirdCollegeCre.setMinuteStart(25);
            lessonThirdCollegeCre.setHourEnd(13);
            lessonThirdCollegeCre.setMinuteEnd(55);
            lessonTimeRepository.save(lessonThirdCollegeCre);
            log.info("Init lesson third CollegeCre");
        } else {
            log.info("No need to init lesson third CollegeCre");
        }

        LessonTime lessonFourthCollegeCre = lessonTimeRepository.findByLessonNumber(44);
        if (lessonFourthCollegeCre == null) {
            lessonFourthCollegeCre = new LessonTime();
            lessonFourthCollegeCre.setLessonNumber(44);
            lessonFourthCollegeCre.setHourStart(14);
            lessonFourthCollegeCre.setMinuteStart(5);
            lessonFourthCollegeCre.setHourEnd(15);
            lessonFourthCollegeCre.setMinuteEnd(35);
            lessonTimeRepository.save(lessonFourthCollegeCre);
            log.info("Init lesson fourth CollegeCre");
        } else {
            log.info("No need to init lesson fourth CollegeCre");
        }

        LessonTime lessonFifthCollegeCre = lessonTimeRepository.findByLessonNumber(55);
        if (lessonFifthCollegeCre == null) {
            lessonFifthCollegeCre = new LessonTime();
            lessonFifthCollegeCre.setLessonNumber(55);
            lessonFifthCollegeCre.setHourStart(15);
            lessonFifthCollegeCre.setMinuteStart(45);
            lessonFifthCollegeCre.setHourEnd(17);
            lessonFifthCollegeCre.setMinuteEnd(15);
            lessonTimeRepository.save(lessonFifthCollegeCre);
            log.info("Init lesson fifth CollegeCre");
        } else {
            log.info("No need to init lesson fifth CollegeCre");
        }

        LessonTime lessonFirstCollegeKgl = lessonTimeRepository.findByLessonNumber(101);
        if (lessonFirstCollegeKgl == null) {
            lessonFirstCollegeKgl = new LessonTime();
            lessonFirstCollegeKgl.setLessonNumber(101);
            lessonFirstCollegeKgl.setHourStart(9);
            lessonFirstCollegeKgl.setMinuteStart(0);
            lessonFirstCollegeKgl.setHourEnd(10);
            lessonFirstCollegeKgl.setMinuteEnd(30);
            lessonTimeRepository.save(lessonFirstCollegeKgl);
            log.info("Init lesson first CollegeKgl");
        } else {
            log.info("No need to init lesson first CollegeKgl");
        }

        LessonTime lessonSecondCollegeKgl = lessonTimeRepository.findByLessonNumber(202);
        if (lessonSecondCollegeKgl == null) {
            lessonSecondCollegeKgl = new LessonTime();
            lessonSecondCollegeKgl.setLessonNumber(202);
            lessonSecondCollegeKgl.setHourStart(10);
            lessonSecondCollegeKgl.setMinuteStart(40);
            lessonSecondCollegeKgl.setHourEnd(12);
            lessonSecondCollegeKgl.setMinuteEnd(10);
            lessonTimeRepository.save(lessonSecondCollegeKgl);
            log.info("Init lesson second CollegeKgl");
        } else {
            log.info("No need to init lesson second CollegeKgl");
        }

        LessonTime lessonThirdCollegeKgl = lessonTimeRepository.findByLessonNumber(303);
        if (lessonThirdCollegeKgl == null) {
            lessonThirdCollegeKgl = new LessonTime();
            lessonThirdCollegeKgl.setLessonNumber(303);
            lessonThirdCollegeKgl.setHourStart(12);
            lessonThirdCollegeKgl.setMinuteStart(30);
            lessonThirdCollegeKgl.setHourEnd(14);
            lessonThirdCollegeKgl.setMinuteEnd(0);
            lessonTimeRepository.save(lessonThirdCollegeKgl);
            log.info("Init lesson third CollegeKgl");
        } else {
            log.info("No need to init lesson third CollegeKgl");
        }

        LessonTime lessonFourthCollegeKgl = lessonTimeRepository.findByLessonNumber(404);
        if (lessonFourthCollegeKgl == null) {
            lessonFourthCollegeKgl = new LessonTime();
            lessonFourthCollegeKgl.setLessonNumber(404);
            lessonFourthCollegeKgl.setHourStart(14);
            lessonFourthCollegeKgl.setMinuteStart(10);
            lessonFourthCollegeKgl.setHourEnd(15);
            lessonFourthCollegeKgl.setMinuteEnd(40);
            lessonTimeRepository.save(lessonFourthCollegeKgl);
            log.info("Init lesson fourth CollegeKgl");
        } else {
            log.info("No need to init lesson fourth CollegeKgl");
        }

        LessonTime lessonFifthCollegeKgl = lessonTimeRepository.findByLessonNumber(505);
        if (lessonFifthCollegeKgl == null) {
            lessonFifthCollegeKgl = new LessonTime();
            lessonFifthCollegeKgl.setLessonNumber(505);
            lessonFifthCollegeKgl.setHourStart(15);
            lessonFifthCollegeKgl.setMinuteStart(50);
            lessonFifthCollegeKgl.setHourEnd(17);
            lessonFifthCollegeKgl.setMinuteEnd(20);
            lessonTimeRepository.save(lessonFifthCollegeKgl);
            log.info("Init lesson fifth CollegeKgl");
        } else {
            log.info("No need to init lesson fifth CollegeKgl");
        }

        LessonTime lessonSixthCollegeKgl = lessonTimeRepository.findByLessonNumber(606);
        if (lessonSixthCollegeKgl == null) {
            lessonSixthCollegeKgl = new LessonTime();
            lessonSixthCollegeKgl.setLessonNumber(606);
            lessonSixthCollegeKgl.setHourStart(17);
            lessonSixthCollegeKgl.setMinuteStart(30);
            lessonSixthCollegeKgl.setHourEnd(19);
            lessonSixthCollegeKgl.setMinuteEnd(0);
            lessonTimeRepository.save(lessonSixthCollegeKgl);
            log.info("Init lesson sixth CollegeKgl");
        } else {
            log.info("No need to init lesson sixth CollegeKgl");
        }

        LessonTime lessonSevethCollegeKgl = lessonTimeRepository.findByLessonNumber(707);
        if (lessonSevethCollegeKgl == null) {
            lessonSevethCollegeKgl = new LessonTime();
            lessonSevethCollegeKgl.setLessonNumber(707);
            lessonSevethCollegeKgl.setHourStart(19);
            lessonSevethCollegeKgl.setMinuteStart(10);
            lessonSevethCollegeKgl.setHourEnd(20);
            lessonSevethCollegeKgl.setMinuteEnd(40);
            lessonTimeRepository.save(lessonSevethCollegeKgl);
            log.info("Init lesson seventh CollegeKgl");
        } else {
            log.info("No need to init lesson seventh CollegeKgl");
        }

        log.info("End init lesson times");
    }

    private void initDepartments() {
        log.info("Start init departments");
        Department dep = departmentRepository.findByUrl("bf");
        if (dep == null) {
            Department biofac = new Department();
            biofac.setFullName("Биологический факультет");
            biofac.setShortName("БИОЛОГ Ф-Т");
            biofac.setUrl("bf");
            biofac.setWeekShift(false);
            departmentRepository.save(biofac);
            log.info("Init bf");
        } else {
            log.info("No need to init bf");
        }

        dep = departmentRepository.findByUrl("gf");
        if (dep == null) {
            Department geofac = new Department();
            geofac.setFullName("Географический факультет");
            geofac.setShortName("ГЕОГРАФ Ф-Т");
            geofac.setUrl("gf");
            geofac.setWeekShift(false);
            departmentRepository.save(geofac);
            log.info("Init gf");
        } else {
            log.info("No need to init gf");
        }

        dep = departmentRepository.findByUrl("gl");
        if (dep == null) {
            Department geolfac = new Department();
            geolfac.setFullName("Геологический факультет");
            geolfac.setShortName("ГЕОЛОГ Ф-Т");
            geolfac.setUrl("gl");
            geolfac.setWeekShift(false);
            departmentRepository.save(geolfac);
            log.info("Init gl");
        } else {
            log.info("No need to init gl");
        }

        dep = departmentRepository.findByUrl("idpo");
        if (dep == null) {
            Department idpo = new Department();
            idpo.setFullName("Институт дополнительного профессионального образования");
            idpo.setShortName("ИДПО");
            idpo.setUrl("idpo");
            idpo.setWeekShift(false);
            departmentRepository.save(idpo);
            log.info("Init idpo");
        } else {
            log.info("No need to init idpo");
        }

        dep = departmentRepository.findByUrl("ii");
        if (dep == null) {
            Department instisk = new Department();
            instisk.setFullName("Институт искусств");
            instisk.setShortName("И-Т ИСКУССТВ");
            instisk.setUrl("ii");
            instisk.setWeekShift(false);
            departmentRepository.save(instisk);
            log.info("Init ii");
        } else {
            log.info("No need to init ii");
        }

        dep = departmentRepository.findByUrl("imo");
        if (dep == null) {
            Department iimo = new Department();
            iimo.setFullName("Институт истории и международных отношений");
            iimo.setShortName("ИИИМО");
            iimo.setUrl("imo");
            iimo.setWeekShift(false);
            departmentRepository.save(iimo);
            log.info("Init imo");
        } else {
            log.info("No need to init imo");
        }

        dep = departmentRepository.findByUrl("ff");
        if (dep == null) {
            Department phys = new Department();
            phys.setFullName("Институт физики");
            phys.setShortName("И-Т ФИЗИКИ");
            phys.setUrl("ff");
            phys.setWeekShift(false);
            departmentRepository.save(phys);
            log.info("Init ff");
        } else {
            log.info("No need to init ff");
        }

        dep = departmentRepository.findByUrl("ifk");
        if (dep == null) {
            Department ifkis = new Department();
            ifkis.setFullName("Институт физической культуры и спорта");
            ifkis.setShortName("И-Т ФИЗ КУЛ");
            ifkis.setUrl("ifk");
            ifkis.setWeekShift(false);
            departmentRepository.save(ifkis);
            log.info("Init ifk");
        } else {
            log.info("No need to init ifk");
        }

        dep = departmentRepository.findByUrl("ifg");
        if (dep == null) {
            Department philological = new Department();
            philological.setFullName("Институт филологии и журналистики");
            philological.setShortName("ИФИЖ");
            philological.setUrl("ifg");
            philological.setWeekShift(false);
            departmentRepository.save(philological);
            log.info("Init ifg");
        } else {
            log.info("No need to init ifg");
        }

        dep = departmentRepository.findByUrl("ih");
        if (dep == null) {
            Department chemical = new Department();
            chemical.setFullName("Институт химии");
            chemical.setShortName("И-Т ХИМИИ");
            chemical.setUrl("ih");
            chemical.setWeekShift(false);
            departmentRepository.save(chemical);
            log.info("Init ih");
        } else {
            log.info("No need to init ih");
        }

        dep = departmentRepository.findByUrl("mm");
        if (dep == null) {
            Department mechmath = new Department();
            mechmath.setFullName("Механико-математический факультет");
            mechmath.setShortName("МЕХМАТ");
            mechmath.setUrl("mm");
            mechmath.setWeekShift(false);
            departmentRepository.save(mechmath);
            log.info("Init mm");
        } else {
            log.info("No need to init mm");
        }

        dep = departmentRepository.findByUrl("sf");
        if (dep == null) {
            Department sociological = new Department();
            sociological.setFullName("Социологический факультет");
            sociological.setShortName("СОЦ Ф-Т");
            sociological.setUrl("sf");
            sociological.setWeekShift(false);
            departmentRepository.save(sociological);
            log.info("Init sf");
        } else {
            log.info("No need to init sf");
        }

        dep = departmentRepository.findByUrl("fi");
        if (dep == null) {
            Department fi = new Department();
            fi.setFullName("Факультет иностранных языков и лингводидактики");
            fi.setShortName("ИН ЯЗ Ф-Т");
            fi.setUrl("fi");
            fi.setWeekShift(false);
            departmentRepository.save(fi);
            log.info("Init fi");
        } else {
            log.info("No need to init fi");
        }

        dep = departmentRepository.findByUrl("knt");
        if (dep == null) {
            Department csit = new Department();
            csit.setFullName("Факультет компьютерных наук и информационных технологий");
            csit.setShortName("КНИИТ");
            csit.setUrl("knt");
            csit.setWeekShift(false);
            departmentRepository.save(csit);
            log.info("Init knt");
        } else {
            log.info("No need to init knt");
        }

        dep = departmentRepository.findByUrl("fmimt");
        if (dep == null) {
            Department fmimt = new Department();
            fmimt.setFullName("Факультет фундаментальной медицины и медицинских технологий");
            fmimt.setShortName("ФФМИМТ");
            fmimt.setUrl("fmimt");
            fmimt.setWeekShift(false);
            departmentRepository.save(fmimt);
            log.info("Init fmimt");
        } else {
            log.info("No need to init fmimt");
        }

        dep = departmentRepository.findByUrl("fps");
        if (dep == null) {
            Department psych = new Department();
            psych.setFullName("Факультет психологии");
            psych.setShortName("ПСИХОЛОГ Ф-Т");
            psych.setUrl("fps");
            psych.setWeekShift(false);
            departmentRepository.save(psych);
            log.info("Init fps");
        } else {
            log.info("No need to init fps");
        }

        dep = departmentRepository.findByUrl("fppso");
        if (dep == null) {
            Department fppiso = new Department();
            fppiso.setFullName("Факультет психолого-педагогического и специального образования");
            fppiso.setShortName("ППИСО");
            fppiso.setUrl("fppso");
            fppiso.setWeekShift(false);
            departmentRepository.save(fppiso);
            log.info("Init fppso");
        } else {
            log.info("No need to init fppso");
        }

        dep = departmentRepository.findByUrl("fp");
        if (dep == null) {
            Department philosophic = new Department();
            philosophic.setFullName("Философский факультет");
            philosophic.setShortName("ФИЛОСОФ Ф-Т");
            philosophic.setUrl("fp");
            philosophic.setWeekShift(false);
            departmentRepository.save(philosophic);
            log.info("Init fp");
        } else {
            log.info("No need to init fp");
        }

        dep = departmentRepository.findByUrl("ef");
        if (dep == null) {
            Department economy = new Department();
            economy.setFullName("Экономический факультет");
            economy.setShortName("ЭКОНОМ Ф-Т");
            economy.setUrl("ef");
            economy.setWeekShift(false);
            departmentRepository.save(economy);
            log.info("Init ef");
        } else {
            log.info("No need to init ef");
        }

        dep = departmentRepository.findByUrl("uf");
        if (dep == null) {
            Department jurist = new Department();
            jurist.setFullName("Юридический факультет");
            jurist.setShortName("ЮРФАК");
            jurist.setUrl("uf");
            jurist.setWeekShift(false);
            departmentRepository.save(jurist);
            log.info("Init uf");
        } else {
            log.info("No need to init uf");
        }

        dep = departmentRepository.findByUrl("kgl");
        if (dep == null) {
            Department geolCollege = new Department();
            geolCollege.setFullName("Геологический колледж");
            geolCollege.setShortName("ГЕОЛОГ К-Ж");
            geolCollege.setUrl("kgl");
            geolCollege.setWeekShift(false);
            departmentRepository.save(geolCollege);
            log.info("Init kgl");
        } else {
            log.info("No need to init kgl");
        }

        dep = departmentRepository.findByUrl("cre");
        if (dep == null) {
            Department yablCollege = new Department();
            yablCollege.setFullName("Колледж радиоэлектроники им. П.Н. Яблочкова");
            yablCollege.setShortName("К-Ж ЯБЛОЧКОВА");
            yablCollege.setUrl("cre");
            yablCollege.setWeekShift(false);
            departmentRepository.save(yablCollege);
            log.info("Init cre");
        } else {
            log.info("No need to init cre");
        }

        log.info("End init departments");
    }

    private void initExamPeriodMonths() {
        log.info("Start init exam period months");
        Optional<ExamPeriodMonth> monthOpt = examPeriodMonthRepository.findByNumber(1);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(1);
            month.setEnglish("january");
            month.setRusNominative("январь");
            month.setRusGenitive("января");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(2);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(2);
            month.setEnglish("february");
            month.setRusNominative("февраль");
            month.setRusGenitive("февраля");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(3);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(3);
            month.setEnglish("march");
            month.setRusNominative("март");
            month.setRusGenitive("марта");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(4);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(4);
            month.setEnglish("april");
            month.setRusNominative("апрель");
            month.setRusGenitive("апреля");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(5);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(5);
            month.setEnglish("may");
            month.setRusNominative("май");
            month.setRusGenitive("мая");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(6);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(6);
            month.setEnglish("june");
            month.setRusNominative("июнь");
            month.setRusGenitive("июня");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(7);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(7);
            month.setEnglish("jule");
            month.setRusNominative("июль");
            month.setRusGenitive("июля");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(8);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(8);
            month.setEnglish("august");
            month.setRusNominative("август");
            month.setRusGenitive("августа");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(9);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(9);
            month.setEnglish("september");
            month.setRusNominative("сентябрь");
            month.setRusGenitive("сентября");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(10);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(10);
            month.setEnglish("october");
            month.setRusNominative("октябрь");
            month.setRusGenitive("октября");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(11);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(11);
            month.setEnglish("november");
            month.setRusNominative("ноябрь");
            month.setRusGenitive("ноября");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(12);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(12);
            month.setEnglish("december");
            month.setRusNominative("декабрь");
            month.setRusGenitive("декабря");
            examPeriodMonthRepository.save(month);
        }

        monthOpt = examPeriodMonthRepository.findByNumber(0);

        if (monthOpt.isEmpty()) {
            ExamPeriodMonth month = new ExamPeriodMonth();
            month.setNumber(0);
            month.setEnglish(" ");
            month.setRusNominative(" ");
            month.setRusGenitive(" ");
            examPeriodMonthRepository.save(month);
        }

        log.info("End init exam period months");
    }
}
