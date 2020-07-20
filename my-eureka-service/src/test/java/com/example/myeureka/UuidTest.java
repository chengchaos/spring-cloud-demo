package com.example.myeureka;

import org.junit.Test;

import java.util.UUID;

public class UuidTest {

    @Test
    public void Test() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println(uuid);
    }
}
