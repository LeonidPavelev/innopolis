package model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Products {

    Long id;

    String name;

    Long price;

    Long quantity;

}
