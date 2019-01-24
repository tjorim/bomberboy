import java.util.*;
import EvaluateClass.*;
import java.io.*;
import sun.audio.*;
/**
 * De klasse van het Model.
 * 
 * @author Willem Vansimpsen
 * @author Jorim Tielemans
 * @version 08/12/2014
 */
public class Model
{
    @Intentional(ignoreSet=true)
    private Voorwerp[][] rooster;
    @Intentional(ignoreSet=true)
    private static int breedte = 21;  //de breedte van het raster
    @Intentional(ignoreSet=true)
    private static int hoogte = 15; //de hoogte van het raster
    private int doelX, doelY; // coordinaten van het doel
    private int volgX, volgY; // coordinaten van het doel
    private Voorwerp doel, s1, s2, volg;
    Speler speler1;
    Speler speler2;
    private int levelNr;
    private int tellerAantalPU; 
    private boolean isErEenDode;
    private boolean isHetSpelGereset;
    private boolean hijligt;
    Portaal portaal1A = new Portaal(1, 3, 3);
    Portaal portaal1B = new Portaal(2, breedte-4, hoogte-4);
    /**
     * Hier gaan we alle geluidjes inladen.
     * Source: http://sourceforge.net/projects/bombermanja/
     */
    String Tijdbom              = "wav/Tijdbom.wav"; // Source: http://soundbible.com/1203-Time-Bomb.html
    String BombDrop             = "wav/BombDrop.wav";
    String BombeExplode         = "wav/BombeExplode.wav";
    String Die                  = "wav/Die.wav";
    String GameStart            = "wav/GameStart.wav";
    String Life                 = "wav/Life.wav";
    String ReadyFight           = "wav/ReadyFight.wav";
    String Select               = "wav/Select.wav";
    String Warning              = "wav/Warning.wav";
    String WhenKick             = "wav/WhenKick.wav";
    String WhenShift            = "wav/WhenShift.wav";

    /**
     * @param levelNr Het level van het spel.
     */
    public Model(int levelNr)
    {
        this.levelNr = levelNr;
        init(levelNr);
    }

    /**
     * @param levelNr Het level van het spel.
     */
    public void reset(int levelNr)
    {
        this.levelNr = levelNr;
        init(levelNr);
        setIsHetSpelGereset(true);
    }

    /**
     * Constructor voor objecten van de klasse Model.
     * Bij het starten van het spel wordt dit ingeladen, het is als het ware het level met al
     * zijn geplaatste bouwblokken.
     * 
     * NIEUW 18/11/2014:
     * Levels opgesplitst, afhnkelijk van de startwaarde (lees: levelNr) wordt er een ander level
     * ingeladen. Hieronder staat het gemeenschappelijke deel, de resetbutton moet samen met het
     * textfield zorgen dat we nadien een ander level kunnen starten.
     * 
     * Legende:
     * x    muur: kan niet kapot
     * d    doos: kan wel kapot
     * b    speler1 : de blauwe speler
     * r    speler2 : de rode speler
     * k    kruit : ontploft volledig mee als de explosie van een bom dit raakt
     * p    portaal1: het gele portaal
     * p    portaal2: het groene portaal
     * lege plaatsen in gewoon Grond()
     * 
     * ToDo: nog een paar getters en setters maken en gebruiken, zie EvaluateClass.
     * 
     * @param levelNr Het level van het spel.
     */
    public void init(int levelNr)
    {
        this.levelNr = levelNr;
        rooster = new Voorwerp[breedte][hoogte];
        speelGeluidje(GameStart);
        setIsErEenDode(false);
        setIsHetSpelGereset(false);

        if (levelNr == 1)
        {
            speler1 = new Speler(1, 1, 1, new Grond());
            speler2 = new Speler(2, breedte-2, hoogte-2, new Grond());
            speler1.setMaxVuur((getHoogte()-3)/2);
            speler2.setMaxVuur((getHoogte()-3)/2);
            tellerAantalPU = 59;
        }
        else if (levelNr == 2)
        {
            speler1 = new Speler(1, 1, 7, new Grond());
            speler2 = new Speler(2, breedte-2, hoogte-8, new Grond());
            speler1.setMaxVuur((getHoogte()-3)/2);
            speler2.setMaxVuur((getHoogte()-3)/2);
            tellerAantalPU = 0;
        }
        else if (levelNr == 3)
        {
            speler1 = new Speler(1, 1, 1, new Grond());
            speler2 = new Speler(2, breedte-2, hoogte-2, new Grond());
            speler1.setMaxVuur((getHoogte()-3)/2);
            speler2.setMaxVuur((getHoogte()-3)/2);
            tellerAantalPU = 49;
        }
        else if (levelNr == 4)
        {
            speler1 = new Speler(1, 1, 1, new Grond());
            speler2 = new Speler(2, breedte-2, hoogte-2, new Grond());
            speler1.setMaxVuur((getHoogte()-3)/2);
            speler2.setMaxVuur((getHoogte()-3)/2);
            tellerAantalPU = 0;
            // speler1
            speler1.setAantalBommen(speler1.getMaxBommen());
            speler1.setHoeveelVuur(speler1.getMaxVuur());
            speler1.setSnelheid(speler1.getMaxSnelheid());
            speler1.magKicken(true);
            speler1.magShiften(true);
            //speler2
            speler2.setAantalBommen(speler2.getMaxBommen());
            speler2.setHoeveelVuur(speler2.getMaxVuur());
            speler2.setSnelheid(speler2.getMaxSnelheid());
            speler2.magKicken(true);
            speler2.magShiften(true);
        }

        for (int muurX = 0; muurX < breedte; muurX++) {
            rooster[muurX][0]        = new Muur();
            rooster[muurX][hoogte-1] = new Muur();
        }
        for (int muurY = 1; muurY < hoogte-1; muurY++) {
            rooster[0][muurY]         = new Muur();
            rooster[breedte-1][muurY] = new Muur();
        }
        for (int muurX = 2; muurX < breedte-2; muurX = muurX+2){
            for (int muurY = 2; muurY < hoogte-2; muurY = muurY+2){
                rooster[muurX][muurY] = new Muur();
            }
        }

        switch (levelNr)
        {
            case 1: level1(); break;
            case 2: level2(); break;
            case 3: level3(); break;
            case 4: level4(); break;
        }

        verdeelPowerUp();
    }

