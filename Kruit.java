/**
 * class Kruit - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 18/11/2014
 */
public class Kruit extends Voorwerp
{
    private boolean vuur = false;

    /**
     * Constructor voor objects van class Kruit
     */
    public Kruit()
    {
        
    }

    // getters
    /**
     * Is het kruit aan het ontploffen?
     * 
     * @return True indien het kruit aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
    }
        
    /**
     * Is het kruit betreedbaar?
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
     * Laat het kruit ontploffen.
     * 
     * @param vuur True indien het kruit aan het ontploffen is.
     *
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
}