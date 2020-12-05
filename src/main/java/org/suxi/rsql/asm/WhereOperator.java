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
package org.suxi.rsql.asm;

import org.suxi.rsql.util.Assert;
import org.suxi.rsql.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 条件操作符和值
 * 操作符正则表达式 <t>=[a-zA-Z]*=|[><]=?|!=</t>
 * 可以自定义操作符
 *
 * @author lu_it
 * @since V1.0
 */
public class WhereOperator {

    private static final Pattern SYMBOL_PATTERN = Pattern.compile("=[a-zA-Z]*=|[><]=?|!=");

    // 多个相同符号代表同一操作
    private final String[] symbol;
    // 是否多个值（in，not in等）
    private final boolean multiValue;

    public WhereOperator(String[] symbol, boolean multiValue) {
        Assert.notEmpty(symbol, "symbol must not be null or empty");
        for (String s : symbol) {
            Assert.isTrue(isValidOperatorSymbol(s), String.format("symbol must match: %s", SYMBOL_PATTERN));
        }

        this.symbol = symbol;
        this.multiValue = multiValue;
    }

    public WhereOperator(String symbol, boolean multiValue) {
        this(new String[]{symbol}, multiValue);
    }

    public WhereOperator(String... symbol) {
        this(symbol, false);
    }

    public boolean isMultiValue() {
        return multiValue;
    }

    public String[] getSymbol() {
        return symbol;
    }

    private boolean isValidOperatorSymbol(String str) {
        return !StringUtils.isBlank(str) && SYMBOL_PATTERN.matcher(str).matches();
    }

    public String getSymbolStr() {
        return symbol[0];
    }

    @Override
    public String toString() {
        return getSymbolStr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WhereOperator)) {
            return false;
        }
        WhereOperator that = (WhereOperator) o;
        return getSymbolStr().equals(that.getSymbolStr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbolStr());
    }

}
