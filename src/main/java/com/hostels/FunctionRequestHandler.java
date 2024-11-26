
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
import java.util.Objects;

public class FunctionRequestHandler extends MicronautRequestHandler
    <APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    @Inject
    JsonMapper objectMapper;
    @Inject
    UsersService usersService;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            Map<String, String> jsonObject = objectMapper.readValue(input.getBody(), Map.class);
            if("GET".equals(input.getHttpMethod())){
                response.setStatusCode(200);
                response.setBody(usersService.searchUsers(
                    Objects.nonNull(jsonObject.get("email")) ? jsonObject.get("email") : null,
                    Objects.nonNull(jsonObject.get("username")) ? jsonObject.get("username") : null
                ).toString());
                return response;
            }
            if("DELETE".equals(input.getHttpMethod())){
                if (Objects.isNull(jsonObject.get("userId"))) throw new RuntimeException("User id is mandatory.");
                usersService.deleteById(Long.valueOf(jsonObject.get("userId")));
                response.setStatusCode(200);
                response.setBody(String.format("User %s deleted successfully", jsonObject.get("userId")));
                return response;
            }
            if (Objects.isNull(jsonObject.get("email"))) throw new RuntimeException("Email is mandatory.");
            if (Objects.isNull(jsonObject.get("username"))) throw new RuntimeException("Username name is mandatory.");
            Users user = usersService.save(
                Users.builder()
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