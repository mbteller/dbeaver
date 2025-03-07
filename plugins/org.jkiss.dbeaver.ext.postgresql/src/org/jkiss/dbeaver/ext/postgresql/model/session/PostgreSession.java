/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.postgresql.model.session;

import org.jkiss.dbeaver.model.admin.sessions.AbstractServerSession;
import org.jkiss.dbeaver.model.impl.jdbc.JDBCUtils;
import org.jkiss.dbeaver.model.meta.Property;
import org.jkiss.utils.CommonUtils;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Objects;

/**
 * PostgreSQL session
 */
public class PostgreSession extends AbstractServerSession {
    private static final String CAT_CLIENT = "Client";
    private static final String CAT_TIMING = "Timings";

    private final int pid;
    private String user;
    private String clientHost;
    private String clientPort;
    private final String db;
    private String query;
    private Date backendStart;
    private Date xactStart;
    private Date queryStart;
    private Date stateChange;
    private String state;
    private String appName;

    public PostgreSession(ResultSet dbResult) {
        this.pid = JDBCUtils.safeGetInt(dbResult, "pid");
        this.user = JDBCUtils.safeGetStringTrimmed(dbResult, "usename");
        this.clientHost = JDBCUtils.safeGetStringTrimmed(dbResult, "client_hostname");
        if (CommonUtils.isEmpty(this.clientHost)) {
            this.clientHost = JDBCUtils.safeGetStringTrimmed(dbResult, "client_addr");
        }
        this.clientPort = JDBCUtils.safeGetStringTrimmed(dbResult, "client_port");
        this.db = JDBCUtils.safeGetStringTrimmed(dbResult, "datname");
        this.query = JDBCUtils.safeGetStringTrimmed(dbResult, "query");

        this.backendStart = JDBCUtils.safeGetTimestamp(dbResult, "backend_start");
        this.xactStart = JDBCUtils.safeGetTimestamp(dbResult, "xact_start");
        this.queryStart = JDBCUtils.safeGetTimestamp(dbResult, "query_start");
        this.stateChange = JDBCUtils.safeGetTimestamp(dbResult, "state_change");

        this.state = JDBCUtils.safeGetStringTrimmed(dbResult, "state");
        this.appName = JDBCUtils.safeGetStringTrimmed(dbResult, "application_name");
    }

    @Property(viewable = true, order = 1)
    public int getPid()
    {
        return pid;
    }

    @Property(viewable = true, category = CAT_CLIENT, order = 2)
    public String getUser()
    {
        return user;
    }

    @Property(viewable = false, category = CAT_CLIENT, order = 3)
    public String getClientHost()
    {
        return clientHost;
    }

    @Property(viewable = false, category = CAT_CLIENT, order = 4)
    public String getClientPort() {
        return clientPort;
    }

    @Property(viewable = true, order = 5)
    public String getDb()
    {
        return db;
    }

    @Property(viewable = true, category = CAT_CLIENT, order = 6)
    public String getAppName() {
        return appName;
    }

    @Property(viewable = false, category = CAT_TIMING, order = 30)
    public Date getBackendStart() {
        return backendStart;
    }

    @Property(viewable = false, category = CAT_TIMING, order = 31)
    public Date getXactStart() {
        return xactStart;
    }

    @Property(viewable = true, category = CAT_TIMING, order = 32)
    public Date getQueryStart() {
        return queryStart;
    }

    @Property(viewable = false, category = CAT_TIMING, order = 33)
    public Date getStateChange() {
        return stateChange;
    }

    @Property(viewable = true, order = 7)
    public String getState()
    {
        return state;
    }

    @Property(viewable = true, order = 100)
    public String getBriefQuery() {
        if (query != null && query.length() > 500) {
            return CommonUtils.truncateString(query, 500) + " ...";
        } else {
            return query;
        }
    }

    @Override
    public String getActiveQuery()
    {
        return query;
    }

    @Override
    public String getSessionId() {
        return String.valueOf(pid);
    }

    @Override
    public String toString()
    {
        if (!CommonUtils.isEmpty(db)) {
            return pid + "@" + db;
        } else {
            return String.valueOf(pid);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostgreSession that = (PostgreSession) o;
        return pid == that.pid && Objects.equals(db, that.db);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, db);
    }
}
