/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.flink.operator.stateful;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

/**
 * TODO
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/29
 */
public class MyStatefulFlatMap extends RichFlatMapFunction<String, Long> {

  ValueState<Long> counterState;

  @Override
  public void open(Configuration parameters) throws Exception {
    ValueStateDescriptor<Long> descriptor = new ValueStateDescriptor<>("Counter", Types.LONG);
    System.out.println("start open function");
    this.counterState = getRuntimeContext().getState(descriptor);
  }

  @Override
  public void flatMap(String s, Collector<Long> collector) throws Exception {
    System.out.println("s: " + s);
    Long count = 0L;
    if (this.counterState.value() != null) {
      count = this.counterState.value();
    }
    count++;
    this.counterState.update(count);
    collector.collect(count);
  }
}
