package gt.maxzhao.ittest.ittest.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

public class BCryptPasswordEncoderTest {
    @Test
    public void t() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1"));
        System.out.println(bCryptPasswordEncoder.encode("1").length());
        System.out.println(bCryptPasswordEncoder.encode("2"));
        System.out.println(bCryptPasswordEncoder.encode("2").length());
        BCryptPasswordEncoder bCryptPasswordEncoder2 = new BCryptPasswordEncoder(4);
        System.out.println(bCryptPasswordEncoder2.encode("1"));
        System.out.println(bCryptPasswordEncoder2.encode("1"));
        System.out.println(bCryptPasswordEncoder2.encode("2"));
        System.out.println(bCryptPasswordEncoder2.encode("1").length());
        System.out.println(bCryptPasswordEncoder2.encode("2").length());
        BCryptPasswordEncoder bCryptPasswordEncoder3 = new BCryptPasswordEncoder(4, new SecureRandom("user".getBytes()));
        System.out.println(bCryptPasswordEncoder3.encode("1"));
        System.out.println(bCryptPasswordEncoder3.encode("2"));
        System.out.println(bCryptPasswordEncoder3.encode("1").length());
        System.out.println(bCryptPasswordEncoder3.encode("2").length());
    }

    @Test
    public void match() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1"));
        System.out.println(bCryptPasswordEncoder.encode("2"));
        System.out.println(bCryptPasswordEncoder.matches("1", bCryptPasswordEncoder.encode("1")));
        System.out.println(bCryptPasswordEncoder.matches("2", bCryptPasswordEncoder.encode("2")));
    }

    @Test
    public void timeTest() {
        new Thread(() -> {
            BCryptPasswordEncoder b1 = new BCryptPasswordEncoder();
            long startTime = System.currentTimeMillis();
            b1.encode("maxzhao");
            System.out.println("-1=>" + (System.currentTimeMillis() - startTime));
        }).start();
        new Thread(() -> {
            BCryptPasswordEncoder b2 = new BCryptPasswordEncoder(4);
            long startTime = System.currentTimeMillis();
            String code = b2.encode("maxzhao");
            System.out.println("4=>" + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            b2.matches("maxzhao", code);
            System.out.println("4=>" + (System.currentTimeMillis() - startTime));
        }).start();
        new Thread(() -> {
            BCryptPasswordEncoder b8 = new BCryptPasswordEncoder(8);
            long startTime = System.currentTimeMillis();
            String code = b8.encode("maxzhao");
            System.out.println("8=>" + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            b8.matches("maxzhao", code);
            System.out.println("8=>" + (System.currentTimeMillis() - startTime));
        }).start();
        new Thread(() -> {
            BCryptPasswordEncoder b16 = new BCryptPasswordEncoder(16);
            long startTime = System.currentTimeMillis();
            String code = b16.encode("maxzhao");
            System.out.println("16=>" + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            b16.matches("maxzhao", code);
            System.out.println("16=>" + (System.currentTimeMillis() - startTime));
        }).start();
        new Thread(() -> {
            BCryptPasswordEncoder b16 = new BCryptPasswordEncoder(10);
            long startTime = System.currentTimeMillis();
            String code = b16.encode("maxzhao");
            System.out.println("10=>" + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            b16.matches("maxzhao", code);
            System.out.println("10=>" + (System.currentTimeMillis() - startTime));
        }).start();
        new Thread(() -> {
            BCryptPasswordEncoder b31 = new BCryptPasswordEncoder(31);
            long startTime = System.currentTimeMillis();
            String code = b31.encode("maxzhao");
            System.out.println("31=>" + (System.currentTimeMillis() - startTime));
            startTime = System.currentTimeMillis();
            b31.matches("maxzhao", code);
            System.out.println("31=>" + (System.currentTimeMillis() - startTime));
        }).start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void te() {
        for (int i = 0; i < 33; i++) {
            System.out.println(i + "  " + (i == -1 || (i >= 4 && i <= 31)));
        }
    }
}
