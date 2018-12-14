package com.oneops.infoblox.model.cname;

import com.google.auto.value.AutoValue;
import com.oneops.infoblox.model.Record;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Canonical name record {@link com.oneops.infoblox.model.DNSRecord#CNAME } response.
 *
 * @author Suresh G
 */
@AutoValue
public abstract class CNAME extends Record {

  public static Builder builder() {
    return new AutoValue_CNAME.Builder();
  }

  public static JsonAdapter<CNAME> jsonAdapter(Moshi moshi) {
    return new AutoValue_CNAME.MoshiJsonAdapter(moshi);
  }

  public abstract String canonical();

  public abstract String name();

  @AutoValue.Builder
  public abstract static class Builder extends RecBuilder<Builder> {

    public abstract Builder canonical(String canonical);

    public abstract Builder name(String name);

    public abstract CNAME build();
  }
}
