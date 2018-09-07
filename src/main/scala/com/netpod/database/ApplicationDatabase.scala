package com.netpod.database

import com.typesafe.config.Config
import org.apache.commons.dbcp.BasicDataSource
import slick.util.AsyncExecutor
import slick.driver.MySQLDriver.profile.api._

object ApplicationDatabase {
  def config(configuration: Config): Database = {
    //    val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")

    val ds = new BasicDataSource
    ds.setDriverClassName(configuration.getString("database.driverClassName"))
    ds.setUsername(configuration.getString("database.username"))
    ds.setPassword(configuration.getString("database.password"))
    ds.setMaxActive(configuration.getInt("database.maxActive"))
    ds.setMaxIdle(configuration.getInt("database.maxIdle"))
    ds.setInitialSize(configuration.getInt("database.initialSize"))
    ds.setValidationQuery(configuration.getString("database.validationQuery"))
    ds.setUrl(configuration.getString("database.jdbcUrl"))
    ds.setPoolPreparedStatements(true)

    // test the data source validity
    ds.getConnection().close()

    // get the Slick database that uses the pooled connection
    Database.forDataSource(ds, None, executor = AsyncExecutor("DB Thread", numThreads=50, queueSize=1000))


  }

}
