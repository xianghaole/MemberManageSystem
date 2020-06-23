package cn.lger.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BaseEntity {
    @Id
    @Column
    private String name;
    @Column
    private Double value;

    public BaseEntity() {
    }

    public BaseEntity(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
