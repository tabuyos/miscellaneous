/*
 * Copyright (c) 2018-2021 Tabuyos All Right Reserved.
 */
package com.tabuyos.flink.operator.stateless;

import org.apache.flink.api.common.functions.MapFunction;

/**
 * TODO
 *
 * @author <a href="http://www.tabuyos.com">tabuyos</a>
 * @since 2021/6/29
 */
public class MyStatelessMap implements MapFunction<String, String> {

  @Override
  public String map(String s) throws Exception {
    return "Hello, " + s;
  }
}
