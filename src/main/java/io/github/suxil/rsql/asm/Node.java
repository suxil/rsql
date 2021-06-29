package io.github.suxil.rsql.asm;

/**
 * 条件解析节点
 *
 * @author lu_it
 * @since V1.0
 */
public interface Node {

	default <R> R accept(NodeVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
