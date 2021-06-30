/*
 * Copyright (c) 2009-2021 TravelSky Technology Ltd. All Right Reserved.
 */
package com.tabuyos.flink.operator.practical;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 *
 * @author tabuyos
 * @since 2021/6/30
 */
public class MyProcessWindowFunction
    extends ProcessWindowFunction<Tuple3<String, String, Long>, Double, String, GlobalWindow> {

  @Override
  public void process(
      String key,
      Context context,
      Iterable<Tuple3<String, String, Long>> elements,
      Collector<Double> out)
      throws Exception {
    System.out.println(key);
    AtomicLong sum = new AtomicLong();
    AtomicLong count = new AtomicLong();
    elements.forEach(
        it -> {
          sum.addAndGet(it.f2);
          count.getAndIncrement();
        });
    out.collect((double) (sum.get() / count.get()));
  }
}
