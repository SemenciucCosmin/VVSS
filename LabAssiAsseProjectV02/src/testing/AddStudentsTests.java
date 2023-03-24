package testing;

import domain.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.StudentFileRepository;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class AddStudentsTests {
    private StudentFileRepository studentFileRepository;
    @Before
    public void setup() {
        studentFileRepository = new StudentFileRepository("fisiere/Studenti.txt");
    }

    @Test
    public void studentsIteratorTest() {
        int numberOfStudents = 0;
        Iterator<Student> iterator = studentFileRepository.findAll().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            numberOfStudents++;
        }
        Student student1 = new Student("11", "Marius", 936, "silasimarius@yahoo.com");
        Student student2 = new Student("12", "Cosmin", 926, "cosminsemenciuc@yahoo.com");
        Student student3 = new Student("13", "Valentin", 926, "spinuvalentin@yahoo.com");
        Student student4 = new Student("14", "Ioana", 916, "stanioana@yahoo.com");

        studentFileRepository.save(student1);
        studentFileRepository.save(student2);
        studentFileRepository.save(student3);
        studentFileRepository.save(student4);

        int newNumberOfStudents = 0;
        iterator = studentFileRepository.findAll().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            newNumberOfStudents++;
        }

        assertEquals(numberOfStudents + 4, newNumberOfStudents);
    }

    @Test
    public void studentsGettersTest() {
        Iterator<Student> iterator = studentFileRepository.findAll().iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getID().equals("11")) {
                assertEquals(student.getNume(), "Marius");
                assertEquals(student.getEmail(), "silasimarius@yahoo.com");
                assertEquals(student.getGrupa(), 936);
            }
        }
    }

    @After
    public void removeStudents() {
        studentFileRepository.delete("11");
        studentFileRepository.delete("12");
        studentFileRepository.delete("13");
        studentFileRepository.delete("14");
    }
}
