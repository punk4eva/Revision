
package exceptions;

/**
 *
 * @author Adam Whittaker
 */
public class ModeUnparsableException extends Exception{

    /**
     * Creates a new instance of <code>ModeUnparsableException</code> without
     * detail message.
     */
    public ModeUnparsableException(){}

    /**
     * Constructs an instance of <code>ModeUnparsableException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ModeUnparsableException(String msg){
        super(msg);
    }
}
