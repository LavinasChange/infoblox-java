package com.oneops.infoblox.model.aaaa;

import static com.oneops.infoblox.IBAEnvConfig.domain;
import static com.oneops.infoblox.IBAEnvConfig.isValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.oneops.infoblox.IBAEnvConfig;
import com.oneops.infoblox.InfobloxClient;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * AAAA record tests.
 *
 * @author Suresh
 */
@DisplayName("Infoblox IPv6 address record tests.")
class AAAATest {

  private static InfobloxClient client;

  private final String fqdn = "oneops-test-aaaa1." + domain();
  private final String newFqdn = "oneops-test-aaaa1-mod." + domain();

  @BeforeAll
  static void setUp() {
    assumeTrue(isValid(), IBAEnvConfig::errMsg);
    client =
        InfobloxClient.builder()
            .endPoint(IBAEnvConfig.host())
            .userName(IBAEnvConfig.user())
            .password(IBAEnvConfig.password())
            .ttl(1)
            .tlsVerify(false)
            .debug(true)
            .build();
  }

  /** Make sure to clean the AAAA record before each test. */
  @BeforeEach
  void clean() throws IOException {
    client.deleteAAAARec(fqdn);
    client.deleteAAAARec(newFqdn);
  }

  @Test
  void create() throws IOException {
    List<AAAA> quadARec = client.getAAAARec(fqdn);
    assertTrue(quadARec.isEmpty());

    // Creates AAAA Record
    String ipv6 = "fe80:f0aa:f0bb:f0eb:f0ea:f6ff:fd97:5d51";
    AAAA newAAAARec = client.createAAAARec(fqdn, ipv6);
    assertEquals(ipv6, newAAAARec.ipv6Addr());

    // Check the AAAA record for given fqdn and IPv6.
    List<AAAA> recs = client.getAAAARec(fqdn, ipv6);
    assertEquals(1, recs.size());
    assertEquals(ipv6, recs.get(0).ipv6Addr());

    // Modify AAAA Record
    List<AAAA> modAAAARec = client.modifyAAAARec(fqdn, newFqdn);
    assertEquals(1, modAAAARec.size());

    // Delete AAAA Records
    List<String> delAAAARec = client.deleteAAAARec(fqdn);
    assertEquals(0, delAAAARec.size());

    List<String> delQuadA = client.deleteAAAARec(newFqdn);
    assertEquals(1, delQuadA.size());
  }

  @Test
  @DisplayName("QuadA record modify tests.")
  void modifyTest() throws Exception {
    List<AAAA> rec = client.getAAAARec(fqdn);
    assertTrue(rec.isEmpty());

    String ipv6 = "fe80:f0aa:f0bb:f0eb:f0ea:f6ff:fd97:5d51";
    String newIPv6 = "fe81:f0aa:f1bb:f0eb:f0ea:f6ff:fd97:8d51";

    // Creates AAAA Record
    AAAA newQuadA = client.createAAAARec(fqdn, ipv6);
    assertEquals(ipv6, newQuadA.ipv6Addr());

    // Get quadA record by IP
    List<AAAA> quadRecs = client.getAAAARecByIP(ipv6);
    assertTrue(quadRecs.size() > 0);
    assertTrue(quadRecs.contains(newQuadA));

    // Modify the quadA record IPv6 to new one.
    List<AAAA> quadRecs1 = client.modifyAAAARec(fqdn, ipv6, newIPv6);
    assertEquals(1, quadRecs1.size());
    assertEquals(newIPv6, quadRecs1.get(0).ipv6Addr());

    // Now old IPv6 shouldn't exists.
    List<AAAA> quadRecs2 = client.getAAAARec(fqdn, ipv6);
    assertEquals(0, quadRecs2.size());

    // Cleanup quadA Records
    List<String> delquadARec = client.deleteAAAARec(fqdn);
    assertEquals(1, delquadARec.size());
  }
}
