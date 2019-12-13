package com.scribassu.tracto.util;

import com.scribassu.tracto.domain.Day;
import com.scribassu.tracto.domain.Department;
import com.scribassu.tracto.domain.Time;
import com.scribassu.tracto.domain.WeekDay;
import com.scribassu.tracto.repository.DayRepository;
import com.scribassu.tracto.repository.DepartmentRepository;
import com.scribassu.tracto.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TablesDataInitializerImpl implements TablesDataInitializer {

    private final DayRepository dayRepository;
    private  final TimeRepository timeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public TablesDataInitializerImpl(DayRepository dayRepository,
                                     TimeRepository timeRepository,
                                     DepartmentRepository departmentRepository) {
        this.dayRepository = dayRepository;
        this.timeRepository = timeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void init() {
        initDays();
        initTimes();
        initDepartments();
    }
    
    private void initDays(){
        Optional<Day> mondayOpt = dayRepository.findByDayNumber(1);
        if(!mondayOpt.isPresent()) {
            Day monday = new Day();
            monday.setDayNumber(1);
            monday.setWeekDay(WeekDay.MONDAY);
            dayRepository.save(monday);
        }

        Optional<Day> tuesdayOpt = dayRepository.findByDayNumber(2);
        if(!tuesdayOpt.isPresent()) {
            Day tuesday = new Day();
            tuesday.setDayNumber(2);
            tuesday.setWeekDay(WeekDay.TUESDAY);
            dayRepository.save(tuesday);
        }

        Optional<Day> wednesdayOpt = dayRepository.findByDayNumber(3);
        if(!wednesdayOpt.isPresent()) {
            Day wednesday = new Day();
            wednesday.setDayNumber(3);
            wednesday.setWeekDay(WeekDay.WEDNESDAY);
            dayRepository.save(wednesday);
        }

        Optional<Day> thursdayOpt = dayRepository.findByDayNumber(4);
        if(!thursdayOpt.isPresent()) {
            Day thursday = new Day();
            thursday.setDayNumber(4);
            thursday.setWeekDay(WeekDay.THURSDAY);
            dayRepository.save(thursday);
        }

        Optional<Day> fridayOpt = dayRepository.findByDayNumber(5);
        if(!fridayOpt.isPresent()) {
            Day friday = new Day();
            friday.setDayNumber(5);
            friday.setWeekDay(WeekDay.FRIDAY);
            dayRepository.save(friday);
        }

        Optional<Day> saturdayOpt = dayRepository.findByDayNumber(6);
        if(!saturdayOpt.isPresent()) {
            Day saturday = new Day();
            saturday.setDayNumber(6);
            saturday.setWeekDay(WeekDay.SATURDAY);
            dayRepository.save(saturday);
        }

        Optional<Day> sundayOpt = dayRepository.findByDayNumber(7);
        if(!sundayOpt.isPresent()) {
            Day sunday = new Day();
            sunday.setDayNumber(7);
            sunday.setWeekDay(WeekDay.SUNDAY);
            dayRepository.save(sunday);
        }
    }

    private void initTimes() {
        Optional<Time> lessonFirstOpt = timeRepository.findByLessonNumber(1);
        if(!lessonFirstOpt.isPresent()) {
            Time lessonFirst = new Time();
            lessonFirst.setLessonNumber(1);
            lessonFirst.setTimeStart("8:20");
            lessonFirst.setTimeFinish("09:50");
            timeRepository.save(lessonFirst);
        }

        Optional<Time> lessonSecondOpt = timeRepository.findByLessonNumber(2);
        if(!lessonSecondOpt.isPresent()) {
            Time lessonSecond = new Time();
            lessonSecond.setLessonNumber(2);
            lessonSecond.setTimeStart("10:00");
            lessonSecond.setTimeFinish("11:35");
            timeRepository.save(lessonSecond);
        }

        Optional<Time> lessonThirdOpt = timeRepository.findByLessonNumber(3);
        if(!lessonThirdOpt.isPresent()) {
            Time lessonThird = new Time();
            lessonThird.setLessonNumber(3);
            lessonThird.setTimeStart("12:05");
            lessonThird.setTimeFinish("13:40");
            timeRepository.save(lessonThird);
        }

        Optional<Time> lessonFourthOpt = timeRepository.findByLessonNumber(4);
        if(!lessonFourthOpt.isPresent()) {
            Time lessonFourth = new Time();
            lessonFourth.setLessonNumber(4);
            lessonFourth.setTimeStart("13:50");
            lessonFourth.setTimeFinish("15:25");
            timeRepository.save(lessonFourth);
        }

        Optional<Time> lessonFifthOpt = timeRepository.findByLessonNumber(5);
        if(!lessonFifthOpt.isPresent()) {
            Time lessonFifth = new Time();
            lessonFifth.setLessonNumber(5);
            lessonFifth.setTimeStart("15:35");
            lessonFifth.setTimeFinish("17:10");
            timeRepository.save(lessonFifth);
        }

        Optional<Time> lessonSixOpt = timeRepository.findByLessonNumber(6);
        if(!lessonSixOpt.isPresent()) {
            Time lessonSix = new Time();
            lessonSix.setLessonNumber(6);
            lessonSix.setTimeStart("17:20");
            lessonSix.setTimeFinish("18:40");
            timeRepository.save(lessonSix);
        }

        Optional<Time> lessonSeventhOpt = timeRepository.findByLessonNumber(7);
        if(!lessonSeventhOpt.isPresent()) {
            Time lessonSeventh = new Time();
            lessonSeventh.setLessonNumber(7);
            lessonSeventh.setTimeStart("18:45");
            lessonSeventh.setTimeFinish("20:05");
            timeRepository.save(lessonSeventh);
        }

        Optional<Time> lessonEighthOpt = timeRepository.findByLessonNumber(8);
        if(!lessonEighthOpt.isPresent()) {
            Time lessonEighth = new Time();
            lessonEighth.setLessonNumber(8);
            lessonEighth.setTimeStart("20:10");
            lessonEighth.setTimeFinish("21:30");
            timeRepository.save(lessonEighth);
        }
    }

    private void initDepartments() {
        Optional<Department> dep = departmentRepository.findByURL("bf");
        if(!dep.isPresent()) {
            Department biofac = new Department();
            biofac.setFullName("Биологический факультет");
            biofac.setShortName("БИОЛОГ Ф-Т");
            biofac.setURL("bf");
            departmentRepository.save(biofac);
        }

        dep = departmentRepository.findByURL("gf");
        if(!dep.isPresent()) {
            Department geofac = new Department();
            geofac.setFullName("Географический факультет");
            geofac.setShortName("ГЕОГРАФ Ф-Т");
            geofac.setURL("gf");
            departmentRepository.save(geofac);
        }

        dep = departmentRepository.findByURL("gl");
        if(!dep.isPresent()) {
            Department geolfac = new Department();
            geolfac.setFullName("Геологический факультет");
            geolfac.setShortName("ГЕОЛОГ Ф-Т");
            geolfac.setURL("gl");
            departmentRepository.save(geolfac);
        }

        dep = departmentRepository.findByURL("idpo");
        if(!dep.isPresent()) {
            Department idpo = new Department();
            idpo.setFullName("Институт дополнительного профессионального образования");
            idpo.setShortName("ИДПО");
            idpo.setURL("idpo");
            departmentRepository.save(idpo);
        }

        dep = departmentRepository.findByURL("ii");
        if(!dep.isPresent()) {
            Department instisk = new Department();
            instisk.setFullName("Институт искусств");
            instisk.setShortName("И-Т ИСКУССТВ");
            instisk.setURL("ii");
            departmentRepository.save(instisk);
        }

        dep = departmentRepository.findByURL("imo");
        if(!dep.isPresent()) {
            Department imimo = new Department();
            imimo.setFullName("Институт истории и международных отношений");
            imimo.setShortName("ИИИМО");
            imimo.setURL("imo");
            departmentRepository.save(imimo);
        }

        dep = departmentRepository.findByURL("ifk");
        if(!dep.isPresent()) {
            Department ifkis = new Department();
            ifkis.setFullName("Институт физической культуры и спорта");
            ifkis.setShortName("И-Т ФИЗ КУЛ");
            ifkis.setURL("ifk");
            departmentRepository.save(ifkis);
        }

        dep = departmentRepository.findByURL("ifg");
        if(!dep.isPresent()) {
            Department philological = new Department();
            philological.setFullName("Институт филологии и журналистики");
            philological.setShortName("ИФИЖ");
            philological.setURL("ifg");
            departmentRepository.save(philological);
        }

        dep = departmentRepository.findByURL("ih");
        if(!dep.isPresent()) {
            Department chemical = new Department();
            chemical.setFullName("Институт химии");
            chemical.setShortName("И-Т ХИМИИ");
            chemical.setURL("ih");
            departmentRepository.save(chemical);
        }

        dep = departmentRepository.findByURL("mm");
        if(!dep.isPresent()) {
            Department mechmath = new Department();
            mechmath.setFullName("Механико-математический факультет");
            mechmath.setShortName("МЕХМАТ");
            mechmath.setURL("mm");
            departmentRepository.save(mechmath);
        }

        dep = departmentRepository.findByURL("sf");
        if(!dep.isPresent()) {
            Department sociological = new Department();
            sociological.setFullName("Социологический факультет");
            sociological.setShortName("СОЦ Ф-Т");
            sociological.setURL("sf");
            departmentRepository.save(sociological);
        }

        dep = departmentRepository.findByURL("fi");
        if(!dep.isPresent()) {
            Department fi = new Department();
            fi.setFullName("Факультет иностранных языков и лингводидактики");
            fi.setShortName("ИН ЯЗ Ф-Т");
            fi.setURL("fi");
            departmentRepository.save(fi);
        }

        dep = departmentRepository.findByURL("knt");
        if(!dep.isPresent()) {
            Department csit = new Department();
            csit.setFullName("Факультет компьютерных наук и информационных технологий");
            csit.setShortName("КНИИТ");
            csit.setURL("knt");
            departmentRepository.save(csit);
        }

        dep = departmentRepository.findByURL("fn");
        if(!dep.isPresent()) {
            Department nbmt = new Department();
            nbmt.setFullName("Факультет нано- и биомедицинских технологий");
            nbmt.setShortName("ФНБМТ");
            nbmt.setURL("fn");
            departmentRepository.save(nbmt);
        }

        dep = departmentRepository.findByURL("fnp");
        if(!dep.isPresent()) {
            Department fnp = new Department();
            fnp.setFullName("Факультет нелинейных процессов");
            fnp.setShortName("ФНП");
            fnp.setURL("fnp");
            departmentRepository.save(fnp);
        }

        dep = departmentRepository.findByURL("fps");
        if(!dep.isPresent()) {
            Department psych = new Department();
            psych.setFullName("Факультет психологии");
            psych.setShortName("ПСИХОЛОГ Ф-Т");
            psych.setURL("fps");
            departmentRepository.save(psych);
        }

        dep = departmentRepository.findByURL("fppso");
        if(!dep.isPresent()) {
            Department fppiso = new Department();
            fppiso.setFullName("Факультет психолого-педагогического и специального образования");
            fppiso.setShortName("ППИСО");
            fppiso.setURL("fppso");
            departmentRepository.save(fppiso);
        }

        dep = departmentRepository.findByURL("ff");
        if(!dep.isPresent()) {
            Department phys = new Department();
            phys.setFullName("Физический факультет");
            phys.setShortName("ФИЗФАК");
            phys.setURL("ff");
            departmentRepository.save(phys);
        }

        dep = departmentRepository.findByURL("fp");
        if(!dep.isPresent()) {
            Department philosophic = new Department();
            philosophic.setFullName("Философский факультет");
            philosophic.setShortName("ФИЛОСОФ Ф-Т");
            philosophic.setURL("fp");
            departmentRepository.save(philosophic);
        }

        dep = departmentRepository.findByURL("ef");
        if(!dep.isPresent()) {
            Department economy = new Department();
            economy.setFullName("Экономический факультет");
            economy.setShortName("ЭКОНОМ Ф-Т");
            economy.setURL("ef");
            departmentRepository.save(economy);
        }

        dep = departmentRepository.findByURL("uf");
        if(!dep.isPresent()) {
            Department jurist = new Department();
            jurist.setFullName("Юридический факультет");
            jurist.setShortName("ЮРФАК");
            jurist.setURL("uf");
            departmentRepository.save(jurist);
        }
    }
}
