package addGrade;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDate;

public class AppTestAddGradeBbt {
    private Service service;

    @Before
    public void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";
        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
        Student student1 = new Student("1", "Name", 1, "email");
        Tema tema1 = new Tema("1", "description", 10, 10);
        service.addStudent(student1);
        service.addTema(tema1);
    }

    @Test
    public void testTc1Ec() {
        try {
            clearFile();
            LocalDate date = LocalDate.of(2023, 4, 27);
            Nota savedNota = new Nota("1", "1", "1", 10.0, date);
            service.addNota(savedNota, "feedback");
            Nota receivedNota = service.findNota("1");
            assert (savedNota == receivedNota);
        } catch (ValidationException exception) {
            exception.printStackTrace();
        }
    }

    @Test(expected = ValidationException.class)
    public void testTc2Ec() throws ValidationException {
        clearFile();
        LocalDate date = LocalDate.of(2023, 4, 27);
        Nota savedNota = new Nota("1", "111", "1", 10.0, date);
        service.addNota(savedNota, "feedback");
    }

    private void clearFile() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            Document document = documentBuilderFactory.newDocumentBuilder().parse(new File("fisiere/NoteTest.xml"));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(System.out));
        } catch (Exception ignored){}
    }
}
