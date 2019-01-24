import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * test class ModelTest - geef hier een beschrijving van deze class
 *
 * @author Jorim Tielemans
 * @version 08/01/2015
 */
public class ModelTest
{    
    /**
     * Constructor voor test class ModelTest
     */
    public ModelTest()
    {
    }

    /**
     * Opzetten van de test fixture.
     *
     * Aanroep voor elke test case method.
     * 
     * Hier zou ik in principe de verschillende mogelijk gebruikte levels kunnen aanroepen,
     * ik heb echter besloten dit pas in de testcase zelf te doen om geen onnodige resources te gebruiken.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Verwijderen van de test fixture.
     *
     * Aanroep na elke test case method.
     */
    @After
    public void tearDown()
    {
    }

    /**
     * Met deze methode testen we of het Portaal 'gesloten' wordt als een speler deze betreedt;
     * vervolgens weer geopend wordt als de speler eraf gaat;
     * er enkel een speler op kan als ze vrij zijn;
     * of een speler tegoei wordt geteleporteerd naar het andere portaal.
     */
    @Test
    public void PortaalBetreden()
    {
        Model m = new Model(3); // als level 3 wordt ingeladen zijn er meteen portalen aanwezig
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();
        for(int aantalBewegingen = 0; aantalBewegingen < 10000; aantalBewegingen++)
        {
            m.beweegOnder(1);
            m.beweegOnder(1);
            m.beweegRechts(1); // speler1 staat voor portaal A en ze moeten nog betreedbaar zijn
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegRechts(1); // speler1 is door portaal A gegaan en staat nu op B, beide moeten dus gesloten zijn
            assertFalse(m.portaal1A.isBetreedbaar());
            assertFalse(m.portaal1B.isBetreedbaar());
            m.beweegOnder(1); // speler1 is van portaal B afgestapt, beide moeten dus terug open zijn
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegOnder(1);
            m.beweegRechts(1);
            m.beweegRechts(1);

            m.beweegBoven(1);
            m.beweegBoven(1);
            m.beweegLinks(1); // ze moeten nog steeds open zijn want de speler staat nog voor portaal B
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegLinks(1); // speler1 is terug door het portaal gegaan, deze keer door B naar A
            assertFalse(m.portaal1A.isBetreedbaar());
            assertFalse(m.portaal1B.isBetreedbaar());

            m.beweegBoven(2);
            m.beweegBoven(2);
            m.beweegLinks(2); // nu staat speler 2 voor portaal B, omdat speler 1 er nog opstaat aan de andere kant zijn ze nog bezet
            assertFalse(m.portaal1A.isBetreedbaar());
            assertFalse(m.portaal1B.isBetreedbaar());
            int xCo = m.speler2.getX(); // haal de xCo op van waar speler 2 nu staat
            int yCo = m.speler2.getY(); // haal de yCo op van waar speler 2 nu staat
            m.beweegLinks(2); // de speler kan niet verder naar links, het portaal is bezet, de coordinaten (zie hieronder) zijn dus niet verandert
            assertEquals(xCo, m.speler2.getX());
            assertEquals(yCo, m.speler2.getY());

            m.beweegBoven(1); // de speler is er weer afgestapt en ze staan dus terug open
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegBoven(1);
            m.beweegLinks(1);
            m.beweegLinks(1);

            m.beweegLinks(2); // de speler kan nu wel verder naar links, hij wordt verplaatst naar portaal B, de coordinaten (zie hieronder) van de speler zijn nu dus gelijk aan die van het portaal
            assertEquals(m.portaal1A.getX(), m.speler2.getX());
            assertEquals(m.portaal1A.getY(), m.speler2.getY());
            assertFalse(m.portaal1A.isBetreedbaar());
            assertFalse(m.portaal1B.isBetreedbaar());
            m.beweegBoven(2);
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegBoven(2);
            m.beweegLinks(2);
            m.beweegLinks(2);

            m.beweegOnder(2);
            m.beweegOnder(2);
            m.beweegRechts(2);
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegRechts(2);
            assertFalse(m.portaal1A.isBetreedbaar());
            assertFalse(m.portaal1B.isBetreedbaar());
            m.beweegOnder(2);
            assertTrue(m.portaal1A.isBetreedbaar());
            assertTrue(m.portaal1B.isBetreedbaar());
            m.beweegOnder(2);
            m.beweegRechts(2);
            m.beweegRechts(2);
        }
    }

