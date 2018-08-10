package com.oneops.infoblox.model.a;

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
 * Address record tests.
 *
 * @author Suresh G
 */
@DisplayName("Infoblox address record tests.")
class ARecordTest {

  private static InfobloxClient client;

  private final String fqdn = "oneops-test-a1." + domain();
  private final String newFqdn = "oneops-test-a1-mod." + domain();

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

  /** Make sure to clean the A record before each test. */
  @BeforeEach
  void clean() throws IOException {
    client.deleteARec(fqdn);
    client.deleteARec(newFqdn);
  }

  @Test
  @DisplayName("A record crud tests.")
  void create() throws Exception {
    List<ARec> rec = client.getARec(fqdn);
    assertTrue(rec.isEmpty());

    // Creates A Record
    String ip = "10.11.12.13";
    ARec aRec = client.createARec(fqdn, ip);
    assertEquals(ip, aRec.ipv4Addr());

    // Check the A record for given fqdn and IP.
    List<ARec> aRecs = client.getARec(fqdn, ip);
    assertEquals(1, aRecs.size());
    assertEquals(ip, aRecs.get(0).ipv4Addr());

    // Modify A Record
    List<ARec> modifedARec = client.modifyARec(fqdn, newFqdn);
    assertEquals(1, modifedARec.size());

    // Delete A Records
    List<String> delARec = client.deleteARec(fqdn);
    assertEquals(0, delARec.size());

    List<String> delNewARec = client.deleteARec(newFqdn);
    assertEquals(1, delNewARec.size());
  }

  @Test
  @DisplayName("A record modify tests.")
  void modifyTest() throws Exception {
    List<ARec> rec = client.getARec(fqdn);
    assertTrue(rec.isEmpty());

    String ip = "10.11.12.13";
    String newIP = "10.10.12.13";

    // Creates A Record
    ARec aRec = client.createARec(fqdn, ip);
    assertEquals(ip, aRec.ipv4Addr());

    // Get A record by IP
    List<ARec> aRecs = client.getARecByIP(ip);
    assertTrue(aRecs.size() > 0);
    assertTrue(aRecs.contains(aRec));

    // Modify the A record IP to new one.
    List<ARec> aRecs1 = client.modifyARec(fqdn, ip, newIP);
    assertEquals(1, aRecs1.size());
    assertEquals(newIP, aRecs1.get(0).ipv4Addr());

    // Now old IP shouldn't exists.
    List<ARec> aRecs2 = client.getARec(fqdn, ip);
    assertEquals(0, aRecs2.size());

    // Cleanup A Records
    List<String> delARec = client.deleteARec(fqdn);
    assertEquals(1, delARec.size());
  }
}
