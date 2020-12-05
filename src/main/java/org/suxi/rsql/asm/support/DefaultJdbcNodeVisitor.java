/*
 * Copyright 2020 suxi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.suxi.rsql.asm.support;

import org.suxi.rsql.RSQLOperator;
import org.suxi.rsql.asm.*;
import org.suxi.rsql.util.StringUtils;
import org.suxi.rsql.asm.*;

import java.util.List;

/**
 *
 *
 * @author lu_it
 * @since V1.0
 */
public class DefaultJdbcNodeVisitor extends BaseNodeVisitor<String, Void> {

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
            result = visit((AndNode) node, null);
        } else if (node instanceof OrNode) {
            result = visit((OrNode) node, null);
        } else if (node instanceof WhereNode) {
            result = visit((WhereNode) node, null);
        }
        return result;
    }

    @Override
    public String visit(OrNode node, Void param) {
        return visitOrAndNode(node.getChildren(), node.getConditionSymbol(), param);
    }

    @Override
    public String visit(AndNode node, Void param) {
        return visitOrAndNode(node.getChildren(), node.getConditionSymbol(), param);
    }

    @Override
    public String visit(WhereNode node, Void param) {
        String fieldName = node.getFieldName();

        String value = String.format("'%s'", StringUtils.join(node.getValue(), "','"));
        if (node.getOperator().isMultiValue()) {
            value = String.format("(%s)", value);
        }

        switch (node.getOperator().getSymbolStr()) {
            case RSQLOperator.EQ:
                String v = node.getValue().get(0);
                if (v.startsWith("*") && v.endsWith("*")) {
                    return fieldName + " like " + "'%" + v.substring(1, v.length() - 1) + "%'";
                } else if (v.startsWith("*")) {
                    return fieldName + " like " + "'%" + v.substring(1) + "'";
                } else if (v.endsWith("*")) {
                    return fieldName + " like " + "'" + v.substring(0, v.length() - 1) + "%'";
                } else {
                    return fieldName + " = " + v;
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
