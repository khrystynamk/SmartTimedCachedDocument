package com.lab10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args)
      {
        Connection connection = null;
        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:parse.db");
          Statement statement = connection.createStatement();
          statement.setQueryTimeout(30);  // set timeout to 30 sec.

          statement.executeUpdate("create table IF NOT EXISTS parse_results (id integer primary key, gcsPath string, result string)");

          // TODO: finish configuring app to upload image to google bucket
          // gcs://<link>;
        //   Document timed = new TimedDocument('');
        //   timed.parse();
        //   Document cachedTimed = new CachedDocument('');
        //   cachedTimed.parse();
        //   cachedTimed.parse();
        }
        catch(SQLException e)
        {
          // if the error message is "out of memory",
          // it probably means no database file is found
          System.err.println(e.getMessage());
        }
        finally
        {
          try
          {
            if(connection != null)
              connection.close();
          }
          catch(SQLException e)
          {
            // connection close failed.
            System.err.println(e.getMessage());
          }
        }
      }
}
