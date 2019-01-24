
/**
 * class Grond - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Grond extends Voorwerp
{
    private boolean vuur = false;

    /**
     * Constructor voor objects van class Grond
     */
    public Grond()
    {
        
    }

    // getters
    /**
     * Is de grond aan het ontploffen?
     * 
     * @return True indien de grond aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
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

    // setters
    /**
     * Laat de bom ontploffen.
     * 
     * @param vuur True indien de bom aan het ontploffen is.
     *
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
}