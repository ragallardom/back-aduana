package cl.duoc.sistema_aduanero.javafx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * Utilitario sencillo para convertir de JSON a objetos y viceversa.
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convierte un JSON que representa una lista de elementos T a List<T>.
     * Ejemplo:
     *   String json = "[{...},{...}]";
     *   List<SolicitudAduana> lista = JsonUtils.fromJsonList(json, SolicitudAduana.class);
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, new TypeReference<List<T>>() {});
    }

    /**
     * Convierte un JSON a un solo objeto de tipo T.
     * Ejemplo:
     *   String json = "{\"id\":1,\"nombreSolicitante\":\"Juan\"}";
     *   SolicitudAduana s = JsonUtils.fromJson(json, SolicitudAduana.class);
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    /**
     * Convierte un objeto Java a su String JSON.
     */
    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
