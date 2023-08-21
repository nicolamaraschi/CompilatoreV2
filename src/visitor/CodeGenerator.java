package visitor;
import ast.*;

import java.util.HashMap;
import java.util.List;

public class CodeGenerator implements IVisitor{

    private HashMap<LangOper, Character> operatori;

    /**
     * Costruttore del CodeGeneratorVisitor in cui vengono inizializzati i registri disponibili
     * per le variabili dichiarate nel codice sorgente e inizializza un HashMap con gli operatori accettabili dal compilatore.
     */
    public CodeGenerator() {
        super();
        this.operatori = new HashMap<>();
        this.operatori.put(ast.LangOper.PLUS, '+');
        this.operatori.put(ast.LangOper.MINUS, '-');
        this.operatori.put(ast.LangOper.DIV, '/');
        this.operatori.put(ast.LangOper.TIMES, '*');
    }

    @Override
    public void visit(NodeBinOp node) {
        // Inizializza la stringa del codice
        String codice = "";
        // Ottiene l'operatore corrispondente
        Character operatore = this.operatori.get(node.getOp());
        node.getLeft().accept(this); // Visita e setta il codice del NodeExpr a sinistra
        node.getRight().accept(this); // Visita e setta il codice del NodeExpr a destra
        // Combina il codice dei due nodi
        codice += node.getLeft().getCodice() + " " + node.getRight().getCodice();
        // Se uno dei due nodi è float allora setta la precisione a 5 k
        if (node.getLeft().getResType() == TypeDescriptor.Float || node.getRight().getResType() == TypeDescriptor.Float)
            codice += " 5 k";
        // Combina il codice e setta il codice del NodeBinOp
        node.setCodice(codice + " " + operatore + " 0 k");
    }

    @Override
    public void visit(NodeConvert node) {
        node.getExpr().accept(this); // Visita e setta il codice del NodeExpr
        // Setta il codice del NodeConvert
        node.setCodice(node.getExpr().getCodice());
    }

    @Override
    public void visit(NodeCost node) {
        // Setta il codice di NodeCost con il valore associato
        node.setCodice(node.getValue());
    }

    @Override
    public void visit(NodeProgram node) {
        node.setCodice(node.getResType() == TypeDescriptor.Void ? generateCode(node.getDecSts()) : "");
    }

    private String generateCode(List<NodeExpr> decSts) {
        String code = "";
        for (NodeExpr nodeSt : decSts) {
            nodeSt.accept(this);
            if (nodeSt.getCodice() != null) code += nodeSt.getCodice() + " ";
        }
        return code.substring(0, code.length() - 1);
    }

}
