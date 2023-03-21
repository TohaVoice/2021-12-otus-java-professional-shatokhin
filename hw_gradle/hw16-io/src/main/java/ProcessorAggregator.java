import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> result = new LinkedHashMap<>();
        for (Measurement measurement : data) {
            Double cValue = result.get(measurement.getName());
            if (result.get(measurement.getName()) != null)
                result.put(measurement.getName(), cValue + measurement.getValue());
            else
                result.put(measurement.getName(), measurement.getValue());
        }
        return result;
    }
}
