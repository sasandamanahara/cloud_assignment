import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

public class ACBrandRatingMapper extends Mapper<LongWritable, Text, Text, Text> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // Skip header
        if (key.get() == 0 && value.toString().contains("name")) return;

        String[] fields = value.toString().split(",");
        if (fields.length < 9) return;

        try {
            String name = fields[1];
            String brand = name.split(" ")[0];
            String rating = fields[6];
            String noOfRatings = fields[7].replaceAll(",", "");

            context.write(new Text(brand), new Text(rating + "," + noOfRatings));
        } catch (Exception e) {
            // skip malformed lines
        }
    }
}
