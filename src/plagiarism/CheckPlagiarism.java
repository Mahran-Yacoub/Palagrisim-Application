package plagiarism;


import ngram.NGram;
import ngram.Row;
import java.io.File;
import java.util.*;

public class CheckPlagiarism {

    private String input;

    public CheckPlagiarism(String input) {
        this.input = input;
    }


    public int getPercentageOfPlagiarism() {
        NGram nGramModel = readCSVModelFile(new File("src/files/model.csv"));
        HashMap<String, Row> model = nGramModel.getNGramModel();
        int percentageOfPlagiarismOfOneWords = getPercentageOfPlagiarism(model, 1);
        int percentageOfPlagiarismOfTwoWords = getPercentageOfPlagiarism(model, 2);
        int percentageOfPlagiarismOfThreeWords = getPercentageOfPlagiarism(model, 3);
        int percentageOfPlagiarismOfFourWords = getPercentageOfPlagiarism(model, 4);
        return getPercentage(percentageOfPlagiarismOfOneWords, percentageOfPlagiarismOfTwoWords,
                percentageOfPlagiarismOfThreeWords, percentageOfPlagiarismOfFourWords);
    }

    private int getPercentage(int percentageOfPlagiarismOfOneWords, int percentageOfPlagiarismOfTwoWords,
                        int percentageOfPlagiarismOfThreeWords, int percentageOfPlagiarismOfFourWords) {
        int sum = percentageOfPlagiarismOfOneWords + percentageOfPlagiarismOfTwoWords +
                percentageOfPlagiarismOfThreeWords + percentageOfPlagiarismOfFourWords;
        return sum / 4;
    }

    private NGram readCSVModelFile(File file) {
        NGram nGram = new NGram();
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                nGram.addRow(new Row(parts[0],1,1,1));

            }
            scanner.close();
        } catch (Exception ioException) {
            System.out.println("read csv exception");
        }

        return nGram;
    }

    public int getPercentageOfPlagiarism(HashMap<String, Row> model, int n) {

        Queue<String> ngrams = countNWordInInput(n);
        if(ngrams == null){
            return 0;
        }

        int count = 0 ;
        for(String ngram : ngrams){
            if(model.containsKey(ngram)){
                count++;
            }
        }

        return (int)(((double)count/ngrams.size())*100);
    }

    private ArrayList<String> getSingleWords() {
        ArrayList<String> singleWords = new ArrayList<>();
        String [] words = cleanWordFromPunctuations(input).split("\\s+");
        for(String word : words){
            singleWords.add(word);
        }
        return singleWords;
    }

    public Queue<String> countNWordInInput(int n) {
        Queue<String> wordsInString = new LinkedList<>();
        ArrayList<String> words = getSingleWords();

        if (words.size() < n) {
            return null;
        }

        int numberOfSegment = words.size() - (n - 1);
        for (int i = 0; i < numberOfSegment; i++) {
            String key = getKey(words, n);
            wordsInString.add(key);
        }
        return wordsInString;
    }

    private String getKey(ArrayList<String> words, int n) {

        String key = words.remove(0);
        for (int i = 0; i < (n - 1); i++) {
            key = key + " " + words.get(i);
        }

        return key.trim();
    }

    private String cleanWordFromPunctuations(String word){
        String [] punctuations = new String[]{".","؟","،","؛","!","~",":"};
        for(int i=0 ; i<punctuations.length ; i++){
            if(word.contains(punctuations[i])){
                word = word.replace(punctuations[i],"");
            }
        }

        return word;
    }

}
