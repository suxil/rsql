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
        return "(" + StringUtils.join(children, conditionSymbol.toString()) + ")";
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
