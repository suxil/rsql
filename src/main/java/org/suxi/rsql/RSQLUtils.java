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
package org.suxi.rsql;

import org.suxi.rsql.asm.*;
import org.suxi.rsql.asm.support.DefaultJdbcNodeVisitor;
import org.suxi.rsql.parser.RSQLParser;

/**
 * rsql parser utils
 *
 * @author lu_it
 * @since V1.0
 */
public final class RSQLUtils {

    public static final RSQLParser RSQL_PARSER = new RSQLParser();

    public static Node parse(String search) {
        return RSQL_PARSER.parse(search);
    }

    public static String parseJdbc(String search) {
        return parseJdbc(new DefaultJdbcNodeVisitor(), search);
    }

    public static String parseJdbc(NodeVisitor<String, Void> visitor, String search) {
        Node node = parse(search);

        String result = "";

        if (node instanceof AndNode) {
            result = visitor.visit((AndNode) node, null);
        } else if (node instanceof OrNode) {
            result = visitor.visit((OrNode) node, null);
        } else if (node instanceof WhereNode) {
            result = visitor.visit((WhereNode) node, null);
        }

        return result;
    }

}