    /**
     * Dit is level 1: Old Boring Level. Hier zitten 181 dozen in,
     * nog een bepaald percentage moet gevuld worden met PowerUps.
     * 
     * Voorbeeld:
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
    x   b       d   d   d   d   d   d   d   d   d   d   d   d   d   d   d           x
    x       x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x       x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x       x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x       x
    x           d   d   d   d   d   d   d   d   d   d   d   d   d   d   d       r   x
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
     */
    public void level1()
    {
        for (int kratX = 1; kratX < breedte-1; kratX++){
            for (int kratY = 1; kratY < hoogte-1; kratY++){
                if((kratX % 2) == 1){
                    rooster[kratX][kratY] = new Krat();
                }
                if((kratY % 2) == 1){
                    rooster[kratX][kratY] = new Krat();
                }
            }
        }
        setVoorwerp(speler1.getX(),   speler1.getY(),   speler1);
        setVoorwerp(speler1.getX()+1, speler1.getY(),   new Grond());
        setVoorwerp(speler1.getX(),   speler1.getY()+1, new Grond());
        setVoorwerp(speler2.getX(),   speler2.getY(),   speler2);
        setVoorwerp(speler2.getX()-1, speler2.getY(),   new Grond());
        setVoorwerp(speler2.getX(),   speler2.getY()-1, new Grond());
        //speler3
        setVoorwerp(1, hoogte-2, new Grond());
        setVoorwerp(2, hoogte-2, new Grond());
        setVoorwerp(1, hoogte-3, new Grond());
        //speler4
        setVoorwerp(breedte-2, 1, new Grond());
        setVoorwerp(breedte-3, 1, new Grond());
        setVoorwerp(breedte-2, 2, new Grond());
    }

    /**
     * Dit is level 2: FIRE EVERYWHERE!!! Xoxo. Hier zijn geen PowerUps aanwezig.
     * In het midden bevind zich een cirkel van kruit (eigenlijk een rechthoek),
     * dit ontploft mee als vuur van een explosie het raakt.
     * 
     * Voorbeeld:
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
    x   d   d   d                                                       d   d   d   x
    x   d   x       x       x       x       x       x       x       x       x   d   x
    x   d               k   k   k   k   k   k   k   k   k   k   k               d   x
    x       x       x   k   x       x   d   x   d   x       x   k   x       x       x
    x                   k               d   d   d               k                   x
    x       x       x   k   x       x       x       x       x   k   x       x   d   x
    x   b               k                                       k               r   x
    x   d   x       x   k   x       x       x       x       x   k   x       x       x
    x                   k               d   d   d               k                   x
    x       x       x   k   x       x   d   x   d   x       x   k   x       x       x
    x   d               k   k   k   k   k   k   k   k   k   k   k               d   x
    x   d   x       x       x       x       x       x       x       x       x   d   x
    x   d   d   d                                                       d   d   d   x
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
     */
    public void level2()
    {
        for (int grondX = 1; grondX < breedte-1; grondX++){
            for (int grondY = 1; grondY < hoogte-1; grondY++){
                if((grondX % 2) == 1){
                    rooster[grondX][grondY] = new Grond();
                }
                if((grondY % 2) == 1){
                    rooster[grondX][grondY] = new Grond();
                }
            }
        }

        setVoorwerp(speler1.getX(), speler1.getY(), speler1);
        setVoorwerp(speler1.getX(), speler1.getY()+1, new Krat());
        setVoorwerp(speler2.getX(), speler2.getY(), speler2);
        setVoorwerp(speler2.getX(), speler2.getY()-1, new Krat());

        setVoorwerp(9,  4, new Krat());
        setVoorwerp(9,  5, new Krat());
        setVoorwerp(10, 5, new Krat());
        setVoorwerp(11, 5, new Krat());
        setVoorwerp(11, 4, new Krat());

        setVoorwerp(breedte-12, hoogte-5, new Krat());
        setVoorwerp(breedte-12, hoogte-6, new Krat());
        setVoorwerp(breedte-11, hoogte-6, new Krat());
        setVoorwerp(breedte-10, hoogte-6, new Krat());
        setVoorwerp(breedte-10, hoogte-5, new Krat());

        setVoorwerp(1, 3, new Krat());
        setVoorwerp(1, 2, new Krat());
        setVoorwerp(1, 1, new Krat());
        setVoorwerp(2, 1, new Krat());
        setVoorwerp(3, 1, new Krat());

        setVoorwerp(breedte-2, hoogte-4, new Krat());
        setVoorwerp(breedte-2, hoogte-3, new Krat());
        setVoorwerp(breedte-2, hoogte-2, new Krat());
        setVoorwerp(breedte-3, hoogte-2, new Krat());
        setVoorwerp(breedte-4, hoogte-2, new Krat());

        setVoorwerp(1, hoogte-4, new Krat());
        setVoorwerp(1, hoogte-3, new Krat());
        setVoorwerp(1, hoogte-2, new Krat());
        setVoorwerp(2, hoogte-2, new Krat());
        setVoorwerp(3, hoogte-2, new Krat());

        setVoorwerp(breedte-2, 3, new Krat());
        setVoorwerp(breedte-2, 2, new Krat());
        setVoorwerp(breedte-2, 1, new Krat());
        setVoorwerp(breedte-3, 1, new Krat());
        setVoorwerp(breedte-4, 1, new Krat());

        for (int kruitX = 5; kruitX < breedte-5; kruitX++) {
            setVoorwerp(kruitX, 3, new Kruit());
            setVoorwerp(kruitX, hoogte-4, new Kruit());
        }
        for (int kruitY = 4; kruitY < hoogte-4; kruitY++) {
            setVoorwerp(5, kruitY, new Kruit());
            setVoorwerp(breedte-6, kruitY, new Kruit());
        }
    }

