package io.github.suxil.rsql.asm;

import io.github.suxil.rsql.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 基础条件节点
 *
 * @author lu_it
 * @since V1.0
 */
public abstract class ConditionNode implements Node {

    private final ConditionSymbol conditionSymbol;
    private final List<Node> children;

    public ConditionNode(ConditionSymbol conditionSymbol, List<Node> children) {
        this.conditionSymbol = conditionSymbol;
        this.children = children;
    }

    public ConditionSymbol getConditionSymbol() {
        return conditionSymbol;
    }

    public List<Node> getChildren() {
        return children;
    }

    public abstract Node withChildren(List<Node> children);

    @Override
    public String toString() {
    	if (children != null && children.isEmpty()) {
			return "";
		}
    	String str = StringUtils.join(children, conditionSymbol.toString());
    	if (children.size() == 1) {
    		return str;
		}
        return "(" + str + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionNode)) {
            return false;
        }
        ConditionNode that = (ConditionNode) o;
        return Objects.equals(conditionSymbol, that.conditionSymbol) &&
                Objects.equals(children, that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conditionSymbol, children);
    }

}
