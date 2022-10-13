package net.xcyan.citybuild.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import org.bson.UuidRepresentation;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class MongoDB {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static Datastore datastore;

    public MongoDB(String connectionString, String authSource) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .retryWrites(true)
                .retryReads(true)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        datastore = Morphia.createDatastore(mongoClient, authSource);
    }

    public static <T> T save(T entity) {
        return datastore.save(entity);
    }

    public static <T> T findById(Class<T> entityClass, String id) {
        return datastore.find(entityClass).filter(Filters.eq("_id", id)).first();
    }

    public static <T> void delete(T entity) {
        datastore.delete(entity);
    }

    public static <T> Query<T> createQuery(Class<T> entity) {
        return datastore.find(entity);
    }

    public static String toJson(Object object) {
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(Class<T> object, String json) {
        try {
            return jsonMapper.readValue(json, object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[][] serializeInventory(ItemStack[] inventory) {
        byte[][] serializedInventory = new byte[inventory.length][];
        int i = 0;
        for (ItemStack itemStack : inventory) {
            if (itemStack != null) {
                serializedInventory[i] = itemStack.serializeAsBytes();
            }
            i++;
        }
        return serializedInventory;
    }

    public static ItemStack[] deserializeInventory(byte[][] serializedInventory) {
        ItemStack[] inventory = new ItemStack[serializedInventory.length];
        int i = 0;
        for (byte[] bytes : serializedInventory) {
            if (bytes != null) {
                inventory[i] = ItemStack.deserializeBytes(bytes);
            }
            i++;
        }
        return inventory;
    }

    public static String serializeLocation(Location location) {
        return MongoDB.toJson(location);
    }

    public static Location deserializeLocation(String json) {
        return MongoDB.fromJson(Location.class, json);
    }

    public static String serializeChunk(Chunk chunk) {
        return MongoDB.toJson(chunk);
    }

    public static Chunk deserializeChunk(String json) {
        return MongoDB.fromJson(Chunk.class, json);
    }

    public static <T> boolean exists(Class<T> entityClass, Object id) {
        return datastore.find(entityClass).filter(Filters.eq("_id", id)).count() >= 1;
    }
}
