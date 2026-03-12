// SPDX-FileCopyrightText: Copyright (C) 2013-2025 The Apache Software Foundation
// SPDX-License-Identifier: Apache-2.0
// SPDX-FileCopyrightText: Copyright (C) 2026 The ARG-V Project

/** filtered and transformed by ARG-V */

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.sosy_lab.sv_benchmarks.Verifier;
import java.io.File;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration used within the Drill-on-YARN code. Configuration comes from four
 * sources (in order of precedence):
 * <ol>
 * <li>System properties</li>
 * <li>$SITE_DIR/drill-on-yarn.conf</li>
 * <li>Distribution-specific properties in $SITE_HOME/conf/doy-distrib.conf</li>
 * <li>Drill-on-YARN defaults in drill-on-yarn-defaults.conf. (Which should be
 * disjoint from the Drill settings.)</li>
 * <li>Drill properties (via the Drill override system)</li>
 * </ol>
 * <p>
 * Defines constants for each property, including some defined in Drill. This provides
 * a uniform property access interface even if some properties migrate between DoY and
 * Drill proper.
 */

public class Main {
  public static String DEFAULTS_FILE_NAME = "drill-on-yarn-defaults.conf";
  public static String DISTRIB_FILE_NAME = "doy-distrib.conf";
  public static String CONFIG_FILE_NAME = "drill-on-yarn.conf";

  public static String DRILL_ON_YARN_PARENT = "drill.yarn";
  public static String DOY_CLIENT_PARENT = append(DRILL_ON_YARN_PARENT, "client");
  public static String DOY_AM_PARENT = append(DRILL_ON_YARN_PARENT, "am");
  public static String DOY_DRILLBIT_PARENT = append(DRILL_ON_YARN_PARENT, "drillbit");
  public static String FILES_PARENT = append(DRILL_ON_YARN_PARENT, "drill-install");
  public static String DFS_PARENT = append(DRILL_ON_YARN_PARENT, "dfs");
  public static String HTTP_PARENT = append(DRILL_ON_YARN_PARENT, "http");
  public static String YARN_PARENT = append(DRILL_ON_YARN_PARENT, "yarn");
  public static String HADOOP_PARENT = append(DRILL_ON_YARN_PARENT, "hadoop");
  public static String CLIENT_PARENT = append(DRILL_ON_YARN_PARENT, "client");

  public static String APP_NAME = append(DRILL_ON_YARN_PARENT, "app-name");
  public static String CLUSTER_ID = Verifier.nondetString();

  public static String DFS_CONNECTION = append(DFS_PARENT, "connection");
  public static String DFS_APP_DIR = append(DFS_PARENT, "app-dir");

  public static String YARN_QUEUE = append(YARN_PARENT, "queue");
  public static String YARN_PRIORITY = append(YARN_PARENT, "priority");

  public static String DRILL_ARCHIVE_PATH = append(FILES_PARENT, "client-path");
  public static String DRILL_DIR_NAME = append(FILES_PARENT, "dir-name");

  /**
   * Key used for the Drill archive file in the AM launch config.
   */

  public static String DRILL_ARCHIVE_KEY = append(FILES_PARENT, "drill-key");
  public static String SITE_ARCHIVE_KEY = append(FILES_PARENT, "site-key");
  public static String LOCALIZE_DRILL = append(FILES_PARENT, "localize");
  public static String CONF_AS_SITE = append(FILES_PARENT, "conf-as-site");
  public static String DRILL_HOME = append(FILES_PARENT, "drill-home");
  public static String SITE_DIR = append(FILES_PARENT, "site-dir");
  public static String JAVA_LIB_PATH = append(FILES_PARENT, "library-path");

  public static String HADOOP_HOME = append(HADOOP_PARENT, "home");
  public static String HADOOP_CLASSPATH = append(HADOOP_PARENT, "class-path");
  public static String HBASE_CLASSPATH = append(HADOOP_PARENT, "hbase-class-path");

  public static String MEMORY_KEY = "memory-mb";
  public static String VCORES_KEY = "vcores";
  public static String DISKS_KEY = "disks";
  public static String VM_ARGS_KEY = "vm-args";
  public static String HEAP_KEY = "heap";