    /**
     * Met deze methode laten we een random speler in random richtingen bewegen
     * vervolgens testen we steeds of deze speler zich nog wel in het veld bevind (wat in principe niet anders kan aangezien het volledige veld is omringd door een muur).
     */
    @Test
    public void Binnenblijven()
    {
        Model m = new Model(4);
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();
        for(int aantalBewegingen = 0; aantalBewegingen < 10000; aantalBewegingen++)
        {
            willekeurigeActie(m); // laat iemand iets willekeurigs doen
            assertTrue((m.speler1.getX() > 0) && (m.speler1.getX() < 21)); // zit speler 1 nog binnen het x-bereik?
            assertTrue((m.speler1.getY() > 0) && (m.speler1.getY() < 15)); // zit speler 1 nog binnen het y-bereik?
            assertTrue((m.speler2.getX() > 0) && (m.speler2.getX() < 21)); // zit speler 2 nog binnen het x-bereik?
            assertTrue((m.speler2.getY() > 0) && (m.speler2.getY() < 15)); // zit speler 2 nog binnen het y-bereik?
        }
    }

    /**
     * Met deze methode laten we een random speler in random richtingen bewegen
     * vervolgens testen we steeds of de spelers en portalen nog aanwezig zijn (al dan niet onder elkaar).
     */
    @Test
    public void Zelfdeblijven()
    {
        Model m = new Model(3);
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();
        for(int aantalBewegingen = 0; aantalBewegingen < 10000; aantalBewegingen++)
        {
            willekeurigeActie(m); // laat weer iemand iets willekeurigs doen
            if(m.getVoorwerp(m.speler1.getX(), m.speler1.getY()) == m.speler1)
            {
                assertEquals(m.getVoorwerp(m.speler1.getX(), m.speler1.getY()), m.speler1); // is speler 1 aanwezig op zijn plaats?
            }
            else
            {
                assertEquals(m.speler2.getVwOnderSpeler(), m.speler1); // anders zou hij onder speler 2 moeten zitten.
            }
            if(m.getVoorwerp(m.speler2.getX(), m.speler2.getY()) == m.speler2) // analoog aan vorige
            {
                assertEquals(m.getVoorwerp(m.speler2.getX(), m.speler2.getY()), m.speler2); // is speler 2 aanwezig op zijn plaats?
            }
            else
            {
                assertEquals(m.speler1.getVwOnderSpeler(), m.speler2); // anders zou hij onder speler 1 moeten zitten.
            }
            if(m.getVoorwerp(m.portaal1A.getX(), m.portaal1A.getY()) == m.portaal1A)
            {
                assertEquals(m.getVoorwerp(m.portaal1A.getX(), m.portaal1A.getY()), m.portaal1A); // ligt portaal A nog juist?
            }
            else
            {
                if(m.speler1.getVwOnderSpeler() == m.portaal1A)
                {
                    assertEquals(m.speler1.getVwOnderSpeler(), m.portaal1A); // misschien staat speler 1 er op
                }
                else
                {
                    assertEquals(m.speler2.getVwOnderSpeler(), m.portaal1A); // of speler 2
                }
            }
            if(m.getVoorwerp(m.portaal1B.getX(), m.portaal1B.getY()) == m.portaal1B) // analoog aan vorige
            {
                assertEquals(m.getVoorwerp(m.portaal1B.getX(), m.portaal1B.getY()), m.portaal1B); // ligt portaal B nog juist?
            }
            else
            {
                if(m.speler1.getVwOnderSpeler() == m.portaal1B)
                {
                    assertEquals(m.speler1.getVwOnderSpeler(), m.portaal1B); // misschien staat speler 1 er op
                }
                else
                {
                    assertEquals(m.speler2.getVwOnderSpeler(), m.portaal1B); // of speler 2
                }
            }
        }
    }

