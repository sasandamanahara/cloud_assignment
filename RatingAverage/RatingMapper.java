package RatingAverage;
import java.io.IOException;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RatingMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");

        try {
            // Avoid header and check for enough columns
            if (fields.length >= 5 && !fields[3].equalsIgnoreCase("rating")) {
                String category = fields[4].trim();
                float rating = Float.parseFloat(fields[3].trim());
                context.write(new Text(category), new FloatWritable(rating));
            }
        } catch (Exception e) {
            // Skip malformed lines
        }
    }
}
