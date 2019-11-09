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
        Day monday = new Day();
        monday.setDayNumber(1);
        monday.setWeekDay(WeekDay.MONDAY);
        dayRepository.save(monday);

        Day tuesday = new Day();
        tuesday.setDayNumber(2);
        tuesday.setWeekDay(WeekDay.TUESDAY);
        dayRepository.save(tuesday);

        Day wednesday = new Day();
        wednesday.setDayNumber(3);
        wednesday.setWeekDay(WeekDay.WEDNESDAY);
        dayRepository.save(wednesday);
        

        Day thursday = new Day();
        thursday.setDayNumber(4);
        thursday.setWeekDay(WeekDay.THURSDAY);
        dayRepository.save(thursday);

        Day friday = new Day();
        friday.setDayNumber(5);
        friday.setWeekDay(WeekDay.FRIDAY);
        dayRepository.save(friday);

        Day saturday = new Day();
        saturday.setDayNumber(6);
        saturday.setWeekDay(WeekDay.SATURDAY);
        dayRepository.save(saturday);

        Day sunday = new Day();
        sunday.setDayNumber(7);
        sunday.setWeekDay(WeekDay.SUNDAY);
        dayRepository.save(sunday);
    }

    private void initTimes() {
        Time lessonFirst = new Time();
        lessonFirst.setLessonNumber(1);
        lessonFirst.setTimeStart("8:20");
        lessonFirst.setTimeFinish("09:50");
        timeRepository.save(lessonFirst);

        Time lessonSecond = new Time();
        lessonSecond.setLessonNumber(2);
        lessonSecond.setTimeStart("10:00");
        lessonSecond.setTimeFinish("11:35");
        timeRepository.save(lessonSecond);

        Time lessonThird = new Time();
        lessonThird.setLessonNumber(3);
        lessonThird.setTimeStart("12:05");
        lessonThird.setTimeFinish("13:40");
        timeRepository.save(lessonThird);

        Time lessonFourth = new Time();
        lessonFourth.setLessonNumber(4);
        lessonFourth.setTimeStart("13:50");
        lessonFourth.setTimeFinish("15:25");
        timeRepository.save(lessonFourth);

        Time lessonFifth = new Time();
        lessonFifth.setLessonNumber(5);
        lessonFifth.setTimeStart("15:35");
        lessonFifth.setTimeFinish("17:10");
        timeRepository.save(lessonFifth);

        Time lessonSix = new Time();
        lessonSix.setLessonNumber(6);
        lessonSix.setTimeStart("17:20");
        lessonSix.setTimeFinish("18:40");
        timeRepository.save(lessonSix);

        Time lessonSeventh = new Time();
        lessonSeventh.setLessonNumber(7);
        lessonSeventh.setTimeStart("18:45");
        lessonSeventh.setTimeFinish("20:05");
        timeRepository.save(lessonSeventh);

        Time lessonEighth = new Time();
        lessonEighth.setLessonNumber(8);
        lessonEighth.setTimeStart("20:10");
        lessonEighth.setTimeFinish("21:30");
        timeRepository.save(lessonEighth);
    }

    private void initDepartments() {
        Department biofac = new Department();
        biofac.setFullName("Биологический факультет");
        biofac.setShortName("БИОЛОГ.ФАК-Т");
        biofac.setURL("biological");
        departmentRepository.save(biofac);

        Department geofac = new Department();
        geofac.setFullName("Географический факультет");
        geofac.setShortName("ГЕОГРАФ.ФАК-Т");
        geofac.setURL("geographic");
        departmentRepository.save(geofac);

        Department geolfac = new Department();
        geolfac.setFullName("Геологический факультет");
        geolfac.setShortName("ГЕОЛОГ.ФАК-Т");
        geolfac.setURL("geological");
        departmentRepository.save(geolfac);

        Department idpo = new Department();
        idpo.setFullName("Институт дополнительного профессионального образования");
        idpo.setShortName("ИДПО");
        idpo.setURL("idpo");
        departmentRepository.save(idpo);

        Department instisk = new Department();
        instisk.setFullName("Институт искусств");
        instisk.setShortName("И-Т ИСКУССТВ");
        instisk.setURL("instisk");
        departmentRepository.save(instisk);

        Department imimo = new Department();
        imimo.setFullName("Институт истории и международных отношений");
        imimo.setShortName("ИИИМО");
        imimo.setURL("imimo");
        departmentRepository.save(imimo);

        Department ifkis = new Department();
        ifkis.setFullName("Институт физической культуры и спорта");
        ifkis.setShortName("И-Т ФИЗ.КУЛ.");
        ifkis.setURL("ifkis");
        departmentRepository.save(ifkis);

        Department philological = new Department();
        philological.setFullName("Институт филологии и журналистики");
        philological.setShortName("ИФИЖ");
        philological.setURL("philological");
        departmentRepository.save(philological);

        Department chemical = new Department();
        chemical.setFullName("Институт химии");
        chemical.setShortName("И-Т ХИМИИ");
        chemical.setURL("chemical");
        departmentRepository.save(chemical);

        Department mechmath = new Department();
        mechmath.setFullName("Механико-математический факультет");
        mechmath.setShortName("МЕХМАТ");
        mechmath.setURL("mechmath");
        departmentRepository.save(mechmath);

        Department sociological = new Department();
        sociological.setFullName("Социологический факультет");
        sociological.setShortName("СОЦ.ФАК-Т");
        sociological.setURL("sociological");
        departmentRepository.save(sociological);

        Department fi = new Department();
        fi.setFullName("Факультет иностранных языков и лингводидактики");
        fi.setShortName("ИН.ЯЗ.ФАК-Т");
        fi.setURL("fi");
        departmentRepository.save(fi);

        Department csit = new Department();
        csit.setFullName("Факультет компьютерных наук и информационных технологий");
        csit.setShortName("КНИИТ");
        csit.setURL("knt");
        departmentRepository.save(csit);

        Department nbmt = new Department();
        nbmt.setFullName("Факультет нано- и биомедицинских технологий");
        nbmt.setShortName("ФНБМТ");
        nbmt.setURL("fnbmt");
        departmentRepository.save(nbmt);

        Department fnp = new Department();
        fnp.setFullName("Факультет нелинейных процессов");
        fnp.setShortName("ФНП");
        fnp.setURL("non-linearprocesses");
        departmentRepository.save(fnp);

        Department psych = new Department();
        psych.setFullName("Факультет психологии");
        psych.setShortName("ПСИХОЛОГ.ФАК-Т");
        psych.setURL("fps");
        departmentRepository.save(psych);

        Department fppiso = new Department();
        fppiso.setFullName("Факультет психолого-педагогического и специального образования");
        fppiso.setShortName("ППИСО");
        fppiso.setURL("fppiso");
        departmentRepository.save(fppiso);

        Department phys = new Department();
        phys.setFullName("Физический факультет");
        phys.setShortName("ФИЗФАК");
        phys.setURL("fiz");
        departmentRepository.save(phys);

        Department philosophic = new Department();
        philosophic.setFullName("Философский факультет");
        philosophic.setShortName("ФИЛОСОФ.ФАК-Т");
        philosophic.setURL("philosophic");
        departmentRepository.save(philosophic);

        Department economy = new Department();
        economy.setFullName("Экономический факультет");
        economy.setShortName("ЭКОНОМ.ФАК-Т");
        economy.setURL("economy");
        departmentRepository.save(economy);

        Department jurist = new Department();
        jurist.setFullName("Юридический факультет");
        jurist.setShortName("ЮРФАК");
        jurist.setURL("jurist");
        departmentRepository.save(jurist);
    }
}
