package org.suxi.rsql.asm;

import org.junit.Assert;
import org.junit.Test;
import org.suxi.rsql.RSQLOperator;

import java.util.Arrays;

public class WhereOperatorTest {

	@Test
	public void equalsTest() {
		WhereOperator whereOperator = RSQLOperator.EQ_OP;
		WhereOperator whereOperator1 = new WhereOperator("==");
		WhereNode whereNode = new WhereNode(RSQLOperator.EQ_OP, "name", Arrays.asList("zhangsan"));

		Assert.assertEquals(whereOperator, whereOperator);
		Assert.assertEquals(whereOperator, whereOperator1);
		Assert.assertNotEquals(whereOperator, whereNode);
	}

}
