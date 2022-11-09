package odomo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 * Tests portant sur la classe Odomo.
 */
public class EcranTest {

    @Test
    public void testEcranInchange() {
        String ecran_orig
            = "+----------------+-------------------------------------------------------------+\n"
            + "|HH:mm    JJ/MM  | Mode : MODEMODEMODE                                         |\n"
            + "|                |        HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH |\n"
            + "|intérieur :     | ZZZZZ0 ZONE0ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "| ttttt°C        | ZZZZZ1 ZONE1ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|    yy% hygro   | ZZZZZ2 ZONE2ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|                | ZZZZZ3 ZONE3ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|extérieur :     | ZZZZZ4 ZONE4ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "| TTTTT° C       | ZZZZZ5 ZONE5ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|    YY% hygro   | ZZZZZ6 ZONE6ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|  PPPP  hPa     | ZZZZZ7 ZONE7ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ |\n"
            + "|  SSSS  W/m2 SOL|                                                             |\n"
            + "|                |       TITRE_HISTO_TITRE_HISTO_TITRE_HISTO_TITRE_HISTO  LVER |\n"
            + "|      o   o     |        0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 LLL0 |\n"
            + "|   o   NNN   o  |        1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 LLL1 |\n"
            + "|  o NOO   NEE o |        2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 LLL2 |\n"
            + "|  oOOO VVV EEEo |        3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 3 LLL3 |\n"
            + "|  o SOO   SEE o |        4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 LLL4 |\n"
            + "|   o   SSS   o  |        5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 LLL5 |\n"
            + "|      o   o     |        6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 6 LLL6 |\n"
            + "|                |        7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 7 LLL7 |\n"
            + "|                |  LHOR LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL\\LLL8 |\n"
            + "|                |                                                             |\n"
            + "+----------------+-------------------------------------------------------------+";
        assertEquals(ecran_orig, Ecran.ECRAN_VIDE);
    }
    
    @Test
    public void testMotifLigneHisto() {
        assertEquals(" 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2",
                Ecran.motifLigneHisto(2));
    }

    @Test
    public void testHistoLegendeAbscisse() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals(" -22 -20 -18 -16 -14 -12 -10  -8  -6  -4  -2   0",
                Ecran.histoLegendeAbscisse());
        Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
        assertEquals(" 0   2   4   6   8  10  12  14  16  18  20  22  ",
                Ecran.histoLegendeAbscisse());
    }

    @Test
    public void testTitreHisto() {
        assertNotEquals("", Ecran.titreHisto());
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("Pluviométrie des 24 dernières heures :",
                Ecran.titreHisto());
        Ecran.typeHisto = Ecran.TYPE_HISTO_TEMPER_MINUTES;
        assertEquals("Température des 60 dernières minutes",
                Ecran.titreHisto());
        Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
        assertEquals("Créneaux",
                Ecran.titreHisto());
    }

    @Test
    public void testHistoUniteAbscisse() {
        assertEquals("h", Ecran.histoUniteAbscisse());
    }

    @Test
    public void testHistoUniteOrdonnee() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("mm", Ecran.histoUniteOrdonnee());
        Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
        assertEquals("jour", Ecran.histoUniteOrdonnee());
    }

    @Test
    public void testHistoLegendeOrdonnee() {
        // pour la pluviométrie par heure
        Meteo.initialiserDonneesParHeure();
        Meteo.initialiserDonneesParMinute();
        double maxi = Meteo.pluvioHeure[0];
        for (int i = 1; i < 24; i++) {
            if (Meteo.pluvioHeure[i] > maxi) {
                maxi = Meteo.pluvioHeure[i];
            }
        }
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        assertEquals("" + (int) maxi, Ecran.histoLegendeOrdonnee(0));
        assertEquals("", Ecran.histoLegendeOrdonnee(1));
        assertEquals("", Ecran.histoLegendeOrdonnee(2));
        assertEquals("", Ecran.histoLegendeOrdonnee(3));
        assertEquals("" + (int) (maxi / 2), Ecran.histoLegendeOrdonnee(4));
        assertEquals("", Ecran.histoLegendeOrdonnee(5));
        assertEquals("", Ecran.histoLegendeOrdonnee(6));
        assertEquals("", Ecran.histoLegendeOrdonnee(7));
        assertEquals(""+0, Ecran.histoLegendeOrdonnee(8));
        Ecran.typeHisto = Ecran.TYPE_HISTO_CHAUFFAGE;
        assertEquals("lun", Ecran.histoLegendeOrdonnee(0));
        assertEquals("mar", Ecran.histoLegendeOrdonnee(1));
        assertEquals("mer", Ecran.histoLegendeOrdonnee(2));
        assertEquals("jeu", Ecran.histoLegendeOrdonnee(3));
        assertEquals("ven", Ecran.histoLegendeOrdonnee(4));
        assertEquals("sam", Ecran.histoLegendeOrdonnee(5));
        assertEquals("dim", Ecran.histoLegendeOrdonnee(6));
        assertEquals("", Ecran.histoLegendeOrdonnee(7));
        Ecran.typeHisto = Ecran.TYPE_HISTO_TEMPER_MINUTES;
        assertEquals("" + (int)Meteo.valeurMax(Meteo.temperExtMaxMinute), Ecran.histoLegendeOrdonnee(0));
        assertEquals("", Ecran.histoLegendeOrdonnee(1));
        assertEquals("", Ecran.histoLegendeOrdonnee(2));
        assertEquals("", Ecran.histoLegendeOrdonnee(3));
        assertEquals(""+((int)Meteo.valeurMax(Meteo.temperExtMaxMinute)+(int)Meteo.valeurMin(Meteo.temperExtMinMinute))/2, Ecran.histoLegendeOrdonnee(4));
        assertEquals("", Ecran.histoLegendeOrdonnee(5));
        assertEquals("", Ecran.histoLegendeOrdonnee(6));
        assertEquals("", Ecran.histoLegendeOrdonnee(7));
        assertEquals(""+(int)Meteo.valeurMin(Meteo.temperExtMinMinute), Ecran.histoLegendeOrdonnee(8));
    }

    @Test
    public void testHistoLigneExempleDuSujet() {
        double[] pluvio = {2.3, 3.9, 1.0, 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 4.5, 1.7};
        Meteo.pluvioHeure = pluvio;
        StringBuilder ecran = new StringBuilder(Ecran.ECRAN_VIDE);
        Ecran.insererDonneesHistogramme(ecran);
        System.out.println(ecran);
    }

    @Test
    public void testHistoLigneExempleVide() {
        Ecran.typeHisto = Ecran.TYPE_HISTO_PLUVIO_HEURE;
        double[] pluvio = {0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.,
            0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0.};
        Meteo.pluvioHeure = pluvio;
        StringBuilder ecran = new StringBuilder(Ecran.ECRAN_VIDE);
        Ecran.insererDonneesHistogramme(ecran);
        System.out.println(ecran);
    }

    @Test
    public void testNumLigne() {
        double valMin = -15.4;
        double valMax = 17.3;
        // valeur max
        assertEquals(0, Ecran.numLigne(valMax, valMin, valMax));
        // valeur min
        assertEquals(7, Ecran.numLigne(valMin, valMin, valMax));
        // ligne au-dessus du min
        double pas = (valMax - valMin) / 7.;
        assertEquals(6, Ecran.numLigne(valMin + pas, valMin, valMax));
        assertEquals(7, Ecran.numLigne(valMin + .49 * pas, valMin, valMax));
        assertEquals(6, Ecran.numLigne(valMin + .51 * pas, valMin, valMax));
    }
}
