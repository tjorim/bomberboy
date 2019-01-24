import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import EvaluateClass.*;
/**
 * class WidgetsView - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 08/12/2014
 */
public class WidgetsView extends Panel
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    protected Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    protected Label l;
    protected Label leeg;
    protected Label lS1heeft;
    protected Label lS2heeft;
    protected Label lS1Bom;
    protected Label lS2Bom;
    protected Label lS1Vuur;
    protected Label lS2Vuur;
    protected Label lS1Snelheid;
    protected Label lS2Snelheid;
    protected Label lS1Levens;
    protected Label lS2Levens;
    protected Label lS1kan;
    protected Label lS2kan;
    protected Label lS1Shiften;
    private String s1Shiften;
    protected Label lS2Shiften;
    private String s2Shiften;
    protected Label lS1Kicken;
    private String s1Kicken;
    protected Label lS2Kicken;
    private String s2Kicken;
    @Intentional(ignoreSet=true, ignoreGet=true)
    protected TextField vak;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Button herstartKnop;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Button speelKnop;

    /**
     * Constructor voor objects van class WidgetsView
     * 
     * @param m het Model.
     */
    public WidgetsView(Model m)
    {
        this.m = m;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        l = new Label("Informatie over de spelers:");
        leeg =  new Label("");
        lS1heeft = new Label("Speler 1 heeft:");
        lS2heeft = new Label("Speler 2 heeft:");
        lS1Bom = new Label("Speler 1 bom");
        lS2Bom = new Label("Speler 2 bom");
        lS1Vuur = new Label("Speler 1 vuur");
        lS2Vuur = new Label("Speler 2 vuur");
        lS1Snelheid = new Label("Speler 1 snelheid");
        lS2Snelheid = new Label("Speler 2 snelheid");
        lS1Levens = new Label("Speler 1 levens");
        lS2Levens = new Label("Speler 2 levens");
        lS1kan = new Label("en kan:");
        lS2kan = new Label("en kan:");
        lS1Shiften = new Label("Speler 1 shiften");
        lS2Shiften = new Label("Speler 2 shiften");
        lS1Kicken = new Label("Speler 1 kicken");
        lS2Kicken = new Label("Speler 2 kicken");
        vak = new TextField("Welk level wil je spelen?",1);
        herstartKnop = new Button("Herstart");
        speelKnop = new Button("Speel");
        add(l);
        add(lS1heeft);
        add(lS1Bom);
        add(lS1Vuur);
        add(lS1Snelheid);
        add(lS1Levens);
        add(lS1kan);
        add(lS1Shiften);
        add(lS1Kicken);
        add(lS2heeft);
        add(lS2Bom);
        add(lS2Vuur);
        add(lS2Snelheid);
        add(lS2Levens);
        add(lS2kan);
        add(lS2Shiften);
        add(lS2Kicken);
        add(leeg);
        add(vak);
        add(speelKnop);
        add(herstartKnop);
    }

    /**
     * Dit is wat er steeds getekend wordt.
     * Deze functie zorgt ervoor dat het spel wordt stilgelegd indien
     * er iemand dood is (aantalLevens == 0).
     * 
     * @param g Iets grafisch ;)
     */
    public void paint(Graphics g) {
        if (m.getIsErEenDode())
        {
            doodIedereen(g);
        }
        else
        {
            if(m.speler1.kanShiften())
            {
                s1Shiften = "WEL";
            }
            else
            {
                s1Shiften = "NIET";
            }
            if(m.speler1.kanKicken())
            {
                s1Kicken = "WEL";
            }
            else
            {
                s1Kicken = "NIET";
            }
            lS1Bom.setText("         " + m.speler1.getAantalBommen() + " bommen");
            lS1Vuur.setText("         " + m.speler1.getHoeveelVuur() + " vuurkracht");
            lS1Snelheid.setText("         " + m.speler1.getSnelheid() + " snelheid");
            lS1Levens.setText("         " + m.speler1.getLevens() + " levens");
            lS1Shiften.setText("         " + s1Shiften + " shiften");
            lS1Kicken.setText("         " + s1Kicken + " kicken");

            if(m.speler2.kanShiften())
            {
                s2Shiften = "WEL";
            }
            else
            {
                s2Shiften = "NIET";
            }
            if(m.speler2.kanKicken())
            {
                s2Kicken = "WEL";
            }
            else
            {
                s2Kicken = "NIET";
            }
            lS2Bom.setText("         " + m.speler2.getAantalBommen() + " bommen");
            lS2Vuur.setText("         " + m.speler2.getHoeveelVuur() + " vuurkracht");
            lS2Snelheid.setText("         " + m.speler2.getSnelheid() + " snelheid");
            lS2Levens.setText("         " + m.speler2.getLevens() + " levens");
            lS2Shiften.setText("         " + s2Shiften + " shiften");
            lS2Kicken.setText("         " + s2Kicken + " kicken");
        }
    }
    
    /**
     * @return herstartKnop De knop om hetzelfde level te herstarten.
     */
    public Button getHerstartKnop()
    {
        return herstartKnop;
    }
    
    /**
     * @return speelKnop De knop om een nieuw level te spelen.
     */
    public Button getSpeelKnop()
    {
        return speelKnop;
    }
    
    /**
     * Source: http://stackoverflow.com/questions/5585779/converting-string-to-int-in-java
     * 
     * @return De waarde die in het tekstvak staat als integer.
     */
    public int getTekstVak()
    {
        return Integer.parseInt(vak.getText());
    }
    
    /**
     * Dit moet in de toekomst ervoor zorgen dat we een nieuw spel
     * (eventueel een ander level) kunnen inladen.
     * 
     * @param r Een actionlistener
     */
    public void addActionListener(ActionListener r) {
        herstartKnop.addActionListener(r);
        speelKnop.addActionListener(r);
    }
    
    /**
     * Deze functie zou ervoor moeten zorgen dat het spel wordt stilgelegd indien er iemand dood is (aantalLevens == 0).
     * 
     * @param g Weeral iets grafisch, we zouden deze functie moeten kunnen oproepen maar we initialiseren niet overal de view v.
     */
    public void doodIedereen(Graphics g)
    {
        if (m.speler1.getLevens() == 0)
        {
            lS1heeft.setText("");
            lS1Bom.setText("");
            lS1Vuur.setText("");
            lS1Snelheid.setText("Speler 1");
            lS1Levens.setText("         IS DOOD!!!");
            lS1kan.setText("");
            lS1Shiften.setText("");
            lS1Kicken.setText("");
        }
        if (m.speler2.getLevens() == 0)
        {
            lS2heeft.setText("");
            lS2Bom.setText("");
            lS2Vuur.setText("");
            lS2Snelheid.setText("Speler 2");
            lS2Levens.setText("         IS DOOD!!!");
            lS2kan.setText("");
            lS2Shiften.setText("");
            lS2Kicken.setText("");
        }
    }
}