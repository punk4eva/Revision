
package rev;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import static rev.MessageLog.getLog;

/**
 *
 * @author Adam Whittaker
 */
public class Revise{
    
    
    protected static Scanner scan = new Scanner(System.in);
    protected static MessageLog log;
    protected static final String filePath = "H:\\rev\\";
    
    protected static enum Subject{
        BIOLOGY, CHEMISTRY, PHYSICS, ENGLISH, MATHS, HISTORY, GEOLOGY, 
        ECONOMICS, GERMAN, COMPUTING, MISTAKES
    }
    
    
    protected static String next(){
        return scan.nextLine();
    }
    
    protected static void add(){
        System.out.println("Which subject do you want to add flashcards to?");
        String sub = next().toUpperCase();
        if(sub.equals("/EXIT")) return;
        setLog(sub);
        log.displayTopics();
        while(true){
            System.out.println("TOPIC?");
            String topic = next();
            if(topic.equals("/exit")) break;
            while(true){
                System.out.println("FRONT?");
                String front = next();
                if(front.equals("/change")) break;
                if(front.equals("/exit")) return;
                System.out.println("BACK?");
                String back = next();
                FlashCard flash = new FlashCard(front, back, topic);
                if(!back.equals("/delete")) log.add(flash);
            }
        }
    }
    
    protected static void collect(){
        System.out.println("Which subject do you want to collect?");
        String sub = next().toUpperCase();
        if(sub.equals("/EXIT")) return;
        setLog(sub);
        System.out.print("Which topics do you want to collect?\n");
        log.displayTopics();
        String collection = next();
        System.out.print("What do you want to name your collection?\n");
        String name = next();
        log.collections.put(name, collection);
    }
    
    protected static void end(){
        log.end();
    }
    
    protected static void reviseAll(){
        List<FlashCard> all = new LinkedList<>();
        log = getLog(new File(filePath+"biology.ser"));
        all.addAll(log.log);
        all.addAll(getLog(new File(filePath+"chemistry.ser")).log);
        all.addAll(getLog(new File(filePath+"physics.ser")).log);
        all.addAll(getLog(new File(filePath+"english.ser")).log);
        all.addAll(getLog(new File(filePath+"maths.ser")).log);
        all.addAll(getLog(new File(filePath+"geology.ser")).log);
        all.addAll(getLog(new File(filePath+"economics.ser")).log);
        all.addAll(getLog(new File(filePath+"german.ser")).log);
        all.addAll(getLog(new File(filePath+"history.ser")).log);
        all.addAll(getLog(new File(filePath+"computing.ser")).log);
        all.addAll(getLog(new File(filePath+"mistakes.ser")).log);
        Collections.shuffle(all);
        all.stream().map(m -> {
            m.print(log);
            System.out.print("\n");
            return m;
        }).forEach(ignore -> {
            System.out.println();
        });
    }
    
    protected static void edit(){
        System.out.println("Which subject do you want to edit?");
        String sub = scan.next().toUpperCase();
        if(sub.equals("/EXIT")) return;
        setLog(sub);
        log.edit(getTopics(log));
    }
    
    protected static boolean contains(){
        System.out.println("Which subject do you want to scan?");
        String sub = scan.next().toUpperCase();
        if(sub.equals("/EXIT")) return true;
        System.out.println("What do you want to scan for?");
        String sc = scan.next();
        setLog(sub);
        return log.contains(sc);
    }
    
    protected static String[] getTopics(MessageLog meslog){
        System.out.print("Which topic do you want to revise/edit?\n");
        meslog.displayTopics();
        String t[] = next().split(", ");
        Set<String> cols = meslog.collections.keySet();
        LinkedList<String> ret = new LinkedList<>();
        for(String s : t){
            recursiveAdd(s, ret, cols, meslog);
        }
        return ret.toArray(new String[ret.size()]);
    }
    
    private static void recursiveAdd(String s, List<String> ret, Set<String> cols, MessageLog meslog){
        if(cols.contains(s)) for(String i : meslog.collections.get(s).split(", ")) recursiveAdd(i, ret, cols, meslog);
        else ret.add(s);
    }
    
    protected static void setLog(String l){
        switch(Subject.valueOf(l)){
            case BIOLOGY:
                log = getLog(new File(filePath+"biology.ser"));
                break;
            case CHEMISTRY:
                log = getLog(new File(filePath+"chemistry.ser"));
                break;
            case PHYSICS:
                log = getLog(new File(filePath+"physics.ser"));
                break;
            case ENGLISH:
                log = getLog(new File(filePath+"english.ser"));
                break;
            case MATHS:
                log = getLog(new File(filePath+"maths.ser"));
                break;
            case HISTORY:
                log = getLog(new File(filePath+"history.ser"));
                break;
            case GEOLOGY:
                log = getLog(new File(filePath+"geology.ser"));
                break;
            case ECONOMICS:
                log = getLog(new File(filePath+"economics.ser"));
                break;
            case GERMAN:
                log = getLog(new File(filePath+"german.ser"));
                break;
            case COMPUTING:
                log = getLog(new File(filePath+"computing.ser"));
                break;
            case MISTAKES:
                log = getLog(new File(filePath+"mistakes.ser"));
                break;
        }
    }
    
    protected static boolean filesExist(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath+"biology.ser")))){
            MessageLog l = (MessageLog) ois.readObject();
        }catch(IOException e){
            System.out.println("Files don't exist.");
            return false;
        }catch(ClassNotFoundException e){
            System.out.println("Failed to find class.");
            return false;
        }
        return true;
    }
    
    protected static void endAll(){
        getLog(new File(filePath+"biology.ser")).end();
        getLog(new File(filePath+"chemistry.ser")).end();
        getLog(new File(filePath+"physics.ser")).end();
        getLog(new File(filePath+"english.ser")).end();
        getLog(new File(filePath+"maths.ser")).end();
        getLog(new File(filePath+"geology.ser")).end();
        getLog(new File(filePath+"economics.ser")).end();
        getLog(new File(filePath+"german.ser")).end();
        getLog(new File(filePath+"history.ser")).end();
        getLog(new File(filePath+"computing.ser")).end();
        getLog(new File(filePath+"mistakes.ser")).end();
    }
    
    
    public static void main(String args[]){
        //endAll(); return;
        if(!filesExist()) return;
        System.out.println("Which subject do you want to revise?");
        String sub = scan.next().toUpperCase();
        switch (sub) {
            case "COLLECT":
                collect();
                end();
                break;
            case "ADD":
                add();
                end();
                break;
            case "ALL":
                reviseAll();
                break;
            case "EDIT":
                edit();
                end();
                break;
            case "CONTAINS":
                System.err.println(contains());
            case "/EXIT": break; 
            default:
                setLog(sub);
                log.revise(getTopics(log));
        }
        
    }
    
}
