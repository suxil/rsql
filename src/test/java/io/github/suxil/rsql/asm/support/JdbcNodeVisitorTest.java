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

import io.github.suxil.rsql.RSQLUtils;
import io.github.suxil.rsql.asm.Node;
import io.github.suxil.rsql.asm.NodeVisitor;
import org.junit.Assert;
import org.junit.Test;

/**
 * jdbc 访问节点测试用例
 *
 * @author lu_it
 * @version V1.0
 */
public class JdbcNodeVisitorTest {

	@Test
    public void visitJdbcTest() {
        String search = "a=in=(1,2);(b.type=out=(1,2,3),c=='test*');d=nu=;e=nnu=";

        Node node = RSQLUtils.parse(search);

        NodeVisitor<String> visitor = new DefaultJdbcNodeVisitor();

        String result = node.accept(visitor);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, visitor.visit(node));
    }

    @Test
    public void visitJdbcDateTest() {
        String search = "startDate==2020-10-11";

        Node node = RSQLUtils.parse(search);

        String result = node.accept(new DefaultJdbcNodeVisitor(), whereNode -> {
			if ("startDate".equals(whereNode.getFieldName())) {
				return String.format("start_date = to_date('%s', 'YYYY-MM-DD')", whereNode.getOneValue());
			}
			return null;
		});

        Assert.assertEquals("start_date = to_date('2020-10-11', 'YYYY-MM-DD')", result);
    }

}
