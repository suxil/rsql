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
package org.suxi.rsql;

import org.junit.Assert;
import org.junit.Test;
import org.suxi.rsql.asm.Node;
import org.suxi.rsql.asm.NodeVisitor;
import org.suxi.rsql.asm.support.DefaultJdbcNodeVisitor;

/**
 * <p> Title: 标题 </p>
 * <pre> Description: 描述 </pre>
 * date: 2019/11/20 21:21
 * <p>
 *
 * @author lu_it
 * @version V1.0
 */
public class RSQLNodeVisitorTest {

    @Test
    public void visitJdbcTest() {
        String search = "a=in=(1,2);((b=out=(1,2,3),c=='test*',c=='*test',c=='*test*');((d==1;e==1,f==1);h==2;i==3));j==1;j=nu=1;j=nnu=1";

        Node node = RSQLUtils.parse(search);

        NodeVisitor<String> visitor = new DefaultJdbcNodeVisitor();

        String result = visitor.visit(node);

        Assert.assertNotNull(result);
    }

    @Test
    public void visitJdbcDateTest() {
        String search = "startDate==2020-10-11";

        Node node = RSQLUtils.parse(search);

        NodeVisitor<String> visitor = new DefaultJdbcNodeVisitor();

        String result = visitor.visit(node, whereNode -> {
            if ("startDate".equals(whereNode.getFieldName())) {
                return String.format("start_date = to_date('%s', 'YYYY-MM-DD')", whereNode.getOneValue());
            }
            return null;
        });

        Assert.assertEquals("start_date = to_date('2020-10-11', 'YYYY-MM-DD')", result);
    }

}
