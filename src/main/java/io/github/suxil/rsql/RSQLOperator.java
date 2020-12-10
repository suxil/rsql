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
package io.github.suxil.rsql;

import io.github.suxil.rsql.asm.WhereOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * rsql 操作符
 *
 * @author lu_it
 * @since V1.0
 */
public class RSQLOperator {

    public static final String EQ = "=="; // 相等
    public static final String NEQ = "!="; // 不相等
    public static final String GT = "=gt="; // 大于
    public static final String GT2 = ">"; // 大于
    public static final String GE = "=ge="; // 大于等于
    public static final String GE2 = ">="; // 大于等于
    public static final String LT = "=lt="; // 小于
    public static final String LT2 = "<"; // 小于
    public static final String LE = "=le="; // 小于等于
    public static final String LE2 = "<="; // 小于等于
    public static final String NULL = "=nu="; // 为 null
    public static final String NOT_NULL = "=nnu="; // 不为 null
    public static final String IN = "=in="; // in查询
    public static final String OUT = "=out=";  // not in 查询

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
