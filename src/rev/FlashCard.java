
package rev;

import java.io.Serializable;

/**
 *
 * @author Adam Whittaker
 */
public class FlashCard implements Serializable{
    
    private static final long serialVersionUID = 2684292507281322676L;
    
    protected String front;
    protected String back;
    protected String topic;
    protected Status status = Status.UNREVISED;
    protected int revision = 0;
    
    
    public FlashCard(String f, String b, String t, int r, String stat){
        front = f;
        back = b;
        topic = t;
        revision = r;
        status = Status.valueOf(stat);
    }
    
    public FlashCard(String f, String b, String t){
        front = f;
        back = b;
        topic = t;
    }
    
    public enum Status{
        UNREVISED, REVISED
    }
    
    
    public void print(MessageLog meslog){
        System.out.println(topic + ": " + front);
        String s = Revise.next();
        System.out.println("\n" + back);
        revise(meslog);
    }
    
    public void printout(){
        System.out.println(topic + ": " + front + "\n\n" + back);
    }
    
    protected boolean contains(String p){
        if(front.toLowerCase().contains(p)) return true;
        if(back.toLowerCase().contains(p)) return true;
        return topic.toLowerCase().contains(p);
    }
    
    protected void revise(MessageLog meslog){
        revision++;
        if(revision>=meslog.revisionDifficulty) status = Status.REVISED;
    }
    
}
