package com.oneops.infoblox;

import com.oneops.infoblox.model.Result;
import com.oneops.infoblox.model.a.ARec;
import com.oneops.infoblox.model.aaaa.AAAA;
import com.oneops.infoblox.model.cname.CNAME;
import com.oneops.infoblox.model.host.Host;
import com.oneops.infoblox.model.mx.MX;
import com.oneops.infoblox.model.ns.NS;
import com.oneops.infoblox.model.ptr.PTR;
import com.oneops.infoblox.model.srv.SRV;
import com.oneops.infoblox.model.ttl.TTLRec;
import com.oneops.infoblox.model.txt.TXT;
import com.oneops.infoblox.model.zone.ZoneAuth;
import com.oneops.infoblox.model.zone.ZoneDelegate;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Infoblox DNS appliance (IBA) REST interface.
 *
 * @author Suresh G
 */
public interface Infoblox {

  /**
   * By default, all the fields are not returned during a GET request. You can use the
   * <b>_return_fields</b> argument to get the desired data. You would have to explicitly mention
   * the additional fields you require with a <b>_return_fields+={requiredfield}</b>. The following
   * variables describe the fields for each record type. <b>view</b> and <b>ttl</b> fields are
   * included by default as it's part of the base {@link com.oneops.infoblox.model.Record} object.
   *
   * <p>Note: AuthZone record doesn't have any TTL field.
   */
  String A_FIELDS = "_return_fields=name,ipv4addr,view,ttl";

  String AAAA_FIELDS = "_return_fields=name,ipv6addr,view,ttl";

  String CNAME_FIELDS = "_return_fields=name,canonical,view,ttl";

  String HOST_FIELDS = "_return_fields=name,ipv4addrs,view,ttl";

  String MX_FIELDS = "_return_fields=name,mail_exchanger,preference,view,ttl";

  String NS_FIELDS = "_return_fields=name,nameserver,addresses,view,ttl";

  String PTR_FIELDS = "_return_fields=name,ipv4addr,ipv6addr,ptrdname,view,ttl";

  String SRV_FIELDS = "_return_fields=name,port,priority,target,weight,view,ttl";

  String TXT_FIELDS = "_return_fields=name,text,view,ttl";

  String ZONE_AUTH_FIELDS = "_return_fields=fqdn,view";

  String ZONE_DELEGATE_FIELDS = "_return_fields=delegate_to,fqdn,view,delegated_ttl,locked";

  String TTL_FIELDS = "_return_fields=view,ttl";

  /** Auth zone Record */
  @GET("{version}/zone_auth?" + ZONE_AUTH_FIELDS)
  Call<Result<List<ZoneAuth>>> queryAuthZones(@Path(value = "version") String version);

  @GET("{version}/zone_auth?" + ZONE_AUTH_FIELDS)
  Call<Result<List<ZoneAuth>>> queryAuthZone(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  /** Zone delegation */
  @GET("{version}/zone_delegated?" + ZONE_DELEGATE_FIELDS)
  Call<Result<List<ZoneDelegate>>> queryDelegatedZone(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, Object> options);

  @POST("{version}/zone_delegated?" + ZONE_DELEGATE_FIELDS)
  Call<Result<ZoneDelegate>> createDelegatedZone(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + ZONE_DELEGATE_FIELDS)
  Call<Result<ZoneDelegate>> modifyDelegatedZone(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, Object> req);

  /** Host Record */
  @GET("{version}/record:host?" + HOST_FIELDS)
  Call<Result<List<Host>>> queryHostRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:host?" + HOST_FIELDS)
  Call<Result<Host>> createHostRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  /** A Record */
  @GET("{version}/record:a?" + A_FIELDS)
  Call<Result<List<ARec>>> queryARec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:a?" + A_FIELDS)
  Call<Result<ARec>> createARec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + A_FIELDS)
  Call<Result<ARec>> modifyARec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** AAAA Record */
  @GET("{version}/record:aaaa?" + AAAA_FIELDS)
  Call<Result<List<AAAA>>> queryAAAARec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:aaaa?" + AAAA_FIELDS)
  Call<Result<AAAA>> createAAAARec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + AAAA_FIELDS)
  Call<Result<AAAA>> modifyAAAARec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** CNAME Record */
  @GET("{version}/record:cname?" + CNAME_FIELDS)
  Call<Result<List<CNAME>>> queryCNAMERec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:cname?" + CNAME_FIELDS)
  Call<Result<CNAME>> createCNAMERec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + CNAME_FIELDS)
  Call<Result<CNAME>> modifyCNAMERec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** MX Record */
  @GET("{version}/record:mx?" + MX_FIELDS)
  Call<Result<List<MX>>> queryMXRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:mx?" + MX_FIELDS)
  Call<Result<MX>> createMXRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + MX_FIELDS)
  Call<Result<MX>> modifyMXRec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** NS Record */
  @GET("{version}/record:ns?" + NS_FIELDS)
  Call<Result<List<NS>>> queryNSRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:ns?" + NS_FIELDS)
  Call<Result<NS>> createNSRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + NS_FIELDS)
  Call<Result<NS>> modifyNSRec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** PTR Record */
  @GET("{version}/record:ptr?" + PTR_FIELDS)
  Call<Result<List<PTR>>> queryPTRRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:ptr?" + PTR_FIELDS)
  Call<Result<PTR>> createPTRRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + PTR_FIELDS)
  Call<Result<PTR>> modifyPTRRec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** SRV Record */
  @GET("{version}/record:srv?" + SRV_FIELDS)
  Call<Result<List<SRV>>> querySRVRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:srv?" + SRV_FIELDS)
  Call<Result<SRV>> createSRVRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + SRV_FIELDS)
  Call<Result<SRV>> modifySRVRec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** TXT Record */
  @GET("{version}/record:txt?" + TXT_FIELDS)
  Call<Result<List<TXT>>> queryTXTRec(
      @Path(value = "version") String version,
      @QueryMap(encoded = true) Map<String, String> options);

  @POST("{version}/record:txt?" + TXT_FIELDS)
  Call<Result<TXT>> createTXTRec(
      @Path(value = "version") String version, @Body Map<String, Object> req);

  @PUT("{version}/{ref}?" + TXT_FIELDS)
  Call<Result<TXT>> modifyTXTRec(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, String> req);

  /** Delete Record */
  @DELETE("{version}/{ref}")
  Call<Result<String>> deleteRef(
      @Path(value = "version") String version, @Path(value = "ref", encoded = true) String ref);

  /** Modify TTL for a record */
  @PUT("{version}/{ref}?" + TTL_FIELDS)
  Call<Result<TTLRec>> modifyTTL(
      @Path(value = "version") String version,
      @Path(value = "ref", encoded = true) String ref,
      @Body Map<String, Object> req);

  /** Logout session */
  @POST("{version}/logout")
  Call<Void> logout(@Path(value = "version") String version);
}
