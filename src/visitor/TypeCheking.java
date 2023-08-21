package visitor;
import ast.*;
public class TypeCheking implements IVisitor{

    @Override
    public void visit(NodeProgram node) {
        // Imposta il tipo di risultato del nodo NodeProgram a TypeDescriptor.Void
        node.setResType(TypeDescriptor.Void);

        // Itera attraverso tutti i nodi NodeDecSt presenti in NodeProgram e li analizza
        for (NodeExpr nodeAnalyze : node.getDecSts()) {
            nodeAnalyze.accept(this);

            // Aggiorna il tipo di risultato del nodo NodeProgram in caso di errore
            if (nodeAnalyze.getResType() == TypeDescriptor.Error) {
                node.setResType(TypeDescriptor.Error);
            }
        }
    }

    @Override
    public void visit(NodeBinOp node) {
        // Ottieni le due espressioni dal nodo NodeBinOp
        NodeExpr expr1 = node.getLeft();
        NodeExpr expr2 = node.getRight();

        // Visita le due espressioni
        expr1.accept(this);
        expr2.accept(this);

        // Controlla se una delle due espressioni ha un errore nella risoluzione del tipo
        if (expr1.getResType() == TypeDescriptor.Error || expr2.getResType() == TypeDescriptor.Error) {
            node.setResType(TypeDescriptor.Error);
        }
        // Controlla se le due espressioni hanno lo stesso tipo
        else if (expr1.getResType().equals(expr2.getResType())) {
            node.setResType(expr1.getResType());
        }
        // Controlla se una delle due espressioni è di tipo int, in tal caso è necessario convertire l'altra in float
        else if (expr1.getResType() == TypeDescriptor.Int && expr2.getResType() == TypeDescriptor.Float) {
            // È necessario convertire expr1 in float e verificare se la conversione è corretta
            NodeExpr exprConv1 = this.convert(expr1);
            node.setLeft(exprConv1);
            node.setResType(TypeDescriptor.Float);
        } else if (expr1.getResType() == TypeDescriptor.Float && expr2.getResType() == TypeDescriptor.Int) {
            // È necessario convertire expr2 in float e verificare se la conversione è corretta
            node.setRight(this.convert(expr2));
            node.setResType(TypeDescriptor.Float);
        }
    }

    private NodeExpr convert(NodeExpr node) {
        NodeExpr expr = new NodeConvert(node);
        expr.setResType(TypeDescriptor.Float);
        return expr;
    }

    @Override
    public void visit(NodeConvert node) {
        // Ottieni l'espressione dal nodo NodeConvert
        NodeExpr expr = node.getExpr();

        // Visita l'espressione
        expr.accept(this);

        // Controlla se l'espressione ha un errore nella risoluzione del tipo
        if (expr.getResType() == TypeDescriptor.Error) {
            node.setResType(TypeDescriptor.Error);
        } else {
            // Il risultato del NodeConvert sarà di tipo float
            node.setResType(TypeDescriptor.Float);
        }
    }

    @Override
    public void visit(NodeCost node) {
        // Controlla il tipo del nodo NodeCost e imposta il tipo di risultato in base al tipo del nodo
        if (node.getType() == LangType.INTy) {
            node.setResType(TypeDescriptor.Int);
        } else if (node.getType() == LangType.FLOATy) {
            node.setResType(TypeDescriptor.Float);
        } else {
            node.setResType(TypeDescriptor.Error);
        }
    }

}
