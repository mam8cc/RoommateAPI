package com.roommateAPI.utility;

import static java.util.UUID.randomUUID;

public class UniqueIdentifierGenerator {
    public String generateId() {
        return randomUUID().toString();
    }
}
