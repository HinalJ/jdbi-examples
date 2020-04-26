package book;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:bookDb");
        jdbi.installPlugin(new SqlObjectPlugin());

        try(Handle handle = jdbi.open())
        {
            BookDao bookDao = handle.attach((BookDao.class));
            bookDao.createTable();
            bookDao.insert(new Book("9781631066450", "William Shakespeare",
                    "The Complete Works of William Shakespeare", Book.Format.Hardback, "Rock Point",
                    LocalDate.parse("2019-10-08"), 1296, true));

            bookDao.insert(new Book("9781401960520", "Jim Kwik",
                    "Limitless : Upgrade Your Brain, Learn Anything Faster, and Unlock Your Exceptional Life",
                    Book.Format.PAPERBACK, "Hay House Inc", LocalDate.parse("2020-04-28"), 600, false));

            bookDao.insert(new Book("9781509858637", "Adam Kay", "This is Going to Hurt : Secret Diaries of a Junior Doctor",
                    Book.Format.PAPERBACK, "Pan MacMillan", LocalDate.parse("2018-04-19"), 256, true));

            bookDao.insert(new Book("9780190680916", "Yuval Noah Harari", "Sapiens: A Brief History of Humankind Kindle",
                    Book.Format.Hardback, "Oxford University Press", LocalDate.parse("2018-08-10"), 469, true));

            System.out.println("\"Book Database\"");
            bookDao.findAll().forEach(System.out::println);

            bookDao.delete(bookDao.find("9781509858637").get());

            System.out.println("\"Deleted a book from Database\"");
            System.out.println("\"Updated Database\"");
            bookDao.findAll().forEach(System.out::println);

        }
    }
}
