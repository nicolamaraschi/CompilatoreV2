package ast;
import visitor.IVisitor;

/** 
 * Questa classe astratta gestisce i nodi appartenenti ad un AST.
 * */
public abstract class NodeAST {
	
	private TypeDescriptor resType;
	private String codice;
	
	/** 
	 * Getter che ritorna la stringa di codice associato al nodo
	 * @return ritorna la stringa di codice associata al nodo
	 * */
	public String getCodice() {
		return codice;
	}
	/** 
	 * Setter che assegna la stringa al campo del codice al nodo
	 * @param  codice la stringa di codice da associare al nodo
	 * */
	public void setCodice(String codice) {
		this.codice = codice;
	}
	/** 
	 * Getter che ritorna il TypeDescriptor del nodo associato
	 * @return ritorna il TypeDescriptor associato al nodo
	 * */
	public TypeDescriptor getResType() {
		return resType;
	}
	/** 
	 * Setter che assegna il TypeDescriptor al campo resType del nodo
	 * @param  resType il TypeDescriptor da associare al nodo
	 * */
	public void setResType(TypeDescriptor resType) {
		this.resType = resType;
	}
	/**
	 * Metodo accept associato al metodo visit con firma NodeAST

	 * @param visitor l'interfaccia IVisitor 
	 * */
	public abstract void accept(IVisitor visitor);
	
}
