package com.mdlsf.springdiccionariodecalle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDiccionarioDeCalleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDiccionarioDeCalleApplication.class, args);
    }
// TODO add api specs
    // todo review endpoints
    // todo review database used
    // todo can I use dynamodb or aurora?
    // todo should I rewrite this in python?
    // todo find a ci/cd that is free
    // todo add functional tests
    // todo deploy where? aws? then I'd need terraform
}
