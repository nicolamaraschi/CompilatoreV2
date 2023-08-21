package parser;

import ast.*;
import eccezioni.LessicaleException;
import eccezioni.SintatticaExeption;
import scanner.Scanner;
import token.Token;
import token.TokenType;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {
    private Scanner scanner;

    // Costruttore del parser
    public Parser(Scanner scanner) {
        this.scanner=scanner;
    }


    // Metodo principale per avviare il parsing
    public NodeProgram parse() throws SintatticaExeption {
        return this.parsePrg();
    }


    private NodeProgram parsePrg() throws SintatticaExeption{
        Token tk = null;
        try {
            tk = this.scanner.peekToken(); // Prende il token successivo
        } catch(IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e); // Gestisce eventuali errori lessicali
        }
        switch (tk.getType()) { // Analizza il tipo del token
            case INT, FLOAT, EOF: // Se il tipo e uno di questi, procede con il parsing
                ArrayList<NodeExpr> decSt1 = parseDSs(); // Effettua il parsing delle dichiarazioni e delle istruzioni
                match(TokenType.EOF); // Controlla che non ci siano altri token
                return new NodeProgram(decSt1); // Restituisce il programma parsato
            default: // Se il token non e valido, lancia un'eccezione
                throw new SintatticaExeption("ErroreSintattico:Il token "+ tk.getType() +" alla riga "+ tk.getRiga() + " non è corretto\n");
        }
    }

    private ArrayList<NodeExpr> parseDSs() throws SintatticaExeption {
        Token tk = null;
        try {
            tk = this.scanner.peekToken(); // Prende il token successivo
        } catch(IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e); // Gestisce eventuali errori lessicali
        }
        switch (tk.getType()) { // Analizza il tipo del token
            case INT, FLOAT: // Se il tipo e uno di questi, procede con il parsing delle dichiarazioni
                NodeExpr nodeExpr = parseExp(); // Effettua il parsing di una dichiarazione
                ArrayList<NodeExpr> a = parseDSs(); // Effettua il parsing di una dichiarazione
                a.add(0, nodeExpr); // Aggiunge la dichiarazione alla lista
                return a; // Restituisce la lista di dichiarazioni e istruzioni

            case EOF: // Se non ci sono altri token, restituisce una lista vuota
                ArrayList<NodeExpr> decSt = new ArrayList<>();
                return decSt;
            default: // Se il token non e valido, lancia un'eccezione
                throw new SintatticaExeption("ErroreSintattico:Il token "+ tk.getType() +" alla riga "+ tk.getRiga() + " non è corretto\n");
        }
    }

    private NodeExpr parseExp() throws SintatticaExeption {
        // Legge il prossimo token dallo scanner
        Token tk = null;
        try {
            tk = this.scanner.peekToken();
        } catch(IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e);
        }
        switch (tk.getType()) {
            case INT, FLOAT:
                NodeExpr exp1 = parseTr();
                return parseExpP(exp1);
            default:
                // Il token letto non e corretto
                throw new SintatticaExeption("ErroreSintattico:Il token "+ tk.getType() +" alla riga "+ tk.getRiga() + " non è corretto\n");
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws SintatticaExeption {
        Token tk = null;
        try {
            tk = this.scanner.peekToken(); // Legge il prossimo token senza avanzare
        } catch (IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e);
        }
        switch (tk.getType()) {
            case PLUS:
                match(TokenType.PLUS); // Legge il token di somma
                NodeExpr exp1 = parseTr(); // Effettua il parsing del termine successivo
                NodeBinOp node = new NodeBinOp(LangOper.PLUS, left, exp1); // Crea un nodo binario di somma
                return parseExpP(node); // Chiama ricorsivamente il metodo per leggere ulteriori termini
            case MINUS:
                match(TokenType.MINUS); // Legge il token di sottrazione
                NodeExpr exp3 = parseTr(); // Effettua il parsing del termine successivo
                NodeBinOp node1 = new NodeBinOp(LangOper.MINUS, left, exp3); // Crea un nodo binario di sottrazione
                return parseExpP(node1); // Chiama ricorsivamente il metodo per leggere ulteriori termini
            case SEMI:
                // QUESTO MATCH HA SISTEMATO OGNI COSA
                match(TokenType.SEMI);
                return left;
            default:
                throw new SintatticaExeption("ErroreSintattico: Il token " + tk.getType() + " alla riga " + tk.getRiga() + " non è corretto\n");
        }
    }

    private NodeExpr parseTr() throws SintatticaExeption {
        Token tk = null;
        try {
            tk = this.scanner.peekToken();
        } catch(IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e);
        }
        switch (tk.getType()) {
            case INT, FLOAT:
                // Il termine e un valore numerico o una variabile
                NodeExpr node = parseVal();
                return parseTrP(node);
            default:
                // Il token letto non e corretto
                throw new SintatticaExeption("ErroreSintattico:Il token "+ tk.getType() +" alla riga "+ tk.getRiga() + " non è corretto\n");
        }
    }

    private NodeExpr parseTrP(NodeExpr left) throws SintatticaExeption {
        // Legge il prossimo token dallo scanner
        Token tk = null;
        try {
            tk = this.scanner.peekToken();
        } catch(IOException | LessicaleException e) {
            throw new SintatticaExeption("Errore lessicale\n", e);
        }
        switch (tk.getType()) {
            case TIMES:
                match(TokenType.TIMES);
                NodeExpr val1 = parseVal();
                NodeBinOp node = new NodeBinOp(LangOper.TIMES, left, val1);
                return parseTrP(node);
            case DIV:
                match(TokenType.DIV);
                NodeExpr val2 = parseVal();
                NodeBinOp node1 = new NodeBinOp(LangOper.DIV, left, val2);
                return parseTrP(node1);
            case PLUS, MINUS,SEMI:
                return left;
            default:
                throw new SintatticaExeption("ErroreSintattico:Il token "+ tk.getType() +" alla riga "+ tk.getRiga() + " non è corretto\n");
        }
    }

    private NodeExpr parseVal() throws SintatticaExeption {
        Token tk = null;
        try {
            tk = this.scanner.peekToken(); // Legge il prossimo token senza avanzare
        } catch (IOException | LessicaleException e) {
            // Lancia una SyntacticException con un messaggio di errore adeguato se si verifica un'eccezione di tipo IOException o LexicalException
            throw new SintatticaExeption("Errore lessicale\n", e);
        }
        switch (tk.getType()) {
            case INT:
                // Se il token successivo e INT, chiama il metodo match con il tipo TokenType.INT e restituisce un nuovo oggetto NodeCost con il valore intero e il tipo INTy
                Token t = match(TokenType.INT); // Legge il token INT
                return new NodeCost(t.getVal(), LangType.INTy); // Restituisce un nuovo oggetto NodeCost con il valore e il tipo appropriati
            case FLOAT:
                // Se il token successivo e FLOAT, chiama il metodo match con il tipo TokenType.FLOAT e restituisce un nuovo oggetto NodeCost con il valore floating-point e il tipo FLOATy
                Token t1 = match(TokenType.FLOAT); // Legge il token FLOAT
                return new NodeCost(t1.getVal(), LangType.FLOATy); // Restituisce un nuovo oggetto NodeCost con il valore e il tipo appropriati
            default:
                // Se non corrisponde a nessuno dei casi precedenti, lancia una SyntacticException con un messaggio di errore adeguato
                throw new SintatticaExeption("ErroreSintattico: Il token " + tk.getType() + " alla riga " + tk.getRiga() + " non è corretto\n");
        }
    }

    private Token match(TokenType type) throws SintatticaExeption {
        // Ottiene il prossimo token dallo stream di input.
        Token tk = null;
        try {
            tk = this.scanner.peekToken();
        }
        catch(IOException | LessicaleException e) {
            // Lancia una SyntacticException con un messaggio di errore adeguato se si verifica un'eccezione di tipo IOException o LexicalException.
            throw new SintatticaExeption("Errore Lessicale\n",e);
        }

        // Verifica se il tipo del prossimo token nello stream di input corrisponde al tipo di token specificato.
        if(type.equals(tk.getType())) {
            // Se corrisponde, consuma il token successivo e restituiscilo.
            try {
                return this.scanner.nextToken();
            } catch (LessicaleException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            // Se non corrisponde, lancia una SyntacticException con un messaggio di errore adeguato.
            throw new SintatticaExeption("Errore il token trovato è di tipo "+tk.getType()+" ma il tipo che ci si aspettava era "+type+"\n");
        }
    }
}

