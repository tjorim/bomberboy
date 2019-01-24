import java.awt.event.*;
import EvaluateClass.*;
/**
 * De klasse van de Controller.
 * 
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 07/12/2014
 */
public class Controller implements KeyListener, ActionListener
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private GroteView gv;

    /**
     * Constructor voor objecten van de klasse Controller.
     * 
     * @param m het Model.
     * @param gv de GroteView.
     */
    public Controller(Model m, GroteView gv)
    {
        this.m = m;
        this.gv = gv;
        gv.addKeyListener(this);
        gv.addActionListener(this);
    }

    /**
     * Dit zorgt ervoor dat we alle ingedrukte toetsen kunnen uitlezen,
     * hierbij worden bepaalde acties uitgevoerd.
     * 
     * @param e Welke toetsenbordtoets er juist wordt ingedrukt.
     */
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            if (!m.speler1.isVuur())
            {
                m.legBom(1);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_LEFT:
            if (!m.speler1.isVuur())
            {
                m.speler1.setRichting(Richting.LINKS);
                m.beweegLinks(1);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_RIGHT:
            if (!m.speler1.isVuur())
            {
                m.speler1.setRichting(Richting.RECHTS);
                m.beweegRechts(1);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_UP:
            if (!m.speler1.isVuur())
            {
                m.speler1.setRichting(Richting.BOVEN);
                m.beweegBoven(1);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_DOWN:
            if (!m.speler1.isVuur())
            {
                m.speler1.setRichting(Richting.ONDER);
                m.beweegOnder(1);
                gv.repaint();
            }
            break;

            case KeyEvent.VK_NUMPAD5: // is bom leggen voor speler 2
            if (!m.speler2.isVuur())
            {
                m.legBom(2);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_NUMPAD4: // is naar links voor speler 2
            if (!m.speler2.isVuur())
            {
                m.speler2.setRichting(Richting.LINKS);
                m.beweegLinks(2);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_NUMPAD6: // is naar rechts voor speler 2
            if (!m.speler2.isVuur())
            {
                m.speler2.setRichting(Richting.RECHTS);
                m.beweegRechts(2);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_NUMPAD8: // is naar boven voor speler 2
            if (!m.speler2.isVuur())
            {
                m.speler2.setRichting(Richting.BOVEN);
                m.beweegBoven(2);
                gv.repaint();
            }
            break;
            case KeyEvent.VK_NUMPAD2: // is naar beneden voor speler 2
            if (!m.speler2.isVuur())
            {
                m.speler2.setRichting(Richting.ONDER);
                m.beweegOnder(2);
                gv.repaint();
            }
            break;
        }
    }

    /**
     * Deze moeten we verplicht initialiseren omdat
     * we een KeyListener hebben aangeroepen.
     * 
     * @param e Welke keyevent.
     */
    public void keyReleased(KeyEvent e){}

    /**
     * Deze moeten we verplicht initialiseren omdat
     * we een KeyListener hebben aangeroepen.
     * 
     * @param e Welk keyevent.
     */
    public void keyTyped(KeyEvent e){}  
    
    /**
     * Hiermee kunnen we nakijken of er er een button is ingedrukt bij de widgetview.
     * 
     * @param e Welk actionevent.
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == gv.getSpeelKnop())
        {
            if (gv.getTekstVak()>0 && gv.getTekstVak()<5)
            {
                m.reset(gv.getTekstVak());
            }
        }
        if (e.getSource() == gv.getHerstartKnop())
        {
            m.reset(m.getLevelNr());
        }
    }
    // getters

    // setters
}