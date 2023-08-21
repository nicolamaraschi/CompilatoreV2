package ast;

import visitor.IVisitor;
/**
 * Questa classe crea e gestisce i nodi per la conversione del TypeDescriptor da int a float.
 * Tale classe estende la classe NodeExpr.
 * */
public class NodeConvert extends NodeExpr {
	
	private NodeExpr expr; 
	/** 
	 * Costruttore del NodeConvert a cui viene assegnato il NodeExpr
	 * @param expr il NodeExpr che viene assegnato al NodeConvert
	 * */
	public NodeConvert(NodeExpr expr) {
		this.expr = expr;
	}
	/** 
	 * Getter che ritorna il NodeExpr associato al NodeConvert
	 * @return ritorna il NodeExpr associato al NodeConvert
	 * */
	public NodeExpr getExpr() {
		return expr;
	}
	/** 
	 * Metodo che ritorna la stringa che rappresenta il NodeConvert
	 * @return stringa che rappresenta il NodeConvert
	 * */
	@Override
	public String toString() {
		return "NodeConvert [expr=" + expr + "]";
	}
	/** 
	 * Metodo che va a richiamare il metodo visit del NodeConvert
	 * @param visitor interfaccia IVisitor
	 * */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
