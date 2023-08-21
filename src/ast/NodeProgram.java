package ast;

import visitor.IVisitor;

import java.util.ArrayList;
/** 
 * Questa classe crea e gestisce il nodo che rappresenterà l'intero AST del codice.
 * Tale classe estende la classe NodeAST.
 * */
public class NodeProgram extends NodeAST {
	
	private ArrayList<NodeExpr> decSts;
	
	/** 
	 * Costruttore del NodeProgram a cui viene assegnato l'ArrayList di NodeDecSt
	 * @param decSts ArrayList che viene assegnato al NodeProgram
	 * */
	public NodeProgram(ArrayList<NodeExpr> decSts) {
		super();
		this.decSts = decSts;
	}
	/** Getter che ritorna l'ArrayList di NodeDecSt associato al NodeProgram
	 * @return ritorna l'ArrayList di NodeDecSt associato al NodeProgram
	 * */
	public ArrayList<NodeExpr> getDecSts() {
		return this.decSts;
	}
	
	/** 
	 * Metodo che ritorna la stringa che rappresenta il NodeProgram
	 * @return stringa che rappresenta il NodeProgram
	 * */
	@Override
	public String toString() {
		return "NodeProgram [decSts=" + decSts + "]";
	}
	/** 
	 * Metodo che ritorna la stringa che rappresenta il NodeProgram formattato.
	 * In questo toStringFormatted ogni nodo dell'AST viene indicato in una riga diversa.
	 * @return stringa che rappresenta il NodeProgram
	 * */
	public String toStringFormatted() {
		String stampa = "NodeProgram [\n\tdecSts=[\n";
		for(NodeExpr node :decSts) {
			stampa += "\t\t"+ node.toString() + ",\n";
		}
		stampa += "\t]\n"+"]";
		return stampa;
	}
	/** 
	 * Metodo che va a richiamare il metodo visit del NodeProgram
	 * @param visitor interfaccia IVisitor
	 * */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
