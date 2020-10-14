package com.anand.Application;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Corona {
    @Id
    @Column(name = "id",updatable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String combinedKey;

    Long active;
    Long activeChanges;
    Long recovered;
    Long recoveredChanges;
    Long confirmed;

    Long confirmedChanges;
    LocalDateTime lastUpdate;


}
