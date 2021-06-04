package com.cdzq.api.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import io.lettuce.core.ReadFrom;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.host}")
	private String hostname;
	@Value("${spring.redis.database}")
	private int database;
	@Value("${spring.redis.sentinel.master:#{null}}")
	private String master;
	@Value("${spring.redis.sentinel.nodes:#{null}}")
	private String nodes;
	@Value("${spring.redis.replica-read}")
	private boolean replicaRead;
	@Value("${spring.redis.lettuce.pool.max-idle}")
	private int maxIdle;
	@Value("${spring.redis.lettuce.pool.min-idle}")
	private int minIdle;
	@Value("${spring.redis.lettuce.pool.max-active}")
	private int maxActive;
	@Value("${spring.redis.lettuce.pool.max-wait}")
	private long maxWait;
	@Value("${spring.redis.lettuce.pool.shutdown-timeout}")
	private long shutDownTimeout;
	@Value("${spring.redis.lettuce.pool.time-between-eviction-runs-millis}")
	private long timeBetweenEvictionRunsMillis;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle(maxIdle);
		genericObjectPoolConfig.setMinIdle(minIdle);
		genericObjectPoolConfig.setMaxTotal(maxActive);
		genericObjectPoolConfig.setMaxWaitMillis(maxWait);
		genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		ReadFrom readFrom = replicaRead?ReadFrom.REPLICA_PREFERRED:ReadFrom.MASTER_PREFERRED;

		LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.readFrom(readFrom)
				.poolConfig(genericObjectPoolConfig)
				.shutdownTimeout(Duration.ofMillis(shutDownTimeout))
				.build();

		RedisConfiguration redisConfiguration = null;
		if(master!=null){
			redisConfiguration = new RedisSentinelConfiguration().master(master);

			Set<RedisNode> redisNodeSet = new HashSet<>();
			List<String> _nodes= Arrays.asList(nodes.split(","));
			_nodes.forEach(x->{
				redisNodeSet.add(new RedisNode(x.split(":")[0],Integer.parseInt(x.split(":")[1])));
			});
			((RedisSentinelConfiguration) redisConfiguration).setSentinels(redisNodeSet);
		}else{
			redisConfiguration = new RedisStandaloneConfiguration();
			((RedisStandaloneConfiguration) redisConfiguration).setHostName(hostname);
			((RedisStandaloneConfiguration) redisConfiguration).setPort(port);
			((RedisStandaloneConfiguration) redisConfiguration).setDatabase(database);
		}

		return new LettuceConnectionFactory(redisConfiguration,clientConfig);
	}
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		// redis缓存配置
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		jacksonSeial.setObjectMapper(om);
		
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				//.entryTtl(Duration.ofSeconds(10))//有效时间10秒
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSeial))
				.disableCachingNullValues();
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
		        .cacheDefaults(config)
		        .build();
		return redisCacheManager;
	}


	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		// 配置连接工厂
		template.setConnectionFactory(factory);

		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer<Object> jacksonSeial = new Jackson2JsonRedisSerializer<Object>(Object.class);

		ObjectMapper om = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
		jacksonSeial.setObjectMapper(om);

		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		// 使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jacksonSeial);
		template.setDefaultSerializer(jacksonSeial);
		template.afterPropertiesSet();

		//支持事务
		template.setEnableTransactionSupport(true);

		return template;
	}

}
