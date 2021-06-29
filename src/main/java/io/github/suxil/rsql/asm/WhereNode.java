package io.github.suxil.rsql.asm;

import io.github.suxil.rsql.util.Assert;
import io.github.suxil.rsql.util.StringUtils;

import java.util.List;

/**
 * search condition node
 * e.g. <tt>userType=in=('admin', 'user')</tt>
 *
 * @author lu_it
 * @since V1.0
 */
public class WhereNode implements Node {

    private final WhereOperator operator;
    private final String fieldName;
    private final List<String> value;

    public WhereNode(WhereOperator operator, String fieldName, List<String> value) {
        Assert.notNull(operator, "operator must not be null");
        Assert.hasText(fieldName, "fieldName must not be text");
        Assert.notEmpty(value, "value list must not be empty");

        this.operator = operator;
        this.fieldName = fieldName;
        this.value = value;
    }

    public WhereOperator getOperator() {
        return operator;
    }

    public String getFieldName() {
        return fieldName;
    }

    public List<String> getValue() {
        return value;
    }

    public String getOneValue() {
        if (value.isEmpty()) {
            return "";
        }
        return value.get(0);
    }

    @Override
    public String toString() {
        String valueStr = value.size() > 1 ? "('" + StringUtils.join(value, "','") + "')": "'" + value.get(0) + "'";
        return fieldName + operator + valueStr;
    }

}
