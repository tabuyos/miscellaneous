/*
 * Copyright 2019-2021 the Tabuyos.
 */
package com.tabuyos.flink.operator.practical;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

/**
 * TODO
 *
 * @author tabuyos
 */
public class MyTrigger extends Trigger<Tuple3<String, String, Long>, TimeWindow> {
  @Override
  public TriggerResult onElement(Tuple3<String, String, Long> element, long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
    ValueState<Integer> lastPriceState = triggerContext.getPartitionedState(new ValueStateDescriptor<>("lastPriceState", Integer.class));
    TriggerResult triggerResult = TriggerResult.CONTINUE;
    if (lastPriceState.value() == null) {
      lastPriceState.update(0);
    }
    if ("class2".equalsIgnoreCase(element.f0)) {
      lastPriceState.update(lastPriceState.value() + 1);
    }
    if (lastPriceState.value() == 3) {
      return TriggerResult.FIRE_AND_PURGE;
    }
    return triggerResult;
  }

  @Override
  public TriggerResult onProcessingTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
    return TriggerResult.FIRE_AND_PURGE;
  }

  @Override
  public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {
    return TriggerResult.CONTINUE;
  }

  @Override
  public void clear(TimeWindow timeWindow, TriggerContext triggerContext) throws Exception {

  }
}
