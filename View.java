import java.awt.*;
import EvaluateClass.*;
/**
 * De klasse van de view.
 * 
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 08/12/2014
 */
public class View extends Canvas
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Image buffer;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private static int grootte = 40;
    /**
     * Hier gaan we alle afbeeldingen inladen.
     * Source: http://sourceforge.net/projects/bombermaniacs/
     */
    Image muur          = Toolkit.getDefaultToolkit().getImage("img/muur.gif");
    Image krat          = Toolkit.getDefaultToolkit().getImage("img/krat.gif");
    Image bom           = Toolkit.getDefaultToolkit().getImage("img/bom.gif");
    Image explosie      = Toolkit.getDefaultToolkit().getImage("img/explosie.gif");
    Image Speler1Onder  = Toolkit.getDefaultToolkit().getImage("img/Blauw_onder.gif");
    Image Speler1Boven  = Toolkit.getDefaultToolkit().getImage("img/Blauw_boven.gif");
    Image Speler1Links  = Toolkit.getDefaultToolkit().getImage("img/Blauw_links.gif");
    Image Speler1Rechts = Toolkit.getDefaultToolkit().getImage("img/Blauw_rechts.gif");
    Image Speler1Dood   = Toolkit.getDefaultToolkit().getImage("img/Blauw_dood.gif");
    Image Speler2Onder  = Toolkit.getDefaultToolkit().getImage("img/Rood_onder.gif");
    Image Speler2Boven  = Toolkit.getDefaultToolkit().getImage("img/Rood_boven.gif");
    Image Speler2Links  = Toolkit.getDefaultToolkit().getImage("img/Rood_links.gif");
    Image Speler2Rechts = Toolkit.getDefaultToolkit().getImage("img/Rood_rechts.gif");
    Image Speler2Dood   = Toolkit.getDefaultToolkit().getImage("img/Rood_dood.gif");
    Image grond         = Toolkit.getDefaultToolkit().getImage("img/grond.gif");
    Image bomb          = Toolkit.getDefaultToolkit().getImage("img/item_bomb.gif");
    Image flame         = Toolkit.getDefaultToolkit().getImage("img/item_flame.gif");
    Image goldenflame   = Toolkit.getDefaultToolkit().getImage("img/item_goldenflame.gif");
    Image kicker        = Toolkit.getDefaultToolkit().getImage("img/item_kicker.gif");
    Image life          = Toolkit .getDefaultToolkit().getImage("img/item_life.gif");
    Image shifter       = Toolkit.getDefaultToolkit().getImage("img/item_shifter.gif");
    Image speedup       = Toolkit.getDefaultToolkit().getImage("img/item_speedup.gif");
    Image Portaal1      = Toolkit.getDefaultToolkit().getImage("img/portaal_1.gif");
    Image Portaal2      = Toolkit.getDefaultToolkit().getImage("img/portaal_2.gif");
    Image kruit         = Toolkit.getDefaultToolkit().getImage("img/kruit.gif");
    
    /**
     * Constructor voor objecten van de klasse View.
     * 
     * @param m het Model.
     */
    public View(Model m)
    {
        this.m = m;
    }

    /**
     * Dit wordt steeds hertekend,
     * dit gebeurt vanuit een buffer om flikkering te voorkomen.
     * 
     * @param g Iets grafisch ;)
     */
    public void paint(Graphics g)
    {
        if (buffer == null)
        {
            buffer = createImage(getWidth(),getHeight());
        }
        teken(buffer.getGraphics());
        g.drawImage(buffer, 0, 0, this);
    }

    /**
     * Is dit misschien wat er gebeurd bij repaint()?
     * 
     * @param g Iets grafisch ;)
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Hier wordt alles getekend:
     * 1) er wordt uit het model in het rooster uitgelezen wat er ligt;
     * 2) soms moet er iets anders worden getekend bij bepaalde voorwaarden, dit wordt nagezien;
     * 3) we gaan het effectief 'tekenen' we roepen de gewenste afbeelding op die bovenaan zijn ingeladen en plaatsen deze op het canvas.
     * 
     * @param g Iets grafisch ;)
     */
    public void teken(Graphics g) {
        g.clearRect(0,0,getWidth(), getHeight());
        if (m.getIsErEenDode())
        {
            doodIedereen(g);
        }
        else
        {
            for (int i=0; i < m.getBreedte(); i++)
            {
                for (int j=0; j < m.getHoogte(); j++)
                {
                    Voorwerp vw = m.getVoorwerp(i, j);
                    if (vw instanceof Muur)
                    {
                        g.drawImage(muur, i*grootte, j*grootte, grootte, grootte, this);
                    }
                    else if (vw instanceof Krat)
                    {
                        Krat kra = (Krat)vw;
                        if (kra.isVuur()) {
                            g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                        }
                        else {
                            g.drawImage(krat, i*grootte, j*grootte, grootte, grootte, this);
                        }
                    }
                    else if (vw instanceof Grond)
                    {
                        Grond gro = (Grond)vw;
                        if (gro.isVuur()) {
                            g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                        }
                        else {
                            g.drawImage(grond, i*grootte, j*grootte, grootte, grootte, this);
                        }
                    }
                    else if (vw instanceof Kruit)
                    {
                        Kruit kru = (Kruit)vw;
                        if (kru.isVuur()) {
                            g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                        }
                        else {
                            g.drawImage(kruit, i*grootte, j*grootte, grootte, grootte, this);
                        }
                    }
                    else if (vw instanceof Bom)
                    {
                        Bom bo = (Bom)vw;
                        if (bo.isVuur()) {
                            g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                        }
                        else {
                            g.drawImage(bom, i*grootte, j*grootte, grootte, grootte, this);
                        }
                    }
                    else if (vw instanceof Speler)
                    {
                        Speler sl = (Speler)vw;
                        switch (sl.getSpelerNr()) {
                            case 1:
                                if (m.speler1.getLevens() == 0)
                                {
                                    g.drawImage(Speler1Dood, i*grootte, j*grootte, grootte, grootte, this);
                                }
                                else if (m.speler1.isVuur())
                                {
                                    g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                                }
                                else 
                                {
                                    switch (m.speler1.getRichting()) {
                                        case ONDER: g.drawImage(Speler1Onder, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case BOVEN: g.drawImage(Speler1Boven, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case LINKS: g.drawImage(Speler1Links, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case RECHTS: g.drawImage(Speler1Rechts, i*grootte, j*grootte, grootte, grootte, this); break;
                                    }
                                }
                                break;
                            case 2:
                                if (m.speler2.getLevens() == 0) 
                                {
                                    g.drawImage(Speler2Dood, i*grootte, j*grootte, grootte, grootte, this);
                                }
                                else if (m.speler2.isVuur())
                                {
                                    g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                                }
                                else 
                                {
                                    switch (m.speler2.getRichting()) {
                                        case ONDER: g.drawImage(Speler2Onder, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case BOVEN: g.drawImage(Speler2Boven, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case LINKS: g.drawImage(Speler2Links, i*grootte, j*grootte, grootte, grootte, this); break;
                                        case RECHTS: g.drawImage(Speler2Rechts, i*grootte, j*grootte, grootte, grootte, this); break;
                                    }
                                }
                                break;
                        }              
                    }
                    else if (vw instanceof PowerUp)
                    {
                        PowerUp pu = (PowerUp)vw;
                        if (pu.isVuur()) {
                            if (pu.isOpen()) {
                                switch (pu.getPower()) {
                                    case EXTRA_BOM: g.drawImage(bomb, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case MEER_VLAM: g.drawImage(flame, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case GOLDENFLAME: g.drawImage(goldenflame, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case KICKER: g.drawImage(kicker, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case EXTRA_LEVEN: g.drawImage(life, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case SHIFTER: g.drawImage(shifter, i*grootte, j*grootte, grootte, grootte, this); break;
                                    case SNELLER_LOPEN: g.drawImage(speedup, i*grootte, j*grootte, grootte, grootte, this); break;
                                }
                            }
                            else
                            {
                                g.drawImage(explosie, i*grootte, j*grootte, grootte, grootte, this);
                            }
                        }
                        else
                        {
                            g.drawImage(krat, i*grootte, j*grootte, grootte, grootte, this);
                        }
                    }
                    else if (vw instanceof Portaal)
                    {
                        g.drawImage(Portaal1, i*grootte, j*grootte, grootte, grootte, this);
                    }
                }
            }
        }
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
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, m.getBreedte(), m.getHoogte());
            g.setColor(Color.BLACK);
            g.drawString("Speler 2 is gewonnen.", (m.getBreedte()/3)*grootte, (m.getHoogte()/3)*grootte);
        }
        else if (m.speler2.getLevens() == 0)
        {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, m.getBreedte(), m.getHoogte());
            g.setColor(Color.BLACK);
            g.drawString("Speler 1 is gewonnen.", (m.getBreedte()/3)*grootte, (m.getHoogte()/3)*grootte);
        }
    }
}