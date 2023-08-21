package ast;

import visitor.IVisitor;
/** 
 * Questa classe crea e gestisce i nodi che rappresentano un numero int o float.
 * */
public class NodeCost extends NodeExpr{
	private String value;
	private LangType type;
	/** 
	 * Costruttore del NodeCost a cui viene assegnato la stringa che rappresenta il numero 
	 * @param value la stringa che viene assegnata al NodeCost
	 * @param type il LangType che viene assegnato al NodeCost
	 * */
	public NodeCost(String value, LangType type) {
		super();
		this.value = value;
		this.type = type;
	}
	/** 
	 * @return ritorna la stringa associata al NodeCost
	 * */
	public String getValue() {
		return value;
	}
	/** 
	 * @return ritorna il LangType associato al NodeCost
	 * */
	public LangType getType() {
		return type;
	}
	/**
	 * @return stringa che rappresenta il NodeCost
	 * */
	@Override
	public String toString() {
		return "NodeCost [value=" + value + ", type=" + type + "]";
	}
	/**
	 * @param visitor interfaccia IVisitor
	 * */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
