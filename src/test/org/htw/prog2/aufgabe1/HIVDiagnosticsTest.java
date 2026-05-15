package org.htw.prog2.aufgabe1;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HIVDiagnosticsTest {

    /**
     * Apache Commons CLI: wir müssen das Parsen nicht selbst programmieren. wir sagen der Bibliothek nur:
     * 1. welche optionen es gibt (-m, -d, -r, -p)
     * 2. ob sie Pflicht sind (alle 4 ja!)
     * 3. ob sie einen Wert haben (ja z.B. -m data/HIVMutationPatterns.csv)
     * und die Bibliothek macht den Rest
     *
     * -m < Mutation file
     * -d < Drug name
     * -r < Reference sequence
     * -p < Patient sequences
     */

    @Test
    void parseOptions_requiredArguments() {
        assertNull(HIVDiagnostics.parseOptions(new String[] {})); // wenn keine Argumente übergeben werden > null zurückgeben
        assertNull(HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -r data/protease_reference.fasta".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -d ProteaseInhibitor".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-d ProteaseInhibitor -r data/protease_reference.fasta".split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -d ProteaseInhibitor -r data/protease_reference.fasta".
                        split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -p data/protease_sequences.fasta -r data/protease_reference.fasta".
                        split(" ")));
        assertNull(HIVDiagnostics.parseOptions(
                "-p data/protease_sequences.fasta -r data/protease_reference.fasta -d ProteaseInhibitor ".
                        split(" ")));
        assertNotNull(HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -p data/protease_sequences.fasta -r data/protease_reference.fasta -d ProteaseInhibitor ".
                        split(" "))); // nur wenn alle vier Argumente da sind -> nicht null zurückgeben
    }



    @Test
    void parseOptions_argumentValues() {
        CommandLine cli = HIVDiagnostics.parseOptions(
                "-m data/HIVMutationPatterns.csv -p data/protease_sequences.fasta -r data/protease_reference.fasta -d ProteaseInhibitor ".
                split(" "));
        assertEquals("data/HIVMutationPatterns.csv", cli.getOptionValue('m'));
        assertEquals("data/protease_sequences.fasta", cli.getOptionValue('p'));
        assertEquals("data/protease_reference.fasta", cli.getOptionValue('r'));
        assertEquals("ProteaseInhibitor", cli.getOptionValue('d'));
    }

}