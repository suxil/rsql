/**
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
import org.suxi.rsql.asm.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * <p> Title: 标题 </p>
 * <pre> Description: 描述 </pre>
 * date: 2019/11/20 22:26
 * <p>
 *
 * @author lu_it
 * @since V1.0
 */
public abstract class BaseNodeVisitor<R, P> implements NodeVisitor<R, P> {

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
                result.add(visit((OrNode) itemNode, param));
            } else if (itemNode instanceof AndNode) {
                result.add(visit((AndNode) itemNode, param));
            } else if (itemNode instanceof WhereNode) {
                result.add(visit((WhereNode) itemNode, param));
            }
            if (consumer != null) {
                consumer.accept(param);
            }
        }

        return result;
    }

}
