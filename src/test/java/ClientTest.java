package test.java;

import org.junit.jupiter.api.Test;

import main.java.client.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
  @Test
   void Add() {
    Client client = new  Client();
      assertEquals(4, client.sumTest(2, 2));
  }
}
