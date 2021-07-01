package com.tabuyos.flink.operator.practical;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.runtime.operators.windowing.WindowOperator;
import org.apache.flink.streaming.runtime.operators.windowing.functions.InternalIterableProcessWindowFunction;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.util.KeyedOneInputStreamOperatorTestHarness;
import org.apache.flink.streaming.util.OneInputStreamOperatorTestHarness;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TODO
 *
 * @author bjliu(a.k.a tabuyos)
 * @since 2021/6/30
 */
class MyProcessWindowFunctionTestProcessing {

  public static Tuple3<String, String, Long>[] ENGLISH = null;
  private static final TypeInformation<Tuple3<String, String, Long>> STRING_INT_TUPLE =
      TypeInformation.of(new TypeHint<Tuple3<String, String, Long>>() {});

  @BeforeEach
  void setUp() {
    ENGLISH =
        new Tuple3[] {
          Tuple3.of("class1", "张三", 100L),
          Tuple3.of("class1", "李四", 78L),
          Tuple3.of("class1", "王五", 99L),
          Tuple3.of("class2", "赵六", 81L),
          Tuple3.of("class2", "小七", 59L),
          Tuple3.of("class2", "小八", 97L),
        };
  }

  @AfterEach
  void tearDown() {}

  @Test
  void process() throws Exception {
    final int sessionSize = 3;
    ListStateDescriptor<Tuple3<String, String, Long>> stateDesc =
        new ListStateDescriptor<>(
            "window-contents", STRING_INT_TUPLE.createSerializer(new ExecutionConfig()));
    WindowOperator<
            String,
            Tuple3<String, String, Long>,
            Iterable<Tuple3<String, String, Long>>,
            Double,
            TimeWindow>
        operator =
            new WindowOperator<>(
                EventTimeSessionWindows.withGap(Time.seconds(sessionSize)),
                new TimeWindow.Serializer(),
                x -> x.f0,
                BasicTypeInfo.STRING_TYPE_INFO.createSerializer(new ExecutionConfig()),
                stateDesc,
                new InternalIterableProcessWindowFunction<>(new MyProcessWindowFunction()),
                ProcessingTimeTrigger.create(),
                0,
                null);
    OneInputStreamOperatorTestHarness<Tuple3<String, String, Long>, Double> testHarness =
        createTestHarness(operator);
    ConcurrentLinkedQueue<StreamRecord<Double>> expectedOutput = new ConcurrentLinkedQueue<>();
    testHarness.open();

    testHarness.setProcessingTime(3);
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class1", "张三", 100L), 10));
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class1", "李四", 78L), 20));
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class1", "王五", 99L), 40));
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class2", "赵六", 81L), 30));
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class2", "小七", 59L), 50));
    testHarness.processElement(new StreamRecord<>(Tuple3.of("class2", "小八", 97L), 60));
    testHarness.setProcessingTime(5000);

    expectedOutput.add(new StreamRecord<>(92.0, 5499));
    expectedOutput.add(new StreamRecord<>(70.0, 5499));

    testHarness.extractOutputStreamRecords().forEach(System.out::println);

    Assertions.assertEquals(expectedOutput.size(), testHarness.extractOutputStreamRecords().size());
    testHarness.close();
  }

  private static <OUT>
      OneInputStreamOperatorTestHarness<Tuple3<String, String, Long>, OUT> createTestHarness(
          OneInputStreamOperator<Tuple3<String, String, Long>, OUT> operator) throws Exception {
    return new KeyedOneInputStreamOperatorTestHarness<>(
        operator, x -> x.f0, BasicTypeInfo.STRING_TYPE_INFO);
  }
}