  public static String AM_MEMORY = append(DOY_AM_PARENT, MEMORY_KEY);
  public static String AM_VCORES = append(DOY_AM_PARENT, VCORES_KEY);
  public static String AM_DISKS = append(DOY_AM_PARENT, DISKS_KEY);
  public static String AM_NODE_LABEL_EXPR = append(DOY_AM_PARENT, "node-label-expr");
  public static String AM_HEAP = append(DOY_AM_PARENT, HEAP_KEY);
  public static String AM_VM_ARGS = append(DOY_AM_PARENT, VM_ARGS_KEY);
  public static String AM_POLL_PERIOD_MS = append(DOY_AM_PARENT, "poll-ms");
  public static String AM_TICK_PERIOD_MS = append(DOY_AM_PARENT, "tick-ms");
  public static String AM_PREFIX_CLASSPATH = append(DOY_AM_PARENT, "prefix-class-path");
  public static String AM_CLASSPATH = append(DOY_AM_PARENT, "class-path");
  public static String AM_DEBUG_LAUNCH = append(DOY_AM_PARENT, "debug-launch");
  public static String AM_ENABLE_AUTO_SHUTDOWN = append(DOY_AM_PARENT, "auto-shutdown");

  public static String DRILLBIT_MEMORY = append(DOY_DRILLBIT_PARENT, MEMORY_KEY);
  public static String DRILLBIT_VCORES = append(DOY_DRILLBIT_PARENT, VCORES_KEY);
  public static String DRILLBIT_DISKS = append(DOY_DRILLBIT_PARENT, DISKS_KEY);
  public static String DRILLBIT_VM_ARGS = append(DOY_DRILLBIT_PARENT, VM_ARGS_KEY);
  public static String DRILLBIT_HEAP = append(DOY_DRILLBIT_PARENT, HEAP_KEY);
  public static String DRILLBIT_DIRECT_MEM = append(DOY_DRILLBIT_PARENT, "max-direct-memory");
  public static String DRILLBIT_CODE_CACHE = append(DOY_DRILLBIT_PARENT, "code-cache");
  public static String DRILLBIT_LOG_GC = append(DOY_DRILLBIT_PARENT, "log-gc");
  public static String DRILLBIT_PREFIX_CLASSPATH = append( DOY_DRILLBIT_PARENT, "prefix-class-path");
  public static String DRILLBIT_EXTN_CLASSPATH = append( DOY_DRILLBIT_PARENT, "extn-class-path");
  public static String DRILLBIT_CLASSPATH = append(DOY_DRILLBIT_PARENT, "class-path");
  public static String DRILLBIT_MAX_RETRIES = append(DOY_DRILLBIT_PARENT, "max-retries");
  public static String DRILLBIT_DEBUG_LAUNCH = append(DOY_DRILLBIT_PARENT, "debug-launch");
  public static String DRILLBIT_HTTP_PORT = Verifier.nondetString();
  public static String DISABLE_YARN_LOGS = append(DOY_DRILLBIT_PARENT, "disable-yarn-logs");
  public static String DRILLBIT_USER_PORT = Verifier.nondetString();
  public static String DRILLBIT_BIT_PORT = Verifier.nondetString();
  public static String DRILLBIT_USE_HTTPS = Verifier.nondetString();
  public static String DRILLBIT_MAX_EXTRA_NODES = append(DOY_DRILLBIT_PARENT, "max-extra-nodes");
  public static String DRILLBIT_REQUEST_TIMEOUT_SEC = append(DOY_DRILLBIT_PARENT, "request-timeout-secs");

  public static String ZK_CONNECT = Verifier.nondetString();
  public static String ZK_ROOT = Verifier.nondetString();
  public static String ZK_FAILURE_TIMEOUT_MS = Verifier.nondetString();
  public static String ZK_RETRY_COUNT = Verifier.nondetString();
  public static String ZK_RETRY_DELAY_MS = Verifier.nondetString();

  // Names selected to be parallel to Drillbit HTTP config.

  public static String HTTP_ENABLED = append(HTTP_PARENT, "enabled");
  public static String HTTP_ENABLE_SSL = append(HTTP_PARENT, "ssl-enabled");
  public static String HTTP_PORT = append(HTTP_PARENT, "port");
  public static String HTTP_AUTH_TYPE = append(HTTP_PARENT, "auth-type");
  public static String HTTP_REST_KEY = append(HTTP_PARENT, "rest-key");
  public static String HTTP_SESSION_MAX_IDLE_SECS = append(HTTP_PARENT, "session-max-idle-secs");
  public static String HTTP_DOCS_LINK = append(HTTP_PARENT, "docs-link");
  public static String HTTP_REFRESH_SECS = append(HTTP_PARENT, "refresh-secs");
  public static String HTTP_USER_NAME = append(HTTP_PARENT, "user-name");
  public static String HTTP_PASSWORD = append(HTTP_PARENT, "password");

  public static String AUTH_TYPE_NONE = "none";
  public static String AUTH_TYPE_DRILL = "drill";
  public static String AUTH_TYPE_SIMPLE = "simple";

  public static String CLIENT_POLL_SEC = append(CLIENT_PARENT, "poll-sec");
  public static String CLIENT_START_WAIT_SEC = append(CLIENT_PARENT, "start-wait-sec");
  public static String CLIENT_STOP_WAIT_SEC = append(CLIENT_PARENT, "stop-wait-sec");

