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
import org.suxi.rsql.asm.Node;
import org.junit.Test;

/**
 * rsql utils test
 *
 * @author lu_it
 * @version V1.0
 */
public class RSQLUtilsTest {

    @Test
    public void parseTest() {
        String search = "a=in=(1,2);((b=out=(1,2,3),c=='test*');((d==1;e==1;f==1);h==2;i==3));j==1";

        Node node = RSQLUtils.parse(search);

        Assert.assertNotNull(node);
    }

}
