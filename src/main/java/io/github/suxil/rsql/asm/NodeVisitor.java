package io.github.suxil.rsql.asm;

/**
 * node visit, handle search condition
 *
 * @author lu_it
 * @since V1.0
 */
public interface NodeVisitor<R> {

    R visit(Node node);

}
