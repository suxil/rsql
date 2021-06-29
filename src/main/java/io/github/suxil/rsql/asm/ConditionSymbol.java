package io.github.suxil.rsql.asm;

/**
 * condition operate symbol
 *
 * @author lu_it
 * @since V1.0
 */
public enum  ConditionSymbol {

    OR(","),
    AND(";");

    private final String condition;

    ConditionSymbol(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return condition;
    }

}
