package scanner;

import token.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.*;
import eccezioni.*;

public class Scanner {


    static final char EOF = (char) -1; // il carattere EOF
    private PushbackReader buffer; // il buffer di lettura
    private int riga; // il numero di riga corrente
    private Token currentToken; // il token corrente

    // insiemi di caratteri di skip, lettere e cifre
    private static final Set<Character> skpChars = new HashSet<>(Arrays.asList(' ', '\n', '\r', '\t', EOF));
    private static final Set<Character> letters = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
    private static final Set<Character> digits = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    // mapping fra caratteri e TokenType per gli operatori
    private static Map<Character, TokenType> operatorsMap = new HashMap<>();
    static {
        operatorsMap.put('=', TokenType.ASSIGN);
        operatorsMap.put('+', TokenType.PLUS);
        operatorsMap.put('-', TokenType.MINUS);
        operatorsMap.put('*', TokenType.TIMES);
        operatorsMap.put('/', TokenType.DIV);
        operatorsMap.put(';', TokenType.SEMI);
    }
    // PENSO DEBBA ELIMINARE TYPEFLOAT E TYPEINT
    private static Map<String, TokenType> keyWordsMap = new HashMap<>();
    static {
        // keyWordsMap.put("print", TokenType.PRINT);
        keyWordsMap.put("float", TokenType.TYFLOAT);
        keyWordsMap.put("int", TokenType.TYINT);
    }


    public Scanner(String fileName) throws FileNotFoundException {
        // Crea un PushbackReader per leggere il file di input
        this.buffer = new PushbackReader(new FileReader(fileName));
        if (this.buffer == null) {
            // Solleva un'eccezione se il buffer non viene inizializzato correttamente
            throw new FileNotFoundException("Il file non è stato trovato");
        }
        this.riga = 1;  // Imposta la riga corrente a 1

    }

    public Token nextToken() throws LessicaleException {
        if (this.currentToken != null) {
            Token t = this.currentToken;
            this.currentToken = null;
            return t;
        }
        char nextChar = 0;
        try {
            nextChar = peekChar();
            while (this.skpChars.contains(nextChar)) {
                this.readChar();
                if (nextChar == EOF) return new Token(TokenType.EOF, this.riga);
                if (nextChar == '\n') riga++;
                nextChar = peekChar();
            }
            if (this.digits.contains(nextChar)) return scanNumber();
            if (this.operatorsMap.containsKey(nextChar)) {
                this.readChar();
                return new Token(this.operatorsMap.get(nextChar), this.riga);
            }
            this.readChar();
            throw new LessicaleException("Il carattere " + nextChar + " in riga " + this.riga + " non è legale per l'inizio di un Token\n");
        } catch (IOException e) {
            throw new LessicaleException("Errore di lettura in riga " + this.riga + "\n", e);
        }
    }

    private Token scanNumber() throws IOException, LessicaleException {
        char nextChar = this.peekChar();
        String result = "";
        int conteggioDecimali = 0;
        final int MAX_DECIMAL_PLACES = 5;

        // Leggi le cifre per costruire il numero
        while (this.digits.contains(nextChar)) {
            result += nextChar; // Aggiunge il carattere letto al valore del numero
            this.readChar(); // Legge il carattere successivo
            nextChar = this.peekChar(); // Aggiorna il carattere da analizzare
        }

        // Se si incontra un punto, passa al valore decimale
        if (nextChar == '.') {
            result += nextChar; // Aggiunge il punto al valore del numero
            this.readChar(); // Legge il carattere successivo
            nextChar = this.peekChar(); // Aggiorna il carattere da analizzare

            // Continua a leggere i caratteri finché sono cifre
            while (this.digits.contains(nextChar)) {
                result += nextChar; // Aggiunge il carattere letto al valore del numero
                this.readChar(); // Legge il carattere successivo
                nextChar = this.peekChar(); // Aggiorna il carattere da analizzare
                conteggioDecimali++; // Incrementa il conteggio dei decimali del numero
                if (conteggioDecimali > MAX_DECIMAL_PLACES) {
                    throw new LessicaleException("Il Token non è valido per i caratteri numerici perché ci sono " +
                            conteggioDecimali + " decimali\n");
                }
            }

            // Se il carattere successivo non è né una cifra, né un operatore o un carattere da ignorare
            if (!(this.digits.contains(nextChar) || this.operatorsMap.containsKey(nextChar) ||
                    this.skpChars.contains(nextChar))) {
                throw new LessicaleException("Il carattere " + nextChar + " letto in riga " + this.riga +
                        " non è valido per i caratteri numerici\n");
            }

            return new Token(TokenType.FLOAT, this.riga, result);
        }

        // Se il carattere successivo non è né una cifra, né un operatore o un carattere da ignorare
        if (!(this.digits.contains(nextChar) || this.operatorsMap.containsKey(nextChar) || this.skpChars.contains(nextChar))) {
            throw new LessicaleException("Il carattere " + nextChar + " letto in riga " + this.riga + " non è valido per i caratteri numerici\n");
        }
        return new Token(TokenType.INT, this.riga, result);
    }


    private char readChar() throws IOException {
        try {
            return ((char) this.buffer.read()); // Legge un carattere dal buffer di input e lo restituisce
        } catch (IOException e) {
            throw new IOException("Errore nella lettura del successivo carattere\n", e); // Solleva un'eccezione con un messaggio di errore e l'eccezione originale
        }
    }

    private char peekChar() throws IOException {
        try {
            char c = (char) buffer.read(); // Legge il prossimo carattere dal buffer
            buffer.unread(c); // Riporta il carattere letto nel buffer per non avanzare il puntatore di lettura
            return c; // Restituisce il carattere letto
        }
        catch(IOException e) {
            throw new IOException("Errore nel vedere qual è il successivo carattere\n", e); // Genera un'eccezione se si verificano errori
        }
    }

    public Token peekToken() throws LessicaleException, IOException {
        if (currentToken == null) { // Se il token corrente non è stato ancora letto
            currentToken = this.nextToken(); // Legge il token corrente utilizzando il metodo nextToken()
        }
        return this.currentToken; // Restituisce il token corrente
    }

}
