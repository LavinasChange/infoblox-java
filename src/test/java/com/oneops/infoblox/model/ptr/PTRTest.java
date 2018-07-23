package com.oneops.infoblox.model.ptr;

import static com.oneops.infoblox.IBAEnvConfig.domain;
import static com.oneops.infoblox.IBAEnvConfig.isValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.oneops.infoblox.IBAEnvConfig;
import com.oneops.infoblox.InfobloxClient;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * PTR record tests.
 *
 * @author Suresh G
 */
@DisplayName("Infoblox PTR record tests.")
class PTRTest {

  private static InfobloxClient client;

  private final String fqdn = "oneops-test-ptr1." + domain();

  private final String modFqdn = "oneops-test-ptr1-mod." + domain();

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

  @Test
  void create() throws IOException {

    // Clean any existing PTR records.
    client.deletePTRDRec(fqdn);
    client.deletePTRDRec(modFqdn);

    List<PTR> rec = client.getPTRDRec(fqdn);
    assertTrue(rec.isEmpty());

    // Creates PTR Record
    String ip = "10.1.1.1";
    String reverseMapName = PTR.reverseMapName(InetAddress.getByName(ip));

    PTR ptrRec = client.createPTRRec(ip, fqdn);
    assertEquals(fqdn, ptrRec.ptrdname());
    assertEquals(ip, ptrRec.ipv4addr());
    assertEquals(reverseMapName, ptrRec.name());

    // Multiple ptrdname can associate with an IP address.
    List<PTR> ptrRec1 = client.getPTRRec(ip);
    assertTrue(ptrRec1.size() > 0);

    List<PTR> ptrdRec = client.getPTRDRec(fqdn);
    assertEquals(1, ptrdRec.size());
    assertEquals(fqdn, ptrdRec.get(0).ptrdname());
    assertEquals(ip, ptrdRec.get(0).ipv4addr());
    assertEquals(reverseMapName, ptrdRec.get(0).name());

    // Update PTR domain
    List<PTR> modPTR = client.modifyPTRRec(ip, modFqdn);
    assertTrue(modPTR.size() > 0);
    assertEquals(modFqdn, modPTR.get(0).ptrdname());
    assertEquals(ip, modPTR.get(0).ipv4addr());
    assertEquals(reverseMapName, modPTR.get(0).name());

    // Delete PTR record
    client.deletePTRDRec(fqdn);
    client.deletePTRDRec(modFqdn);

    List<PTR> ptrdRec1 = client.getPTRDRec(fqdn);
    assertEquals(0, ptrdRec1.size());

    List<PTR> ptrdRec2 = client.getPTRDRec(modFqdn);
    assertEquals(0, ptrdRec2.size());
  }

  @Test
  void reverseMapTest() throws IOException {

    String ipv4 = "8.8.4.4";
    String ipv6 = "2001:db8::567:89ab";

    String ipv4Ptr = "4.4.8.8.in-addr.arpa";
    String ipv6Ptr = "b.a.9.8.7.6.5.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.0.8.b.d.0.1.0.0.2.ip6.arpa";

    assertEquals(ipv4Ptr, PTR.reverseMapName(InetAddress.getByName(ipv4)));
    assertEquals(ipv6Ptr, PTR.reverseMapName(InetAddress.getByName(ipv6)));
  }
}
