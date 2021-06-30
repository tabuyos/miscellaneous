package com.tabuyos.flink.operator.stateful;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.operators.StreamFlatMap;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author bjliu(a.k.a tabuyos)
 * @since 2021/6/29
 */
class MyStatefulFlatMapTest {

  private KeyedOneInputStreamOperatorTestHarness<String, String, Long> testHarness;
  private MyStatefulFlatMap statefulFlatMap;

  @BeforeEach
  void setUp() throws Exception {
    statefulFlatMap = new MyStatefulFlatMap();
    testHarness =
        new KeyedOneInputStreamOperatorTestHarness<>(
            new StreamFlatMap<>(statefulFlatMap), x -> "1", Types.STRING);
    testHarness.open();
  }

  @AfterEach
  void tearDown() {}

  @Test
  void open() {}

  @Test
  void flatMap() throws Exception {
    testHarness.processElement("a", 10);
    Assertions.assertEquals(
        Lists.newArrayList(new StreamRecord<>(1L, 10)),
        this.testHarness.extractOutputStreamRecords());

    // test second record
    testHarness.processElement("b", 20);
    Assertions.assertEquals(
        Lists.newArrayList(new StreamRecord<>(1L, 10), new StreamRecord<>(2L, 20)),
        testHarness.extractOutputStreamRecords());

    testHarness
        .extractOutputStreamRecords()
        .forEach(
            x -> {
              System.out.println(x);
            });
    // test other record
    testHarness.processElement("c", 30);
    testHarness.processElement("d", 40);
    testHarness.processElement("e", 50);
    Assertions.assertEquals(
        Lists.newArrayList(
            new StreamRecord<>(1L, 10),
            new StreamRecord<>(2L, 20),
            new StreamRecord<>(3L, 30),
            new StreamRecord<>(4L, 40),
            new StreamRecord<>(5L, 50)),
        testHarness.extractOutputStreamRecords());
  }
}
