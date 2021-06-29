package io.github.suxil.rsql.asm.support;

import io.github.suxil.rsql.RSQLOperator;
import io.github.suxil.rsql.asm.*;
import io.github.suxil.rsql.util.StringUtils;

import java.util.List;
import java.util.function.Function;

/**
 * default use jdbc grammar analysis
 *
 * @author lu_it
 * @since V1.0
 */
public class DefaultJdbcNodeVisitor extends BaseNodeVisitor<String, Void> {

	private Function<WhereNode, String> function;

	public DefaultJdbcNodeVisitor(Function<WhereNode, String> function) {
		this.function = function;
	}

    public String visitOrAndNode(List<Node> nodeList, ConditionSymbol conditionSymbol, Void param) {
        List<String> result = handlerChildren(nodeList, param);

        if (result != null && result.size() > 0) {
            String symbol = conditionSymbol.name();
            return String.format("(%s)", StringUtils.join(result, String.format(" %s ", symbol)));
        }
        return "";
    }

    @Override
    public String visit(Node node) {
        String result = "";
        if (node instanceof AndNode) {
            result = visitNode((AndNode) node, null);
        } else if (node instanceof OrNode) {
            result = visitNode((OrNode) node, null);
        } else if (node instanceof WhereNode) {
            result = visitNode((WhereNode) node, null);
        }
        return result;
    }

    @Override
    protected String visitNode(OrNode node, Void param) {
        return visitOrAndNode(node.getChildren(), node.getConditionSymbol(), param);
    }

    @Override
    protected String visitNode(AndNode node, Void param) {
        return visitOrAndNode(node.getChildren(), node.getConditionSymbol(), param);
    }

    @Override
    protected String visitNode(WhereNode node, Void param) {
        String fieldName = node.getFieldName();

        if (function != null) {
            String res = function.apply(node);
            if (StringUtils.hasText(res)) {
                return res;
            }
        }

        String value = String.format("'%s'", StringUtils.join(node.getValue(), "','"));
        if (node.getOperator().isMultiValue()) {
            value = String.format("(%s)", value);
        }

        switch (node.getOperator().getSymbolStr()) {
            case RSQLOperator.EQ:
                String v = node.getOneValue();
                if (v.startsWith("*") && v.endsWith("*")) {
                    return fieldName + " like " + "'%" + v.substring(1, v.length() - 1) + "%'";
                } else if (v.startsWith("*")) {
                    return fieldName + " like " + "'%" + v.substring(1) + "'";
                } else if (v.endsWith("*")) {
                    return fieldName + " like " + "'" + v.substring(0, v.length() - 1) + "%'";
                } else {
                    return fieldName + " = " + value;
                }
            case RSQLOperator.NEQ:
                return fieldName + " != " + value;
            case RSQLOperator.GT:
            case RSQLOperator.GT2:
                return fieldName + " > " + value;
            case RSQLOperator.GE:
            case RSQLOperator.GE2:
                return fieldName + " >= " + value;
            case RSQLOperator.LT:
            case RSQLOperator.LT2:
                return fieldName + " < " + value;
            case RSQLOperator.LE:
            case RSQLOperator.LE2:
                return fieldName + " <= " + value;
            case RSQLOperator.NULL:
                return fieldName + " is null ";
            case RSQLOperator.NOT_NULL:
                return fieldName + " is not null ";
            case RSQLOperator.IN:
                return fieldName + " in " + value;
            case RSQLOperator.OUT:
                return fieldName + " not in " + value;
        }

        return "";
    }

}
