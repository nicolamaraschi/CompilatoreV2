package visitor;
import ast.*;
public interface IVisitor {

        /**
         * Metodo visit per il NodeBinOp
         * @param node NodeBinOp
         * */
        public abstract void visit(NodeBinOp node);
        /**
         * Metodo visit per il NodeConvert
         * @param node NodeConvert
         * */
        public abstract void visit(NodeConvert node);
        /**
         * Metodo visit per il NodeCost
         * @param node NodeCost
         * */
        public abstract void visit(NodeCost node);

        /**
         * Metodo visit per il NodeProgram
         * @param node NodeProgram
         * */
        public abstract void visit(NodeProgram node);

}
