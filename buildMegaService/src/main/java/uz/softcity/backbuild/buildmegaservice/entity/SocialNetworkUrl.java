package uz.softcity.backbuild.buildmegaservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class SocialNetworkUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    private String url;

    @ManyToOne
    private SocialNetwork socialNetwork;

    public SocialNetworkUrl(String url, SocialNetwork socialNetwork) {
        this.url = url;
        this.socialNetwork = socialNetwork;
    }
}
