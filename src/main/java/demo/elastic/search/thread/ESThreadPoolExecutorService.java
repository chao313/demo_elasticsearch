package demo.elastic.search.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 本地线程池
 */
@Slf4j
@Component
public class ESThreadPoolExecutorService {

    private InheritableThreadLocal<BlockingQueue<Runnable>> blockingQueueThreadLocal = new InheritableThreadLocal<>();
    private InheritableThreadLocal<ThreadPoolExecutor> threadPoolExecutorThreadLocal = new InheritableThreadLocal<>();
    private ThreadLocal<Boolean> endFlag = new ThreadLocal<>();


    public synchronized void addWork(Runnable runnable) {
        BlockingQueue<Runnable> workQueue = blockingQueueThreadLocal.get();
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorThreadLocal.get();
        if (null == workQueue) {
            /**
             * 如果队列为null -> 新建一个
             */
            workQueue = new LinkedBlockingQueue<Runnable>();
            blockingQueueThreadLocal.set(workQueue);
        }

        if (threadPoolExecutor == null) {
            int corePoolSize = 20;
            int maximumPoolSize = 50;
            long keepAliveTime = 20;
            TimeUnit unit = TimeUnit.MINUTES;
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            threadPoolExecutorThreadLocal.set(threadPoolExecutor);
        }
        threadPoolExecutor.submit(runnable);
    }

    /**
     * 自旋 直至完成
     *
     * @return
     * @throws Exception
     */
    public synchronized boolean waitComplete() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorThreadLocal.get();
        if (threadPoolExecutor == null) {
            throw new Exception("线程池为空，请初始化,可能未执行addWork方法");
        }
        for (; ; ) {
            log.info("当前剩余作业:{}", blockingQueueThreadLocal.get().size());
            log.info("当前活跃作业:{}", threadPoolExecutor.getActiveCount());
            Thread.sleep(1000);
            if (threadPoolExecutor.getActiveCount() == 0) {
                return true;
            }
        }
    }

    /**
     * 自旋 直至完成
     *
     * @return
     * @throws Exception
     */
    public synchronized boolean isComplete() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorThreadLocal.get();
        if (threadPoolExecutor == null) {
            throw new Exception("线程池为空，请初始化,可能未执行addWork方法");
        }
        return threadPoolExecutor.getActiveCount() == 0;
    }

    /**
     * 自旋 直至完成
     *
     * @return
     * @throws Exception
     */
    public void isCompleteLog() throws Exception {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorThreadLocal.get();
        if (threadPoolExecutor == null) {
            return;
        }
        log.info("当前剩余作业:{}", blockingQueueThreadLocal.get().size());
        log.info("当前活跃作业:{}", threadPoolExecutor.getActiveCount());
    }

}
