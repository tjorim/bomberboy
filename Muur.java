
/**
 * class Muur - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class Muur extends Voorwerp
{
    /**
     * Constructor voor objects van class Muur
     */
    public Muur()
    {
        // geef de data members een beginwaarde
    }

    // getters
    /**
     * Is de Voorwerp betreedbaar?
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
     * Een muur kan niet opfikken.
     * 
     * @return false Aangezien een muur niet kan opfikken.
     *
     */
    public boolean isVuur()
    {
        return false;
    }
    // setters
}