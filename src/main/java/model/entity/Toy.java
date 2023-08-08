package model.entity;

import model.Probability;

import javax.persistence.*;

@Entity
@Table(name = "toysapp", schema = "public", catalog = "postgres")
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double probability;

    public Toy(String name, Probability probability) {
        this.name = name;
        this.probability = probability.getProbability();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getProbability() {
        return this.probability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProbability(Probability probability) {
        this.probability = probability.getProbability();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Toy{ " + "id: " + getId() + ", name: " + getName() + ", probability: " + getProbability() + " }";
    }
}
