package eccezioni;

public class LessicaleException extends Exception{
    public LessicaleException(String message) {
        super(message);
    }
    /**
     * Costruttore dell'eccezione sintattica in cui si indica il messaggio che deve stampare tale eccezione
     * e la causa che ha fatto lanciare l'eccezione
     * @param message stringa che l'eccezione deve stampare
     * @param cause Throwable che mi indica un altro tipo di eccezione da rilanciare come eccezione sintattica
     * */
    public LessicaleException(String message, Throwable cause) {
        super(message, cause);
    }
}
