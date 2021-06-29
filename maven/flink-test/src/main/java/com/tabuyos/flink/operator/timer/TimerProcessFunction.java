/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.flink.operator.timer;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * TODO
 *
 * @author tabuyos
 */
public class TimerProcessFunction extends KeyedProcessFunction<String, String, String> {
  @Override
  public void processElement(String s, Context context, Collector<String> collector) throws Exception {
    context.timerService().registerProcessingTimeTimer(50);
    String out = "hello " + s;
    collector.collect(out);
  }

  @Override
  public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) throws Exception {
    // 到达时间点触发事件操作
    out.collect(String.format("Timer triggered at timestamp %d", timestamp));
  }
}
