# AOP核心概念
| 概念          | 含义                                              |
| ------------- | ------------------------------------------------- |
| Aspect        | 切面                                              |
| Join Point    | 连接点，Spring AOP里总是代表一次方法执行          |
| Advice        | 通知，在连接点执行的动作                          |
| Pointcut      | 切入点，说明如何匹配连接点                        |
| Introduction  | 引入，为现有类声明额外的方法和属性                |
| Target object | 目标对象                                          |
| AOP proxy     | AOP代理对象，可以是JDK动态代理，也可以是CGLIB代理 |
| Weaving       | 织入，连接切面与目标对象或类型创建代理的过程      |

# AOP 常用注解
1. @EnableAspectJAutoProxy
    > 开启AspectJ的支持，会对打了Aspect注解的类去进行一个Proxy(代理)
2. @Aspect
    > 声明当前类是一个切面，光声明Aspect还不能变为一个bean，要么使用javaConfigurer自己把类声明成一个bean，要么就声明@Component注解
3. @Pointcut
4. @Before
    > 方法执行前去执行
5. @After / @AfterReturning / @AfterThrowing
    > 只要返回就在之后执行、return执行、throw异常之后执行
6. @Around
    > 前后都去执行
7. @Order
    > 指定切面执行顺序，数字越小，优先级越高