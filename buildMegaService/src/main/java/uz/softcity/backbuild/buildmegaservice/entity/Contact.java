package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String address;

    @Column(columnDefinition = "text")
    private String addressRu;

    private String phoneNumber;

    private String phoneNumberRu;

    private String email;

    public Contact(String address, String addressRu, String phoneNumber, String phoneNumberRu, String email) {
        this.address = address;
        this.addressRu = addressRu;
        this.phoneNumber = phoneNumber;
        this.phoneNumberRu = phoneNumberRu;
        this.email = email;
    }
}
