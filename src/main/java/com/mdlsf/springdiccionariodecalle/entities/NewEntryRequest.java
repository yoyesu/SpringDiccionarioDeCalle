package com.mdlsf.springdiccionariodecalle.entities;

import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewEntryRequest {
    private String term;
    private String def;
    private Set<String> countryUse;
}
