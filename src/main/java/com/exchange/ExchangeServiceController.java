package com.exchange;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ExchangeServiceController {

    @Autowired
    private ReadValueConfig readValueConfig;

    @GetMapping(value = "readProperty")
    public ResponseEntity readProperty() {
        ReadValueConfigDto dto = new ReadValueConfigDto();
        dto.setKey1(readValueConfig.getKey1());
        dto.setKey2(readValueConfig.getKey2());
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @Autowired
    private Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {
        BigDecimal b = new BigDecimal(65);
        CurrencyExchange currencyExchange = new CurrencyExchange(1001L, "USD", "INR", b);
        if (currencyExchange == null) {
            throw new RuntimeException
                    ("Unable to Find data for " + from + " to " + to);
        }
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;

    }

}
