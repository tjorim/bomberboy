import EvaluateClass.*;
/**
 * class Animator - geef hier een beschrijving van deze class
 *
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 08/12/2014
 */
public class Animator implements Runnable
{
    @Intentional(ignoreSet=true, ignoreGet=true)
    private Model m;
    @Intentional(ignoreSet=true, ignoreGet=true)
    private GroteView gv;
    private long startTime;
    private long runTime;
    private int timerMuren;
    private boolean murenToDo;
    private boolean murenDoing;
    private boolean horizontaal;
    private int i;
    private int j;
    private int k;

    long previousMillis;        // will store last time LED was updated
    long interval;           // interval at which to blink (milliseconds)
    long currentMillis;

    /**
     * Constructor voor objects van class Animator
     * 
     * @param m het Model.
     * @param gv de GroteView.
     */
    public Animator(Model m, GroteView gv)
    {
        this.m = m;
        this.gv = gv;
        reset();
    }

    public void reset() {
        setStartTime(System.currentTimeMillis());
        timerMuren = 120000;
        murenToDo = true;
        murenDoing = false;
        horizontaal = true;
        i = 1;
        j = 1;
        k = 2;
        previousMillis = 0;
        interval = 400;
        gv.repaint();
    }

    /**
     * Deze functie is runnable en wordt gestart met het spel,
     * hier zitten alle timers in die functies oproepen in het spel.
     */
    public void run() {
        while(true){
            if (m.getIsHetSpelGereset())
            {
                m.setIsHetSpelGereset(false);
                reset();
            }
            try
            {
                Thread.sleep(17); // Hij moet dit niet continu doen, anders is het te belastend voor ons systeem.
                setRunTime(System.currentTimeMillis()-getStartTime());
                gv.repaint();
                if(m.speler2.getLevens() == 0 || m.speler1.getLevens() == 0)
                {
                    m.setIsErEenDode(true);
                }
                for (int i=0; i < m.getBreedte(); i++)
                {
                    for (int j=0; j < m.getHoogte(); j++)
                    {
                        Voorwerp vw = m.getVoorwerp(i, j);
                        if (vw instanceof Bom)
                        {
                            Bom bo = (Bom)vw;
                            if(System.currentTimeMillis()-bo.getStartTime() > bo.getTimerVuur() && bo.getVuurToDo())
                            {
                                bo.setVuurToDo(false);
                                m.bomVuur(bo.getSpelerNr(), bo.getX(), bo.getY(), bo.getStartTime());
                                bo.maakVuur(true);
                            }
                            else if(System.currentTimeMillis()-bo.getStartTime() > bo.getTimerDoof() && bo.getDoofToDo())
                            {
                                bo.setDoofToDo(false);
                                m.bomDoven(bo.getSpelerNr(), bo.getX(), bo.getY(), bo.getVwOnderBom());
                                bo.maakVuur(false);
                            }
                        }
                    }
                }
                if (getRunTime() > getTimerMuren() && getMurenToDo())
                {
                    setMurenToDo(false);
                    setMurenDoing(true);
                }
                if (getMurenDoing())
                {
                    currentMillis = System.currentTimeMillis();
                    if((currentMillis - previousMillis) > interval)
                    {
                        previousMillis = currentMillis;   // save the last time you blinked the LED
                        if (i < (m.getHoogte()/2)-2)
                        {
                            muren();
                        }
                        else
                        {
                            setMurenDoing(false);
                        }
                        if ((j == m.getBreedte()-i) && (k == m.getHoogte()-(i+1)))
                        {
                            i++;
                            j = i;
                            k = i+1;
                            horizontaal = true;
                        }
                    }
                }
            }
            catch (InterruptedException e) {}
        }
    }

    /**
     * Deze functie zorgt ervoor dat het veld kleiner wordt,
     * hij wordt na 2 minuten (120000 ms) opgeroepen.
     */
    public void muren() {
        if (horizontaal)
        {
            horizontaleMuur();
        }
        else
        {
            verticaleMuur();
        }
    }

