package com.luv2code.springboot.thymeleafdemo.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springboot.thymeleafdemo.entity.AccessToken;
import com.luv2code.springboot.thymeleafdemo.entity.CustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.*;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthenticateController {
    @Value("${authentication.url}")
    private String authenticationUrl;

    @Value("${authentication.loginId}")
    private String loginId;

    @Value("${authentication.password}")
    private String password;

    @GetMapping("/authenticate")
    @ResponseBody
    public List<CustomerResponse> authenticate() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.canRead(AccessToken.class,MediaType.APPLICATION_JSON);
        RestClient restClient = RestClient.builder()
                .messageConverters(converters -> converters.add(mappingJackson2HttpMessageConverter))
                .build();

        String uri = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        AccessToken token = null;
        ResponseEntity<String> response = restClient.post()
                .uri(uri)
                .body("{\"login_id\": \"test@sunbasedata.com\",\"password\": \"Test@123\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
        System.out.println(response.getBody());
        ObjectMapper mapper = new ObjectMapper();
        try {
            token = mapper.readValue(response.getBody(),AccessToken.class);
            System.out.println("Access Token - "+token.getToken());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<CustomerResponse> list = null;
        String uri2 = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
        ResponseEntity<String> response2 = restClient.get()
                .uri(uri2)
                .header("Authorization","Bearer "+token.getToken())
                .retrieve()
                .toEntity(String.class);
        try {
            list = mapper.readValue(response2.getBody(), new TypeReference<List<CustomerResponse>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    


}
