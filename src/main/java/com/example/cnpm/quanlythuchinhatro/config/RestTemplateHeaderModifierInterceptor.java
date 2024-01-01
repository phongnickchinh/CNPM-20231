//package com.example.cnpm.quanlythuchinhatro.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.lang.NonNull;
//import org.springframework.lang.NonNullApi;
//
//import java.io.IOException;
//
//@Configuration
//public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
//
//    @Override
//    @NonNull
//    public ClientHttpResponse intercept(
//            @NonNull HttpRequest request,
//            @NonNull byte[] body,
//            ClientHttpRequestExecution execution) throws IOException {
//        ClientHttpResponse response = execution.execute(request, body);
//        response.get().add("Foo", "bar");
//        return response;
//    }
//}