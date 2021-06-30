package com.tabuyos.flink.operator.stateless;

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
class MyStatelessMapTest {

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void map() throws Exception {
    MyStatelessMap statelessMap = new MyStatelessMap();
    String out = statelessMap.map("tabuyos");
    Assertions.assertEquals("Hello, tabuyos", out);
  }
}
