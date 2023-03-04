package ngram;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NGramBuilder {

    private File tokens;
    private int n;

    private int corpusLength;

    public NGramBuilder(File tokens, int n) {
        this.tokens = tokens;
        this.n = n;
    }

    public void buildNGramModel(File modelCSV) {
        NGram model = buildModel();
        writeModelInCSVFile(modelCSV, model);
    }

    public void writeModelInCSVFile(File modelCSV, NGram model) {
        try {
            FileWriter fileWriter = new FileWriter(modelCSV);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String header = "ngram,N,count,probability";
            printWriter.println(header);
            Queue<Row> rows = model.getRows();
            int size = rows.size();
            for (int i = 0; i < size; i++) {
                Row row = rows.poll();
                String line = row.getNgram() + "," + row.getN() + "," + row.getCount() + "," + row.getProbability();
                printWriter.println(line);
            }

            printWriter.close();
            fileWriter.close();

        } catch (IOException ioException) {
            System.out.println("write CSV file exception");
        }
    }

    private NGram buildModel() {
        NGram model = new NGram();
        countCorpus();

        for (int i = 1; i <= n; i++) {

            HashMap<String, Integer> ngram = countNWordInFile(i);
            HashMap<String, Integer> previousNgram = null;
            if (i != 1) {
                previousNgram = countNWordInFile(i - 1);
            }

            Set<String> keys = ngram.keySet();
            String[] keysAsArray = new String[keys.size()];
            keys.toArray(keysAsArray);

            fillNGramModelWithRows(keysAsArray, model, ngram, previousNgram, i);
        }

        return model;
    }

    private void fillNGramModelWithRows(String[] keysAsArray, NGram model, HashMap<String,
            Integer> ngram, HashMap<String, Integer> previousNgram, int n) {

        for (int i = 0; i < keysAsArray.length; i++) {
            String key = keysAsArray[i];

            //problem1
            if(key.isEmpty()){
                continue;
            }

            int count = ngram.get(key);
            double probability = 0;
            if (n == 1) {
                probability = (double) count / corpusLength;

            } else {

                String[] parts = key.split(" ");
                String previousPart = "";
                for (int j = 0; j < parts.length - 1; j++) {
                    previousPart += " " + parts[j];
                }

                try{
                    Integer countOfPreviousPart = previousNgram.get(previousPart.trim());
                    probability = (double) count / countOfPreviousPart;
                }catch (Exception e){
                }

            }

            probability = ((int)(probability*10000))/10000.0;
            model.addRow(new Row(key, n, count, probability));
        }
    }

    public void countCorpus() {

        try {
            Scanner scanner = new Scanner(tokens);
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                corpusLength++;
            }
            scanner.close();
        } catch (IOException ioException) {
            System.out.println("count corpus exception");
        }
    }

    private ArrayList<String> getSingleWords() {
        ArrayList<String> singleWords = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(tokens);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                singleWords.add(word);
            }
            scanner.close();
        } catch (IOException ioException) {
            System.out.println("count single words exception");
        }

        return singleWords;
    }

    public HashMap<String, Integer> countNWordInFile(int n) {
        HashMap<String, Integer> wordsCount = new HashMap<>();
        ArrayList<String> words = getSingleWords();

        if (words.size() < n) {
            return null;
        }

        int numberOfSegment = words.size() - (n - 1);
        for (int i = 0; i < numberOfSegment; i++) {
            String key = getKey(words, n);
            if (!wordsCount.containsKey(key)) {
                wordsCount.put(key, 1);
            } else {
                wordsCount.put(key, wordsCount.get(key) + 1);
            }
        }
        return wordsCount;
    }

    private String getKey(ArrayList<String> words, int n) {

        String key = words.remove(0);
        for (int i = 0; i < (n - 1); i++) {
            key = key + " " + words.get(i);
        }

        return key.trim();
    }

}
