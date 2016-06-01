package com.icap.service.primes.controllers;


import com.icap.service.primes.model.PrimesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class PrimesController {

    @Autowired
    private PrimesModel primesModel;

    @RequestMapping(value = "/v1/primes",
            method = GET,
            headers = {"content-type=text/html"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void findAllPrimes() throws InterruptedException {
        primesModel.hasFactors();
    }

    //could show all the primes so far from the model, or use a URL callback to notify when the next prime has been found
//    @RequestMapping(value = "/v1/primes/register",
//            method = POST,
//            consumes = "*",
//            produces = "application/json",
//            headers = {"content-type=text/html"})
//    @ResponseStatus(HttpStatus.OK)
//    public void registerCallback(String urlCallback) throws Exception {
//
//        primesModel.register(urlCallback);
//
//    }
}
