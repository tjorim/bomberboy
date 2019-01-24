
/**
 * class Bom - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 18/11/2014
 */
public class Bom extends Voorwerp
{
    private int spelerNr;
    private int xCo, yCo;
    private boolean vuur = false;
    private long startTime;
    private boolean vuurToDo = true;
    private int timerVuur = 2000;
    private boolean doofToDo = true;
    private int timerDoof = 3000;
    private Voorwerp vwOnderBom;

    /**
     * Constructor voor objects van class Bom
     * 
     * @param spelerNr Het nummer van de speler.
     * @param xCo De x-positie van het portaal.
     * @param yCo De y-positie van het portaal.
     * @param vwOnderBom Het voorwerp onder de bom.
     */
    public Bom(int spelerNr, int xCo, int yCo, Voorwerp vwOnderBom)
    {
        setStartTime(System.currentTimeMillis());
        this.spelerNr = spelerNr;
        this.xCo = xCo;
        this.yCo = yCo;
        this.vwOnderBom = vwOnderBom;
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
     * Is de bom aan het ontploffen?
     * 
     * @return True indien de bom aan het ontploffen is.
     *
     */
    public boolean isVuur()
    {
        return vuur;
    }
    
    /**
     * Wat is de x-positie van de bom?
     * 
     * @return De x-positie van de bom.
     */
    public int getX()
    {
        return xCo;
    }
    
    /**
     * Wat is de y-positie van de bom?
     * 
     * @return De y-positie van de bom.
     */
    public int getY()
    {
        return yCo;
    }
    
    /**
     * Is de bom betreedbaar?
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
        return true;
    }

    /**
     * Geeft de starttijd van de bom terug.
     * 
     * @return startTime Het tijdstip waarop de bom is neergelegd in milliseconden.
     */
    public long getStartTime(){
        return startTime;
    }
    
    /**
     * Geeft terug of de bom nog moet ontploffen.
     * 
     * @return vuurToDo True als de bom nog moet ontploffen.
     */
    public boolean getVuurToDo() {
        return vuurToDo;
    }
    
    /**
     * Geeft terug na hoeveel tijd de bom moet ontploffen.
     * 
     * @return timerVuur De tijdsduur waarop de bom moet ontploffen in milliseconden.
     */
    public int getTimerVuur() {
        return timerVuur;
    }
    
    /**
     * Geeft terug of de bom nog moet worden gedoofd.
     * 
     * @return doofToDo True als de bom nog moet worden gedoofd.
     */
    public boolean getDoofToDo() {
        return doofToDo;
    }
    
    /**
     * Geeft terug na hoeveel tijd de bom moet worden gedoofd.
     * 
     * @return timerDoof De tijdsduur waarop de bom moet worden gedoofd in milliseconden.
     */
    public int getTimerDoof() {
        return timerDoof;
    }
    
    /**
     * Wat ligt er onder de bom in het veld?
     * 
     * @return het voorwerp onder de bom.
     */
    public Voorwerp getVwOnderBom()
    {
        return vwOnderBom;
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
     * Laat de bom ontploffen.
     * 
     * @param vuur True indien de bom aan het ontploffen is.
     *
     */
    public void maakVuur(boolean vuur)
    {
        this.vuur = vuur;
    }
    
    /**
     * Stel de x-positie van de bom in.
     * 
     * @param xCo De x-positie van de bom.
     */
    public void setX(int xCo)
    {
        this.xCo = xCo;
    }
    
    /**
     * Stel de y-positie van de bom in.
     * 
     * @param yCo De y-positie van de bom.
     */
    public void setY(int yCo)
    {
        this.yCo = yCo;
    }
    
    /**
     * Stel de starttijd van de bom in.
     * @param startTime De starttijd van de bom.
     */
    public void setStartTime(long startTime){
        this.startTime = startTime;
    }
    
    /**
     * Stel in of de bom nog moet ontploffen.
     * 
     * @param vuurToDo True als de bom nog moet ontploffen.
     */
    public void setVuurToDo(boolean vuurToDo) {
        this.vuurToDo = vuurToDo;
    }
    
    /**
     * Stel in na hoeveel tijd de bom moet ontploffen.
     * 
     * @param timerVuur De tijdsduur waarop de bom moet ontploffen in milliseconden.
     */
    public void setTimerVuur(int timerVuur) {
        this.timerVuur = timerVuur;
    }
    
    /**
     * Stel in of de bom nog moet worden gedoofd.
     * 
     * @param doofToDo True als de bom nog moet worden gedoofd.
     */
    public void setDoofToDo(boolean doofToDo) {
        this.doofToDo = doofToDo;
    }
    
    /**
     * Stel in na hoeveel tijd de bom moet worden gedoofd.
     * 
     * @param timerDoof De tijdsduur waarop de bom moet worden gedoofd in milliseconden.
     */
    public void setTimerDoof(int timerDoof) {
        this.timerDoof = timerDoof;
    }
    
    /**
     * Verander het voorwerp onder de bom.
     * 
     * @param vwOnderBom Het nieuwe voorwerp dat je onder de bom wilt leggen.
     */
    public void setVwOnderBom(Voorwerp vwOnderBom)
    {
        this.vwOnderBom = vwOnderBom;
    }
}