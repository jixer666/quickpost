package com.abc.app.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class PackCodeUtil {

    private static PriorityQueue<String> PRIORITY_QUEUE = new PriorityQueue<>((t1, t2) -> ThreadLocalRandom.current().nextInt(-1, 2));
    private static int CODE_RANGE_MAX_SIZE = 999999;


    @PostConstruct
    public void init() {
        log.info("开始加载取件码数据");
        for (int i = 0; i <= CODE_RANGE_MAX_SIZE; i++) {
            setCode(String.format("%06d", i));
        }
        log.info("完成加载取件码数据");
    }

    public static String getCode() {
        return PRIORITY_QUEUE.poll();
    }

    public static void setCode(String code) {
        PRIORITY_QUEUE.add(code);
    }


}
