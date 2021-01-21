  package com.eunice.musicapp.util

import java.sql.Connection
import java.sql.DriverManager

object ConnectionUtil {

  var conn: Connection = null;

  /** utility for retrieving connection, with hardcorded credentials
    *
    * @return Connection
    */
  def getConnection(): Connection = {

    if (conn == null || conn.isClosed()) {
      classOf[org.postgresql.Driver].newInstance() // manually load the Driver
    
      //hardcoding, bad practice
      conn = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/eunicelee?currentSchema=musicapp",
        "eunicelee",
<<<<<<< HEAD
        "eunice1245" //changed for security reason
=======
        "password" //changed for security reason
>>>>>>> 68f445bf446237761ed1aefcaf99e7ce72fb6372
      )
    }
    conn
  }

}
