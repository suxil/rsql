package io.github.suxil.rsql.asm;

import java.util.List;

/**
 * or 节点
 *
 * @author lu_it
 * @since V1.0
 */
public class OrNode extends ConditionNode {

    public OrNode(List<Node> children) {
        super(ConditionSymbol.OR, children);
    }

    @Override
    public Node withChildren(List<Node> children) {
        return new OrNode(children);
    }

}
