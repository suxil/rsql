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