package com.goldsign.acc.frame.util;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Description:
 *
 * @author: zhongziqi
 * @Date: 2019-09-03
 * @Time: 14:21
 */
public class DataSourceUtil extends AbstractRoutingDataSource {
    static org.apache.log4j.Logger logger = Logger.getLogger(DataSourceUtil.class);

    private static ThreadLocal<String> DATASOURCE_KEY = new ThreadLocal<String>();

    @Override
    protected Object determineCurrentLookupKey() {
        String key = DATASOURCE_KEY.get();
        logger.info("thread:" + Thread.currentThread().getName() + ",getDataSourceKety:" + key);

        return key;
    }

    public static void setDatasourceKey(String datasourceKey) {
        DATASOURCE_KEY.set(datasourceKey);
        logger.info("thread:" + Thread.currentThread().getName() + ",setDatasourceKey:" + datasourceKey);

    }

    public static void clearDatasourceKey() {
        logger.info("thread:" + Thread.currentThread().getName() + ",clearDatasourceKey:" + DATASOURCE_KEY.get());
        DATASOURCE_KEY.remove();
    }
}
