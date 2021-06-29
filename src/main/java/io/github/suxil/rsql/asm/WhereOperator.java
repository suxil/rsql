package io.github.suxil.rsql.asm;

import io.github.suxil.rsql.util.Assert;
import io.github.suxil.rsql.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * condition operate symbol and value
 * pattern <tt>=[a-zA-Z]*=|[&gt;&lt;]=?|!=</tt>
 * custom condition operate symbol
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
