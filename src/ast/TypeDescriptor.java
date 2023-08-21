package ast;
/**
 * Classe di enumerativi che rappresentano i possibili TypeDescriptor che mi indicano
 * dopo la fase di analisi semantica se un nodo all'interno dell'AST � semanticamente corretto 
 * (attraverso i tipi enumerativi Int, Float e Void) oppure � semanticamente erratto (attraverso il tipo
 * enumerativo Error).
 * */
public enum TypeDescriptor {
	/** enumerativo che indica un espressione intera
	 * */
	Int,
	/** enumerativo che indica un espressione floating-point
	 * */
	Float,
	/** enumerativo che indica che un espressione � corretta
	 * */
	Void,
	/** enumerativo che indica che un espressione non � corretta
	 * */
	Error;
}
