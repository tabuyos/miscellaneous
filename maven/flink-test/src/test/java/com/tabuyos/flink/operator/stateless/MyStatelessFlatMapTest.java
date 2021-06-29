package com.tabuyos.flink.operator.stateless;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.flink.api.common.functions.util.ListCollector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @author bjliu(a.k.a tabuyos)
 * @since 2021/6/29
 */
class MyStatelessFlatMapTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void flatMap() throws Exception {
        MyStatelessFlatMap statelessFlatMap = new MyStatelessFlatMap();
        List<String> out = new ArrayList<>();
        ListCollector<String> listCollector = new ListCollector<>(out);
        statelessFlatMap.flatMap("tabuyos", listCollector);
        Assertions.assertEquals(Lists.newArrayList("Hello, tabuyos"), out);
    }
}