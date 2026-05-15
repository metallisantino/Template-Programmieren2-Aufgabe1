package org.htw.prog2.aufgabe1;
import org.apache.commons.cli.*;

public class HIVDiagnostics {

    /**
     * Parst die Kommandozeilenargumente. Gibt null zurück, falls:
     * <ul>
     *     <li>Ein Fehler beim Parsen aufgetreten ist (z.B. eins der erforderlichen Argumente nicht angegeben wurde)</li>
     *     <li>Bei -m, -d und -r nicht die gleiche Anzahl an Argumenten angegeben wurde</li>
     * </ul>
     * @param args Array mit Kommandozeilen-Argumenten
     * @return CommandLine-Objekt mit geparsten Optionen
     */

    public static CommandLine parseOptions(String[] args) {
        // 1. Optionen definieren
        Options options = new Options(); // leere "liste" von Optionen
        // options.addRequiredOption(Kurzform, Langform, hat Wert?, Beschreibung)
        options.addRequiredOption("m", "mutationfiles", true, "Pfad zu CSV-Datei mit Mutationspattern" );
        options.addRequiredOption("d", "drugnames", true, "Name des Medikaments" );
        options.addRequiredOption("r", "references", true, "Pfad zu FASTA-Datei mit der Referenzsequenz" );
        options.addRequiredOption("p", "patientseqs", true, "Pfad zu FASTA-Datei mit Patientensequenzen" );

        // 2. Parser und HelpFormatter
        CommandLineParser parser = new DefaultParser(); // das Werkzeug das die Argumente liest
        HelpFormatter formatter = new HelpFormatter(); // gibt eine Hilfsmeldung aus wenn etwas fehlt

        /**
         * 3. Parsen versuchen
         * parser.parse() kann einen ParseException Fehler werfen wenn:
         * a. eine Pflicht-Option fehlt (z.B. -m nicht angegeben)
         * b. eine unbekannte Option angegeben wird
         *
         * also: versuche die Argumente zu parsen. Falls eine Pflicht-Option fehlt > drucke die Hilfe aus > gib null zurück
         */

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            // fehlt eine Pflicht-Option > Hilfe ausgeben > null zurückgeben
            formatter.printHelp("HIVDiagnostics", options);
            return null;
        }
    }

    public static void main(String[] args) {
    }
}
