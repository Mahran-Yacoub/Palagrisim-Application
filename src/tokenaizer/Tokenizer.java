package tokenaizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Tokenizer {

    public void tokenizeFileToSingleWords(File source,File target) {

        try {
            Scanner scanner = new Scanner(source);
            FileWriter fileWriter = new FileWriter(target);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = line.trim().split("\\s+");
                fileWriter = new FileWriter(target, true);
                printWriter = new PrintWriter(fileWriter);

                for (int i = 0; i < words.length; i++) {
                    if (words[i].isEmpty()) {
                        continue;
                    }
                    words[i] = cleanWordFromPunctuations(words[i]);
                    printWriter.println(words[i]);
                }

                printWriter.close();
                fileWriter.close();
            }

        } catch (IOException exception) {
            System.out.println("Error with read source file");
        }
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