    /**
     * Dit is level 3: Let's teleport! Hier zitten 149 dozen in,
     * nog een bepaald percentage moet gevuld worden met PowerUps.
     * In de hoeken (niet echt helemaal uiterst in de hoeken) bevinden zich portalen,
     * hier kan men doorgaan en men komt bij de andere poort (de overkant van het veld) uit.
     * 
     * Voorbeeld:
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
    x   b                   d   d   d   d   d   d   d   d   d                       x
    x       x       x   d   x   d   x   d   x   d   x   d   x   d   x       x       x
    x           p   d   d   d   d   d   d   d   d   d   d   d   d   d   p           x
    x       x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x       x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x
    x   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   d   x
    x       x   d   x   d   x   d   x   d   x   d   x   d   x   d   x   d   x       x
    x           p   d   d   d   d   d   d   d   d   d   d   d   d   d   p           x
    x       x       x   d   x   d   x   d   x   d   x   d   x   d   x       x       x
    x                       d   d   d   d   d   d   d   d   d                   r   x
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
     */
    public void level3()
    {
        for (int kratX = 1; kratX < breedte-1; kratX++){
            for (int kratY = 1; kratY < hoogte-1; kratY++){
                if((kratX % 2) == 1){
                    rooster[kratX][kratY] = new Krat();
                }
                if((kratY % 2) == 1){
                    rooster[kratX][kratY] = new Krat();
                }
            }
        }

        setVoorwerp(speler1.getX(),   speler1.getY(),   speler1);
        setVoorwerp(speler1.getX()+1, speler1.getY(),   new Grond());
        setVoorwerp(speler1.getX()+2, speler1.getY(),   new Grond());
        setVoorwerp(speler1.getX()+3, speler1.getY(),   new Grond());
        setVoorwerp(speler1.getX()+4, speler1.getY(),   new Grond());
        setVoorwerp(speler1.getX(),   speler1.getY()+1, new Grond());
        setVoorwerp(speler1.getX(),   speler1.getY()+2, new Grond());
        setVoorwerp(speler1.getX(),   speler1.getY()+3, new Grond());
        setVoorwerp(speler1.getX()+2, speler1.getY()+1, new Grond());
        setVoorwerp(speler1.getX()+1, speler1.getY()+2, new Grond());

        setVoorwerp(speler2.getX(),   speler2.getY(),   speler2);
        setVoorwerp(speler2.getX()-1, speler2.getY(),   new Grond());
        setVoorwerp(speler2.getX()-2, speler2.getY(),   new Grond());
        setVoorwerp(speler2.getX()-3, speler2.getY(),   new Grond());
        setVoorwerp(speler2.getX()-4, speler2.getY(),   new Grond());
        setVoorwerp(speler2.getX(),   speler2.getY()-1, new Grond());
        setVoorwerp(speler2.getX(),   speler2.getY()-2, new Grond());
        setVoorwerp(speler2.getX(),   speler2.getY()-3, new Grond());
        setVoorwerp(speler2.getX()-2, speler2.getY()-1, new Grond());
        setVoorwerp(speler2.getX()-1, speler2.getY()-2, new Grond());

        //speler3
        setVoorwerp(1, hoogte-2, new Grond());
        setVoorwerp(2, hoogte-2, new Grond());
        setVoorwerp(3, hoogte-2, new Grond());
        setVoorwerp(4, hoogte-2, new Grond());
        setVoorwerp(5, hoogte-2, new Grond());
        setVoorwerp(1, hoogte-3, new Grond());
        setVoorwerp(1, hoogte-4, new Grond());
        setVoorwerp(1, hoogte-5, new Grond());
        setVoorwerp(3, hoogte-3, new Grond());
        setVoorwerp(2, hoogte-4, new Grond());

        //speler4
        setVoorwerp(breedte-2, 1, new Grond());
        setVoorwerp(breedte-3, 1, new Grond());
        setVoorwerp(breedte-4, 1, new Grond());
        setVoorwerp(breedte-5, 1, new Grond());
        setVoorwerp(breedte-6, 1, new Grond());
        setVoorwerp(breedte-2, 2, new Grond());
        setVoorwerp(breedte-2, 3, new Grond());
        setVoorwerp(breedte-2, 4, new Grond());
        setVoorwerp(breedte-4, 2, new Grond());
        setVoorwerp(breedte-3, 3, new Grond());

        setVoorwerp(portaal1A.getX(), portaal1A.getY(), portaal1A);
        setVoorwerp(portaal1B.getX(), portaal1B.getY(), portaal1B);
    }

