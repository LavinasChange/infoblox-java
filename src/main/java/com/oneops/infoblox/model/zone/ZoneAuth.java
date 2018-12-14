package com.oneops.infoblox.model.zone;

import com.google.auto.value.AutoValue;
import com.oneops.infoblox.model.Record;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * DNS Authoritative Zone
 *
 * @author Suresh G
 */
@AutoValue
public abstract class ZoneAuth extends Record {

  public static Builder builder() {
    return new AutoValue_ZoneAuth.Builder();
  }

  public static JsonAdapter<ZoneAuth> jsonAdapter(Moshi moshi) {
    return new AutoValue_ZoneAuth.MoshiJsonAdapter(moshi);
  }

  public abstract String fqdn();

  @AutoValue.Builder
  public abstract static class Builder extends RecBuilder<Builder> {

    public abstract Builder fqdn(String fqdn);

    public abstract ZoneAuth build();
  }
}
