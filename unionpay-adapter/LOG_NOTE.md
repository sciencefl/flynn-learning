`<logger>` 和 `<root>` 标签在 Logback 中的作用如下：

1. `<logger>` 标签：
- 用于配置指定包/类的日志行为
- 你的配置示例：
```xml
<logger name="com.sciencefl.flynn.controller.BatchController" level="INFO" additivity="true">
    <appender-ref ref="UNION_PAY_REQUEST_FILE"/>
</logger>
```
这表示：
- 只对 `BatchController` 类的日志进行特殊处理
- 日志级别设为 `INFO`
- `additivity="true"` 表示同时继承父级的配置
- 日志会输出到 `UNION_PAY_REQUEST_FILE` 指定的文件中

2. `<root>` 标签：
- 是全局的默认配置，相当于根 logger
- 你的配置示例：
```xml
<root level="INFO">
    <appender-ref ref="CONSOLE"/>
    <appender-ref ref="FILE"/>
</root>
```
这表示：
- 所有未被 `<logger>` 特别指定的类都使用这个配置
- 日志级别为 `INFO`
- 日志同时输出到控制台和文件

总结：
- `<logger>` 用于细粒度控制特定包/类的日志行为
- `<root>` 用于配置全局默认的日志行为
- 如果某个类的日志同时匹配了 `<logger>` 和 `<root>`，且 `additivity="true"`，那么两种配置都会生效