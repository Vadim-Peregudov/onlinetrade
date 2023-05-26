package my.shop.onlinetrade.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "features")
public class Features {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_filterable", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isFilterable;

    public Features() {
    }

    public Features(String name, Boolean isFilterable) {
        this.name = name;
        this.isFilterable = isFilterable;
    }

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

    public Boolean getFilterable() {
        return isFilterable;
    }

    public void setFilterable(Boolean filterable) {
        isFilterable = filterable;
    }

    @Override
    public String toString() {
        return "Features{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFilterable=" + isFilterable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Features features = (Features) o;
        return Objects.equals(id, features.id) && Objects.equals(name, features.name) && Objects.equals(isFilterable, features.isFilterable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isFilterable);
    }
}
