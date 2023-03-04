package ngram;

public class Row {

    private String ngram;
    private int n;
    private int count ;
    private double probability ;

    public Row(String ngram, int n, int count, double probability) {
        this.ngram = ngram;
        this.n = n;
        this.count = count;
        this.probability = probability;
    }

    public String getNgram() {
        return ngram;
    }

    public int getN() {
        return n;
    }

    public int getCount() {
        return count;
    }

    public double getProbability() {
        return probability;
    }
}
