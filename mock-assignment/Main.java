import java.io.IOException;
import java.util.Scanner;

class Book {
    private String title = "";
    private String author = "";
    private int pages;

    public Book(String title, String author) {
        this.author = author;
        this.title = title;
    }

    public Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public Book() {
        // def constructor
    }

    void print() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Pages: " + Integer.toString(pages));
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String title = sc.nextLine();
        String author = sc.nextLine();
        int pages = sc.nextInt();

        Book b1 = new Book();
        Book b2 = new Book(title, author);
        Book b3 = new Book(title, author, pages);

        b1.print();
        b2.print();
        b3.print();

        sc.close();
    }
}
