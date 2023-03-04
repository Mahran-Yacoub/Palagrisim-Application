package ngram;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NGram {

    private Queue<Row> rows ;

    public NGram() {
        this.rows = new LinkedList<>();
    }

    public boolean addRow(Row row){
        return rows.add(row);
    }

    public Queue<Row> getRows(){
        Queue<Row> copyOfRows = new LinkedList<>();
        for(int i=0 ; i<rows.size() ; i++){
            Row row = rows.poll();
            copyOfRows.add(row);
            rows.add(row);
        }

        return copyOfRows;
    }

    public HashMap<String,Row> getNGramModel(){
        HashMap<String,Row> model = new HashMap<>();
        for(Row row : rows){
            model.put(row.getNgram(),row);
        }
        return model;
    }

}
