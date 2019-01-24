import java.awt.*;
import java.awt.event.*;
import EvaluateClass.*;
/**
 * Write a description of class Main here.
 * 
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Bomberman extends Frame
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private GroteView gv;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Controller c;
    private Animator a;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Thread t;

    /**
     * Constructor for objects of class Main
     * 
     * @param levelNr Het level van het spel.
     */
    public Bomberman(int levelNr)
    {
        m = new Model(levelNr);
        gv = new GroteView(m);
        c = new Controller(m,gv);
        a = new Animator(m,gv);
        t = new Thread(a);
        add(gv);
        t.start();
        this.addWindowListener(new WindowAdapter()
            {
                /**
                 * Deze methode zorgt ervoor dat we het venster kunnen sluiten.
                 * 
                 * @param e Een WindowEvent.
                 */
                public void windowClosing(WindowEvent e)
                {
                    dispose();
                    System.exit(0);
                }
            }
        );
    }

    /**
     * Deze functie laat het spel starten en stelt alles in van het frame
     * 
     * @param levelNr Het level van het spel.
     */
    public static void main(int levelNr)
    {
        Bomberman venster = new Bomberman(levelNr);
        venster.setVisible(true);
        venster.setSize(1024,629);
        venster.setTitle("Bomberman");
        venster.setResizable(false);
    }
}