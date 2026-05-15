package org.htw.prog2.aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
// für catch (IOException e)
import java.io.IOException;

public class SeqFile {
    // Was brauchen wir um alle Tests zu erfüllen?
    private boolean valid = false;
    private HashSet<String> sequences = new HashSet<>();
    private String firstSequence = "";

    /**
     * Reads the specified FASTA file and stores sequences. In case the file does not exist or is not a valid FASTA
     * file, the Constructor does not throw an Exception. Instead, isValid() on the resulting object will return false.
     * @param filename
     */
    public SeqFile(String filename) {
        valid = readFile(filename); // readFile() gibt true/ false zurück > direkt in valid speichern
    }

    /**
     * Reads the specified FASTA file.
     * @param filename The path to the FASTA file
     * @return false if the file could not be parsed (wrong format, does not exist), true otherwise.
     */
    private boolean readFile(String filename) {
        try {
            // Werkzeug zum zeilenweisen Lesen
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line; // line = aktuelle Zeile
            StringBuilder currentSeq = null; // Zwischenspeicher noch null: "es gibt noch gar keinen StringBuilder", keine > Zeile gesehen

            while ((line = reader.readLine()) != null) { // lies Zeile > speichere sie in line > wenn null = Datei zu Ende > aufhören

                // der > Fall: neue > Zeile erkannt > erst alte Sequenz speichern > dann frisch starten
                if (line.startsWith(">")) {
                    if(currentSeq != null) { // Überprüfung VOR addsequence(): Beim allerersten > ist currentSeq noch null. es gibt noch keine alte Sequenz zu retten. ohne Überprüfung > Absturz
                        addSequence(currentSeq); // alte bzw. letzte Sequenz retten
                    }
                    currentSeq = new StringBuilder(); // neu starten
                }

                // der normale Fall: keine > Zeile -> einfach an aktuelle Sequenz dranhängen
                else {
                    // Sequenzzeile -> anhängen
                    if (currentSeq != null) {
                        currentSeq.append(line);
                    }
                }
            }
            // Nach while Schleife
            if (currentSeq != null) {
                addSequence(currentSeq); // alte bzw. letzte Sequenz retten (allerletzte Sequenz hat keine > nach sich
            }

            reader.close();
            return !sequences.isEmpty(); // true wenn mindestens EINE Sequenz. !isEmpty() = "ist NICHT leer?" -> true wenn mindestens eine Sequenz drin. / isEmpty() = "ist leer?" -> true wenn leer.
        } catch (IOException e) {
            return false; // Datei nicht gefunden
        }
    }

    /**
     * Adds the sequence in the passed StringBuilder to the internal hash set and also sets the first sequence if it
     * is still empty.
     * @param seq SequenceBuilder to get the sequence from.
     * @return The length of the added sequence.
     */
    private int addSequence(StringBuilder seq) {
        String sequence = seq.toString(); // 1. Sequenz aus dem StringBuilder holen
        sequences.add(sequence); // 2. Zum HashSet hinzufügen
        if (firstSequence.isEmpty()) {
            firstSequence = sequence; // 3. Falls firstSequence noch leer ist > diese als erste merken (nur beim allerersten Aufruf)
        }
        return sequence.length(); // 4. Die Länge zurückgeben
    }

    /**
     *
     * @return The number of sequences read from the FASTA file, or 0 if isValid() is false.
     */
    public int getNumberOfSequences() {
        return sequences.size(); // gibt die echte Anzahl zurück
    }

    /**
     *
     * @return The sequences read from the FASTA file, or an empty HashSet if isValid() is false.
     */
    public HashSet<String> getSequences() {
        return sequences; // gibt das echte HashSet, unsere Variable zurück
    }

    /**
     *
     * @return The first sequence read from the FASTA file, or an empty String if isValid() is false.
     */
    public String getFirstSequence() {
        return firstSequence; // gibt die erste Sequenz zurück oder "" wenn noch nichts drin ist, da firstSequence = "" am Anfang gesetzt
    }

    /**
     *
     * @return true if the FASTA file was read successfully, false otherwise.
     */
    public boolean isValid() {
        return valid; // gibt den echten Wert, unsere Variable zurück
    }
}