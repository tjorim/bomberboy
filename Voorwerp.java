
/**
 * Abstract class Voorwerp - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public abstract class Voorwerp
{
    //getters
    /**
     * Is het voorwerp betreedbaar?
     * 
     * @return true indien betreedbaar.
     */
    abstract public boolean isBetreedbaar();

    /**
     * Laat dit voorwerp vuur door van een bom?
     * 
     * @return true indien het vuur doorlaat.
     */
    abstract public boolean isVuurDoorlaatbaar();
    
    /**
     * Staat het voorwerp in vuur en vlam, ten gevolgen van een explosie?
     * 
     * @return true indien in brand.
     */
    abstract public boolean isVuur();

    /**
     * Kan je dit voorwerp oprapen?
     * 
     * @return true indien je het kan oprapen.
     */
    public boolean isOpneembaar()
    {
        return false;
    }

    /**
     * Is het voorwerp breekbaar, ten gevolgen van een explosie?
     * 
     * @return true indien breekbaar.
     */
    public boolean isBreekbaar()
    {
        return false;
    }
}