    /**
     * Dit is level 4: Just go kill yourself >:(. Hier zijn geen PowerUps aanwezig,
     * maar ook geen kratten of kruit. Just plain simple Grond everywhere.
     * Men kan zich nergens verbergen, dus ze moeten elkaar gewoon afmaken, of toch proberen.
     * 
     * ToDo: De spelers krijgen direct het maximale aantal bommen,
     * de maximale vuurkracht en ze lopen op hun snelst. Levens krijgen ze maar 3.
     * 
     * Voorbeeld:
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
    x   b                                                                           x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                               x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                               x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                               x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                               x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                               x
    x       x       x       x       x       x       x       x       x       x       x
    x                                                                           r   x
    x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x   x
     */
    public void level4()
    {
        for (int grondX = 1; grondX < breedte-1; grondX++){
            for (int grondY = 1; grondY < hoogte-1; grondY++){
                if((grondX % 2) == 1){
                    rooster[grondX][grondY] = new Grond();
                }
                if((grondY % 2) == 1){
                    rooster[grondX][grondY] = new Grond();
                }
            }
        }
        setVoorwerp(speler1.getX(),   speler1.getY(),   speler1);
        setVoorwerp(speler2.getX(),   speler2.getY(),   speler2);
    }

    /**
     * 
     */
    protected void verdeelPowerUp()
    {
        for(int i = 1; i<=tellerAantalPU; )
        {
            int x = 1+(int)(Math.random()*(breedte-2));
            int y = 1+(int)(Math.random()*(hoogte-2));
            if(getVoorwerp(x, y) instanceof Krat)
            {
                setVoorwerp(x, y, new PowerUp(getRandomPower()));
                i++;
            }
        }
    }

    // getters
    /**
     * Speelt een geluidje af.
     * Source: http://docstore.mik.ua/orelly/java/awt/ch14_05.htm
     * Hier heb ik wat info gevonden, overbodige dingen zijn weggesmeten.
     * 
     * @param audioPath     De link naar een champion zijn audio file
     */
    public void speelGeluidje(String audioPath){
        AudioStream audioStream;
        AudioData audiodata;
        AudioDataStream audiostream;
        try
        {
            audioStream = new AudioStream (new FileInputStream(audioPath));
            audiodata = audioStream.getData();
            audiostream = null;
            audiostream = new AudioDataStream(audiodata);
            AudioPlayer.player.start(audiostream);
        }
        catch (Exception e){}
    }

    /**
     * Selecteert een willekeurige powerup uit een enum.
     * Source: http://stackoverflow.com/questions/18145766/generating-a-random-enum-value-continuously-without-getting-the-same-value-twice
     * 
     * @return Een willekeurige powerup.
     */
    protected static Power getRandomPower() 
    {
        return Power.values()[(int)(Math.random()*Power.values().length)];
    }

    /**
     * Hoeveel vakjes zijn er in de breedte?
     * 
     * @return De breedte in vakjes.
     */
    public int getBreedte() {
        return breedte;
    }

    /**
     * Hoeveel vakjes zijn er in de hoogte?
     * 
     * @return De hoogte in vakjes.
     */
    public int getHoogte() {
        return hoogte;
    }

    /**
     * Welk voorwerp ligt er op (x,y)?
     * 
     * @param x De x-coordinaat.
     * @param y De y-coordinaat.
     * 
     * @return Het voorwerp op die coordinaat, kan ook null zijn.
     */
    public Voorwerp getVoorwerp(int x, int y) {
        // defensief programmeren
        if (x<0) {
            return null;
        }
        if (x>=breedte) {
            return null;
        }
        if (y<0) {
            return null;
        }
        if (y>=hoogte) {
            return null;
        }
        return rooster[x][y];
    }

