package odomo;


import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests portant sur la classe Jardin.
 */
public class JardinTest {

    @Test
    public void testInitialiser(){
        Jardin.initialiser();
        assertTrue(Jardin.zone==2);
        assertFalse(Jardin.zone!=2);
    }
    @Test
    public void testPluvioSommeHeure() {
        double[] pluvio = {2.3, 3.9, 1.0, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 4.5, 1.7};
        Meteo.pluvioHeure = pluvio;
        assertEquals(7.2, Jardin.pluvioSommeHeure(2), 0.1);
        assertEquals(11.7, Jardin.pluvioSommeHeure(1), 0.1);
        assertEquals(13.4, Jardin.pluvioSommeHeure(0), 0.1);
        double[] pluvio2 = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvio2;
        for (int i = 0; i<24; i++){
             assertEquals(0., Jardin.pluvioSommeHeure(i), 0.1);
        }
        double[] pluvio3 = {0., 0., 0., 0., 0., 0., -1.4, 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvio3;
        for (int i = 0; i<18; i++){
             assertEquals(-1.4, Jardin.pluvioSommeHeure(i), 0.1);
        }
        
    }

    @Test
    public void testTempsLimiteIrrigation() {
        int[][] creneau = {{8, 10}, {10, 15}, {11, 12}, {9, 15}};
        Jardin.creneauQuotidien = creneau;
        assertEquals(2, Jardin.tempsLimiteIrrigation(0));
        assertEquals(5, Jardin.tempsLimiteIrrigation(1));
        assertEquals(1, Jardin.tempsLimiteIrrigation(2));
        assertEquals(6, Jardin.tempsLimiteIrrigation(3));
        int[][] creneau2 = {{1, 0}, {10, 15}, {1, 0}, {9, 15}};
        Jardin.creneauQuotidien = creneau2;
        assertEquals(-1, Jardin.tempsLimiteIrrigation(0));
        assertEquals(5, Jardin.tempsLimiteIrrigation(1));
        assertEquals(-1, Jardin.tempsLimiteIrrigation(2));
        assertEquals(6, Jardin.tempsLimiteIrrigation(3));
    }
    
    @Test
    public void testCycle(){
        Jardin.zone = 4;
        int[][] creneau = {{8, 10}, {10, 15}, {11, 12}, {9, 15}};
        Jardin.creneauQuotidien = creneau;
        double[] eau = {1.5, 4, 0.4, 6};
        Jardin.quantiteJournaliere = eau;
        double[] pluvioHeure = {0., 0., 1.5, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure;
        assertEquals(1.5, Jardin.pluvioSommeHeure(2), 0.1);
        assertFalse(Jardin.cycle(1));
        assertTrue(Jardin.cycle(2));
        assertFalse(Jardin.cycle(3));
        assertTrue(Jardin.cycle(4));
        double[] pluvioHeure4 = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 10., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure4;
        assertEquals(0, Jardin.pluvioSommeHeure(5), 0.1);
        assertFalse(Jardin.cycle(1));
        assertTrue(Jardin.cycle(2));
        assertFalse(Jardin.cycle(3));
        assertTrue(Jardin.cycle(4));
        double[] pluvioHeure2 = {0., 0., 0., 0., 0, 0., 0., 0., 0.3, 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 9., 0.};
        Meteo.pluvioHeure = pluvioHeure2;
        assertEquals(9.3, Jardin.pluvioSommeHeure(1), 0.1);
        assertTrue(Jardin.cycle(1));
        assertTrue(Jardin.cycle(2));
        assertFalse(Jardin.cycle(3));
        assertTrue(Jardin.cycle(4));
        double[] pluvioHeure3 = {0., 0., 0., 0., 0, 0.1, 0., 0., 0.2, 0., 0., 0.,
            0., 0., 0., 0., 17, 0., 0., 9, 7, 0., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure3;
        assertFalse(Jardin.cycle(1));
        assertFalse(Jardin.cycle(2));
        assertFalse(Jardin.cycle(3));
        assertFalse(Jardin.cycle(4));
        
        
        
    }
    @Test
    public void testQuantiteEauPossible(){
        Jardin.zone = 4;
        int[][] creneau = {{8, 10}, {10, 15}, {11, 12}, {9, 15}};
        Jardin.creneauQuotidien = creneau;
        double[] eau = {1.5, 4, 0.4, 6};
        Jardin.quantiteJournaliere = eau;
        double[] pluvioHeure = {0., 0., 1.5, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure;
        assertEquals(0, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(2.5, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(-1.1, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(4.5, Jardin.quantiteEauPossible(4), 0.1);
        double[] pluvioHeure2 = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure2;
        assertEquals(1.5, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(4, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(0.4, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(6, Jardin.quantiteEauPossible(4), 0.1);
        double[] pluvioHeure3 = {0., 0., 0., 0., 0., 0.3, 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvioHeure3;
        assertEquals(1.2, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(3.7, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(0.1, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(5.7, Jardin.quantiteEauPossible(4), 0.1);
        double[] pluvioHeure4 = {0., 0., 0., 0., 0., 7.3, 0., 9.2, 0., 17.1, 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 7.2, 0., 0.};
        Meteo.pluvioHeure = pluvioHeure4;
        assertEquals(-39.3, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(-29.7, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(-40.4, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(-27.6, Jardin.quantiteEauPossible(4), 0.1);
        Jardin.zone = 4;
        int[][] creneau2 = {{1, 0}, {1, 0}, {11, 12}, {9, 15}};
        Jardin.creneauQuotidien = creneau2;
        double[] eau2 = {0, 0, 0.4, 6};
        Jardin.quantiteJournaliere = eau2;
        double[] newPluvioHeure = {0., 0., 1.5, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = newPluvioHeure;
        assertEquals(-1, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(-1, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(-1.1, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(4.5, Jardin.quantiteEauPossible(4), 0.1);
        double[] newPluvioHeure2 = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = newPluvioHeure2;
        assertEquals(-1, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(-1, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(0.4, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(6, Jardin.quantiteEauPossible(4), 0.1);
        double[] newPluvioHeure3 = {0., 0., 0., 0., 0., 0.3, 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = newPluvioHeure3;
        assertEquals(-1, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(-1, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(0.1, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(5.7, Jardin.quantiteEauPossible(4), 0.1);
        double[] newPluvioHeure4 = {0., 0., 0., 0., 0., 7.3, 0., 9.2, 0., 17.1, 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 7.2, 0., 0.};
        Meteo.pluvioHeure = newPluvioHeure4;
        assertEquals(-1, Jardin.quantiteEauPossible(1), 0.1);
        assertEquals(-1, Jardin.quantiteEauPossible(2), 0.1);
        assertEquals(-40.4, Jardin.quantiteEauPossible(3), 0.1);
        assertEquals(-27.6, Jardin.quantiteEauPossible(4), 0.1);
    }
    @Test 
    public void testSupprimerZone(){
        int[][] creneau = {{8, 10}, {10, 15}, {11, 12}, {9, 15}};
        Jardin.creneauQuotidien = creneau;
        double[] eau = {1.5, 4, 0.4, 6};
        Jardin.quantiteJournaliere = eau;
        Jardin.supprimerUneZone(1);
        assertArrayEquals(Jardin.creneauQuotidien, new int[][]{{1,0}, {10, 15}, {11, 12}, {9, 15}});
        assertArrayEquals(Jardin.quantiteJournaliere, new double[]{0, 4, 0.4, 6}, 0.1);
        Jardin.supprimerUneZone(2);
        assertArrayEquals(Jardin.creneauQuotidien, new int[][]{{1,0}, {1, 0}, {11, 12}, {9, 15}});
        assertArrayEquals(Jardin.quantiteJournaliere, new double[]{0, 0, 0.4, 6}, 0.1);
        Jardin.supprimerUneZone(3);
        assertArrayEquals(Jardin.creneauQuotidien, new int[][]{{1,0}, {1, 0}, {1, 0}, {9, 15}});
        assertArrayEquals(Jardin.quantiteJournaliere, new double[]{0, 0, 0, 6}, 0.1);
        Jardin.supprimerUneZone(4);
        assertArrayEquals(Jardin.creneauQuotidien, new int[][]{{1,0}, {1, 0}, {1, 0}, {1, 0}});
        assertArrayEquals(Jardin.quantiteJournaliere, new double[]{0, 0, 0, 0}, 0.1);
        
        
    }
}
