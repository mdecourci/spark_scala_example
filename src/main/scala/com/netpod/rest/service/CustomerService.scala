package com.netpod.rest.service

import java.util.UUID

import com.datastax.spark.connector.cql.CassandraConnector
import com.netpod.rest.domain.{Customer, Customers}
import com.typesafe.scalalogging.LazyLogging
//import org.apache.spark.sql.cassandra.CassandraSQLContext
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by michaeldecourci on 30/07/15.
 */

class CustomerService(_sparkConf: SparkConf) extends LazyLogging {

  require(_sparkConf != null)
  val sparkConf = _sparkConf
  val sc = new SparkContext(sparkConf)

  generateTables(sc);

  var allCustomers = Customers.customers;

  def save(customer : Customer) = {
      allCustomers = customer :: allCustomers
  }

  def find (p : Customer => Boolean) : List[Customer] = {
      allCustomers.filter(c => p(c))
  }

  def findAll : List[Customer] = {
    allCustomers
  }

  def find (index : Int) : Customer = {
    allCustomers(index)
  }

  def generateTables(sc: SparkContext): Unit = {
    logger.debug("generateTables() sc={}", sc);

    val conn = CassandraConnector.apply(sc.getConf)
    val session = conn.openSession()
    session.execute("DROP KEYSPACE IF EXISTS customers_api")
    session.execute("CREATE KEYSPACE customers_api WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1}")
    session.execute("CREATE TABLE customers_api.customers (id UUID PRIMARY KEY, firstName TEXT, lastName TEXT)")

    session.execute("INSERT INTO customers_api.customers (id, firstName, lastName) VALUES (" + UUID.randomUUID().toString + ",'Tom', 'Jones')")

//    val cc = new CassandraSQLContext(sc)
//    cc.setKeyspace("customers_api")
//
//
//   val result = cc.sql("SELECT * FROM customers WHERE lastname = 'Jones'")
//   println("** The Answer = " + result.collectAsList())
  }
}
