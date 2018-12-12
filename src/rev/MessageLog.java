
package rev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Adam Whittaker
 */
public class MessageLog implements Serializable{    
    
    public interface Sort{
        boolean select(FlashCard message);
    }
    
    private static final long serialVersionUID = 3689964712109686681L;
    protected List<FlashCard> log = new LinkedList<>();
    protected File loggingFile;
    protected int revisionDifficulty = 27;
    public HashMap<String, String> collections = new HashMap<>();
    
    protected MessageLog(File lf){
        loggingFile = lf;
    }
    
    public static MessageLog getLog(File f){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))){
            MessageLog ret = (MessageLog) ois.readObject();
            ret.loggingFile = f;
            return ret;
        }catch(IOException e){
            System.err.println("failed to create OIS.");
            e.printStackTrace(System.out);
        }catch(ClassNotFoundException e){
            System.err.println("failed to find class.");
        }
        return null;
    }
    
    protected void displayTopics(){
        new HashSet<>(log.stream().map(fl -> fl.topic).collect(Collectors.toList())).stream().forEach((i) -> {
            System.out.println(i);
        });
        collections.keySet().stream().forEach((i) -> {
            System.err.println(i);
        });
    }
    
    protected void add(FlashCard m){
        log.add(m);
    }
    
    protected void clear(){
        log = new ArrayList<>();
    }
    
    protected void revise(Sort sort){
        Collections.shuffle(log);
        log.stream().filter(m -> sort.select(m)).map(m -> {
            m.print(this);
            System.out.print("\n");
            return m;
        }).forEach(ignore -> {
            System.out.println();
        });
    }
    
    protected void revise(String topics[]){
        if(topics[0].endsWith("all")) revise();
        else for(String topic : topics) revise(m -> m.topic.compareToIgnoreCase(topic)==0);
    }
    
    protected void revise(){
        Collections.shuffle(log);
        log.stream().map(m -> {
            m.print(this);
            System.out.print("\n");
            return m;
        }).forEach(ignore -> {
            System.out.println();
        });
    }
    
    protected boolean contains(String p){
        String phrase = p.toLowerCase();
        return log.stream().anyMatch(flash -> flash.contains(phrase));
    }
    
    protected void edit(String topics[]){
        if(topics[0].endsWith("all")) edit();
        else for(String topic : topics) edit(m -> m.topic.compareToIgnoreCase(topic)==0);
    }
    
    protected void edit(){
        Iterator<FlashCard> iter = log.iterator();
        LinkedList<FlashCard> tempList = new LinkedList<>();
        while(iter.hasNext()){
            FlashCard edit = iter.next();
            System.out.println("What section do you want to edit?");
            String in = Revise.scan.next().toUpperCase();
            boolean edited = true;
            switch(in){
                case "topic":
                    System.out.println("Type the new topic...");
                    edit.topic = Revise.scan.next().toUpperCase();
                    break;
                case "front":
                    System.out.println("Type the new front...");
                    edit.front = Revise.scan.next().toUpperCase();
                    break;
                case "back":
                    System.out.println("Type the new back...");
                    edit.back = Revise.scan.next().toUpperCase();
                    break;
                default: edited = false;
            }
            if(edited){
                iter.remove();
                tempList.add(edit);
            }
        }
        log.addAll(tempList);
    }
    
    protected void edit(Sort sort){
        Iterator<FlashCard> iter = log.iterator();
        LinkedList<FlashCard> tempList = new LinkedList<>();
        while(iter.hasNext()){
            FlashCard edit = iter.next();
            if(!sort.select(edit)) continue;
            edit.printout();
            System.out.println("\nWhat section do you want to edit?");
            String in = Revise.next().toLowerCase();
            boolean edited = true;
            switch(in){
                case "topic":
                    System.out.println("Type the new topic...");
                    edit.topic = Revise.next();
                    break;
                case "front":
                    System.out.println("Type the new front...");
                    edit.front = Revise.next();
                    break;
                case "back":
                    System.out.println("Type the new back...");
                    edit.back = Revise.next();
                    break;
                default: edited = false;
            }
            if(edited){
                System.err.println("Edit successful");
                iter.remove();
                tempList.add(edit);
            }
        }
        log.addAll(tempList);
    }
    
    protected void addAll(MessageLog meslog){
        log.addAll(meslog.log);
    }
    
    protected void addAll(MessageLog meslog, Sort sort){
        log.addAll(meslog.log.stream().filter(m -> sort.select(m)).collect(Collectors.toList()));
    }
    
    protected void end(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(loggingFile))){
            oos.writeObject(this);
        }catch(IOException e){
            System.err.println("failed to write");
            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("writing.ser")))){
                oos.writeObject(this);
            }catch(IOException ie){
                System.err.println("failed to write again");
            }
        }
    }
    
}
