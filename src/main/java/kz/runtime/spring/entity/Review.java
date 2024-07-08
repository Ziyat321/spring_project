package kz.runtime.spring.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @Column(name = "user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "rating")
    private Short rating;
}
