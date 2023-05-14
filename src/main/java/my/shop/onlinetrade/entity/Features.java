package my.shop.onlinetrade.entity;

import jakarta.persistence.*;

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
}
