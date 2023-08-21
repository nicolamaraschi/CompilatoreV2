package token;

public enum TokenType {
    /** enumerativo che indica il tipo floating-point
     * */
    TYFLOAT,
    /** enumerativo che indica il tipo intero
     * */
    TYINT,

    /** enumerativo che indica un numero di tipo intero
     * */
    INT,
    /** enumerativo che indica un numero di tipo floating-point
     * */
    FLOAT,
    /** enumerativo che indica l'operatore di assegnamento
     * */
    ASSIGN,
    /** enumerativo che indica l'operatore di somma
     * */
    PLUS,
    /** enumerativo che indica l'operatore della differenza
     * */
    MINUS,
    /** enumerativo che indica l'operatore del prodotto
     * */
    TIMES,
    /** enumerativo che indica l'operatore della divisione
     * */
    DIV,
    /** enumerativo che indica il punto e virgola ovvero il delimitatore
     * */
    SEMI,
    /** enumerativo che indica la fine di un file
     * */
    EOF;

}
