server:
    port: 8888
# mysql的配置
spring:
    datasource:
        url: jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: 123456

mybatis-plus:
    type-aliases-package: com.simple.${_package}.domain
    mapper-locations: classpath*:mapper/*.xml
    # mybatis-plus 的全局配置
    global-config:
        banner: false
    # mybatis 原生配置
    configuration:
        log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
        map-underscore-to-camel-case: true
        lazy-loading-enabled: false
