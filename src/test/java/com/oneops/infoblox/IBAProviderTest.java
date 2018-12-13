package com.oneops.infoblox;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Infoblox client provider tests")
class IBAProviderTest {

  @BeforeAll
  static void setUp() {
    assumeTrue(Files.exists(Paths.get("/secrets/infoblox-conf.json")));
  }

  @Test
  void forDomain() {
    assertNotNull(InfobloxClientProvider.forDomain("test.us.walmart.net"));
    assertThrows(
        IllegalArgumentException.class, () -> InfobloxClientProvider.forDomain("google.com"));
  }
}