    /**
     * Is er een dooie int spel?
     * 
     * @return isErEenDode True als er een dooie is.
     */
    public boolean getIsErEenDode()
    {
        return isErEenDode;
    }

    /**
     * Is het spel gereset?
     * 
     * @return isHetSpelGereset True als het spel gereset is.
     */
    public boolean getIsHetSpelGereset()
    {
        return isHetSpelGereset;
    }

    /**
     * Welke level is dit?
     * 
     * @return levelNr Het nummer van dit level.
     */
    public int getLevelNr()
    {
        return levelNr;
    }

    // setters
    /**
     * Nu is er dus wel een dooie int spel.
     * 
     * @param isErEenDode True als er een dooie is.
     */
    public void setIsErEenDode(boolean isErEenDode)
    {
        this.isErEenDode = isErEenDode;
    }

    /**
     * Nu is het spel dus wel gereset.
     * 
     * @param isHetSpelGereset True als het spel gereset is.
     */
    public void setIsHetSpelGereset(boolean isHetSpelGereset)
    {
        this.isHetSpelGereset = isHetSpelGereset;
    }

    /**
     * Verander het level?
     * 
     * @param levelNr Het nieuwe level.
     */
    public void setLevelNr(int levelNr)
    {
        this.levelNr = levelNr;
    }

    /**
     * Leg een voorwerp op (x,y).
     * 
     * @param x De x-coordinaat
     * @param y De y-coordinaat
     * @param vw Het voorwerp dat op die coordinaat moet komen.
     * 
     * MOETEN WE HIER NOG defensief programmeren ZOALS BIJ DE GETTER???
     */
    public void setVoorwerp(int x, int y, Voorwerp vw) {
        rooster[x][y] = vw;
    }

    /**
     * Hiermee leg je een bom op de plaats van de speler.
     * Afhankelijk van de vuurkracht van de speler ontploffen er meer blokskes.
     * Een explosie stopt aan een muur en kan maar 1 krat tegelijk laten ontploffen.
     * 
     * @param speler Het nummer van de speler.
     */
    public void legBom(int speler)
    {
        switch (speler) {
            case 1:
            if(speler1.getAantalBommen() > 0 && (speler1.getVwOnderSpeler() instanceof Grond))
            {
                speler1.setVwOnderSpeler(new Bom(1, speler1.getX(), speler1.getY(), new Grond()));
                speler1.setAantalBommen(speler1.getAantalBommen()-1);
                speelGeluidje(BombDrop);
            }
            else if(speler1.getAantalBommen() > 0 && (speler1.getVwOnderSpeler() instanceof Kruit))
            {
                speler1.setVwOnderSpeler(new Bom(1, speler1.getX(), speler1.getY(), new Kruit()));
                speler1.setAantalBommen(speler1.getAantalBommen()-1);
                speelGeluidje(BombDrop);
            }
            break;
            case 2:
            if(speler2.getAantalBommen() > 0 & (speler2.getVwOnderSpeler() instanceof Grond))
            {
                speler2.setVwOnderSpeler(new Bom(2, speler2.getX(), speler2.getY(), new Grond()));
                speler2.setAantalBommen(speler2.getAantalBommen()-1);
                speelGeluidje(BombDrop);
            }
            else if(speler2.getAantalBommen() > 0 & (speler2.getVwOnderSpeler() instanceof Kruit))
            {
                speler2.setVwOnderSpeler(new Bom(2, speler2.getX(), speler2.getY(), new Kruit()));
                speler2.setAantalBommen(speler2.getAantalBommen()-1);
                speelGeluidje(BombDrop);
            }
            break;
        }
    }

    /**
     * Hiermee maak je vuur rondom een bom.
     * Afhankelijk van de vuurkracht van de speler ontploffen er meer blokskes.
     * De bomLinks, -recht, etc. zorgen ervoor dat het vuur niet voorbij de muur kan.
     * 
     * @param speler Het nummer van de speler.
     * @param bomX De x-coördinaat van de bom.
     * @param bomY De Y-coördinaat van de bom.
     * @param startTime Het tijdstip waarop de bom is neergelegd in milliseconden.
     */
    public void bomVuur(int speler, int bomX, int bomY, long startTime)
    {
        switch (speler) {
            case 1: vuurHelper(speler1, bomX, bomY, startTime); break;
            case 2: vuurHelper(speler2, bomX, bomY, startTime); break;
        }
    }

    /**
     * Hier gaan we in elke richting zover mogelijk voorwerpen nakijken
     * om in brand te steken. De breakflag beperkt de explosie.
     * 
     * @param speler Het nummer van de speler.
     * @param bomX De x-coördinaat van de bom.
     * @param bomY De Y-coördinaat van de bom.
     * @param startTime Het tijdstip waarop de bom is neergelegd in milliseconden.
     */
    public void vuurHelper(Speler spelerZ, int bomX, int bomY, long startTime)
    {
        // Naar boven
        boolean breakFlag = false;
        for (int j = bomY-1; j > bomY-spelerZ.getHoeveelVuur()-1 && !breakFlag; j--)
        {
            breakFlag = vuurActies(bomX, j, startTime);
        }
        // Naar rechts
        breakFlag = false;
        for (int i = bomX+1; i < bomX+spelerZ.getHoeveelVuur()+1 && !breakFlag; i++)
        {
            breakFlag = vuurActies(i, bomY, startTime);
        }
        // Naar onder
        breakFlag = false;
        for (int j = bomY+1; j < bomY+spelerZ.getHoeveelVuur()+1 && !breakFlag; j++)
        {
            breakFlag = vuurActies(bomX, j, startTime);
        }
        // Naar links
        breakFlag = false;
        for (int i = bomX-1; i > bomX-spelerZ.getHoeveelVuur()-1 && !breakFlag; i--)
        {
            breakFlag = vuurActies(i, bomY, startTime);
        }
    }

