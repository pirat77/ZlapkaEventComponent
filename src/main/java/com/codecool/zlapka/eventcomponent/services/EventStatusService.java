package com.codecool.zlapka.eventcomponent.services;

import com.codecool.zlapka.eventcomponent.model.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class EventStatusService {

    @Autowired
    private JsonMapper jsonMapper;

    public String statusAfterGetElementsNotFound(String uriPath) {
        return jsonMapper.jsonRepresentation(createErrorToReturn(400, "get",
                "Not found", -1L, uriPath));
    }

    public <T> String statusAfterAdd(String uriPath, Optional<T> addedObject) {
        if (addedObject.isPresent()) return jsonMapper.jsonRepresentation(addedObject.get());
        return jsonMapper.jsonRepresentation(createErrorToReturn(500, "add",
                "Unable to convert and add element", -1L, uriPath));
    }

    public String statusAfterDelete(String uriPath, long rows) {
        if (rows == -1L) {
            return jsonMapper.jsonRepresentation(
                    createErrorToReturn(500, "delete",
                            "Improper number of records in database", rows, uriPath));
        }
        return jsonMapper.jsonRepresentation(
                createMessageToReturn(200, "delete", rows, uriPath));
    }

    public String statusAfterReplace(String uriPath, long rows) {
        if (rows == 0L) {
            jsonMapper.jsonRepresentation(
                    createErrorToReturn(400, "replace",
                            "No proper json format", rows, uriPath));
        }
        if (rows == -1L) {
            return jsonMapper.jsonRepresentation(
                    createErrorToReturn(500, "replace",
                            "Unable to replace records", rows, uriPath));
        }
        return jsonMapper.jsonRepresentation(
                createMessageToReturn(200, "replace", rows, uriPath));
    }

    private ReturnMessage createMessageToReturn(int status, String operation, long numberOfRows, String path) {
        String message = String.format("Operation %s succeed\nRows affected: %d",
                operation.toUpperCase(), numberOfRows);
        return new ReturnMessage(new Date(System.currentTimeMillis()), status, "", message, path);
    }

    private ReturnMessage createErrorToReturn(int status, String operation, String message,
                                              long numberOfRows, String path) {
        String error = String.format("Not able to do: %s\nRows affected: %d",
                operation, numberOfRows);
        return new ReturnMessage(new Date(System.currentTimeMillis()), status, error, message, path);
    }

}
