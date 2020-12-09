/*
 * Copyright 2020 suxi
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
package io.github.suxi.rsql.asm;

import io.github.suxi.rsql.util.Assert;
import io.github.suxi.rsql.util.StringUtils;

import java.util.List;

/**
 * 查询条件节点
 * e.g. <t>userType=in=('admin', 'user')</t>
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