    /**
     * Acties die het vuur kan uitvoeren.
     * 
     * @param x De x-coördinaat van de plaats die momenteel wordt nagekeken.
     * @param y De Y-coördinaat van de plaats die momenteel wordt nagekeken.
     * @param startTime Het tijdstip waarop de bom is neergelegd in milliseconden.
     * 
     * @return True or false, dit bepaald of de explosie nog verder kan of niet.
     */
    public boolean vuurActies(int x, int y, long startTime)
    {
        speelGeluidje(BombeExplode);
        if (getVoorwerp(x, y) instanceof Muur || getVoorwerp(x, y) instanceof Portaal)
        {
            return true;
        }
        else if (getVoorwerp(x, y) instanceof Bom)
        {
            Bom bo = (Bom)getVoorwerp(x, y);
            bo.setStartTime(startTime);
        }
        else if (getVoorwerp(x, y) instanceof Krat)
        {
            Krat kra = (Krat)getVoorwerp(x, y);
            kra.maakVuur(true);
            return true;
        }
        else if (getVoorwerp(x, y) instanceof PowerUp)
        {
            PowerUp pu = (PowerUp)getVoorwerp(x, y);
            pu.maakVuur(true);
            return true;
        }
        else if (getVoorwerp(x, y) instanceof Grond)
        {
            Grond gro = (Grond)getVoorwerp(x, y);
            gro.maakVuur(true);
        }
        else if (getVoorwerp(x, y) instanceof Speler)
        {
            Speler s = (Speler)getVoorwerp(x, y);
            s.maakVuur(true);
        }
        else if (getVoorwerp(x, y) instanceof Kruit)
        {
            vuurKruit();
        }
        return false;
    }