    /**
     * Deze functie test of er een bom wordt gelegd en of deze nadien verdwijnd (dus terug grond is geworden).
     */
    @Test
    public void wordtDeBomGelegd()
    {
        Model m = new Model(4);
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();
        for(int aantalBommen = 0; aantalBommen < 3; aantalBommen++)
        {
            long runTime = 0;
            m.beweegOnder(1);
            m.beweegOnder(1);
            m.beweegRechts(1);
            m.beweegRechts(1);
            m.legBom(1);
            long startTime=System.currentTimeMillis();
            assertEquals(m.getVoorwerp(m.speler1.getX(), m.speler1.getY()), m.speler1);
            int speler1X = m.speler1.getX();
            int speler1Y = m.speler1.getY();
            m.beweegOnder(1);
            m.beweegOnder(1);
            m.beweegRechts(1);
            m.beweegRechts(1);
            assertEquals(m.getVoorwerp(m.speler1.getX(), m.speler1.getY()), m.speler1);
            assertTrue(m.getVoorwerp(speler1X, speler1Y) instanceof Bom);
            while (runTime < 4000)
            {
                runTime = System.currentTimeMillis() - startTime;
            }
            assertEquals(m.getVoorwerp(m.speler1.getX(), m.speler1.getY()), m.speler1);
            assertTrue(m.getVoorwerp(speler1X, speler1Y) instanceof Grond);
        }
    }

    /**
     * Deze functie test of ik een leven kwijtspeel als ik een bom neerleg zonder ver genoeg weg te lopen (of het hoekje niet omga, letterlijk dan).
     * Wordt de variabele of er een dode is ook correct uitgevoerd, dit zou het spel moeten stilleggen
     * --> in latere versies (ja, ik heb nog verscheidene verbeteringen&vernieuwingen aangebracht) wordt dit overal beter gecontroleerd en verdere acties of geluiden voorkomen.
     */
    @Test
    public void speelIkEenLevenKwijt()
    {
        Model m = new Model(4);
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();
        int levens = 3;
        assertFalse(m.getIsErEenDode());
        for(int aantalBommen = 0; aantalBommen < 3; aantalBommen++)
        {
            long runTime = 0;
            m.beweegOnder(1);
            m.beweegOnder(1);
            m.beweegRechts(1);
            m.beweegRechts(1);
            assertEquals(levens, m.speler1.getLevens());
            m.legBom(1);
            levens--;
            long startTime=System.currentTimeMillis();
            int speler1X = m.speler1.getX();
            int speler1Y = m.speler1.getY();
            m.beweegOnder(1);
            while (runTime < 4000)
            {
                runTime = System.currentTimeMillis() - startTime;
            }
            assertEquals(levens, m.speler1.getLevens());
            m.beweegOnder(1);
            m.beweegRechts(1);
            m.beweegRechts(1);
        }
        assertTrue(m.getIsErEenDode());
    }

