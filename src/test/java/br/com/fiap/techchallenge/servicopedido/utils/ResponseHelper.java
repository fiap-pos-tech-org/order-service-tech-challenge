package br.com.fiap.techchallenge.servicopedido.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.squareup.okhttp.*;

public class ResponseHelper {

    private final JsonMapper jsonMapper;

    public ResponseHelper(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public Response getResponse(Object obj, int httpStatus) throws JsonProcessingException {
        var jsonBody = jsonMapper.writeValueAsString(obj);
        var responseBody = ResponseBody.create(MediaType.parse("application/json"), jsonBody);
        return new Response.Builder()
                .request(new Request.Builder().url("http://fakelocalhost:8090/test").build())
                .protocol(Protocol.HTTP_1_1)
                .body(responseBody)
                .code(httpStatus)
                .build();
    }

}
