package com.icap.config;

import com.icap.primes.factory.PrimeServiceFactory;
import com.icap.primes.factory.PrimeType;
import com.icap.primes.model.IPrimeService;
import com.icap.primes.model.PrimesModel;
import com.icap.primes.model.DefaultPrimesModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public IPrimeService getPrimeService(){
        return PrimeServiceFactory.getService(PrimeType.FUNCTIONAL);
    }

    @Bean
    public PrimesModel getPrimesModel(IPrimeService primeService){
        return new DefaultPrimesModel(primeService);
    }
}