  public static String CLUSTERS = append(DRILL_ON_YARN_PARENT, "cluster");

  /**
   * Name of the subdirectory of the container directory that will hold
   * localized Drill distribution files. This name must be consistent between AM
   * launch request and AM launch, and between Drillbit launch request and
   * Drillbit launch. This name is fixed; there is no reason for the user to
   * change it as it is visible only in the YARN container environment.
   */

  public static String LOCAL_DIR_NAME = "drill";

  // Environment variables used to pass information from the Drill-on-YARN
  // Client to the AM, or from the AM to the Drillbit launch script.

  public static String APP_ID_ENV_VAR = "DRILL_AM_APP_ID";
  public static String DRILL_ARCHIVE_ENV_VAR = "DRILL_ARCHIVE";
  public static String SITE_ARCHIVE_ENV_VAR = "SITE_ARCHIVE";
  public static String DRILL_HOME_ENV_VAR = "DRILL_HOME";
  public static String DRILL_SITE_ENV_VAR = "DRILL_CONF_DIR";
  public static String AM_HEAP_ENV_VAR = "DRILL_AM_HEAP";
  public static String AM_JAVA_OPTS_ENV_VAR = "DRILL_AM_JAVA_OPTS";
  public static String DRILL_CLASSPATH_ENV_VAR = "DRILL_CLASSPATH";
  public static String DRILL_CLASSPATH_PREFIX_ENV_VAR = "DRILL_CLASSPATH_PREFIX";
  public static String DOY_LIBPATH_ENV_VAR = "DOY_JAVA_LIB_PATH";
  public static String DRILL_DEBUG_ENV_VAR = "DRILL_DEBUG";

  /**
   * Special value for the DRILL_DIR_NAME parameter to indicate to use the base
   * name of the archive as the Drill home path.
   */

  private static Object BASE_NAME_MARKER = "<base>";

  /**
   * The name of the Drill site archive stored in dfs. Since the archive is
   * created by the client as a temp file, it's local name has no meaning; we
   * use this standard name on dfs.
   */

  public static String SITE_ARCHIVE_NAME = "site.tar.gz";

  private File drillSite = null;
  private File drillHome = null;
  /** ARG-V: suitable */
 public static String append(String parent, String key) {
    assert parent != null && key != null; //inline assert generated by ARG-V
	  return parent + key;
  }


  public static String keys[] = {
    // drill.yarn

    APP_NAME,
    CLUSTER_ID,

    // drill.yarn.dfs

    DFS_CONNECTION,
    DFS_APP_DIR,

    // drill.yarn.hadoop

    HADOOP_HOME,
    HADOOP_CLASSPATH,
    HBASE_CLASSPATH,

    // drill.yarn.yarn

    YARN_QUEUE,
    YARN_PRIORITY,

    // drill.yarn.drill-install

    DRILL_ARCHIVE_PATH,
    DRILL_DIR_NAME,
    LOCALIZE_DRILL,
    CONF_AS_SITE,
    DRILL_HOME,
    DRILL_ARCHIVE_KEY,
    SITE_ARCHIVE_KEY,
    JAVA_LIB_PATH,

    // drill.yarn.client

    CLIENT_POLL_SEC,
    CLIENT_START_WAIT_SEC,
    CLIENT_STOP_WAIT_SEC,

    // drill.yarn.am

    AM_MEMORY,
    AM_VCORES,
    AM_DISKS,
    AM_NODE_LABEL_EXPR,
    AM_VM_ARGS,
    AM_HEAP,
    AM_POLL_PERIOD_MS,
    AM_TICK_PERIOD_MS,
    AM_PREFIX_CLASSPATH,
    AM_CLASSPATH,
    AM_DEBUG_LAUNCH,
    AM_ENABLE_AUTO_SHUTDOWN,

    // drill.yarn.zk

    ZK_CONNECT,
    ZK_ROOT,
    ZK_RETRY_COUNT,
    ZK_RETRY_DELAY_MS,
    ZK_FAILURE_TIMEOUT_MS,

    // drill.yarn.drillbit

    DRILLBIT_MEMORY,
    DRILLBIT_VCORES,
    DRILLBIT_DISKS,
    DRILLBIT_VM_ARGS,
    DRILLBIT_HEAP,
    DRILLBIT_DIRECT_MEM,
    DRILLBIT_CODE_CACHE,
    DRILLBIT_PREFIX_CLASSPATH,
    DRILLBIT_EXTN_CLASSPATH,
    DRILLBIT_CLASSPATH,
    DRILLBIT_MAX_RETRIES,
    DRILLBIT_DEBUG_LAUNCH,
    DRILLBIT_MAX_EXTRA_NODES,
    DRILLBIT_REQUEST_TIMEOUT_SEC,
    DISABLE_YARN_LOGS,
    DRILLBIT_HTTP_PORT,
    DRILLBIT_USER_PORT,
    DRILLBIT_BIT_PORT,
    DRILLBIT_USE_HTTPS,

    // drill.yarn.http

    HTTP_ENABLED,
    HTTP_ENABLE_SSL,
    HTTP_PORT,
    HTTP_AUTH_TYPE,
    HTTP_SESSION_MAX_IDLE_SECS,
    HTTP_DOCS_LINK,
    HTTP_REFRESH_SECS,
    // Do not include AM_REST_KEY: it is supposed to be secret.
    // Same is true of HTTP_USER_NAME and HTTP_PASSWORD
  };

