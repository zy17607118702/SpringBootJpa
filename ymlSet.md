server:
  port: 9001
spring:
  application:
    name: imes-mdm-apiservice
  profiles:
    active: localhost
  aop:
     proxy-target-class: false
  cache:
    jcache:
      provider: org.ehcache.jsr107.EhcacheCachingProvider
      config: classpath:ehcache.xml
    redis:
      provider: org.springframework.data.redis.cache.RedisCacheManager
      time-to-live: 60000
    type: none
  messages:
    basename: com/saicmotor/framework/core/messages,com/saicmotor/framework/core/exceptions,com/saicmotor/framework/messages,com/saicmotor/framework/exceptions
    use-code-as-default-message: true
  datasource:
    database: ORACLE
    druid:
      db-type: ORACLE
      max-active: 50
    driver-class-name: net.sf.log4jdbc.DriverSpy
  hibernate5:
    packages: com.saicmotor.imes.model
    properties:
      hibernate:
        hbm2ddl:
           auto: none
        cache:
          use_second_level_cache: true
          region:
            factory_class: org.hibernate.cache.jcache.JCacheRegionFactory
        javax:
          cache:
            provider: org.ehcache.jsr107.EhcacheCachingProvider
        dialect: org.hibernate.dialect.Oracle10gDialect
  jackson:
    serialization.INDENT_OUTPUT: true
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: Asia/Shanghai
    joda-date-time-format: yyyy-MM-dd HH:mm:ss
  session:
    store-type: none
  data:
    redis:
      repositories:
        enabled: false
  rabbitmq:
    enabled: false
    host: localhost
    port: 5672
    username: root
    password: dan7844
    virtual-host: /
    exchange: exchange
security:
  jwt:
    login: /api/login
    logout: /api/logout
    refresh: refresh
    prefix: Bearer
    header: Authorization
    expiration: 86400
    secret: changeitasyouwish

logging:
  file: /app/logs/${spring.application.name}.log
  level:
    root: INFO
    jdbc:
      audit: FATAL
      resultset: FATAL
      sqlonly: FATAL