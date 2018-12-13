package com.oneops.infoblox

import com.squareup.moshi.*
import org.slf4j.*
import java.io.*
import java.util.concurrent.*

/**
 * [InfobloxClient] provider based on the `fqdn` domain.
 *
 * @author Suresh
 */
object InfobloxClientProvider {

    private val log = LoggerFactory.getLogger(javaClass)

    private val moshi = Moshi.Builder().build()

    private val ibaConfigs: List<InfobloxConfig>

    private val clientCache = ConcurrentHashMap<String, InfobloxClient>()

    /**
     * Read and initialize Infoblox config from the config file.
     */
    init {
        val adapter = listAdapter<InfobloxConfig>()
            ibaConfigs = adapter.fromJson(File("/secrets/infoblox-conf.json").readText()) ?: emptyList()
        log.info("Infoblox config: $ibaConfigs")
        check(ibaConfigs.isNotEmpty()) { "Invalid infoblox config." }
    }

    /**
     * A helper function to generate type safe Moshi list adapter
     */
    private inline fun <reified T> listAdapter(): JsonAdapter<List<T>> {
        val type = Types.newParameterizedType(List::class.java, T::class.java)
        return moshi.adapter<List<T>>(type)
    }

    /**
     * Returns the [InfobloxClient] for the given `fqdn` domain. The infoblox config is
     * selected based on the best matching of the supported authoritative domains.
     *
     * @param fqdn domain name.
     */
    @JvmStatic
    fun forDomain(fqdn: String): InfobloxClient {
        require(fqdn.isNotEmpty()) { "Empty fqdn" }
        var config: InfobloxConfig? = null
        var matchLength = 0
        for (ibaConfig in ibaConfigs) {
            for (domain in ibaConfig.authDomains) {
                if (fqdn.endsWith(domain, ignoreCase = true) && domain.length > matchLength) {
                    matchLength = domain.length
                    config = ibaConfig
                }
            }
        }
        requireNotNull(config) { "Can't find the infoblox config for $fqdn" }
        log.info("Using $config for $fqdn")

        return clientCache.computeIfAbsent(config.endpoint) {
            log.info("Creating infoblox client for ${config.endpoint}")
            InfobloxClient.builder()
                .endPoint(config.endpoint)
                .userName(config.username)
                .password(config.password)
                .tlsVerify(config.tlsVerify)
                .debug(config.debug)
                .build()
        }
    }
}

/**
 * Infoblox JSON config model.
 */
@JsonClass(generateAdapter = true)
data class InfobloxConfig(
    val endpoint: String,
    val username: String,
    val password: String,
    @Json(name = "authoritative_domains")
    val authDomains: List<String> = emptyList(),
    @Json(name = "tls_verify")
    val tlsVerify: Boolean = false,
    val debug: Boolean = false
) {
    override fun toString(): String {
        return "InfobloxConfig(endpoint='$endpoint', username='$username', authDomains=$authDomains, tlsVerify=$tlsVerify, debug=$debug)"
    }
}
