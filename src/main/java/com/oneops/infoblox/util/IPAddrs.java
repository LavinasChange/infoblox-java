package com.oneops.infoblox.util;

import java.util.regex.Pattern;

/**
 * Contains utility methods to validate IPv4 and IPv6 addresses.
 *
 * @author Suresh G
 */
public class IPAddrs {

  private static final Pattern IPv4 =
      Pattern.compile(
          "^((25[0-5]|2[0-4][0-9]|[01]?[0-9]{1,2})\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9]{1,2})$");

  private static final Pattern IPv6 = Pattern.compile("^([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}$");

  /**
   * Checks that the given address is of IPv4 format, else throws an IllegalArgumentException.
   *
   * @param ipAddr IP address.
   */
  public static void requireIPv4(String ipAddr) {
    if (!IPv4.matcher(ipAddr).matches()) {
      throw new IllegalArgumentException("Invalid IPv4 address: " + ipAddr);
    }
  }

  /**
   * Checks that the given address is of IPv6 format, else throws an IllegalArgumentException.
   *
   * @param ipAddr IP address.
   */
  public static void requireIPv6(String ipAddr) {
    if (!IPv6.matcher(ipAddr).matches()) {
      throw new IllegalArgumentException("Invalid IPv6 address: " + ipAddr);
    }
  }

  /**
   * Checks if the given address is IPv4
   *
   * @param ipAddr ip address
   * @return return <code>true</code> if it's IPv4.
   */
  public static boolean isIPv4(String ipAddr) {
    return IPv4.matcher(ipAddr).matches();
  }
}
