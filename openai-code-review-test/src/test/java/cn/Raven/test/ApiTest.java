package cn.Raven.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author susu
 * @Date 2025/2/9
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {
    @Test
    public void test(){
        System.out.println("Raven");

    }
}
