package com.royenheart.server.atomics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.royenheart.server.databaseopt.DatabaseQuery;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 原子操作，查询用户姓名
 * @author RoyenHeart
 */
public class AtomicQueryName extends AtomicOperations {

    private final Connection con;
    private final String tables;
    private final String accountId;

    public AtomicQueryName(Connection con, String tables, String accountId) {
        this.con = con;
        this.tables = tables;
        this.accountId = accountId;
    }

    /**
     * 只查询单个用户的姓名
     * @return 查询结果数组（只有一个有用）
     * @throws SQLException 数据库请求错误
     */
    public LinkedList<HashMap<String, String>> query() throws SQLException {
        Gson gson = new Gson();
        DatabaseQuery o1 = (DatabaseQuery) OPERATIONS.get("q");
        String r1 = o1.executeSql(con, tables, new LinkedList<String>() {
            {
                this.add("name");
            }
        }, new HashMap<String, String>() {
            {
                this.put("accountId", accountId);
            }
        });
        return gson.fromJson(r1, new TypeToken<LinkedList<HashMap<String, String>>>() {}.getType());
    }
}
