
/**
 * class Portaal - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Portaal extends Voorwerp
{
    private int portaalNr;
    private int poortNr;
    private int xCo, yCo;
    private boolean vrij;

    /**
     * Constructor voor objects van class Portaal
     * 
     * @param poortNr Het nummer van de poort.
     * @param xCo De x-positie van het portaal.
     * @param yCo De y-positie van het portaal.
     */
    public Portaal( int poortNr, int xCo, int yCo)
    {
        this.portaalNr = portaalNr;
        this.poortNr   = poortNr;
        this.xCo       = xCo;
        this.yCo       = yCo;
        vrij           = true;
    }

    // getters
    /**
     * Wat is de x-positie van het portaal?
     * 
     * @return xCo De x-positie van het portaal.
     */
    public int getX()
    {
        return xCo;
    }

    /**
     * Wat is de y-positie van het portaal?
     * 
     * @return yCo De y-positie van het portaal.
     */
    public int getY()
    {
        return yCo;
    }
    
    /**
     * Hiermee vraag je het nummer van het portaal op.
     * 
     * @return portaalNr Het nummer van het portaal.
     */
    public int getPortaalNr()
    {
        return portaalNr;
    }
    
    /**
     * Hiermee vraag je het nummer van de poort op.
     * 
     * @return poortNr Het nummer van de poort.
     */
    public int getPoortNr()
    {
        return poortNr;
    }

    /**
     * Hiermee vraag je of de er niemand in het portaal zit .
     * 
     * @return vrij True indien het portaal vrij is.
     */
    public boolean isVrij()
    {
        return vrij;
    }

    /**
     * laat dit voorwerp vuur door van een bom?
     * 
     * @return true indien het vuur doorlaat.
     */
    public boolean isVuurDoorlaatbaar()
    {
        return false;
    }

    /**
     * Is de Voorwerp betreedbaar?
     * 
     * @return true indien betreedbaar.
     */
    public boolean isBetreedbaar()
    {
        if (vrij) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Een portaal kan niet opfikken.
     * 
     * @return false Aangezien een portaal niet kan opfikken.
     */
    public boolean isVuur()
    {
        return false;
    }

    // setters
    /**
     * Hiermee stel je het nummer van het portaal in.
     * 
     * @param portaalNr Het nummer van het portaal.
     */
    public void setPortaalNr(int portaalNr)
    {
        this.portaalNr = portaalNr;
    }
    
    /**
     * Hiermee stel je het nummer van de poort in.
     * 
     * @param poortNr Het nummer van de poort.
     */
    public void setPoortNr(int poortNr)
    {
        this.poortNr = poortNr;
    }

    /**
     * Stel de x-positie van het portaal in.
     * 
     * @param xCo De x-positie van het portaal.
     */
    public void setX(int xCo)
    {
        this.xCo = xCo;
    }

    /**
     * Stel de y-positie van het portaal in.
     * 
     * @param yCo De y-positie van het portaal.
     */
    public void setY(int yCo)
    {
        this.yCo = yCo;
    }

    /**
     * Hiermee kan je zeggen of het portaal leeg is, dus vrij.
     * 
     * @param vrij True indien het portaal vrij is.
     */
    public void maakVrij(boolean vrij)
    {
        this.vrij = vrij;
    }
}