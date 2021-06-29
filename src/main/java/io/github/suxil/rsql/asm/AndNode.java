package io.github.suxil.rsql.asm;

import java.util.List;

/**
 * and 节点
 *
 * @author lu_it
 * @since V1.0
 */
public class AndNode extends ConditionNode {

    public AndNode(List<Node> children) {
        super(ConditionSymbol.AND, children);
    }

    @Override
    public Node withChildren(List<Node> children) {
        return new AndNode(children);
    }

}
