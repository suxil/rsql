package io.github.suxi.rsql.asm;

import io.github.suxi.rsql.RSQLOperator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class OrNodeTest {

	@Test
	public void withChildTest() {
		WhereNode whereNode = new WhereNode(RSQLOperator.EQ_OP, "name", Arrays.asList("zhangsan"));
		OrNode node = new OrNode(Arrays.asList(whereNode));

		WhereNode whereNode2 = new WhereNode(RSQLOperator.GE_OP, "age", Arrays.asList("20"));
		Node resNode = node.withChildren(Arrays.asList(whereNode2));

		Assert.assertEquals(resNode.toString(), String.format("(%s)", whereNode2.toString()));
		Assert.assertEquals(((OrNode) resNode).getChildren().get(0), whereNode2);
	}

}
