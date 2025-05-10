package com.naiyin.healthy.service.comon;

import com.naiyin.healthy.enums.SysErrorEnum;
import com.naiyin.healthy.exception.CommonException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class TaskService {

    @Resource
    private TaskScheduler taskScheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new HashMap<>();

    /**
     * 添加定时任务
     * @param taskId 任务ID
     * @param executionTime 执行时间
     * @param task 任务逻辑
     */
    @Async
    public void addTask(Long taskId, LocalDateTime executionTime, Runnable task) {
        // 取消已存在的相同ID任务
        cancelTask(taskId);

        // 计算延迟时间（毫秒）
        long delay = executionTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();

        if (delay > 0) {
            ScheduledFuture<?> future = taskScheduler.schedule(task, new Date(System.currentTimeMillis() + delay));
            tasks.put(String.valueOf(taskId), future);
        } else {
            throw new CommonException(SysErrorEnum.OPERATION_ERROR, "发布时间必须是将来的时间");
        }
    }

    /**
     * 取消定时任务
     * @param taskId 任务ID
     */
    public void cancelTask(Long taskId) {
        ScheduledFuture<?> future = tasks.get(taskId);
        if (future != null) {
            future.cancel(true);
            tasks.remove(taskId);
        }
    }
}