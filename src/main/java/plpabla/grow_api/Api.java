package plpabla.grow_api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("")
public class Api {
    @GetMapping("/ping")
    public String ping()
    {
        return "pong.";
    }

    @GetMapping(value = "/measurement",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public List<MeasurementDTO> getMeasurements()
    {
        Connection connection = null;
        List<MeasurementDTO> meas = new ArrayList<>();

        MeasurementDTO m = new MeasurementDTO(0,"b1","2423x3", "0", 30.0,
                69.0, 1020.1, 30, 50, 40, 20, 3.13);

        meas.add(m);
        meas.add(m);
        return meas;
    }


    @GetMapping("/demo")
    public String demo()
    {
        // Soruce: https://github.com/xerial/sqlite-jdbc
        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
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
        return "Done";
    }
}
