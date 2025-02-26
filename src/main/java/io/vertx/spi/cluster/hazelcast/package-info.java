/**
 * = Hazelcast Cluster Manager
 *
 * This is a cluster manager implementation for Vert.x that uses http://hazelcast.com[Hazelcast].
 *
 * It is the default cluster manager used in the Vert.x distribution, but it can be replaced with another implementation as Vert.x
 * cluster managers are pluggable.  This implementation is packaged inside:
 *
 * [source,xml,subs="+attributes"]
 * ----
 * <dependency>
 *   <groupId>${maven.groupId}</groupId>
 *   <artifactId>${maven.artifactId}</artifactId>
 *   <version>${maven.version}</version>
 * </dependency>
 * ----
 *
 * In Vert.x a cluster manager is used for various functions including:
 *
 * * Discovery and group membership of Vert.x nodes in a cluster
 * * Maintaining cluster wide topic subscriber lists (so we know which nodes are interested in which event bus addresses)
 * * Distributed Map support
 * * Distributed Locks
 * * Distributed Counters
 *
 * Cluster managers *do not* handle the event bus inter-node transport, this is done directly by Vert.x with TCP connections.
 *
 * == Using this cluster manager
 *
 * If you are using Vert.x from the command line, the jar corresponding to this cluster manager (it will be named `vertx-hazelcast-${version}`.jar`
 * should be in the `lib` directory of the Vert.x installation.
 *
 * If you want clustering with this cluster manager in your Vert.x Maven or Gradle project then just add a dependency to
 * the artifact: `io.vertx:vertx-hazelcast:${version}` in your project.
 *
 * If the jar is on your classpath as above then Vert.x will automatically detect this and use it as the cluster manager.
 * Please make sure you don't have any other cluster managers on your classpath or Vert.x might
 * choose the wrong one.
 *
 * You can also specify the cluster manager programmatically if you are embedding Vert.x by specifying it on the options
 * when you are creating your Vert.x instance, for example:
 *
 * [source,$lang]
 * ----
 * {@link io.vertx.spi.cluster.hazelcast.examples.Examples#example1()}
 * ----
 *
 * == Configuring this cluster manager
 *
 * Usually the cluster manager is configured by a file
 * https://github.com/vert-x3/vertx-hazelcast/blob/master/src/main/resources/default-cluster.xml[`default-cluster.xml`]
 * which is packaged inside the jar.
 *
 * If you want to override this configuration you can provide a file called `cluster.xml` on your classpath and this
 * will be used instead.
 *
 * The xml file is a Hazelcast configuration file and is described in detail in the documentation on the Hazelcast
 * web-site.
 *
 * You can also specify configuration programmatically if embedding:
 *
 * [source,$lang]
 * ----
 * {@link io.vertx.spi.cluster.hazelcast.examples.Examples#example2()}
 * ----
 *
 * Hazelcast supports several different transports including multicast and TCP. The default configuration uses
 * multicast so you must have multicast enabled on your network for this to work.
 *
 * For full documentation on how to configure the transport differently or use a different transport please consult the
 * Hazelcast documentation.
 *
 * == Trouble shooting clustering
 *
 * If the default multicast configuration is not working here are some common causes:
 *
 * === Multicast not enabled on the machine.
 *
 * It is quite common in particular on OSX machines for multicast to be disabled by default. Please google for
 * information on how to enable this.
 *
 * === Using wrong network interface
 *
 * If you have more than one network interface on your machine (and this can also be the case if you are running
 * VPN software on your machine), then Hazelcast may be using the wrong one.
 *
 * To tell Hazelcast to use a specific interface you can provide the IP address of the interface in the `interfaces`
 * element of the configuration. Make sure you set the `enabled` attribute to `true`. For example:
 *
 * ----
 * <interfaces enabled="true">
 *   <interface>192.168.1.20</interface>
 * </interfaces>
 * ----
 *
 * When running Vert.x is in clustered mode, you should also make sure that Vert.x knows about the correct interface.
 * When running at the command line this is done by specifying the `cluster-host` option:
 *
 * ----
 * vertx run myverticle.js -cluster -cluster-host your-ip-address
 * ----
 *
 * Where `your-ip-address` is the same IP address you specified in the Hazelcast configuration.
 *
 * If using Vert.x programmatically you can specify this using {@link io.vertx.core.VertxOptions#setClusterHost(java.lang.String)}.
 *
 * === Using a VPN
 *
 * This is a variation of the above case. VPN software often works by creating a virtual network interface which often
 * doesn't support multicast. If you have a VPN running and you do not specify the correct interface to use in both the
 * hazelcast configuration and to Vert.x then the VPN interface may be chosen instead of the correct interface.
 *
 * So, if you have a VPN running you may have to configure both the Hazelcast and Vert.x to use the correct interface as
 * described in the previous section.
 *
 * === When multicast is not available
 *
 * In some cases you may not be able to use multicast as it might not be available in your environment. In that case
 * you should configure another transport, e.g. TCP  to use TCP sockets, or AWS when running on Amazon EC2.
 *
 * For more information on available Hazelcast transports and how to configure them please consult the Hazelcast
 * documentation.
 *
 * === Enabling logging
 *
 * When trouble-shooting clustering issues with Hazelcast it's often useful to get some logging output from Hazelcast
 * to see if it's forming a cluster properly. You can do this (when using the default JUL logging) by adding a file
 * called `vertx-default-jul-logging.properties` on your classpath. This is a standard java.util.loging (JUL)
 * configuration file. Inside it set:
 *
 * ----
 * com.hazelcast.level=INFO
 * ----
 *
 * and also
 *
 * ----
 * java.util.logging.ConsoleHandler.level=INFO
 * java.util.logging.FileHandler.level=INFO
 * ----
 *
 */
@Document(fileName = "index.adoc")
package io.vertx.spi.cluster.hazelcast;

import io.vertx.docgen.Document;
