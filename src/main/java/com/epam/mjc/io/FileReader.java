package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public class FileReader {
    private static final Logger logger = Logger.getLogger(FileReader.class.getName());

    public Profile getDataFromFile(File file) {
        Profile profile = new Profile();
        try (
                java.io.FileReader fileReader = new java.io.FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String dataLine;
            while ((dataLine = bufferedReader.readLine()) != null) {
                KeyValue keyValue = KeyValue.fromString(dataLine);
                if (keyValue != null) {
                    switch (keyValue.getKey()) {
                        case "name":
                            profile.setName(keyValue.getValue());
                            break;
                        case "email":
                            profile.setEmail(keyValue.getValue());
                            break;
                        case "phone":
                            Long phone = Long.parseLong(keyValue.getValue());
                            profile.setPhone(phone);
                            break;
                        case "age":
                            Integer age = Integer.parseInt(keyValue.getValue());
                            profile.setAge(age);
                            break;
                        default:
                            logger.info("Unknown parameter: " + keyValue.getKey());
                    }
                }
            }
        } catch (IOException e) {
            logger.info(String.format("Error on file Profile.txt processing: %s", e.getMessage()));
        }

        return profile;
    }
}

class KeyValue {
    private static final char KEY_VALUE_SEPARATOR = ':';

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static KeyValue fromString(String value) {
        int keyValueSeparatorPos = value.indexOf(KEY_VALUE_SEPARATOR);
        if (keyValueSeparatorPos != -1) {
            KeyValue keyValue = new KeyValue();

            keyValue.key = value.substring(0, keyValueSeparatorPos).trim().toLowerCase();
            keyValue.value = value.substring(keyValueSeparatorPos + 1).trim();

            return keyValue;
        } else {
            return null;
        }
    }
}

