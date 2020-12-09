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
package io.github.suxi.rsql;

import io.github.suxi.rsql.asm.Node;
import io.github.suxi.rsql.asm.NodeFactory;
import io.github.suxi.rsql.asm.WhereOperator;
import io.github.suxi.rsql.asm.support.DefaultNodeFactory;
import io.github.suxi.rsql.exception.RSQLCommonException;
import io.github.suxi.rsql.parser.RSQLParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * rsql 工具类
 * 1. 提供各种方式获取rsql解析对象
 * 2. 自定义操作符的解析对象获取
 * 3. 默认rsql解析
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

}
