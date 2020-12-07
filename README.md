# RSQL ![license](https://img.shields.io/github/license/suxi-lu/rsql) ![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/suxi-lu/rsql) ![GitHub Workflow Status](https://img.shields.io/github/workflow/status/suxi-lu/rsql/Java%20CI%20with%20Gradle) ![Gitlab code coverage](https://img.shields.io/gitlab/coverage/suxi-lu/rsql/master)

RSQL是一种查询语言，用于对restfulapi中的条目进行参数化过滤。
它基于FIQL（feeditemquerylanguage）——一种URI友好的语法，用于在Atom提要中的条目之间表示过滤器。
FIQL非常适合在URI中使用；没有不安全的字符，所以不需要URL编码。
另一方面，FIQL的语法不是很直观，URL编码也不总是那么重要，因此RSQL还为逻辑运算符和一些比较运算符提供了更友好的语法。

## 语法

| 操作符 | 说明 |
| --- | --- |
| `==` | 相等 |
| `!=` | 不相等 |
| `=gt=` `>` | 大于 |
| `=ge=` `>=` | 大于等于 |
| `=lt=` `<` | 小于 |
| `=le=` `<=` | 小于等于 |
| `=nu=` | 为 null |
| `=nnu=` | 不为 null |
| `=in=` | in 查询 |
| `=out=` | not in 查询 |

## 示例

示例 rsql 语法:
```
name=='Hello World';age>10
name=='Hello*';age>=20
name=='*Hello';age<=30
name=in=('zhangsan','lisi');(age>18,age<=35)
```

## 使用

node 可以根据[NodeVisitor](src/main/java/org/suxi/rsql/asm/NodeVisitor.java)遍历语法树，将语法树转换成对应的查询条件。  
```
Node node = RSQLUtils.parse("type==rsql");
```

[DefaultJdbcNodeVisitor](src/main/java/org/suxi/rsql/asm/support/DefaultJdbcNodeVisitor.java)一个基于jdbc的实现供参考和使用。  
```
String search = "a=in=(1,2);(b=out=(1,2,3),c=='test*');d=nu=;e=nnu=";
String where = RSQLUtils.parse(search).accept(new DefaultJdbcNodeVisitor());

output: 
(a in ('1','2') AND (b not in ('1','2','3') OR c like 'test%') AND d is null  AND e is not null )
```

## 自定义操作符

操作符可根据需要自行定义，需满足正则 `=[a-zA-Z]*=|[><]=?|!=` 即可。
```
Set<WhereOperator> whereOperators = RSQLOperator.defaultOperator();
whereOperators.add(new WhereOperator("=da="));

Node node = RSQLUtils.parse("startDate=da=2020-10-11", whereOperators);
```

## License

The project is released under version 2.0 of the [Apache License](https://www.apache.org/licenses/LICENSE-2.0).