    /**
     * Kruit aansteken.
     */
    public void vuurKruit()
    {
        for (int i=0; i < getBreedte(); i++)
        {
            for (int j=0; j < getHoogte(); j++)
            {
                Voorwerp vw = getVoorwerp(i, j);
                if (vw instanceof Kruit)
                {
                    Kruit kru = (Kruit)vw;
                    kru.maakVuur(true);
                }
                else if (vw instanceof Speler)
                {
                    Speler s = (Speler)vw;
                    switch (s.getSpelerNr())
                    {
                        case 1:
                        if (speler1.getVwOnderSpeler() instanceof Kruit)
                        {
                            s.maakVuur(true);
                        }
                        break;
                        case 2:
                        if (speler2.getVwOnderSpeler() instanceof Kruit)
                        {
                            s.maakVuur(true);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Hiermee laat je alles verdwijnen rondom een bom.
     * Tenzij het een powerup of kruit is natuurlijk ;)
     * 
     * @param speler Het nummer van de speler.
     * @param bomX De x-coördinaat van de bom.
     * @param bomY De Y-coördinaat van de bom.
     * @param vwOnderBom Het voorwerp dat onder de bom lag.
     */
    public void bomDoven(int speler, int bomX, int bomY, Voorwerp vwOnderBom)
    {
        switch (speler) {
            case 1: doofHelper(speler1, bomX, bomY);
            if (vwOnderBom instanceof Grond)
            {
                setVoorwerp(bomX, bomY, new Grond());
            }
            else if (vwOnderBom instanceof Kruit)
            {
                setVoorwerp(bomX, bomY, new Kruit());
            }
            speler1.setAantalBommen(speler1.getAantalBommen()+1);
            break;
            case 2: doofHelper(speler2, bomX, bomY);
            if (vwOnderBom instanceof Grond)
            {
                setVoorwerp(bomX, bomY, new Grond());
            }
            else if (vwOnderBom instanceof Kruit)
            {
                setVoorwerp(bomX, bomY, new Kruit());
            }
            speler2.setAantalBommen(speler2.getAantalBommen()+1);
            break;
        }
    }

    /**
     * Hier gaan we in elke richting zover mogelijk voorwerpen nakijken
     * om te laten doven.
     * 
     * @param speler Het nummer van de speler.
     * @param bomX De x-coördinaat van de bom.
     * @param bomY De Y-coördinaat van de bom.
     */
    public void doofHelper(Speler speler, int bomX, int bomY)
    {
        // Naar boven
        for (int j = bomY-1; j > bomY-speler.getHoeveelVuur()-1; j--)
        {
            // defensief programmeren
            if (bomX<0) {
                break;
            }
            else if (bomX>=breedte) {
                break;
            }
            else if (j<0) {
                break;
            }
            else if (j>=hoogte) {
                break;
            }
            else if (getVoorwerp(bomX, j).isVuur())
            {
                doofActies(bomX, j);
            }
        }
        // Naar rechts
        for (int i = bomX+1; i < bomX+speler.getHoeveelVuur()+1; i++)
        {
            // defensief programmeren
            if (i<0) {
                break;
            }
            else if (i>=breedte) {
                break;
            }
            else if (bomY<0) {
                break;
            }
            else if (bomY>=hoogte) {
                break;
            }
            else if (getVoorwerp(i, bomY).isVuur())
            {
                doofActies(i, bomY);
            }
        }
        // Naar onder
        for (int j = bomY+1; j < bomY+speler.getHoeveelVuur()+1; j++)
        {
            // defensief programmeren
            if (bomX<0) {
                break;
            }
            else if (bomX>=breedte) {
                break;
            }
            else if (j<0) {
                break;
            }
            else if (j>=hoogte) {
                break;
            }
            else if (getVoorwerp(bomX, j).isVuur())
            {
                doofActies(bomX, j);
            }
        }
        // Naar links
        for (int i = bomX-1; i > bomX-speler.getHoeveelVuur()-1; i--)
        {
            // defensief programmeren
            if (i<0) {
                break;
            }
            else if (i>=breedte) {
                break;
            }
            else if (bomY<0) {
                break;
            }
            else if (bomY>=hoogte) {
                break;
            }
            else if (getVoorwerp(i, bomY).isVuur())
            {
                doofActies(i, bomY);
            }
        }
    }

    /**
     * Acties die het vuur kan uitvoeren.
     * 
     * @param x De x-coördinaat van de plaats die momenteel wordt nagekeken.
     * @param y De Y-coördinaat van de plaats die momenteel wordt nagekeken.
     */
    public void doofActies(int x, int y)
    {
        if (getVoorwerp(x, y) instanceof Krat)
        {
            setVoorwerp(x, y, new Grond());
        }
        else if (getVoorwerp(x, y) instanceof PowerUp)
        {
            PowerUp pu = (PowerUp)getVoorwerp(x, y);
            pu.maakOpen(true);
        }
        else if (getVoorwerp(x, y) instanceof Grond)
        {
            Grond gro = (Grond)getVoorwerp(x, y);
            gro.maakVuur(false);
        }
        else if (getVoorwerp(x, y) instanceof Speler)
        {
            Speler s = (Speler)getVoorwerp(x, y);
            s.eenLevenMinder();
            if (s.getLevens() == 0)
            {
                speelGeluidje(Die);
            }
            else
            {
                speelGeluidje(Warning);
            }
            s.maakVuur(false);
        }
        else if (getVoorwerp(x, y) instanceof Kruit)
        {
            doofKruit();
        }
    }

    /**
     * Kruit doven.
     * 
     */
    public void doofKruit()
    {
        for (int i=0; i < getBreedte(); i++)
        {
            for (int j=0; j < getHoogte(); j++)
            {
                Voorwerp vw = getVoorwerp(i, j);
                if (vw instanceof Kruit)
                {
                    Kruit kru = (Kruit)vw;
                    kru.maakVuur(false);
                }
                else if (vw instanceof Speler)
                {
                    Speler s = (Speler)vw;
                    s.eenLevenMinder();
                    if (s.getLevens() == 0)
                    {
                        speelGeluidje(Die);
                    }
                    else
                    {
                        speelGeluidje(Warning);
                    }
                    switch (s.getSpelerNr())
                    {
                        case 1:
                        if (speler1.getVwOnderSpeler() instanceof Kruit)
                        {
                            s.maakVuur(false);
                        }
                        break;
                        case 2:
                        if (speler2.getVwOnderSpeler() instanceof Kruit)
                        {
                            s.maakVuur(false);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Hier geven we, afhankelijk van wie de beweging doet (het spelersnummer), de juiste variabelen mee aan de beweeghelper,
     * dit doen we om dubbele code met als enigste verschil andere variabelen te vermijden.
     * 
     * @param dx Delta-x: verandering in de x-richting.
     * @param dy Delta-y: verandering in de y-richting.
     * @param speler Het nummer van de speler.
     */
    protected void beweeg(int dx, int dy, int speler)
    {
        switch (speler) {
            case 1:
            beweegHelper(dx, dy, speler1, speler2);
            break;
            case 2:
            beweegHelper(dx, dy, speler2, speler1);
            break;
        }
    }

    /**
     * Beweeg in een dx-dy richting.
     * 
     * @param dx Delta-x: verandering in de x-richting.
     * @param dy Delta-y: verandering in de y-richting.
     * @param speler De speler die de beweging uitvoert.
     * @param spelerZ De andere speler.
     */
    protected void beweegHelper(int dx, int dy, Speler speler, Speler spelerZ)
    {
        speler.setS(getVoorwerp(speler.getX(), speler.getY()));
        doelX = speler.getX() + dx;
        doelY = speler.getY() + dy;
        doel  = getVoorwerp(doelX, doelY);
        volgX = doelX + dx;
        volgY = doelY + dy;
        volg  = getVoorwerp(volgX, volgY);
        if (speler.getS() instanceof Muur || speler.getLevens() == 0)
        {
            speler.maakDood();
            return;
        }
        if (doel instanceof Bom && speler.kanShiften() && !(volg instanceof Speler))
        {
            Voorwerp bom = getVoorwerp(doelX, doelY);
            try 
            {
                if (volg.isBetreedbaar())
                {
                    Bom bo = (Bom)doel;
                    setVoorwerp(volgX, volgY, bom);
                    setVoorwerp(doelX, doelY, speler1);
                    setVoorwerp(speler.getX(), speler.getY(), new Grond());
                    bo.setX(volgX);
                    bo.setY(volgY);
                    speler.setX(doelX);
                    speler.setY(doelY);
                }
                else
                {

                }
            }
            catch (ArrayIndexOutOfBoundsException e) {}
            return;
        }
        if(doel.isBetreedbaar())
        {
            if (doel instanceof PowerUp) {
                PowerUp pu = (PowerUp)doel;
                switch (pu.getPower()) {
                    case EXTRA_BOM: speler.setAantalBommen(speler.getAantalBommen()+1); speelGeluidje(Select); break;
                    case MEER_VLAM: speler.setHoeveelVuur(speler.getHoeveelVuur()+1); speelGeluidje(Select); break;
                    case GOLDENFLAME: speler.setHoeveelVuur(speler.getMaxVuur()); speelGeluidje(Select); break;
                    case KICKER: speler.magKicken(true); speelGeluidje(Select); break;
                    case EXTRA_LEVEN: speler.eenLevenMeer(); speelGeluidje(Life); break;
                    case SHIFTER: speler.magShiften(true); speelGeluidje(Select); break;
                    case SNELLER_LOPEN: speler.setSnelheid(speler.getSnelheid()+1); speelGeluidje(Select); break;
                }
                speler.setVwOnderSpeler(new Grond());
                setVoorwerp(speler.getX(), speler.getY(), speler.getVwOnderSpeler());
                setVoorwerp(doelX, doelY, speler.getS());
            }
            else if (doel instanceof Portaal)
            {
                Portaal pu = (Portaal)doel;
                if (pu.getPoortNr() == 1)
                {
                    poortHelper(portaal1B, speler, spelerZ);
                }
                else if (pu.getPoortNr() == 2)
                {
                    poortHelper(portaal1A, speler, spelerZ);
                }
                portaal1A.maakVrij(false);
                portaal1B.maakVrij(false);
                return;
            }
            else if(spelerZ.getVwOnderSpeler() instanceof Speler)
            {
                setVoorwerp(doelX, doelY, spelerZ.getVwOnderSpeler());
                spelerZ.setVwOnderSpeler(speler.getVwOnderSpeler());
                speler.setVwOnderSpeler(doel);
            }
            else
            {
                setVoorwerp(speler.getX(), speler.getY(), speler.getVwOnderSpeler());
                speler.setVwOnderSpeler(doel);
                setVoorwerp(doelX, doelY, speler.getS());
                if(!(spelerZ.getVwOnderSpeler() instanceof Portaal))
                { 
                    portaal1A.maakVrij(true);
                    portaal1B.maakVrij(true);
                }
            }
            speler.setX(doelX);
            speler.setY(doelY);
        }
        else
        {
            return;
        }
    }

    /**
     * Deze functie laat een speler teleporteren. Ook hier doen we dit om dubbele code met als enigste verschil andere variabelen te vermijden.
     * 
     * @param portaal Het portaal waar de speler naar wordt geteleporteerd.
     * @param speler De speler die de beweging uitvoert.
     * @param spelerZ De andere speler.
     */
    public void poortHelper(Portaal portaal, Speler speler, Speler spelerZ)
    {
        setVoorwerp(portaal.getX(), portaal.getY(), speler);
        if(spelerZ.getVwOnderSpeler() instanceof Speler)
        {
            setVoorwerp(speler.getX(), speler.getY(), spelerZ);
            spelerZ.setVwOnderSpeler(speler.getVwOnderSpeler());
        }
        else
        {
            setVoorwerp(speler.getX(), speler.getY(), speler.getVwOnderSpeler());
        }
        speler.setX(portaal.getX());
        speler.setY(portaal.getY());
        speler.setVwOnderSpeler(portaal);
    }

    /**
     * Beweeg 1 vakje naar links.
     * 
     * @param speler Het nummer van de speler.
     */
    public void beweegLinks(int speler)
    {
        beweeg(-1,0,speler);
    }

    /**
     * Beweeg 1 vakje naar rechts.
     * 
     * @param speler Het nummer van de speler.
     */
    public void beweegRechts(int speler)
    {
        beweeg(1,0,speler);
    }

    /**
     * Beweeg 1 vakje naar boven.
     * 
     * @param speler Het nummer van de speler.
     */
    public void beweegBoven(int speler)
    {
        beweeg(0,-1,speler);
    }

    /**
     * Beweeg 1 vakje naar onder.
     * 
     * @param speler Het nummer van de speler.
     */
    public void beweegOnder(int speler)
    {
        beweeg(0,1,speler);
    }
}