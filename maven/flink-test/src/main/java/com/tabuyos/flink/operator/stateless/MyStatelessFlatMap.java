/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.flink.operator.stateless;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * TODO
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/29
 */
public class MyStatelessFlatMap implements FlatMapFunction<String, String> {

  @Override
  public void flatMap(String s, Collector<String> collector) throws Exception {
    collector.collect("Hello, " + s);
  }
}
