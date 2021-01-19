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
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
	 * iterator node, return fieldName condition, origin condition no change
	 * @param node node
	 * @param fieldName fieldName
	 * @return list
	 */
	public static List<WhereNode> getWhereNodeByFieldName(Node node, String fieldName) {
		return getWhereNodeByFieldName(node, fieldName, true);
	}

	/**
	 * iterator node, return fieldName condition, origin condition sure remove
	 * @param node node
	 * @param fieldName fieldName
	 * @param remove remove
	 * @return list
	 */
	public static List<WhereNode> getWhereNodeByFieldName(Node node, String fieldName, boolean remove) {
		if (node == null || StringUtils.isBlank(fieldName)) {
			return Collections.emptyList();
		}

		List<WhereNode> result = new ArrayList<>();

		whereNodeIterator(node, (nodeIterator, whereNode) -> {
			if (whereNode.getFieldName().equals(fieldName)) {
				result.add(whereNode);
				// 是否删除
				if (remove) {
					nodeIterator.remove();
				}
			}
		});

    	return result;
	}

	public static void whereNodeIterator(String search, Consumer<WhereNode> whereNodeConsumer) {
		Node node = parse(search);

		whereNodeIterator(node, whereNodeConsumer);
	}

	public static void whereNodeIterator(Node node, Consumer<WhereNode> whereNodeConsumer) {
		if (node == null || whereNodeConsumer == null) {
			return;
		}

		whereNodeIterator(node, (nodeIterator, whereNode) -> {
			whereNodeConsumer.accept(whereNode);
		});
	}

	/**
	 * iterator node
	 * @param node node
	 * @param whereNodeBiConsumer whereNodeConsumer
	 * @return void
	 */
	private static void whereNodeIterator(Node node, BiConsumer<Iterator<Node>, WhereNode> whereNodeBiConsumer) {
		if (node == null || whereNodeBiConsumer == null) {
			return;
		}

		Stack<Node> nodeStack = new Stack<>();
		nodeStack.add(node);

		while (!nodeStack.isEmpty()) {
			Node itemNode = nodeStack.pop();
			if (itemNode instanceof ConditionNode) {
				ConditionNode conditionNode = (ConditionNode) itemNode;

				Iterator<Node> iterator = conditionNode.getChildren().iterator();
				while (iterator.hasNext()) {
					Node childNode = iterator.next();
					if (childNode instanceof WhereNode) {
						WhereNode whereNode = (WhereNode) childNode;
						whereNodeBiConsumer.accept(iterator, whereNode);
					} else {
						nodeStack.add(childNode);
					}
				}
			} else if (itemNode instanceof WhereNode) {
				WhereNode whereNode = (WhereNode) itemNode;
				whereNodeBiConsumer.accept(null, whereNode);
			}
		}
	}

	public static List<WhereNode> getFieldAllValue(String search, String fieldName) {
		Node node = RSQLUtils.parse(search);
		return getWhereNodeByFieldName(node, fieldName);
	}

	public static String getFieldValue(String search, String fieldName) {
		List<WhereNode> list = getFieldAllValue(search, fieldName);
		if (list.isEmpty()) {
			return "";
		}
		return list.get(0).getOneValue();
	}

	public static String removeField(String search, String fieldName) {
		Node node = RSQLUtils.parse(search);
		getWhereNodeByFieldName(node, fieldName);
		return node.toString();
	}

}
