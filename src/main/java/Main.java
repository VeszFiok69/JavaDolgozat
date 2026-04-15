import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Feladat feladat = new Feladat();
        feladat.beolvas("src/termekek.txt");
        System.out.println("a legmagasabb ár: " + feladat.legdragabb());
        System.out.println(feladat.hanyTermekkod());
        System.out.println(feladat.atlagar());
    }
}
