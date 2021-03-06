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

import io.github.suxil.rsql.asm.Node;
import io.github.suxil.rsql.asm.WhereNode;
import io.github.suxil.rsql.asm.WhereOperator;
import io.github.suxil.rsql.asm.support.DefaultNodeFactory;
import io.github.suxil.rsql.parser.RSQLParser;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

/**
 * rsql 工具类测试用例
 *
 * @author lu_it
 * @version V1.0
 */
public class RSQLUtilsTest {

	@Test
	public void getRsqlParserTest() {
		RSQLParser rsqlParser = RSQLUtils.getRsqlParser("a=in=(1,2)", StandardCharsets.UTF_8);
		Assert.assertNotNull(rsqlParser);
	}

	/**
	 * 默认解析规则
	 */
    @Test
    public void parseTest() {
        String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

        Node node = RSQLUtils.parse(search);

        Assert.assertNotNull(node);
    }

	/**
	 * 自定义规则工厂
	 */
	@Test
	public void parseCustomFactoryTest() {
		String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

		Node node = RSQLUtils.parse(search, new DefaultNodeFactory(RSQLOperator.defaultOperator()));

		Assert.assertNotNull(node);
	}

	/**
	 * 自定义规则工厂
	 */
	@Test
	public void parseCustomEncodeAndFactoryTest() {
		String search = "(a=in=('1','2'),b=='12')";

		Node node = RSQLUtils.parse(search, StandardCharsets.UTF_8, new DefaultNodeFactory(RSQLOperator.defaultOperator()));

		Assert.assertNotNull(node);
		Assert.assertEquals(search, node.toString());
	}

	/**
	 * 异常解析规则
	 */
	@Test
	public void parseErrorTest() {
		String search = "a=in=(1,2))";

		try {
			RSQLUtils.parse(search);
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage(), true);
		}
	}

	/**
	 * 自定义操作符
	 */
	@Test
    public void customOperatorTest() {
    	String operator = "=da=";

		Set<WhereOperator> whereOperators = RSQLOperator.defaultOperator();
		whereOperators.add(new WhereOperator(operator));

		String search = "startDate=da=2020-10-11";

		Node node = RSQLUtils.parse(search, whereOperators);

		Assert.assertEquals(operator, ((WhereNode) node).getOperator().getSymbol()[0]);
	}

	/**
	 * 删除字段名称对应的条件
	 */
	@Test
	public void getWhereNodeByFieldNameRemoveTest() {
		String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

		Node node = RSQLUtils.parse(search, new DefaultNodeFactory(RSQLOperator.defaultOperator()));

		List<WhereNode> whereNodeList = RSQLUtils.getWhereNodeByFieldName(node, "a");

		Assert.assertEquals(1, whereNodeList.size());
		Assert.assertEquals("a=in=('1','2')", whereNodeList.get(0).toString());
		Assert.assertFalse(node.toString().contains("a=in="));
	}

	/**
	 * 通过字段名获取条件节点
	 */
	@Test
	public void getWhereNodeByFieldNameTest() {
		String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

		Node node = RSQLUtils.parse(search, new DefaultNodeFactory(RSQLOperator.defaultOperator()));

		List<WhereNode> whereNodeList = RSQLUtils.getWhereNodeByFieldName(node, "c", false);

		Assert.assertEquals(1, whereNodeList.size());
		Assert.assertEquals("c=='test*'", whereNodeList.get(0).toString());
		Assert.assertTrue(node.toString().contains("c=="));
	}

	@Test
	public void getFieldValueTest() {
		String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

		String value= RSQLUtils.getFieldValue(search, "d");

		Assert.assertEquals("1", value);
	}

	@Test
	public void removeFieldTest() {
		String search = "a=in=(1,2);((b.type=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

		String searchStr = RSQLUtils.removeField(search, "c");

		Assert.assertFalse(searchStr.contains("c"));
	}

	@Test
	public void removeFieldOneTest() {
		String search = "a=in=(1,2)";

		String searchStr = RSQLUtils.removeField(search, "a");

		Assert.assertFalse(searchStr.contains("a"));
	}

}
