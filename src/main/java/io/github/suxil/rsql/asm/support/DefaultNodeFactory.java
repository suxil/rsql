package io.github.suxil.rsql.asm.support;

import io.github.suxil.rsql.asm.*;
import io.github.suxil.rsql.exception.RSQLCommonException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认语法解析工厂实现
 *
 * @author lu_it
 * @since V1.0
 */
public class DefaultNodeFactory implements NodeFactory {

    private final Map<String, WhereOperator> whereOperatorMap;

    public DefaultNodeFactory(Set<WhereOperator> whereOperatorSet) {
        this.whereOperatorMap = new HashMap<>(whereOperatorSet.size());

        for (WhereOperator operator : whereOperatorSet) {
            for (String s : operator.getSymbol()) {
                whereOperatorMap.put(s, operator);
            }
        }
    }

    @Override
    public Node createConditionNode(ConditionSymbol conditionSymbol, List<Node> nodeList) {
        switch (conditionSymbol) {
            case OR: return new OrNode(nodeList);
            case AND: return new AndNode(nodeList);
            default: throw new RSQLCommonException("condition symbol not found");
        }
    }

    @Override
    public WhereNode createWhereNode(String fieldName, String operate, List<String> value) {
        WhereOperator whereOperator = whereOperatorMap.get(operate);
        if (whereOperator == null) {
            throw new RSQLCommonException("where operate must not be null");
        }
        return new WhereNode(whereOperator, fieldName, value);
    }

}
