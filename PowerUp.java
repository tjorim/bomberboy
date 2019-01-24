
/**
 * class PowerUp - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 24/11/2014
 */
public class PowerUp extends Voorwerp
{
    private Power power;
    private boolean open;
    private boolean vuur;

    /**
     * Constructor voor objects van class PowerUp
     * 
     * @param power EXTRA_BOM, MEER_VLAM, GOLDENFLAME, KICKER, EXTRA_LEVEN, SHIFTER, SNELLER_LOPEN
     */
    public PowerUp(Power power)
    {
        this.power = power;
        open = false;
        vuur = false;
    }
    
    // getters
    /**
     * Wat zit er in de krat?
     * 
     * @return De inhoud.
     */
    public Power getPower() {
        return power;
    }
    
    /**
     * Is de krat aan het ontploffen?
     * 
     * @return True indien de krat aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
    }
    
    /**
     * Is de krat al open? 
     * 
     * @return True als de krat open is.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Is de Voorwerp betreedbaar?
     * 
     * @return true indien betreedbaar.
     */
    public boolean isBetreedbaar()
    {
        if (open) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * laat dit voorwerp vuur door van een bom??
     * 
     * @return true indien het vuur doorlaat.
     */
    public boolean isVuurDoorlaatbaar()
    {
        return true;
    }

    /**
     * Kan je dit voorwerp oprapen?
     * 
     * @return true indien je het kan oprapen.
     */
    public boolean isOpneembaar()
    {
        return true;
    }

    // setters
    /**
     * Laat de krat ontploffen.
     * 
     * @param vuur True indien de krat aan het ontploffen is.
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
    
    /**
     * Maak de krat open.
     * 
     * @param open True als het krat open is
     */
    public void maakOpen(boolean open) {
        this.open = open;
    }
}