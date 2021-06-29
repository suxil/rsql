package io.github.suxil.rsql.asm;

import java.util.List;

/**
 * 语法解析工厂
 *
 * @author lu_it
 * @since V1.0
 */
public interface NodeFactory {

    Node createConditionNode(ConditionSymbol conditionSymbol, List<Node> nodeList);

    WhereNode createWhereNode(String fieldName, String operate, List<String> value);

}
