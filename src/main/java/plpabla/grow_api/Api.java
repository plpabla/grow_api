package plpabla.grow_api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

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
                Instant instant = Instant.ofEpochSecond(rs.getInt("timestamp"));
                String timestamp_str = instant.toString();

                MeasurementDTO m = new MeasurementDTO(
                        rs.getInt("id"),
                        rs.getString("board_name"),
                        rs.getString("uid"),
                        timestamp_str,
                        rs.getDouble("temperature"),
                        rs.getDouble("humidity"),
                        rs.getDouble("pressure"),
                        rs.getDouble("luminance"),
                        rs.getDouble("moisture_a"),
                        rs.getDouble("moisture_b"),
                        rs.getDouble("moisture_c"),
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
    public ResponseEntity<Integer> addMeasurement(@RequestBody MeasGrow meas,
                                                  @RequestHeader("authorization") String authorization)
    {
        // Do not do it!! Especially in internet facing app :D
        // todo: move out secret from the source code
        if(!authorization.equals("Basic cGFibG86cGFibG9wYXNzd29yZA=="))
        {
            return ResponseEntity.badRequest().build();
        }
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:db.sqlite");

            String query = "INSERT INTO measurement (board_name, uid, timestamp, temperature, " +
                    "humidity, pressure, luminance, moisture_a, moisture_b, moisture_c, voltage) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, meas.nickname);
            ps.setString(2, meas.uid);
            ps.setInt(3, (int)(meas.timestamp.toEpochMilli()/1000));
            ps.setDouble(4, meas.getReadings().temperature);
            ps.setDouble(5, meas.getReadings().humidity);
            ps.setDouble(6, meas.getReadings().pressure);
            ps.setDouble(7, meas.getReadings().luminance);
            ps.setDouble(8,meas.getReadings().moisture_a);
            ps.setDouble(9,meas.getReadings().moisture_b);
            ps.setDouble(10,meas.getReadings().moisture_c);
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

}
