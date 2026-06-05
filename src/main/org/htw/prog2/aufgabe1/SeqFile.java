package org.htw.prog2.aufgabe1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.LinkedList;

public class SeqFile {
    HashSet<String> seqs = new HashSet<>();
    String firstSeq = "";
    boolean isValid = true;

    public SeqFile(String filename) {
        isValid = readFile(filename);
    }

    /**
     * Reads the specified FASTA file.
     * @param filename The path to the FASTA file
     * @return false if the file could not be parsed (wrong format, does not exist), true otherwise.
     */
    private boolean readFile(String filename) {
        File infile = new File(filename);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(infile));
            String line;
            StringBuilder seq = new StringBuilder();
            line = reader.readLine();
            // The first line must be a sequence header
            if(line.charAt(0) != '>') {
                return false;
            }
            while((line = reader.readLine()) != null) {
                if(line.charAt(0) == '>') {
                    // This can only happen if two sequence headers directly follow each other. This is not valid.
                    if(addSequence(seq) == 0) {
                        return false;
                    }
                    seq = new StringBuilder();
                }
                else {
                    seq.append(line.strip());
                }
            }
            // This would be the case if the last line in the file was a sequence header. This is not valid.
            if(addSequence(seq) == 0) {
                return false;
            }
            addSequence(seq);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Adds the sequence in the passed StringBuilder to the internal list and also sets the first sequence if it
     * is still empty.
     * @param seq SequenceBuilder to get the sequence from.
     * @return The length of the added sequence.
     */
    private int addSequence(StringBuilder seq) {
        String seqString = seq.toString();
        seqs.add(seqString);
        // This is the case if this is the first sequence we're reading -> save it
        if(firstSeq.equals("")) {
            firstSeq = seqString;
        }
        return seqString.length();
    }

    public int getNumberOfSequences() {
        if(isValid) {
            return seqs.size();
        }
        return 0;
    }

    public HashSet<String> getSequences() {
        if(isValid) {
            return seqs;
        }
        return new HashSet<>();
    }

    public String getFirstSequence() {
        if(isValid) {
            return firstSeq;
        }
        return "";
    }

    public boolean isValid() {
        return isValid;
    }
}
