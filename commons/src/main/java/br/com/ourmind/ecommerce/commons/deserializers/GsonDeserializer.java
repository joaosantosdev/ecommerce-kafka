package br.com.ourmind.ecommerce.commons.deserializers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {
    private final Gson gson = new GsonBuilder().create();
    public final static String TYPE_CONFIG = "br.com.ourmind.ecommerce.type_config";
    private Class<T> type;

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        var typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class não encontrada.");
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return this.gson.fromJson(new String(bytes), this.type);
    }
}
