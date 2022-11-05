package com.bahar.tacos;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Component
@Data
@ConfigurationProperties(prefix = "taco.order")
public class OrderProps {
    @Min(value = 5)
    @Max(value=25)
    private int pageSize = 20;
}
