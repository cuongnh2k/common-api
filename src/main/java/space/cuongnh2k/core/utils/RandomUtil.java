package space.cuongnh2k.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomUtil {
    public String randomPassword() {
        String random = RandomStringUtils.random(2, "1234567890")
                + RandomStringUtils.random(2, "qwertyuiopasdfghjklzxcvbnm")
                + RandomStringUtils.random(2, "QWERTYUIOPASDFGHJKLZXCVBNM")
                + RandomStringUtils.random(2, "`-=[]\\;',./~!@#$%^&*()_+{}|:\"<>?");
        List<String> list = Arrays.asList(random.split(""));
        Collections.shuffle(list);
        return String.join("", list);
    }
}