  public static String envVars[] = {
      APP_ID_ENV_VAR,
      DRILL_HOME_ENV_VAR,
      DRILL_SITE_ENV_VAR,
      AM_HEAP_ENV_VAR,
      AM_JAVA_OPTS_ENV_VAR,
      DRILL_CLASSPATH_PREFIX_ENV_VAR,
      DRILL_CLASSPATH_ENV_VAR,
      DRILL_ARCHIVE_ENV_VAR,
      SITE_ARCHIVE_ENV_VAR,
      DRILL_DEBUG_ENV_VAR
  };


  private static String suffixes[] = { ".tar.gz", ".tgz", ".zip" };

  /** ARG-V: suitable */
 public static String findSuffix(String baseName) {
    baseName = baseName.toLowerCase();
    assert !baseName.contains("A"); //inline assert generated by ARG-V
	return baseName;
  }

  /**
   * Get the location of Drill home on a remote machine, relative to the
   * container working directory. Used when constructing a launch context.
   * Assumes either the absolute path from the config file, or a constructed
   * path to the localized Drill on the remote node. YARN examples use "./foo"
   * to refer to container resources. But, since we cannot be sure when such a
   * path is evaluated, we explicitly use YARN's PWD environment variable to get
   * the absolute path.
   *
   * @return the remote path, with the "$PWD" environment variable.
   * @throws DoyConfigException
   */

  /** ARG-V: suitable */
 public String getRemoteDrillHome() throws Exception {
    // If the application is not localized, then the user can tell us the remote
    // path in the config file. Otherwise, we assume that the remote path is the
    // same as the local path.

    

    // The application is localized. Work out the location within the container
    // directory. The path starts with the "key" we specify when uploading the
    // Drill archive; YARN expands the archive into a folder of that name.

    String drillHome = "a" + Verifier.nondetString();

    String home = Verifier.nondetString();
    if (home.equals(BASE_NAME_MARKER)) {
      String drillArchivePath = drillHome;
      if (drillArchivePath == null) {
        throw new Exception();
      }
      File localArchiveFile = new File(drillArchivePath);
      home = localArchiveFile.getName();
      String suffix = findSuffix(home);
      if (suffix == null) {
        throw new Exception();
      }
      drillHome += "/" + home.substring(0, home.length() - suffix.length());
    } else {
      drillHome += "/" + home + "z";
    }
    assert drillHome.matches("a.*/.*"); //inline assert generated by ARG-V - should also mean always has a parent if not starting with slash,
    // may not be a parent if home substring = ""
    this.drillHome = new File(drillHome);
	  return drillHome;
  }


  /**
   * Return the app ID file to use for this client run. The file is in the
   * directory that holds the site dir (if a site dir is used), else the
   * directory that holds Drill home (otherwise.) Not that the file does NOT go
   * into the site dir or Drill home as we upload these directories (via
   * archives) to DFS so we don't want to change them by adding a file.
   * <p>
   * It turns out that Drill allows two distinct clusters to share the same ZK
   * root and/or cluster ID (just not the same combination), so the file name
   * contains both parts.
   *
   * @return local app id file
   */

  /** ARG-V: suitable */
 public File getLocalAppIdFile() {
    String rootDir = Verifier.nondetString();
    String clusterId = Verifier.nondetString();
    String key = rootDir + "-" + clusterId;
    String appIdFileName = key + ".appid";
    File appIdDir = null;
    if (hasSiteDir()) {
      appIdDir = drillSite.getParentFile();
    } else {
      appIdDir = drillHome.getParentFile();
    }
    assert appIdDir != null; //inline assert generated by ARG-V
	return new File(appIdDir, appIdFileName);
  }

  /** ARG-V: suitable */
 public boolean hasSiteDir() {
	  return drillSite != null;
  }



/** This main was generated by ARG-V */

public static void main(String[] args) throws Exception {
	Main instance = new Main();
	String remoteDrillHome = instance.getRemoteDrillHome();
	File localAppIdFile = instance.getLocalAppIdFile();
  assert localAppIdFile.getParent() != null;
  assert AM_ENABLE_AUTO_SHUTDOWN.contains("drill.yarn");
}
}
