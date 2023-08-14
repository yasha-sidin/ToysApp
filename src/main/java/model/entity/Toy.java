package model.entity;

import model.Probability;

import javax.persistence.*;

@Entity
@Table(name = "toysapp", schema = "public", catalog = "postgres")
public class Toy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double probability;

    public Toy() {

    }

    public Toy(String name, Probability probability) {
        this.name = name;
        this.probability = probability.getValue();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Probability getProbability() {
        return new Probability(this.probability);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProbability(Probability probability) {
        this.probability = probability.getValue();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Toy{ " + "id: " + getId() + ", name: " + getName() + ", probability: " + getProbability().getValue() + " }";
    }

    public String printToClient() {
        return "Toy: " + getName();
    }

    public String printToOwner() {
        return "Toy's name: " + getName() + "; probability: " + getProbability().getValue();
    }
}
