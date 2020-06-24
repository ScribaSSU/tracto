package com.scribassu.tracto.util;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.LessonTime;
import com.scribassu.tracto.domain.WeekDay;
import com.scribassu.tracto.repository.DayRepository;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.LessonTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TablesDataInitializerImpl implements TablesDataInitializer {

    private final DayRepository dayRepository;
    private  final LessonTimeRepository lessonTimeRepository;
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
    
    private void initDays(){
        Day monday = dayRepository.findByDayNumber(1);
        if(monday == null) {
            monday = new Day();
            monday.setDayNumber(1);
            monday.setWeekDay(WeekDay.MONDAY);
            dayRepository.save(monday);
        }

        Day tuesday = dayRepository.findByDayNumber(2);
        if(tuesday == null) {
            tuesday = new Day();
            tuesday.setDayNumber(2);
            tuesday.setWeekDay(WeekDay.TUESDAY);
            dayRepository.save(tuesday);
        }

        Day wednesday = dayRepository.findByDayNumber(3);
        if(wednesday == null) {
            wednesday = new Day();
            wednesday.setDayNumber(3);
            wednesday.setWeekDay(WeekDay.WEDNESDAY);
            dayRepository.save(wednesday);
        }

        Day thursday = dayRepository.findByDayNumber(4);
        if(thursday == null) {
            thursday = new Day();
            thursday.setDayNumber(4);
            thursday.setWeekDay(WeekDay.THURSDAY);
            dayRepository.save(thursday);
        }

        Day friday = dayRepository.findByDayNumber(5);
        if(friday == null) {
            friday = new Day();
            friday.setDayNumber(5);
            friday.setWeekDay(WeekDay.FRIDAY);
            dayRepository.save(friday);
        }

        Day saturday = dayRepository.findByDayNumber(6);
        if(saturday == null) {
            saturday = new Day();
            saturday.setDayNumber(6);
            saturday.setWeekDay(WeekDay.SATURDAY);
            dayRepository.save(saturday);
        }

        Day sunday = dayRepository.findByDayNumber(7);
        if(sunday == null) {
            sunday = new Day();
            sunday.setDayNumber(7);
            sunday.setWeekDay(WeekDay.SUNDAY);
            dayRepository.save(sunday);
        }
    }

    private void initTimes() {
        LessonTime lessonFirst = lessonTimeRepository.findByLessonNumber(1);
        if(lessonFirst == null) {
            lessonFirst = new LessonTime();
            lessonFirst.setLessonNumber(1);
            lessonFirst.setTimeStart("8:20");
            lessonFirst.setTimeFinish("09:50");
            lessonTimeRepository.save(lessonFirst);
        }

        LessonTime lessonSecond = lessonTimeRepository.findByLessonNumber(2);
        if(lessonSecond == null) {
            lessonSecond = new LessonTime();
            lessonSecond.setLessonNumber(2);
            lessonSecond.setTimeStart("10:00");
            lessonSecond.setTimeFinish("11:35");
            lessonTimeRepository.save(lessonSecond);
        }

        LessonTime lessonThird = lessonTimeRepository.findByLessonNumber(3);
        if(lessonThird == null) {
            lessonThird = new LessonTime();
            lessonThird.setLessonNumber(3);
            lessonThird.setTimeStart("12:05");
            lessonThird.setTimeFinish("13:40");
            lessonTimeRepository.save(lessonThird);
        }

        LessonTime lessonFourth = lessonTimeRepository.findByLessonNumber(4);
        if(lessonFourth == null) {
            lessonFourth = new LessonTime();
            lessonFourth.setLessonNumber(4);
            lessonFourth.setTimeStart("13:50");
            lessonFourth.setTimeFinish("15:25");
            lessonTimeRepository.save(lessonFourth);
        }

        LessonTime lessonFifth = lessonTimeRepository.findByLessonNumber(5);
        if(lessonFifth == null) {
            lessonFifth = new LessonTime();
            lessonFifth.setLessonNumber(5);
            lessonFifth.setTimeStart("15:35");
            lessonFifth.setTimeFinish("17:10");
            lessonTimeRepository.save(lessonFifth);
        }

        LessonTime lessonSix = lessonTimeRepository.findByLessonNumber(6);
        if(lessonSix == null) {
            lessonSix = new LessonTime();
            lessonSix.setLessonNumber(6);
            lessonSix.setTimeStart("17:20");
            lessonSix.setTimeFinish("18:40");
            lessonTimeRepository.save(lessonSix);
        }

        LessonTime lessonSeventh = lessonTimeRepository.findByLessonNumber(7);
        if(lessonSeventh == null) {
            lessonSeventh = new LessonTime();
            lessonSeventh.setLessonNumber(7);
            lessonSeventh.setTimeStart("18:45");
            lessonSeventh.setTimeFinish("20:05");
            lessonTimeRepository.save(lessonSeventh);
        }

        LessonTime lessonEighth = lessonTimeRepository.findByLessonNumber(8);
        if(lessonEighth == null) {
            lessonEighth = new LessonTime();
            lessonEighth.setLessonNumber(8);
            lessonEighth.setTimeStart("20:10");
            lessonEighth.setTimeFinish("21:30");
            lessonTimeRepository.save(lessonEighth);
            eeee
        }
    }

    private void initDepartments() {
        Department dep = departmentRepository.findByURL("bf");
        if(dep == null) {
            Department biofac = new Department();
            biofac.setFullName("Биологический факультет");
            biofac.setShortName("БИОЛОГ Ф-Т");
            biofac.setURL("bf");
            departmentRepository.save(biofac);
        }

        dep = departmentRepository.findByURL("gf");
        if(dep == null) {
            Department geofac = new Department();
            geofac.setFullName("Географический факультет");
            geofac.setShortName("ГЕОГРАФ Ф-Т");
            geofac.setURL("gf");
            departmentRepository.save(geofac);
        }

        dep = departmentRepository.findByURL("gl");
        if(dep == null) {
            Department geolfac = new Department();
            geolfac.setFullName("Геологический факультет");
            geolfac.setShortName("ГЕОЛОГ Ф-Т");
            geolfac.setURL("gl");
            departmentRepository.save(geolfac);
        }

        dep = departmentRepository.findByURL("idpo");
        if(dep == null) {
            Department idpo = new Department();
            idpo.setFullName("Институт дополнительного профессионального образования");
            idpo.setShortName("ИДПО");
            idpo.setURL("idpo");
            departmentRepository.save(idpo);
        }

        dep = departmentRepository.findByURL("ii");
        if(dep == null) {
            Department instisk = new Department();
            instisk.setFullName("Институт искусств");
            instisk.setShortName("И-Т ИСКУССТВ");
            instisk.setURL("ii");
            departmentRepository.save(instisk);
        }

        dep = departmentRepository.findByURL("imo");
        if(dep == null) {
            Department imimo = new Department();
            imimo.setFullName("Институт истории и международных отношений");
            imimo.setShortName("ИИИМО");
            imimo.setURL("imo");
            departmentRepository.save(imimo);
        }

        dep = departmentRepository.findByURL("ifk");
        if(dep == null) {
            Department ifkis = new Department();
            ifkis.setFullName("Институт физической культуры и спорта");
            ifkis.setShortName("И-Т ФИЗ КУЛ");
            ifkis.setURL("ifk");
            departmentRepository.save(ifkis);
        }

        dep = departmentRepository.findByURL("ifg");
        if(dep == null) {
            Department philological = new Department();
            philological.setFullName("Институт филологии и журналистики");
            philological.setShortName("ИФИЖ");
            philological.setURL("ifg");
            departmentRepository.save(philological);
        }

        dep = departmentRepository.findByURL("ih");
        if(dep == null) {
            Department chemical = new Department();
            chemical.setFullName("Институт химии");
            chemical.setShortName("И-Т ХИМИИ");
            chemical.setURL("ih");
            departmentRepository.save(chemical);
        }

        dep = departmentRepository.findByURL("mm");
        if(dep == null) {
            Department mechmath = new Department();
            mechmath.setFullName("Механико-математический факультет");
            mechmath.setShortName("МЕХМАТ");
            mechmath.setURL("mm");
            departmentRepository.save(mechmath);
        }

        dep = departmentRepository.findByURL("sf");
        if(dep == null) {
            Department sociological = new Department();
            sociological.setFullName("Социологический факультет");
            sociological.setShortName("СОЦ Ф-Т");
            sociological.setURL("sf");
            departmentRepository.save(sociological);
        }

        dep = departmentRepository.findByURL("fi");
        if(dep == null) {
            Department fi = new Department();
            fi.setFullName("Факультет иностранных языков и лингводидактики");
            fi.setShortName("ИН ЯЗ Ф-Т");
            fi.setURL("fi");
            departmentRepository.save(fi);
        }

        dep = departmentRepository.findByURL("knt");
        if(dep == null) {
            Department csit = new Department();
            csit.setFullName("Факультет компьютерных наук и информационных технологий");
            csit.setShortName("КНИИТ");
            csit.setURL("knt");
            departmentRepository.save(csit);
        }

        dep = departmentRepository.findByURL("fn");
        if(dep == null) {
            Department nbmt = new Department();
            nbmt.setFullName("Факультет нано- и биомедицинских технологий");
            nbmt.setShortName("ФНБМТ");
            nbmt.setURL("fn");
            departmentRepository.save(nbmt);
        }

        dep = departmentRepository.findByURL("fnp");
        if(dep == null) {
            Department fnp = new Department();
            fnp.setFullName("Факультет нелинейных процессов");
            fnp.setShortName("ФНП");
            fnp.setURL("fnp");
            departmentRepository.save(fnp);
        }

        dep = departmentRepository.findByURL("fps");
        if(dep == null) {
            Department psych = new Department();
            psych.setFullName("Факультет психологии");
            psych.setShortName("ПСИХОЛОГ Ф-Т");
            psych.setURL("fps");
            departmentRepository.save(psych);
        }

        dep = departmentRepository.findByURL("fppso");
        if(dep == null) {
            Department fppiso = new Department();
            fppiso.setFullName("Факультет психолого-педагогического и специального образования");
            fppiso.setShortName("ППИСО");
            fppiso.setURL("fppso");
            departmentRepository.save(fppiso);
        }

        dep = departmentRepository.findByURL("ff");
        if(dep == null) {
            Department phys = new Department();
            phys.setFullName("Физический факультет");
            phys.setShortName("ФИЗФАК");
            phys.setURL("ff");
            departmentRepository.save(phys);
        }

        dep = departmentRepository.findByURL("fp");
        if(dep == null) {
            Department philosophic = new Department();
            philosophic.setFullName("Философский факультет");
            philosophic.setShortName("ФИЛОСОФ Ф-Т");
            philosophic.setURL("fp");
            departmentRepository.save(philosophic);
        }

        dep = departmentRepository.findByURL("ef");
        if(dep == null) {
            Department economy = new Department();
            economy.setFullName("Экономический факультет");
            economy.setShortName("ЭКОНОМ Ф-Т");
            economy.setURL("ef");
            departmentRepository.save(economy);
        }

        dep = departmentRepository.findByURL("uf");
        if(dep == null) {
            Department jurist = new Department();
            jurist.setFullName("Юридический факультет");
            jurist.setShortName("ЮРФАК");
            jurist.setURL("uf");
            departmentRepository.save(jurist);
        }
    }
}
