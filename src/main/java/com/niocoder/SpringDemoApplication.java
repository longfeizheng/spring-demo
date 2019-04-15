package com.niocoder;

import com.niocoder.config.PersonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@RestController
public class SpringDemoApplication implements ApplicationRunner {

    private static Logger logger = LoggerFactory.getLogger(SpringDemoApplication.class);

    @Autowired
    private PersonConfig personConfig;

    private static ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDemoApplication.class, args);
    }


    /**
     * http://localhost:8080/requestParam?website=http://niocoder.com
     *
     * @param url
     * @return
     */
    @RequestMapping("/requestParam")
    public String requestParam(@RequestParam(value = "website") String url) {
        return "url=" + url;
    }

    /**
     * @param url
     * @return
     */
    @RequestMapping("/pathVariable/{website}")
    public String pathVariable(@PathVariable("website") String url) {
        return "url=" + url;
    }

    /**
     * @param website
     * @return
     */
    @RequestMapping("/requestBody")
    public String requestBody(@RequestBody String website) {
        return "website=" + website;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(personConfig.toString());
    }

    @RequestMapping("/exector")
    public String exector() {

        logger.info("threadName:{},threadId:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        executorService.execute(() -> {
            System.out.println("hello world!");
            logger.info("threadName:{},threadId:{}", Thread.currentThread().getName(), Thread.currentThread().getId());
        });
        return "SUCCESS";
    }
}
