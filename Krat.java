
/**
 * class Krat - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Krat extends Voorwerp
{
    private boolean vuur = false;

    /**
     * Constructor voor objects van class Krat
     */
    public Krat()
    {
        
    }

    // getters
    /**
     * Is het krat aan het ontploffen?
     * 
     * @return True indien het krat aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
    }
    
    /**
     * Is het krat betreedbaar?
     * 
     * @return true indien betreedbaar.
     */
    public boolean isBetreedbaar()
    {
        return false;
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
     * Is het krat breekbaar, ten gevolgen van een explosie?
     * 
     * @return true indien breekbaar.
     */
    public boolean isBreekbaar()
    {
        return true;
    }

    // setters
    /**
     * Laat het krat ontploffen.
     * 
     * @param vuur True indien het krat aan het ontploffen is.
     *
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
}