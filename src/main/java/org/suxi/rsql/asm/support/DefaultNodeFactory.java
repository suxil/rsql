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

import org.suxi.rsql.asm.*;
import org.suxi.rsql.exception.RSQLCommonException;

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
