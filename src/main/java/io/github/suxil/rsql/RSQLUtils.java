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
package io.github.suxil.rsql;

import io.github.suxil.rsql.asm.*;
import io.github.suxil.rsql.asm.support.DefaultNodeFactory;
import io.github.suxil.rsql.exception.RSQLCommonException;
import io.github.suxil.rsql.parser.RSQLParser;
import io.github.suxil.rsql.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * rsql utils
 * 1. various ways to obtain rsql parsing objects
 * 2. getting parsing objects of custom operators
 * 3. default rsql parser
 *
 * @author lu_it
 * @since V1.0
 */
public final class RSQLUtils {

    private static final Charset ENCODING = StandardCharsets.UTF_8;

    public static RSQLParser getRsqlParser(String search) {
        return getRsqlParser(search, ENCODING, RSQLOperator.defaultOperator());
    }

    public static RSQLParser getRsqlParser(String search, Charset encoding) {
        return getRsqlParser(search, encoding, RSQLOperator.defaultOperator());
    }

    public static RSQLParser getRsqlParser(String search, Set<WhereOperator> whereOperatorSet) {
        return getRsqlParser(search, ENCODING, new DefaultNodeFactory(whereOperatorSet));
    }

    public static RSQLParser getRsqlParser(String search, Charset encoding, Set<WhereOperator> whereOperatorSet) {
        return getRsqlParser(search, encoding, new DefaultNodeFactory(whereOperatorSet));
    }

    public static RSQLParser getRsqlParser(String search, NodeFactory nodeFactory) {
        return getRsqlParser(search, ENCODING, nodeFactory);
    }

    public static RSQLParser getRsqlParser(String search, Charset encoding, NodeFactory nodeFactory) {
        InputStream inputStream = new ByteArrayInputStream(search.getBytes(encoding));
        return new RSQLParser(inputStream, encoding.name(), nodeFactory);
    }

    private static Node parse(RSQLParser parser) {
        try {
            return parser.parse();
        } catch (Exception e) {
            throw new RSQLCommonException("parse resolve error");
        }
    }

    public static Node parse(String search) {
        return parse(getRsqlParser(search));
    }

	public static Node parse(String search, Set<WhereOperator> whereOperatorSet) {
		return parse(getRsqlParser(search, whereOperatorSet));
	}

    public static Node parse(String search, NodeFactory nodeFactory) {
        return parse(getRsqlParser(search, nodeFactory));
    }

    public static Node parse(String search, Charset encoding, NodeFactory nodeFactory) {
        return parse(getRsqlParser(search, encoding, nodeFactory));
    }

	/**
	 * 遍历节点，返回字段对应的条件，原条件不变
	 * @param node
	 * @param fieldName
	 * @return
	 */
	public static List<WhereNode> getWhereNodeByFieldName(Node node, String fieldName) {
		return getWhereNodeByFieldName(node, fieldName, true);
	}

	/**
	 * 遍历节点，返回字段对应的条件，可以选择是否删除条件
	 * @param node
	 * @param fieldName
	 * @param remove
	 * @return
	 */
	public static List<WhereNode> getWhereNodeByFieldName(Node node, String fieldName, boolean remove) {
		if (node == null || StringUtils.isBlank(fieldName)) {
			return Collections.emptyList();
		}

		Stack<Node> nodeStack = new Stack<>();
		nodeStack.add(node);
		List<WhereNode> result = new ArrayList<>();
		while (!nodeStack.isEmpty()) {
			Node itemNode = nodeStack.pop();
			if (itemNode instanceof ConditionNode) {
				ConditionNode conditionNode = (ConditionNode) itemNode;

				Iterator<Node> iterator = conditionNode.getChildren().iterator();
				while (iterator.hasNext()) {
					Node childNode = iterator.next();
					if (childNode instanceof WhereNode) {
						WhereNode whereNode = (WhereNode) childNode;
						if (whereNode.getFieldName().equals(fieldName)) {
							result.add(whereNode);
							// 是否删除
							if (remove) {
								iterator.remove();
							}
						}
					} else {
						nodeStack.add(childNode);
					}
				}
			}
		}

    	return result;
	}

}