    public void horizontaleMuur()
    {
        if(j < m.getBreedte()-i)
        {
            if (m.getVoorwerp(j, i) instanceof Speler)
            {
                Speler sl = (Speler)m.getVoorwerp(j, i);
                sl.maakDood();
                m.setIsErEenDode(true);
            }
            else if (m.getVoorwerp(m.getBreedte()-(j+1),m.getHoogte()-(i+1)) instanceof Speler)
            {
                Speler sl = (Speler)m.getVoorwerp(m.getBreedte()-(j+1),m.getHoogte()-(i+1));
                sl.maakDood();
                m.setIsErEenDode(true);
            }
            else
            {
                m.setVoorwerp(j, i, new Muur());
                m.setVoorwerp(m.getBreedte()-(j+1),m.getHoogte()-(i+1), new Muur());
            }
            gv.repaint();
            j++;
        }
        else
        {
            horizontaal = false;
        }
    }

    public void verticaleMuur()
    {
        if(k < m.getHoogte()-(i+1))
        {
            if (m.getVoorwerp(m.getBreedte()-(i+1),k) instanceof Speler)
            {
                Speler sl = (Speler)m.getVoorwerp(m.getBreedte()-(i+1),k);
                sl.maakDood();
                m.setIsErEenDode(true);
            }
            else if (m.getVoorwerp(i,m.getHoogte()-(k+1)) instanceof Speler)
            {
                Speler sl = (Speler)m.getVoorwerp(i,m.getHoogte()-(k+1));
                sl.maakDood();
                m.setIsErEenDode(true);
            }
            else
            {
                m.setVoorwerp(m.getBreedte()-(i+1),k, new Muur());
                m.setVoorwerp(i,m.getHoogte()-(k+1), new Muur());
            }
            gv.repaint();
            k++;
        }
        else
        {
            horizontaal = true;
        }
    }

    // getters
    /**
     * Geeft het tijdstip in millisecdonen waarop de thread in de Animator begonnen is terug.
     * @return startTime Het tijdstip in milliseconden waarop de thread in de Animator begonnen is.
     */
    public long getStartTime(){
        return startTime;
    }

    /**
     * Geeft hoe lang het spel gaande is in milliseconden terug.
     * @return runTime Hoe lang het spel gaande is in milliseconden.
     */
    public long getRunTime(){
        return runTime;
    }

    /**
     * Hiermee kan je de tijd oproepen na hoeveel milliseconden het veld kleiner gaat worden.
     * @return timerMuren De timertijd van de vallende muren in milliseconden.
     */
    public int getTimerMuren(){
        return timerMuren;
    }

    /**
     * @return murenToDo Moeten de muren nog worden gedaan?
     */
    public boolean getMurenToDo(){
        return murenToDo;
    }

    /**
     * @return murenDoing Zijn ze momenteel bezig met de muren?
     */
    public boolean getMurenDoing(){
        return murenDoing;
    }

    // setters
    /**
     * Legt de starttijd van de thread in de Animator vast.
     * @param startTime De starttijd van de thread in de Animator.
     */
    public void setStartTime(long startTime){
        this.startTime=startTime;
    }

    /**
     * Legt de huidige duur van het spel vast.
     * @param runTime De huidige duur van het spel.
     */
    public void setRunTime(long runTime){
        this.runTime=runTime;
    }

    /**
     * Hiermee kan je de tijd instellen na hoeveel milliseconden het veld kleiner moet worden.
     * @param timerMuren De timertijd van de vallende muren in milliseconden.
     */
    public void setTimerMuren(int timerMuren){
        this.timerMuren=timerMuren;
    }

    /**
     * @param murenToDo True als de muren nog gedaan moeten worden.
     */
    public void setMurenToDo(boolean murenToDo){
        this.murenToDo = murenToDo;
    }

    /**
     * @param murenDoing Trye als ze momenteel met de muren bezig zijn.
     */
    public void setMurenDoing(boolean murenDoing){
        this.murenDoing = murenDoing;
    }
}