package kr.hhplus.be.server;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestcontainersConfiguration {

	public static final MySQLContainer<?> MYSQL_CONTAINER;
	public static final GenericContainer<?> REDIS_CONTAINER;
	public static final ConfluentKafkaContainer KAFKA_CONTAINER;

	static {
		// 1) MySQL Container
		MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
				.withDatabaseName("mydatabase")
				.withUsername("myuser")
				.withPassword("secret");
		MYSQL_CONTAINER.start();

		// Apply MySQL connection properties
		System.setProperty("spring.datasource.url",
				MYSQL_CONTAINER.getJdbcUrl() + "?characterEncoding=UTF-8&serverTimezone=UTC");
		System.setProperty("spring.datasource.username", MYSQL_CONTAINER.getUsername());
		System.setProperty("spring.datasource.password", MYSQL_CONTAINER.getPassword());

		// 2) Redis Container
		REDIS_CONTAINER = new GenericContainer<>(DockerImageName.parse("bitnami/redis:7.4"))
				.withExposedPorts(6379)  // default redis port
				.withEnv("ALLOW_EMPTY_PASSWORD", "yes")
				.withEnv("REDIS_DISABLE_COMMANDS", "FLUSHDB,FLUSHALL");
		REDIS_CONTAINER.start();

		// Apply Redis connection properties
		String redisHost = REDIS_CONTAINER.getHost();
		Integer redisPort = REDIS_CONTAINER.getFirstMappedPort();
		System.setProperty("spring.redis.host", redisHost);  // 수정된 키
		System.setProperty("spring.redis.port", redisPort.toString());  // 수정된 키

		// 3) Kafka Container
		KAFKA_CONTAINER = new ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
		KAFKA_CONTAINER.start();
	}

	@PreDestroy
	public void preDestroy() {
		if (MYSQL_CONTAINER.isRunning()) {
			MYSQL_CONTAINER.stop();
		}
		if (REDIS_CONTAINER.isRunning()) {
			REDIS_CONTAINER.stop();
		}
		if (KAFKA_CONTAINER.isRunning()) {
			KAFKA_CONTAINER.stop();
		}
	}
}
