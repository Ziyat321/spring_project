package kz.runtime.spring.entity;


import jakarta.persistence.*;
import org.hibernate.sql.results.internal.StandardEntityGraphTraversalStateImpl;

import java.util.List;

@Entity
@Table(name = "characteristic")
public class Characteristic {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "characteristic")
    private List<CharacteristicDescription> characteristicDescriptions;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<CharacteristicDescription> getCharacteristicDescriptions() {
        return characteristicDescriptions;
    }

    public void setCharacteristicDescriptions(List<CharacteristicDescription> characteristicDescriptions) {
        this.characteristicDescriptions = characteristicDescriptions;
    }
}
