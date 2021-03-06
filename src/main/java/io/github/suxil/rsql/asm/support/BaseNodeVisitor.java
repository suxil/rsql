/*
 * Copyright 2020 suxil
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
package io.github.suxil.rsql.asm.support;

import io.github.suxil.rsql.asm.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 基础节点访问
 *
 * @author lu_it
 * @since V1.0
 */
public abstract class BaseNodeVisitor<R, P> implements NodeVisitor<R> {

    protected List<R> handlerChildren(List<Node> nodeList, P param) {
        return handlerChildren(nodeList, param, null);
    }

    protected List<R> handlerChildren(List<Node> nodeList, P param, Consumer<P> consumer) {
        List<R> result = new ArrayList<>();
        if (nodeList == null || nodeList.size() == 0) {
            return result;
        }

        for (Node itemNode : nodeList) {
            if (itemNode instanceof OrNode) {
                result.add(visitNode((OrNode) itemNode, param));
            } else if (itemNode instanceof AndNode) {
                result.add(visitNode((AndNode) itemNode, param));
            } else if (itemNode instanceof WhereNode) {
                result.add(visitNode((WhereNode) itemNode, param));
            }
            if (consumer != null) {
                consumer.accept(param);
            }
        }

        return result;
    }

    protected abstract R visitNode(OrNode node, P param);

    protected abstract R visitNode(AndNode node, P param);

    protected abstract R visitNode(WhereNode node, P param);

}
