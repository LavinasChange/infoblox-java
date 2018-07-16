package com.oneops.infoblox;

import static com.oneops.infoblox.IBAEnvConfig.isValid;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import com.oneops.infoblox.model.cname.CNAME;
import com.oneops.infoblox.model.zone.Delegate;
import com.oneops.infoblox.model.zone.ZoneAuth;
import com.oneops.infoblox.model.zone.ZoneDelegate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for GSLB migration flow.
 *
 * @author Suresh G
 */
@DisplayName("GSLB migration tests.")
@Disabled("Skipped from regular tests.")
class MigrationTest {

  private static InfobloxClient client;

  private final String domain = "oneopstest.walmart.net";

  /** Torbit GSLB domain */
  private final String canonicalName = "testrecord.glb.us.walmart.net";

  /** Netscalar GSLB domain */
  private final String aliasName = "testrecord.glb.oneopstest.walmart.net";

  @BeforeAll
  static void setUp() {
    assumeTrue(isValid(), IBAEnvConfig::errMsg);
    client =
        InfobloxClient.builder()
            .endPoint(IBAEnvConfig.host())
            .userName(IBAEnvConfig.user())
            .password(IBAEnvConfig.password())
            .ttl(5)
            .tlsVerify(false)
            .debug(true)
            .build();
  }

  /** Clean the cname and delegated zone records before each test. */
  @BeforeEach
  void clean() throws IOException {
    client.deleteCNameRec(aliasName);
    client.deleteDelegatedZone(aliasName);
  }

  @Test
  void migrationApis() throws Exception {

    // Get all available auth zones.
    client.getAuthZones().forEach(System.out::println);

    // Make sure the infoblox has this Authoritative Zone
    List<ZoneAuth> authZones = client.getAuthZones(domain);
    authZones.forEach(System.out::println);
    assertEquals(domain, authZones.get(0).fqdn());

    // cname operations.
    List<CNAME> cNameRec = client.getCNameRec(aliasName);
    assertEquals(0, cNameRec.size());

    CNAME cname = client.createCNameRec(aliasName, canonicalName);
    assertEquals(aliasName, cname.name());
    assertEquals(canonicalName, cname.canonical());

    List<CNAME> cNameRec1 = client.getCNameRec(aliasName);
    assertEquals(1, cNameRec1.size());
    assertEquals(aliasName, cNameRec1.get(0).name());
    assertEquals(canonicalName, cNameRec1.get(0).canonical());

    client.deleteCNameRec(aliasName);

    // Delegated zone operations for netscalar GSLB
    ZoneDelegate delegatedZone = client.createDelegatedZone(aliasName, nsDelegatesList(), 30);
    assertEquals(aliasName, delegatedZone.fqdn());
    assertEquals(6, delegatedZone.delegateTo().size());

    List<ZoneDelegate> dZones = client.getDelegatedZones(aliasName);
    assertEquals(1, dZones.size());
    assertEquals(aliasName, dZones.get(0).fqdn());
    assertEquals(6, dZones.get(0).delegateTo().size());
    dZones.forEach(System.out::println);

    client.deleteDelegatedZone(aliasName);
  }

  /** Returns the netscalar zone delegates to list. */
  private List<Delegate> nsDelegatesList() {
    List<Delegate> list = new ArrayList<>();
    list.add(Delegate.of("10.57.151.6", "legacy-glb-ndc.prod.gecwalmart.com"));
    list.add(Delegate.of("10.63.151.6", "legacy-glb-edc.prod.gecwalmart.com"));
    list.add(Delegate.of("10.120.138.19", "legacy-glb-ndc2.cloud.wal-mart.com"));
    list.add(Delegate.of("10.65.74.6", "legacy-glb-dfw.prod.walmart.com"));
    list.add(Delegate.of("10.65.202.6", "legacy-glb-dal.prod.walmart.com"));
    list.add(Delegate.of("10.227.131.147", "legacy-glb-cdc2.cloud.wal-mart.com"));
    return list;
  }
}
