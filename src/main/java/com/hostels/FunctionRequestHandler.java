
package com.hostels;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.hostels.beans.Users;
import com.hostels.services.UsersService;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Map;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    @Inject
    JsonMapper objectMapper;
    @Inject
    private UsersService usersService;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            Map<String, String> jsonObject = objectMapper.readValue(input.getBody(), Map.class);
            Users user = usersService.save(
                Users
                    .builder()
                    .email(jsonObject.get("email"))
                    .username(jsonObject.get("username"))
                    .build()
            );
            response.setStatusCode(200);
            response.setBody(user.toString());
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}