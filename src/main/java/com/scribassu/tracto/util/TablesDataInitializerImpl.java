package com.scribassu.tracto.util;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.LessonTime;
import com.scribassu.tracto.domain.WeekDay;
import com.scribassu.tracto.repository.DayRepository;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.LessonTimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TablesDataInitializerImpl implements TablesDataInitializer {

    private final DayRepository dayRepository;

    private final LessonTimeRepository lessonTimeRepository;

    private final DepartmentRepository departmentRepository;

    @Autowired
    public TablesDataInitializerImpl(DayRepository dayRepository,
                                     LessonTimeRepository lessonTimeRepository,
                                     DepartmentRepository departmentRepository) {
        this.dayRepository = dayRepository;
        this.lessonTimeRepository = lessonTimeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void init() {
        initDays();
        initTimes();
        initDepartments();
    }

    private void initDays() {
        log.info("Start init days");
        Day monday = dayRepository.findByDayNumber(1);
        if(monday == null) {
            monday = new Day();
            monday.setDayNumber(1);
            monday.setWeekDay(WeekDay.MONDAY);
            dayRepository.save(monday);
            log.info("Init monday");
        } else {
            log.info("No need to init monday");
        }

        Day tuesday = dayRepository.findByDayNumber(2);
        if(tuesday == null) {
            tuesday = new Day();
            tuesday.setDayNumber(2);
            tuesday.setWeekDay(WeekDay.TUESDAY);
            dayRepository.save(tuesday);
            log.info("Init tuesday");
        } else {
            log.info("No need to init tuesday");
        }

        Day wednesday = dayRepository.findByDayNumber(3);
        if(wednesday == null) {
            wednesday = new Day();
            wednesday.setDayNumber(3);
            wednesday.setWeekDay(WeekDay.WEDNESDAY);
            dayRepository.save(wednesday);
            log.info("Init wednesday");
        } else {
            log.info("No need to init wednesday");
        }

        Day thursday = dayRepository.findByDayNumber(4);
        if(thursday == null) {
            thursday = new Day();
            thursday.setDayNumber(4);
            thursday.setWeekDay(WeekDay.THURSDAY);
            dayRepository.save(thursday);
            log.info("Init thursday");
        } else {
            log.info("No need to init thursday");
        }

        Day friday = dayRepository.findByDayNumber(5);
        if(friday == null) {
            friday = new Day();
            friday.setDayNumber(5);
            friday.setWeekDay(WeekDay.FRIDAY);
            dayRepository.save(friday);
            log.info("Init friday");
        } else {
            log.info("No need to init friday");
        }

        Day saturday = dayRepository.findByDayNumber(6);
        if(saturday == null) {
            saturday = new Day();
            saturday.setDayNumber(6);
            saturday.setWeekDay(WeekDay.SATURDAY);
            dayRepository.save(saturday);
            log.info("Init saturday");
        } else {
            log.info("No need to init saturday");
        }

        Day sunday = dayRepository.findByDayNumber(7);
        if(sunday == null) {
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
        if(lessonFirst == null) {
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
        if(lessonSecond == null) {
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
        if(lessonThird == null) {
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
        if(lessonFourth == null) {
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
        if(lessonFifth == null) {
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
        if(lessonSix == null) {
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
        if(lessonSeventh == null) {
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
        if(lessonEighth == null) {
            lessonEighth = new LessonTime();
            lessonEighth.setLessonNumber(8);
            lessonEighth.setHourStart(20);
            lessonEighth.setMinuteStart(10);
            lessonEighth.setHourEnd(21);
            lessonEighth.setHourEnd(30);
            lessonTimeRepository.save(lessonEighth);
            log.info("Init lesson eighth");
        } else {
            log.info("No need to init lesson eighth");
        }

        LessonTime lessonFirstCollege = lessonTimeRepository.findByLessonNumber(11);
        if(lessonFirstCollege == null) {
            lessonFirstCollege = new LessonTime();
            lessonFirstCollege.setLessonNumber(11);
            lessonFirstCollege.setHourStart(8);
            lessonFirstCollege.setMinuteStart(45);
            lessonFirstCollege.setHourEnd(10);
            lessonFirstCollege.setMinuteEnd(15);
            lessonTimeRepository.save(lessonFirstCollege);
            log.info("Init lesson first college");
        } else {
            log.info("No need to init lesson first college");
        }

        LessonTime lessonSecondCollege = lessonTimeRepository.findByLessonNumber(22);
        if(lessonSecondCollege == null) {
            lessonSecondCollege = new LessonTime();
            lessonSecondCollege.setLessonNumber(22);
            lessonSecondCollege.setHourStart(10);
            lessonSecondCollege.setMinuteStart(25);
            lessonSecondCollege.setHourEnd(11);
            lessonSecondCollege.setMinuteEnd(55);
            lessonTimeRepository.save(lessonSecondCollege);
            log.info("Init lesson second college");
        } else {
            log.info("No need to init lesson second college");
        }

        LessonTime lessonThirdCollege = lessonTimeRepository.findByLessonNumber(33);
        if(lessonThirdCollege == null) {
            lessonThirdCollege = new LessonTime();
            lessonThirdCollege.setLessonNumber(33);
            lessonThirdCollege.setHourStart(12);
            lessonThirdCollege.setMinuteStart(25);
            lessonThirdCollege.setHourEnd(13);
            lessonThirdCollege.setMinuteEnd(55);
            lessonTimeRepository.save(lessonThirdCollege);
            log.info("Init lesson third college");
        } else {
            log.info("No need to init lesson third college");
        }

        LessonTime lessonFourthCollege = lessonTimeRepository.findByLessonNumber(44);
        if(lessonFourthCollege == null) {
            lessonFourthCollege = new LessonTime();
            lessonFourthCollege.setLessonNumber(44);
            lessonFourthCollege.setHourStart(14);
            lessonFourthCollege.setMinuteStart(5);
            lessonFourthCollege.setHourEnd(15);
            lessonFourthCollege.setMinuteEnd(35);
            lessonTimeRepository.save(lessonFourthCollege);
            log.info("Init lesson fourth college");
        } else {
            log.info("No need to init lesson fourth college");
        }

        LessonTime lessonFifthCollege = lessonTimeRepository.findByLessonNumber(55);
        if(lessonFifthCollege == null) {
            lessonFifthCollege = new LessonTime();
            lessonFifthCollege.setLessonNumber(55);
            lessonFifthCollege.setHourStart(15);
            lessonFifthCollege.setMinuteStart(45);
            lessonFifthCollege.setHourEnd(17);
            lessonFifthCollege.setMinuteEnd(15);
            lessonTimeRepository.save(lessonFifthCollege);
            log.info("Init lesson fifth college");
        } else {
            log.info("No need to init lesson fifth college");
        }

        log.info("End init lesson times");
    }

    private void initDepartments() {
        log.info("Start init departments");
        Department dep = departmentRepository.findByURL("bf");
        if(dep == null) {
            Department biofac = new Department();
            biofac.setFullName("Биологический факультет");
            biofac.setShortName("БИОЛОГ Ф-Т");
            biofac.setURL("bf");
            departmentRepository.save(biofac);
            log.info("Init bf");
        } else {
            log.info("No need to init bf");
        }

        dep = departmentRepository.findByURL("gf");
        if(dep == null) {
            Department geofac = new Department();
            geofac.setFullName("Географический факультет");
            geofac.setShortName("ГЕОГРАФ Ф-Т");
            geofac.setURL("gf");
            departmentRepository.save(geofac);
            log.info("Init gf");
        } else {
            log.info("No need to init gf");
        }

        dep = departmentRepository.findByURL("gl");
        if(dep == null) {
            Department geolfac = new Department();
            geolfac.setFullName("Геологический факультет");
            geolfac.setShortName("ГЕОЛОГ Ф-Т");
            geolfac.setURL("gl");
            departmentRepository.save(geolfac);
            log.info("Init gl");
        } else {
            log.info("No need to init gl");
        }

        dep = departmentRepository.findByURL("idpo");
        if(dep == null) {
            Department idpo = new Department();
            idpo.setFullName("Институт дополнительного профессионального образования");
            idpo.setShortName("ИДПО");
            idpo.setURL("idpo");
            departmentRepository.save(idpo);
            log.info("Init idpo");
        } else {
            log.info("No need to init idpo");
        }

        dep = departmentRepository.findByURL("ii");
        if(dep == null) {
            Department instisk = new Department();
            instisk.setFullName("Институт искусств");
            instisk.setShortName("И-Т ИСКУССТВ");
            instisk.setURL("ii");
            departmentRepository.save(instisk);
            log.info("Init ii");
        } else {
            log.info("No need to init ii");
        }

        dep = departmentRepository.findByURL("imo");
        if(dep == null) {
            Department iimo = new Department();
            iimo.setFullName("Институт истории и международных отношений");
            iimo.setShortName("ИИИМО");
            iimo.setURL("imo");
            departmentRepository.save(iimo);
            log.info("Init imo");
        } else {
            log.info("No need to init imo");
        }

        dep = departmentRepository.findByURL("ifk");
        if(dep == null) {
            Department ifkis = new Department();
            ifkis.setFullName("Институт физической культуры и спорта");
            ifkis.setShortName("И-Т ФИЗ КУЛ");
            ifkis.setURL("ifk");
            departmentRepository.save(ifkis);
            log.info("Init ifk");
        } else {
            log.info("No need to init ifk");
        }

        dep = departmentRepository.findByURL("ifg");
        if(dep == null) {
            Department philological = new Department();
            philological.setFullName("Институт филологии и журналистики");
            philological.setShortName("ИФИЖ");
            philological.setURL("ifg");
            departmentRepository.save(philological);
            log.info("Init ifg");
        } else {
            log.info("No need to init ifg");
        }

        dep = departmentRepository.findByURL("ih");
        if(dep == null) {
            Department chemical = new Department();
            chemical.setFullName("Институт химии");
            chemical.setShortName("И-Т ХИМИИ");
            chemical.setURL("ih");
            departmentRepository.save(chemical);
            log.info("Init ih");
        } else {
            log.info("No need to init ih");
        }

        dep = departmentRepository.findByURL("mm");
        if(dep == null) {
            Department mechmath = new Department();
            mechmath.setFullName("Механико-математический факультет");
            mechmath.setShortName("МЕХМАТ");
            mechmath.setURL("mm");
            departmentRepository.save(mechmath);
            log.info("Init mm");
        } else {
            log.info("No need to init mm");
        }

        dep = departmentRepository.findByURL("sf");
        if(dep == null) {
            Department sociological = new Department();
            sociological.setFullName("Социологический факультет");
            sociological.setShortName("СОЦ Ф-Т");
            sociological.setURL("sf");
            departmentRepository.save(sociological);
            log.info("Init sf");
        } else {
            log.info("No need to init sf");
        }

        dep = departmentRepository.findByURL("fi");
        if(dep == null) {
            Department fi = new Department();
            fi.setFullName("Факультет иностранных языков и лингводидактики");
            fi.setShortName("ИН ЯЗ Ф-Т");
            fi.setURL("fi");
            departmentRepository.save(fi);
            log.info("Init fi");
        } else {
            log.info("No need to init fi");
        }

        dep = departmentRepository.findByURL("knt");
        if(dep == null) {
            Department csit = new Department();
            csit.setFullName("Факультет компьютерных наук и информационных технологий");
            csit.setShortName("КНИИТ");
            csit.setURL("knt");
            departmentRepository.save(csit);
            log.info("Init knt");
        } else {
            log.info("No need to init knt");
        }

        dep = departmentRepository.findByURL("fn");
        if(dep == null) {
            Department nbmt = new Department();
            nbmt.setFullName("Факультет нано- и биомедицинских технологий");
            nbmt.setShortName("ФНБМТ");
            nbmt.setURL("fn");
            departmentRepository.save(nbmt);
            log.info("Init fn");
        } else {
            log.info("No need to init fn");
        }

        dep = departmentRepository.findByURL("fnp");
        if(dep == null) {
            Department fnp = new Department();
            fnp.setFullName("Факультет нелинейных процессов");
            fnp.setShortName("ФНП");
            fnp.setURL("fnp");
            departmentRepository.save(fnp);
            log.info("Init fnp");
        } else {
            log.info("No need to init fnp");
        }

        dep = departmentRepository.findByURL("fps");
        if(dep == null) {
            Department psych = new Department();
            psych.setFullName("Факультет психологии");
            psych.setShortName("ПСИХОЛОГ Ф-Т");
            psych.setURL("fps");
            departmentRepository.save(psych);
            log.info("Init fps");
        } else {
            log.info("No need to init fps");
        }

        dep = departmentRepository.findByURL("fppso");
        if(dep == null) {
            Department fppiso = new Department();
            fppiso.setFullName("Факультет психолого-педагогического и специального образования");
            fppiso.setShortName("ППИСО");
            fppiso.setURL("fppso");
            departmentRepository.save(fppiso);
            log.info("Init fppso");
        } else {
            log.info("No need to init fppso");
        }

        dep = departmentRepository.findByURL("ff");
        if(dep == null) {
            Department phys = new Department();
            phys.setFullName("Физический факультет");
            phys.setShortName("ФИЗФАК");
            phys.setURL("ff");
            departmentRepository.save(phys);
            log.info("Init ff");
        } else {
            log.info("No need to init ff");
        }

        dep = departmentRepository.findByURL("fp");
        if(dep == null) {
            Department philosophic = new Department();
            philosophic.setFullName("Философский факультет");
            philosophic.setShortName("ФИЛОСОФ Ф-Т");
            philosophic.setURL("fp");
            departmentRepository.save(philosophic);
            log.info("Init fp");
        } else {
            log.info("No need to init fp");
        }

        dep = departmentRepository.findByURL("ef");
        if(dep == null) {
            Department economy = new Department();
            economy.setFullName("Экономический факультет");
            economy.setShortName("ЭКОНОМ Ф-Т");
            economy.setURL("ef");
            departmentRepository.save(economy);
            log.info("Init ef");
        } else {
            log.info("No need to init ef");
        }

        dep = departmentRepository.findByURL("uf");
        if(dep == null) {
            Department jurist = new Department();
            jurist.setFullName("Юридический факультет");
            jurist.setShortName("ЮРФАК");
            jurist.setURL("uf");
            departmentRepository.save(jurist);
            log.info("Init uf");
        } else {
            log.info("No need to init uf");
        }

        dep = departmentRepository.findByURL("kgl");
        if(dep == null) {
            Department geolCollege = new Department();
            geolCollege.setFullName("Геологический колледж");
            geolCollege.setShortName("ГЕОЛОГ К-Ж");
            geolCollege.setURL("kgl");
            departmentRepository.save(geolCollege);
            log.info("Init kgl");
        } else {
            log.info("No need to init kgl");
        }

        dep = departmentRepository.findByURL("cre");
        if(dep == null) {
            Department yablCollege = new Department();
            yablCollege.setFullName("Колледж радиоэлектроники им. П.Н. Яблочкова");
            yablCollege.setShortName("К-Ж ЯБЛОЧКОВА");
            yablCollege.setURL("cre");
            departmentRepository.save(yablCollege);
            log.info("Init cre");
        } else {
            log.info("No need to init cre");
        }

        log.info("End init departments");
    }
}
