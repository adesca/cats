package com.example.catdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    @Autowired
    public CatService(RestTemplateBuilder restTemplate) {
        var whatever  = new RestTemplateBuilder().build();
    }
}
