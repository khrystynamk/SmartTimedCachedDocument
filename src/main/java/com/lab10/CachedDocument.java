package com.lab10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

public class CachedDocument extends SmartDocument {
    public CachedDocument(String gcsPath) {
        super(gcsPath);
    }

    public String parse() {
        Connection connection = null;
        String result = null;
        Statement statement = null;

        try
        {
          // create a database connection
          connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
          statement = connection.createStatement();

          ResultSet rs = statement.executeQuery("select * from parse_results where gcsPath = " + gcsPath);
          while(rs.next())
          {
            result = rs.getString("result");
            System.out.println("Got cached result");
          }
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

        if (result.isEmpty()) {
            result = super.parse();

            System.out.println("Got non-cached result");
            try {
                statement.executeUpdate(MessageFormat.format("insert into parse_results values({0}, {1})", gcsPath, result));
            } catch(SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }   
        
        return result;
    }
}