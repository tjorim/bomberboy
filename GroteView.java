import java.awt.*;
import java.awt.event.*;
import EvaluateClass.*;
/**
 * class GroteView - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 08/12/2014
 */
public class GroteView extends Panel
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private View v;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private WidgetsView widgets;

    /**
     * Constructor voor objects van class GroteView
     * 
     * @param m het Model.
     */
    public GroteView(Model m)
    {
        this.m = m;
        v = new View(m);
        widgets = new WidgetsView(m);
        setLayout(new BorderLayout());
        add(v, BorderLayout.CENTER);
        add(widgets, BorderLayout.EAST);
    }

    // delegeren
    /**
     * Waarvoor dient dit precies?
     * 
     * @param l Een keylistener
     */
    public void addKeyListener(KeyListener l) {
        v.addKeyListener(l);
    }

    /**
     * Waarvoor dient dit precies?
     * 
     * @param l Een actionlistener
     */
    public void addActionListener(ActionListener l) {
        widgets.addActionListener(l);
    }
    
    /**
     * @return herstartKnop De knop om hetzelfde level te herstarten.
     */
    public Button getHerstartKnop()
    {
        return widgets.getHerstartKnop();
    }
    
    /**
     * @return speelKnop De knop om een nieuw level te spelen.
     */
    public Button getSpeelKnop()
    {
        return widgets.getSpeelKnop();
    }
    
    /**
     * @return De waarde die in het tekstvak staat als integer.
     */
    public int getTekstVak()
    {
        return widgets.getTekstVak();
    }
    
    /**
     * Dit is wat er steeds getekend wordt.
     * 
     * @param g Iets grafisch ;)
     */
    public void paint(Graphics g) {
        v.repaint();
        widgets.repaint();
    }
}