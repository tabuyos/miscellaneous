package com.tabuyos.flink.operator.timer;

import avro.shaded.com.google.common.collect.Lists;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.operators.KeyedProcessOperator;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @author tabuyos
 */
class TimerProcessFunctionTest {

  private OneInputStreamOperatorTestHarness<String, String> testHarness;
  private TimerProcessFunction                              processFunction;

  @BeforeEach
  void setUp() throws Exception {
    processFunction = new TimerProcessFunction();

    // KeyedOneInputStreamOperatorTestHarness 需要三个参数：算子对象、键 Selector、键类型
    testHarness = new KeyedOneInputStreamOperatorTestHarness<>(
      new KeyedProcessOperator<>(processFunction),
      x -> "1",
      Types.STRING
    );
    // Function time is initialized to 0
    testHarness.open();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void processElement() throws Exception {
    testHarness.processElement("world", 10);
    Assertions.assertEquals(
      Lists.newArrayList(
        new StreamRecord<>("hello world", 10)
      ),
      testHarness.extractOutputStreamRecords()
    );
  }

  @Test
  void onTimer() throws Exception {
    // test first record
    testHarness.processElement("world", 10);
    Assertions.assertEquals(1, testHarness.numProcessingTimeTimers());

    // Function time 设置为 50
    testHarness.setProcessingTime(50);
    Assertions.assertEquals(
      Lists.newArrayList(
        new StreamRecord<>("hello world", 10),
        new StreamRecord<>("Timer triggered at timestamp 50")
      ),
      testHarness.extractOutputStreamRecords()
    );
  }
}
