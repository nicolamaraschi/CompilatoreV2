package token;

public class Token {
    private int riga; // Variabile che memorizza la riga in cui si trova il token
    private TokenType tipo; // Tipo del token (enumerazione TokenType)
    private String val; // Valore associato al token (opzionale)

    public Token(TokenType tipo, int riga) {
        this.riga = riga;
        this.tipo = tipo;
    }

    public Token(TokenType tipo, int riga, String val) {
        this.riga = riga;
        this.tipo = tipo;
        this.val = val;
    }
    public int getRiga() {
        return riga;
    }

    public TokenType getType() {
        return tipo;
    }

    public String getVal() {
        return val;
    }

    @Override
    public String toString() {
        if (this.tipo == TokenType.INT || this.tipo == TokenType.FLOAT )
            return "<" + this.tipo + ",r:" + this.riga + "," + this.val + ">";
        else
            return "<" + this.tipo + ",r:" + this.riga + ">";
    }
}
