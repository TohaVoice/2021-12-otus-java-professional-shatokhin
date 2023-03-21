import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> measurements = new ArrayList<>();
        try (var jsonReader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            JsonStructure jsonFromTheFile = jsonReader.read();
            JsonArray jsonArray = jsonFromTheFile.asJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.getJsonObject(i);
                Measurement measurement = new Measurement(
                    jsonObject.getString("name"),
                    jsonObject.getJsonNumber("value").doubleValue());
                    measurements.add(measurement);
            }
        }
        return measurements;
    }
}
