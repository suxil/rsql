package io.github.suxi.rsql.asm;

import org.junit.Assert;
import org.junit.Test;
import io.github.suxi.rsql.RSQLOperator;

import java.util.Arrays;

public class ConditionNodeTest {

	@Test
	public void conditionNodeTest() {
		WhereNode whereNode = new WhereNode(RSQLOperator.EQ_OP, "name", Arrays.asList("zhangsan"));
		AndNode node = new AndNode(Arrays.asList(whereNode));

		AndNode node2 = new AndNode(Arrays.asList(whereNode));

		WhereNode whereNode3 = new WhereNode(RSQLOperator.GE_OP, "age", Arrays.asList("20"));
		AndNode node3 = new AndNode(Arrays.asList(whereNode3));

		Assert.assertEquals((ConditionNode) node, (ConditionNode) node);
		Assert.assertEquals((ConditionNode) node, (ConditionNode) node2);
		Assert.assertNotEquals((ConditionNode) node, (ConditionNode) node3);
		Assert.assertNotEquals((ConditionNode) node, whereNode);
	}

	@Test
	public void hashCodeTest() {
		WhereNode whereNode = new WhereNode(RSQLOperator.EQ_OP, "name", Arrays.asList("zhangsan"));
		AndNode node = new AndNode(Arrays.asList(whereNode));

		int code = node.hashCode();

		Assert.assertNotNull(code);
	}

}
