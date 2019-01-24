import EvaluateClass.*;
/**
 * class Speler - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Speler extends Voorwerp
{
    @Intentional(ignoreSet=true)
    private int spelerNr;
    private int xCo, yCo; // coordinaten van de speler
    private boolean vuur = false;
    
    private boolean shiften; // kan de speler shiften?
    private boolean kicken; // kan de speler kicken?
    private Richting richting; // ONDER, BOVEN, LINKS, RECHTS

    private int aantalBommen;
    private static int standaardBommen = 1;
    private static int maxBommen = 6;
    private int hoeveelVuur;
    private static int standaardVuur = 1;
    private int maxVuur;
    private int levens;
    private static int standaardLevens = 3;
    private static int maxLevens = 3;
    private int snelheid;
    private static int standaardSnelheid = 1;
    private static int maxSnelheid = 6;

    private Voorwerp vwOnderSpeler;
    private Voorwerp s;

    /**
     * Constructor voor objects van class Speler
     * 
     * @param spelerNr Het nummer van de speler.
     * @param xCo De x-positie van het portaal.
     * @param yCo De y-positie van het portaal.
     */
    public Speler(int spelerNr, int xCo, int yCo, Voorwerp vwOnderSpeler)
    {
        this.spelerNr = spelerNr;
        this.xCo = xCo;
        this.yCo = yCo;
        this.vwOnderSpeler = vwOnderSpeler;
        
        shiften = false;
        kicken = false;        
        richting = Richting.ONDER;
        
        aantalBommen = standaardBommen;
        hoeveelVuur = standaardVuur;
        levens = standaardLevens;
        snelheid = standaardSnelheid;
    }

    // getters
    /**
     * Hiermee vraag je het nummer van de speler op.
     * 
     * @return het nummer van de speler.
     */
    public int getSpelerNr()
    {
        return spelerNr;
    }
    
    /**
     * Is de speler aan het ontploffen?
     * 
     * @return True indien de speler aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
    }
    
    /**
     * Wat is de x-positie van de speler?
     * 
     * @return De x-positie van de speler.
     */
    public int getX()
    {
        return xCo;
    }
    
    /**
     * Wat is de y-positie van de speler?
     * 
     * @return De y-positie van de speler.
     */
    public int getY()
    {
        return yCo;
    }
    
    /**
     * Kan de speler bommen shiften?
     * 
     * @return True als de speler bommen kan shiften.
     */
    public boolean kanShiften()
    {
        return shiften;
    }
    
    /**
     * Kan de speler bommen kicken?
     * 
     * @return True als de speler bommen kan kicken.
     */
    public boolean kanKicken()
    {
        return kicken;
    }
    
    /**
     * Wat is de richting van de speler?
     * ONDER, BOVEN, LINKS, RECHTS
     * 
     * @return De richting van de speler.
     */
    public Richting getRichting()
    {
        return richting;
    }

    /**
     * Hoeveel bommen heeft de speler nog?
     * 
     * @return het aantal bommen van de speler.
     */
    public int getAantalBommen()
    {
        return aantalBommen;
    }
    
    /**
     * Hoeveel bommen kan de speler maximum hebben?
     * 
     * @return het maximum aantal bommen van de speler.
     */
    public int getMaxBommen()
    {
        return maxBommen;
    }
    
    /**
     * Hoe krachtig is de explosie van de speler?
     * 
     * @return De 'krachtigheid' van de explosie van de speler.
     */
    public int getHoeveelVuur()
    {
        return hoeveelVuur;
    }
    
    /**
     * Hoe krachtig kan de explosie van de speler maximum zijn?
     * 
     * @return De maximale 'krachtigheid' van de explosie van de speler.
     */
    public int getMaxVuur()
    {
        return maxVuur;
    }
    
    /**
     * Hoeveel levens heeft de speler nog?
     * 
     * @return het aantal levens van de speler.
     */
    public int getLevens()
    {
        return levens;
    }
    
    /**
     * Hoeveel levens kan de speler maximum hebben?
     * 
     * @return het maximum aantal levens van de speler.
     */
    public int getMaxLevens()
    {
        return maxLevens;
    }
    
    /**
     * Welke snelheid heeft de speler?
     * 
     * @return snelheid De snelheid van de speler.
     */
    public int getSnelheid()
    {
        return snelheid;
    }
    
    /**
     * Welke snelheid kan de speler maximaal hebben?
     * 
     * @return maxSnelheid De maximale snelheid van de speler.
     */
    public int getMaxSnelheid()
    {
        return maxSnelheid;
    }
    
    /**
     * Wat ligt er onder de speler in het veld?
     * 
     * @return vwOnderSpeler Het voorwerp onder de speler.
     */
    public Voorwerp getVwOnderSpeler()
    {
        return vwOnderSpeler;
    }
    
    /**
     * Een niet nader omschreven Voorwerp s
     * 
     * @return s Nog een voorwerp van de speler.
     */
    public Voorwerp getS()
    {
        return s;
    }
    
    /**
     * Is de Voorwerp betreedbaar?
     * 
     * @return true indien betreedbaar.
     */ 
    public boolean isBetreedbaar()
    {
        return true;
    }

    /**
     * laat dit voorwerp vuur door van een bom?
     * 
     * @return true indien het vuur doorlaat.
     */
    public boolean isVuurDoorlaatbaar()
    {
        return true;
    }

    /**
     * Is de Voorwerp breekbaar, ten gevolgen van een explosie?
     * 
     * @return true indien breekbaar.
     */
    public boolean isBreekbaar()
    {
        return true;
    }
    
    // setters
    /**
    * Hiermee stel je het nummer van de speler in.
     * 
     * @param spelerNr Het nummer van de speler.
     */
    public void setSpelerNr(int spelerNr)
    {
        this.spelerNr = spelerNr;
    }
    
    /**
     * Laat de speler ontploffen.
     * 
     * @param vuur True indien de speler aan het ontploffen is.
     *
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
    
    /**
     * Stel de x-positie van de speler in.
     * 
     * @param xCo De x-positie van de speler.
     */
    public void setX(int xCo)
    {
        this.xCo = xCo;
    }
    
    /**
     * Stel de y-positie van de speler in.
     * 
     * @param yCo De y-positie van de speler.
     */
    public void setY(int yCo)
    {
        this.yCo = yCo;
    }
    
    /**
     * Laat de speler bommen shiften.
     * 
     * @param shiften True als de speler bommen mag shiften.
     */
    public void magShiften(boolean shiften)
    {
        this.shiften = shiften;
    }
    
    /**
     * Laat de speler bommen kicken.
     * 
     * @param kicken True als de speler bommen mag kicken.
     */
    public void magKicken(boolean kicken)
    {
        this.kicken = kicken;
    }
    
    /**
     * Geef de speler een richting.
     * ONDER, BOVEN, LINKS, RECHTS
     * 
     * @param richting De richting die de speler krijgt.
     */
    public void setRichting(Richting richting)
    {
        this.richting = richting;
    }
    
    /**
     * Hiermee pas je het aantal bommen van de speler aan.
     * 
     * @param aantalBommen Het nieuwe aantal bommen voor de speler.
     */
    public void setAantalBommen(int aantalBommen)
    {
        if (getAantalBommen() < getMaxBommen())
        {
            this.aantalBommen = aantalBommen;
        }
        else
        {
            this.aantalBommen = maxBommen;
        }
    }
    
    /**
     * Hiermee pas je de kracht van de explosie van de speler aan.
     * 
     * @param hoeveelVuur De 'krachtigheid' van de explosie van de speler.
     */
    public void setHoeveelVuur(int hoeveelVuur)
    {
        if (getHoeveelVuur() < getMaxVuur())
        {
            this.hoeveelVuur = hoeveelVuur;
        }
        else
        {
            this.hoeveelVuur = maxVuur;
        }
    }
    
    /**
     * Hiermee pas je de snelheid van de speler aan.
     * 
     * @param snelheid De snelheid van de speler.
     */
    public void setSnelheid(int snelheid)
    {
        if (getSnelheid() < getMaxSnelheid())
        {
            this.snelheid = snelheid;
        }
        else
        {
            this.snelheid = maxSnelheid;
        }
    }
    
    /**
     * Hoe krachtig kan de explosie van de speler maximum zijn?
     * 
     * @param maxVuur De maximale 'krachtigheid' van de explosie van de speler.
     */
    public void setMaxVuur(int maxVuur)
    {
        this.maxVuur = maxVuur;
    }
    
    /**
     * Verander het voorwerp onder de speler.
     * 
     * @param vwOnderSpeler Het nieuwe voorwerp dat je onder de speler wilt leggen.
     */
    public void setVwOnderSpeler(Voorwerp vwOnderSpeler)
    {
        this.vwOnderSpeler = vwOnderSpeler;
    }
    
    /**
     * Verander het niet nader omschreven Voorwerp s
     * 
     * @param s Het nieuwe voorwerp van de speler.
     */
    public void setS(Voorwerp s)
    {
        this.s = s;
    }
    
    /**
     * Hiermee vermoord je de speler, het aantal levens wordt op nul gezet.
     */
    public void maakDood()
    {
        this.levens = 0;
    }
    
    public void eenLevenMinder()
    {
        levens--;
    }
    
    public void eenLevenMeer()
    {
        if (getLevens() < getMaxLevens())
        {
            levens++;
        }
        else
        {
            this.levens = maxLevens;
        }
    }
}