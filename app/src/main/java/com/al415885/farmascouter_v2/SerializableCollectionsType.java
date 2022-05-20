package com.al415885.farmascouter_v2;

import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.SerializableType;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Class for doing a Collection Serializable
 */
public class SerializableCollectionsType extends SerializableType {

    // Class-specific variables
    private static SerializableCollectionsType singleton;

    /**
     * Class constructor
     */
    public SerializableCollectionsType() {
        super(SqlType.SERIALIZABLE, new Class<?>[0]);
    }

    /**
     * Method that returns the instance
     * @return
     */
    public static SerializableCollectionsType getSingleton() {
        if (singleton == null) {
            singleton = new SerializableCollectionsType();
        }
        return singleton;
    }

    /**
     * Method that checks if the Field is valid
     * @param field: Field
     * @return boolean
     */
    @Override
    public boolean isValidForField(Field field) {
        return Collection.class.isAssignableFrom(field.getType());
    }
}
