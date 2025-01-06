package kr.hhplus.be.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private final static int POOL_SIZE = 5;

    /**
     * 스케줄이 실행될때는 단일 스레드로 실행되기에 동시 작업을 위한 셋팅
     * @param taskRegistrar
     */

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
    }
}
