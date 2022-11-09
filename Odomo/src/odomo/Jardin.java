package odomo;

import java.util.Scanner;

/**
 * Gestion de la partie Jardin.
 */
class Jardin {

    /**
     * Créneau quotidien par zone.
     */
    static int[][] creneauQuotidien;
    /**
     * Quantité d'eau en litre par jour par zone
     */
    static double[] quantiteJournaliere;
    /**
     * Le nombre Max de zone à couvrir
     */
    final static int MAX_ZONE = 8;
    /**
     * Le nombre de zone programmée
     */
    static int zone = 0;

    /**
     * Inistialise les deux matrices, le créneau et la quantité
     */
    static void initialiser() {
        zone = 2;
        creneauQuotidien = new int[][]{{8, 10}, {11, 15}, {1, 0}, {1, 0}, {1, 0}, {1, 0}, {1, 0}, {1, 0}};
        quantiteJournaliere = new double[]{1.5, 4, 0, 0, 0, 0, 0, 0};
    }

    /**
     * Calcule la quantité d'eau de pluie qui a était versée par zone
     *
     * @return un tableau des quantité d'eau par zone
     */
    static double[] quantiteEauPluie() {
        double[] eauPluie = new double[zone];
        for (int i = 0; i < zone; i++) {
            eauPluie[i] = pluvioSommeHeure(tempsLimiteIrrigation(i));
        }
        return eauPluie;
    }

    /**
     * Fonction qui calcule le ratio entre la quantité attendu dans une journée
     * et la quantité reçu
     *
     * @param numZone le numéro de la zone de culture
     * @return le ratio entre attendu et reçu
     */
    static double quantiteEauPossible(int numZone) {
        return quantiteJournaliere[numZone - 1] - quantiteEauPluie()[numZone - 1];
    }

    /**
     * Fonction qui pour chaque zone vérifie la quantité d'eau reçu depuis la
     * fin du cycle précédent
     *
     * @param numZone le numéro de la zone de culture
     * @return retourne faux si la quantité d'eau a était dépassée sinon vrai
     */
    static boolean cycle(int numZone) {
        boolean start = true;
        if (quantiteJournaliere[numZone - 1]==0 || quantiteEauPluie()[numZone - 1]==-1){
            System.out.println("Zone inexistante");
            start = false;
        }
        if (quantiteEauPossible(numZone) <= 0) {
            System.out.println("Durant les " + (24 - tempsLimiteIrrigation(numZone - 1)) + "h dernières heures la quantité journalière a était effectuée");
            System.out.println("La culture ne demande pas plus d'eau");
            start = false;
        } else {
            System.out.printf("Pas assez d'eau de pluie pour la culture, il lui faut donc : %03.1f l en plus", quantiteEauPossible(numZone));
            System.out.println("");
        }
        return start;
    }

    /**
     * Fonction qui contrôle le nombre de zone
     */
    static void gestionDesZones() {
        if (zone == 0) {
            System.out.println("Créer votre première zone");
            ajouterUneZone();
        } else {
            System.out.println("1: pour ajouter une zone, 2: pour enlever une zone, 3: Lancer un cycle");
            int choix = saisieNombreIntervalleEntier(1, 3);
            switch (choix) {
                case 1:
                    ajouterUneZone();
                    break;
                case 2:
                    System.out.println("Quelle zone vous voulez supprimer ?");
                    int numZone2 = saisieNombreIntervalleEntier(1, zone);
                    supprimerUneZone(numZone2);
                    break;
                case 3:
                    System.out.println("Choisi un numéro de Zone");
                    int numZone = saisieNombreIntervalleEntier(1, zone);
                    cycle(numZone);
                    break;
                default:
                    System.out.println("erreur de programme");
                    break;
            }
        }
    }

    /**
     * Fonction qui permet de supprimer n'importe quelle zone
     */
    static void supprimerUneZone(int numZone) {
        creneauQuotidien[numZone - 1][0] = 1;
        creneauQuotidien[numZone - 1][1] = 0;
        quantiteJournaliere[numZone - 1] = 0;

    }

    /**
     * Fonction qui permet l'ajout d'une zone avec ça quantité d'eau
     */
    static void ajouterUneZone() {
        double calcul = 0;
        if (zone >= MAX_ZONE) {
            System.out.println("Nombre Zone maximal");
        } else {
            zone += 1;
            double saisie = ajoutUneQuantiteEau();
            quantiteJournaliere[zone - 1] = saisie;
            calcul = saisie * 60;
            System.out.println("Il faut a l'arrosseur 1 h pour verser 1 litre donc il faut un créneau pour cette zone de " + calcul + "min ou " + saisie + "h");
            System.out.println("Si la quantité n'est pas entière, on arrondira a l'heure supérieur. La machine s'arretera quand la quantité est atteint");
            System.out.println("Donner l'heure sous la forme (h) a laquelle vous voulez que cela commence");
            int h = saisieNombreIntervalleEntier(0, 23);
            creneauQuotidien[zone - 1][0] = h;
            if ((int) saisie < saisie) {
                creneauQuotidien[zone - 1][1] = (h + (int) saisie + 1) % 24;
            } else {
                creneauQuotidien[zone - 1][1] = (h + (int) saisie) % 24;
            }

        }
    }

    /**
     * Fonction qui récupère une quantité d'eau
     *
     * @return le choix de l'utilisateur
     */
    static double ajoutUneQuantiteEau() {
        System.out.println("Quelle est la quantité d'eau pour cette zone ?");
        Scanner sc = new Scanner(System.in);
        double saisie = sc.nextDouble();
        saisie = saisie * 10;
        saisie = (int) saisie / 10;
        return saisie;
    }

    /**
     * Fonction qui renvoie l'entier choisi l'utilisateur dans un intervalle de
     * nombre, placé en paramettre.
     *
     * @param min le plus petit nombre qui peut être entrer
     * @param max le plus grand nombre qui peut être entrer
     * @return la saisie de l'utilisateur
     */
    static int saisieNombreIntervalleEntier(int min, int max) {
        int saisie = 0;
        do {
            System.out.println("choisi un nombre entre " + min + " et " + max);
            Scanner sc = new Scanner(System.in);
            saisie = sc.nextInt();
            if (saisie < min || saisie > max) {
                System.out.println("erreur hors intervalle reesaye");
            }
        } while (saisie < min || saisie > max);
        return saisie;
    }

    /**
     * Fonction qui calcule le temps limite entre deux créneaux
     *
     * @param tZone la zone choisi
     * @return l'écart entre les deux créneaux
     */
    static int tempsLimiteIrrigation(int tZone) {
        return creneauQuotidien[tZone][1] - creneauQuotidien[tZone][0];
    }

    /**
     * Fonction qui permet d'avoir la quantité d'eau de pluie qu'il y a eu entre
     * la dernière irrigation et la nouvelle
     *
     * @param limite le temps irrigation
     * @return la somme de toutes les valeurs
     */
    static double pluvioSommeHeure(int limite) {
        double somme = 0;
        if(limite >= 0)
            for (int i = 0; i < Meteo.pluvioHeure.length - limite; i++) {
                somme += Meteo.pluvioHeure[i];
            }
        else{
            somme = 1;
        }
        return somme;

    }

}
