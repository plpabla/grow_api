package plpabla.grow_api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
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

        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from measurement");
            while(rs.next())
            {
                // todo: rs.getInt("timestamp") is Int and DTO is String, so I have to add conversion
                MeasurementDTO m = new MeasurementDTO(
                        rs.getInt("id"),
                        rs.getString("board_name"),
                        rs.getString("uid"),
                        "timestamp placeholder",
                        rs.getDouble("temperature"),
                        rs.getDouble("humidity"),
                        rs.getDouble("pressure"),
                        rs.getInt("luminance"),
                        rs.getInt("moisture_a"),
                        rs.getInt("moisture_b"),
                        rs.getInt("moisture_c"),
                        rs.getDouble("voltage"));
                meas.add(m);
            }
        }
        catch(SQLException e)
        {
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

        return meas;
    }

    @PostMapping(value =  "/measurement",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addMeasurement(@RequestBody MeasGrow meas)
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
//            Statement statement = connection.createStatement();
//            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            String query = "INSERT INTO measurement (board_name, uid, timestamp, temperature, " +
                    "humidity, pressure, luminance, moisture_a, moisture_b, moisture_c, voltage) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, meas.nickname);
            ps.setString(2, meas.uid);
            ps.setInt(3, 0);
            ps.setDouble(4, meas.getReadings().temperature);
            ps.setDouble(5, meas.getReadings().humidity);
            ps.setDouble(6, meas.getReadings().pressure);
            ps.setDouble(7, meas.getReadings().light);
            ps.setInt(8,meas.getReadings().moisture_1);
            ps.setInt(9,meas.getReadings().moisture_2);
            ps.setInt(10,meas.getReadings().moisture_3);
            ps.setDouble(11,meas.getReadings().voltage);
            ps.executeUpdate();
        }
        catch(SQLException e)
        {
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

        return ResponseEntity.ok(0);
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
