# spring-boot-webflux
spring-boot-webflux

#spec
spring boot 2.1.6
data-redis-reactive

#desc
MongoDB, Redis의 경우 reactive 지원(Event loop가 이벤트 감지하여 결과 리턴)
기존의 Controller 및 requestMapping 어노테이션을 사용하는 방법이 아닌 RouterFunction을 사용하여 핸들러매핑 방식도 지원
jdbc와 같이 서버 I/O가 reactive 지원하지 않는 경우라면 이러한 blocking call을 별도의 스레드에서 backgroud로 돌리는 방식이 
