import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class GuiNezet extends JFrame {


    private Feladat kontroller;
    private List<Modell> termekek;


    private JButton btnBeolvas, btnLegdragabb, btnLegolcsobb, btnTermekkod, btnSzazalekosNoveles;
    private DefaultListModel<String> dlm;
    private JList<String> lstEredmenyek;

    public GuiNezet() {
        kontroller = new Feladat();

        setTitle("Termékkezelő");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ini();
        layoutBeallitas();
        esemenyKezelok();
    }

    private void ini() {

        btnBeolvas = new JButton("Beolvasás");
        btnLegdragabb = new JButton("Legdrágább");
        btnLegolcsobb = new JButton("Legolcsóbb");
        btnTermekkod = new JButton("Termékkódok");
        btnSzazalekosNoveles = new JButton("Ár növelése %");


        dlm = new DefaultListModel<>();
        lstEredmenyek = new JList<>(dlm);
    }

    private void layoutBeallitas() {

        JPanel pnlGombok = new JPanel(new FlowLayout());
        pnlGombok.add(btnBeolvas);
        pnlGombok.add(btnLegdragabb);
        pnlGombok.add(btnLegolcsobb);
        pnlGombok.add(btnTermekkod);
        pnlGombok.add(btnSzazalekosNoveles);

        add(pnlGombok, BorderLayout.NORTH);


        JScrollPane scrollPane = new JScrollPane(lstEredmenyek);

        scrollPane.setBorder(BorderFactory.createTitledBorder("Eredmények és adatok"));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void esemenyKezelok() {

        btnBeolvas.addActionListener(e -> {

            termekek = kontroller.beolvas("src/termekek.txt");
            frissitLista("Fájl beolvasva. Elemek száma: " + termekek.size());
        });


        btnLegdragabb.addActionListener(e -> {
            if (adatEllenorzes()) {
                int max = kontroller.legdragabb(termekek);
                frissitLista("A legdrágább termék ára: " + max + " Ft");
            }
        });


        btnLegolcsobb.addActionListener(e -> {
            if (adatEllenorzes()) {
                int min = kontroller.legolcsobb(termekek);
                frissitLista("A legolcsóbb termék ára: " + min + " Ft");
            }
        });


        btnTermekkod.addActionListener(e -> {
            if (adatEllenorzes()) {
                Set<String> kodok = kontroller.hanyTermekkod(termekek);
                dlm.clear();
                dlm.addElement("--- Egyedi termékkódok ---");
                for (String kod : kodok) {
                    dlm.addElement(kod);
                }
            }
        });


        btnSzazalekosNoveles.addActionListener(e -> {
            if (adatEllenorzes()) {
                String bemenet = JOptionPane.showInputDialog(
                        this,
                        "Százalék mértéke (nem kötelező):",
                        "Bevitel",
                        JOptionPane.QUESTION_MESSAGE
                );

                if (bemenet == null) return;

                List<Double> eredmenyek;
                if (bemenet.trim().isEmpty()) {

                    eredmenyek = kontroller.szazalekHozzaad(termekek);
                } else {
                    try {
                        double szazalek = Double.parseDouble(bemenet.replace(',', '.'));
                        eredmenyek = kontroller.szazalekHozzaad(termekek, szazalek);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Hibás formátum! Kérlek számot adj meg.");
                        return;
                    }
                }

                megjelenitListaDouble(eredmenyek, "Új értékek a növelés után: ");
            }
        });
    }


    private boolean adatEllenorzes() {
        if (termekek == null || termekek.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nincs adat! Olvasd be a fájlt először.");
            return false;
        }
        return true;
    }


    private void frissitLista(String uzenet) {
        dlm.clear();
        dlm.addElement(uzenet);
    }


    private void megjelenitListaDouble(List<Double> lista, String cim) {
        dlm.clear();
        dlm.addElement(cim);
        for (Double d : lista) {
            dlm.addElement(String.format("%.2f Ft", d));
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new GuiNezet().setVisible(true));
    }
}