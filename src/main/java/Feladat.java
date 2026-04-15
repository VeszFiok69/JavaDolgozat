import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Feladat {

    public List<Modell> beolvas(String fajlNev)  {
        List<Modell> lista = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fajlNev))){
            while (sc.hasNext()){
                String termek = sc.next();
                int ar = sc.nextInt();
                lista.add(new Modell(termek, ar));
            }
        }catch (FileNotFoundException e){
            System.err.println("Hiba: a fájl nem található az alábbi okból: " + e);
        }
        return lista;
    }

    private List<Integer> kinyer(List<Modell> termekek){
        List<Integer> arak = new ArrayList<>();
        for (Modell m : termekek){
            arak.add(m.getAr());
        }
        return arak;
    }

    public int legdragabb(List<Modell> termekek) {
        if (termekek.isEmpty()){
            return 0;
        }
        List<Integer> arak = kinyer(termekek);
        return Collections.max(arak);
    }

    public int legolcsobb(List<Modell> termekek) {
        if (termekek.isEmpty()){
            return 0;
        }
        List<Integer> arak = kinyer(termekek);
        return Collections.min(arak);
    }

    public Set<String> hanyTermekkod(List<Modell> termekek) {
        Set<String> termekkod = new TreeSet<>();
        for (Modell m : termekek){
            termekkod.add(m.getTermek());
        }
        return termekkod;
    }

    public List<Double> szazalekHozzaad(List<Modell> termekek, double szazalek){
        List<Double> eredmeny = new ArrayList<>();
        for (Modell m : termekek){
            eredmeny.add( ((m.getAr()/100)*szazalek));
        }
        return eredmeny;
    }
    
    public List<Double> szazalekHozzaad(List<Modell> termekek){
        return szazalekHozzaad(termekek, 10.0);
    }



//    public Map<String, Integer> atlagar() {
//        Map<String, Integer>map=new HashMap<>();
//        for (String t : termek){
//            map.put(t,map.getOrDefault(t, 0)+1);
//            }
//        return map;
//    }
}





