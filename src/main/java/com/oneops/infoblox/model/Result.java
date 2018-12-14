package com.oneops.infoblox.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.lang.reflect.Type;
import javax.annotation.Nullable;

/**
 * Holds a result type containing one or more JSON objects.
 *
 * @author Suresh G
 */
@AutoValue
public abstract class Result<T> {

  public static <T> Builder<T> builder() {
    return new AutoValue_Result.Builder<T>();
  }

  /** Json adapter for {@link Result} type, used by Moshi for JSON [de]serialization. */
  public static <T> JsonAdapter<Result<T>> jsonAdapter(Moshi moshi, Type[] types) {
    return new AutoValue_Result.MoshiJsonAdapter<>(moshi, types);
  }

  public abstract T result();

  @Json(name = "next_page_id")
  @Nullable
  public abstract String nextPageId();

  @AutoValue.Builder
  public abstract static class Builder<T> {

    public abstract Builder<T> result(T result);

    public abstract Builder<T> nextPageId(String nextPageId);

    public abstract Result<T> build();
  }
}