    /**
     * Deze functie gaat testen of level 2 (met het kruitspoor) correct wordt ingeladen.
     * 
     * Bij het inladen kunnen bepaalde locaties gewoon overschreven, om die reden kon niet gewoon dezelfde lussen worden gebruikt;
     * van zodra hij een waarde tegenkomt die niet klopt (ook al kan die in een latere lus wel kloppen) stopt de testing.
     * 
     * Net toen ik klaarwas besefte ik dar er nog andere manier was om dit te controleren:
     *     - een variabele int aantalJuiste gebruiken, telkens als hij iets tegenkomt dat wel juist is (ook al was deze eerder fout), wordt deze variabele verhoogt met 1.
     *          Aangezien de 2D array een rechthoek is van 21x15 zou de variabele op het einde overeen moeten komen met 315
     *          --> deze zou waarschijnlijk niet werken door het vorkomen van dubbele juiste waardes
     *     - een nieuwe 2D array aanmaken van 21x15, deze wordt opgevuld met booleans (allemaal false) als hij deze keer een juiste overeenkomst in het level-rooster tegenkomt,
     *          gaat hij diezelfde locatie in het nieuwe 'rooster' op true zetten, was deze eerder nog niet herkend als juist of was hij al wel herkend als juist
     *          wordt deze op juist gezet of blijft deze juist --> dubbele waardes worden niet extra geteld en de laatste controle blijft geldig net zoals bij het echt inladen van een level
     *          gaan we nadien deze nieuwe rooster controleren en blijken alle locaties true te zijn, dan is de test geslaagd.
     *          
     * Hoewel onderstaande gebruikte manier minder handig is waren er toch manieren om vlotter tot de onderstaande regels te komen.
     * Ik heb in plaats van direct assertTrue(...); te controleren deze met een System.out.println(...); kunnen uitprinten, de code heb ik tussen "" laten staan en de variabelen niet,
     * na die welbepaalde functie uit te voeren kon ik alle regels kopieren, sorteren in wordt, en de dubbele er uit filteren (rekening houdend met de juiste volgorde).
     */
    @Test
    public void laadEenLevelIn()
    {
        Model m = new Model(2);
        GroteView gv = new GroteView(m);
        Animator a = new Animator(m,gv);
        Thread t = new Thread(a);
        t.start();

        assertEquals(2, m.getLevelNr());
        assertFalse(m.getIsErEenDode());
        assertFalse(m.getIsHetSpelGereset());

        assertTrue(m.getVoorwerp(0, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 1) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 3) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 5) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 7) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 9) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 11) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 13) instanceof Muur);
        assertTrue(m.getVoorwerp(0, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(1, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(1, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 2) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 3) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(1, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(1, 6) instanceof Grond); 
        assertEquals(m.getVoorwerp(1, 7), m.speler1);
        assertTrue(m.getVoorwerp(1, 8) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(1, 10) instanceof Grond);
        assertTrue(m.getVoorwerp(1, 11) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 12) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(1, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(2, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(2, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(2, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(2, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(2, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(2, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(2, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(2, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(3, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(3, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(3, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 10) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(3, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(3, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(4, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(4, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(5, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(5, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(5, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(5, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 4) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 5) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 6) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 7) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 8) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 9) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 10) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(5, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(5, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(5, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(6, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(6, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(6, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(6, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(6, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(6, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(6, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(6, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(7, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(7, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(7, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 10) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(7, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(7, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(8, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(8, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(8, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(8, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(8, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(8, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(8, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(8, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(9, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(9, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(9,  4) instanceof Krat);
        assertTrue(m.getVoorwerp(9,  5) instanceof Krat);
        assertTrue(m.getVoorwerp(9, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 9) instanceof Krat);
        assertTrue(m.getVoorwerp(9, 10) instanceof Krat);
        assertTrue(m.getVoorwerp(9, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(9, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(9, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(10, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(10, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(10, 2) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(10, 4) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 5) instanceof Krat);
        assertTrue(m.getVoorwerp(10, 6) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(10, 8) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 9) instanceof Krat);
        assertTrue(m.getVoorwerp(10, 10) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(10, 12) instanceof Muur); 
        assertTrue(m.getVoorwerp(10, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(10, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(11, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(11, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(11, 4) instanceof Krat);
        assertTrue(m.getVoorwerp(11, 5) instanceof Krat);
        assertTrue(m.getVoorwerp(11, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 9) instanceof Krat);
        assertTrue(m.getVoorwerp(11, 10) instanceof Krat);
        assertTrue(m.getVoorwerp(11, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(11, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(11, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(12, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(12, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(12, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(12, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(12, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(12, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(12, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(12, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(13, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(13, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(13, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 10) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(13, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(13, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(14, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(14, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(14, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(14, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(14, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(14, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(14, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(14, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(15, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(15, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(15, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(15, 3) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 4) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 5) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 6) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 7) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 8) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 9) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 10) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 11) instanceof Kruit);
        assertTrue(m.getVoorwerp(15, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(15, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(15, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 1) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(16, 13) instanceof Grond); 
        assertTrue(m.getVoorwerp(16, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(17, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(17, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(17, 2) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 6) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 10) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 12) instanceof Grond); 
        assertTrue(m.getVoorwerp(17, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(17, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(18, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 3) instanceof Grond); 
        assertTrue(m.getVoorwerp(18, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(18, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 7) instanceof Grond); 
        assertTrue(m.getVoorwerp(18, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(18, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 11) instanceof Grond); 
        assertTrue(m.getVoorwerp(18, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(18, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(18, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(19, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(19, 1) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 2) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 3) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 4) instanceof Grond); 
        assertTrue(m.getVoorwerp(19, 5) instanceof Grond); 
        assertTrue(m.getVoorwerp(19, 6) instanceof Krat);
        assertEquals(m.getVoorwerp(19, 7), m.speler2);
        assertTrue(m.getVoorwerp(19, 8) instanceof Grond); 
        assertTrue(m.getVoorwerp(19, 9) instanceof Grond); 
        assertTrue(m.getVoorwerp(19, 10) instanceof Grond); 
        assertTrue(m.getVoorwerp(19, 11) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 12) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 13) instanceof Krat);
        assertTrue(m.getVoorwerp(19, 14) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 0) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 1) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 2) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 3) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 4) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 5) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 6) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 7) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 8) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 9) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 10) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 11) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 12) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 13) instanceof Muur);
        assertTrue(m.getVoorwerp(20, 14) instanceof Muur);
    }

    /**
     * Deze functie laat een bepaalde speler een willekeurige actie doen. Deze kan ook worden aangesproken door de volgende functie, deze geeft dan echter een willekeurige speler mee.
     * Om te voorkomen dat de speler niet te veel bommen legt, gebeurt dit enkel als het willekeurige nummertje 5 wordt getrokken.
     * In alle ander gevallen gaat de fucntie willekeurigBewegen() worden opgeroepen om een willekeurige richting te kiezen en deze laten uit te voeren door de eerder vernoemde speler.
     * 
     * 1,2,3,4 = bewegen
     * 5 = bom leggen
     */
    public void willekeurigeActie(Model testM, int rndspeler)
    {
        if (!testM.getIsErEenDode())
        {
            int rndactie = (int)(1+5*Math.random());
            if (rndactie == 7)
            {
                testM.legBom(rndspeler);
            }
            else
            {
                willekeurigBewegen(testM, rndspeler);
            }
        }
    }

    /**
     * Deze functie laat een willekeurige speler een willekeurige actie doen, met het willekeurige nummer dat wordt getrokken wordt de vorige functie functie uitgevoerd.
     */
    public void willekeurigeActie(Model testM)
    {
        if (!testM.getIsErEenDode())
        {
            int rndspeler = (int)(1+2*Math.random());
            willekeurigeActie(testM, rndspeler);
        }
    }

    /**
     * Deze functie laat een bepaalde speler een willekeurige beweging doen. Deze kan ook worden aangesproken door de volgende functie, deze geeft dan echter een willekeurige speler mee.
     * 
     * 1=beweeglinks
     * 2=beweegrecht
     * 3=beweegonder
     * 4=beweegboven
     */
    public void willekeurigBewegen(Model testM, int rndspeler)
    {
        if (!testM.getIsErEenDode())
        {
            int rndrichting = (int)(1+4*Math.random());
            switch (rndrichting)
            {
                case 1: testM.beweegLinks(rndspeler); break;
                case 2: testM.beweegRechts(rndspeler); break;
                case 3: testM.beweegOnder(rndspeler); break;
                case 4: testM.beweegBoven(rndspeler); break;
            }
        }
    }

    /**
     * Deze functie laat een willekeurige speler een willekeurige beweging doen, met het willekeurige nummer dat wordt getrokken wordt de vorige functie uitgevoerd.
     */
    public void willekeurigBewegen(Model testM)
    {
        if (!testM.getIsErEenDode())
        {
            int rndspeler = (int)(1+2*Math.random());
            willekeurigBewegen(testM, rndspeler);
        }
    }
}