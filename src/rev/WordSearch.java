
package rev;

import exceptions.ModeUnparsableException;
import java.util.ArrayList;

/**
 *
 * @author Adam Whittaker
 */
public class WordSearch{
    
    
    protected String text;
    protected String keyword;
    protected Mode mode = Mode.SENTENCE;
    protected int keynumber = 1;
    protected boolean ignoreCase = false;
    
    
    public WordSearch(String t){
        text = t;
    }
    
    public WordSearch(String t, boolean ic){
        ignoreCase = ic;
        if(ic) text = t.toLowerCase();
        else text = t;
    }
    
    protected enum Mode{
        SENTENCE, LINE, FIRST_INDEX, LAST_INDEX, INDEX_LIST, NTH_INDEX 
    }
    
    
    protected boolean exists(){
        return text.contains(keyword);
    }
    
    protected void setText(String t){
        text = t;
    }
    
    protected void setKeyword(String k){
        keyword = k;
    }
    
    protected void setMode(String m) throws ModeUnparsableException{
        try{
            mode = Mode.valueOf(m.toUpperCase());
        } catch(IllegalArgumentException e){
            throw new ModeUnparsableException("Mode cannot be set to " + m);
        }
    }
    
    protected void setKeynumber(int n){
        keynumber = n;
    }
    
    
    protected String getSentence(){
        if(!exists()){
            System.out.println("The keyword doesn't exist in the text.");
            return "KEYWORD NOT FOUND";
        }
        String sents[] = text.split(".|?|!");
        String ret = "";
        for(int j=0;j<sents.length-1;j++){
            if(sents[j].contains(keyword)){
                for(int n=j;n<sents.length-1&&n<keynumber;n++) ret += sents[n];
                return ret;
            }
        }
        return "Error 0000: Obligatory return in getSentence().";
    }
    
    protected int firstIndex(){
        return text.indexOf(keyword);
    }
    
    protected int lastIndex(){
        return text.lastIndexOf(keyword);
    }
    
    //unfinished
    protected Integer[] indexList(){
        ArrayList<Integer> lst = new ArrayList<>();
        for(int n=0;n<text.length()-keyword.length();n++){
            //unfinished
        }
        return lst.toArray(new Integer[lst.size()]);
    }
    
    protected int nthIndex(){
        int c = 0;
        for(int n=0;n<text.length();n++){
            if(text.substring(n).indexOf(keyword)==0){
                c++;
                if(c==keynumber) return n;
            }
        }
        return -1;
    }
    
    protected String getLine(){
        if(!exists()){
            System.out.println("The keyword doesn't exist in the text.");
            return "KEYWORD NOT FOUND";
        }
        String lines[] = text.split("\n|\r");
        String ret = "";
        for(int j=0;j<lines.length;j++){
            if(lines[j].contains(keyword)){
                for(int n=j;n<lines.length-1&&n<keynumber;n++) ret += 
                        "\n" + lines[n];
                return ret;
            }
        }
        return "Error 0000: Obligatory return in getLine().";
    }
    
    
    public void find(){
        switch(mode){
            case SENTENCE:
                System.out.println(getSentence());
                break;
            case FIRST_INDEX:
                System.out.println(firstIndex());
                break;
            case LAST_INDEX:
                System.out.println(lastIndex());
                break;
            case NTH_INDEX:
                System.out.println(nthIndex());
                break;
            case INDEX_LIST:
                System.out.println();
                for(int i : indexList()) System.out.print(i + " ");
                break;
            default:
                System.out.println(getLine());
                break;
        }
    }
    
    
    public static void main(String args[]){
        WordSearch w = new WordSearch("1111111111");
        w.setKeyword("1");
        w.setKeynumber(4);
        System.out.println(w.nthIndex());
    }
    
}
