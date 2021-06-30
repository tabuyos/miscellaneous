package com.tabuyos.flink.operator.practical;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.experimental.CollectSink;
import org.apache.flink.util.Collector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author bjliu(a.k.a tabuyos)
 * @since 2021/6/30
 */
class MyProcessWindowFunctionTest {

  public static Tuple3<String, String, Long>[] ENGLISH = null;

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
    // 获取执行环境
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
    // 读取数据
    DataStream<Tuple3<String, String, Long>> input = env.fromElements(ENGLISH);
    // 求各班级英语成绩平均分
    DataStream<Double> avgScore =
        input
          .keyBy(x -> x.f0)
          .timeWindow(Time.seconds(10))
//          .trigger(new MyTrigger())
          .process(new MyProcessWindowFunction());
    avgScore.print();
    JobExecutionResult result = env.execute("MyProcessWindowFunctionTest");
    result.getAllAccumulatorResults().values().forEach(System.out::println);
  }
}
