package net.xcyan.citybuild.slums.region;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import net.xcyan.citybuild.database.Model;
import net.xcyan.citybuild.database.MongoDB;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity("region")
public class Region implements Model<Region> {

    @Id
    private int regionId;

    private List<String> chunks;

    private UUID ownerId;
    private boolean claimed;

    private String center;
    private String spawn;

    public Region(int regionId) {
        this.regionId = regionId;

        this.chunks = new ArrayList<>();

        this.ownerId = UUID.fromString("9bbf53d5-b2a2-4d3f-b1a0-8e2d65fd2d94");
        this.claimed = false;

        this.center = MongoDB.serializeLocation(new Location(Bukkit.getWorld("world"), 0,0,0));
        this.spawn = MongoDB.serializeLocation(new Location(Bukkit.getWorld("world"), 0,0,0));
    }

    public int regionId() {
        return regionId;
    }

    public void chunks(List<String> chunks) {
        this.chunks = chunks;
    }
    public List<String> chunks() {
        return chunks;
    }

    public void chunks(ArrayList<Chunk> chunks) {
        List<String> temp = new ArrayList<>();
        for(Chunk c : chunks) {
            temp.add(MongoDB.serializeChunk(c));
        }
        this.chunks = temp;
    }
    public List<Chunk> originalChunks() {
        List<Chunk> temp = new ArrayList<>();
        for(String s : chunks) {
            temp.add(MongoDB.deserializeChunk(s));
        }
        return temp;
    }

    public UUID ownerId() {
        return ownerId;
    }
    public void ownerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public boolean claimed() {
        return claimed;
    }
    public void claimed(boolean claimed) {
        this.claimed = claimed;
    }

    public Location center() {
        return MongoDB.deserializeLocation(center);
    }
    public void center(Location center) {
        this.center = MongoDB.serializeLocation(center);
    }

    public Location spawn() {
        return MongoDB.deserializeLocation(spawn);
    }
    public void spawn(Location spawn) {
        this.spawn = MongoDB.serializeLocation(spawn);
    }

    @Override
    public Region save() {
        return MongoDB.save(this);
    }

    @Override
    public void delete() {
        MongoDB.delete(this);
    }
}
