package io.github.suxil.rsql;

import io.github.suxil.rsql.asm.WhereOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * rsql operate symbol
 *
 * @author lu_it
 * @since V1.0
 */
public class RSQLOperator {

    public static final String EQ = "=="; // equal
    public static final String NEQ = "!="; // not equal
    public static final String GT = "=gt="; // >
    public static final String GT2 = ">"; // >
    public static final String GE = "=ge="; // >=
    public static final String GE2 = ">="; // >=
    public static final String LT = "=lt="; // <
    public static final String LT2 = "<"; // <
    public static final String LE = "=le="; // <=
    public static final String LE2 = "<="; // <=
    public static final String NULL = "=nu="; // is null
    public static final String NOT_NULL = "=nnu="; // is not null
    public static final String IN = "=in="; // in
    public static final String OUT = "=out=";  // not in

    public static final WhereOperator EQ_OP = new WhereOperator(EQ);
    public static final WhereOperator NEQ_OP = new WhereOperator(NEQ);
    public static final WhereOperator GT_OP = new WhereOperator(GT, GT2);
    public static final WhereOperator GE_OP = new WhereOperator(GE, GE2);
    public static final WhereOperator LT_OP = new WhereOperator(LT, LT2);
    public static final WhereOperator LE_OP = new WhereOperator(LE, LE2);
    public static final WhereOperator NULL_OP = new WhereOperator(NULL);
    public static final WhereOperator NOT_NULL_OP = new WhereOperator(NOT_NULL);
    public static final WhereOperator IN_OP = new WhereOperator(IN, true);
    public static final WhereOperator OUT_OP = new WhereOperator(OUT, true);

    public static Set<WhereOperator> defaultOperator() {
        List<WhereOperator> operatorList = Arrays.asList(EQ_OP, NEQ_OP, GT_OP, GE_OP, LT_OP, LE_OP, NULL_OP, NOT_NULL_OP, IN_OP, OUT_OP);
        return new HashSet<>(operatorList);
    }

}
