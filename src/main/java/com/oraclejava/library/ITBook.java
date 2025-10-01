package com.oraclejava.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ITBook {
    private int id;
    private String title;
    private int price;
}
