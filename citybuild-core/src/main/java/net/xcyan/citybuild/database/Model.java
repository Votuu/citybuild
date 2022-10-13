package net.xcyan.citybuild.database;

public interface Model<T> {

    T save();

    void delete();

    default String toJson() {
        return MongoDB.toJson(this);
    }
}
