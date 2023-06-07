package model.impl;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirlinesCompany {
    long id;
    String name;
    String logoImgUrl;
    double priceMulti;
}
