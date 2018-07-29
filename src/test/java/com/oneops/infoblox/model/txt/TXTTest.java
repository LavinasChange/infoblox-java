package com.oneops.infoblox.model.txt;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * TXT record tests.
 *
 * @author Suresh G
 */
@DisplayName("Infoblox TXT record tests.")
class TXTTest {

  private static InfobloxClient client;

  private final String fqdn = "oneops-test-txt1." + domain();

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
    String text = "This a host server";
    String newText = "This a test server";

    // Clean it.
    client.deleteTXTRec(fqdn);

    List<TXT> txtRec1 = client.getTXTRec(fqdn);
    assertTrue(txtRec1.isEmpty());

    // Creates new txt record
    TXT txtRec = client.createTXTRec(fqdn, text);
    assertEquals(fqdn, txtRec.name());
    assertEquals(text, txtRec.text());

    List<TXT> txtRec2 = client.getTXTRec(fqdn);
    assertEquals(1, txtRec2.size());
    assertEquals(fqdn, txtRec2.get(0).name());
    assertEquals(text, txtRec2.get(0).text());

    // Modify TXT Record
    List<TXT> txts = client.modifyTXTRec(fqdn, newText);
    assertEquals(1, txts.size());
    assertEquals(fqdn, txts.get(0).name());
    assertEquals(newText, txts.get(0).text());

    // Delete TXT Record
    List<String> delTxtRec = client.deleteTXTRec(fqdn);
    assertEquals(1, delTxtRec.size());
  }
}
