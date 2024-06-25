package com.dar.server.data.entities;

import com.dar.server.exceptions.IncorrectDataInput;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@DynamicInsert
@Table(name = "location")
public class LocationEntity {
    @Column
    @NotNull
    private long x; //Максимальное значение поля: 625, Поле не может быть null
    @Column
    @NotNull
    private Long y;//Значение поля должно быть больше -354, Поле не может быть null
    @Column
    @NotNull
    private float z;

    @Setter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator")
    @SequenceGenerator(name = "my_sequence_generator", sequenceName = "my_sequence_name", allocationSize = 1)

    private Long id;

    public LocationEntity(long x, Long y, float z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        //this.id = DBConnection.getDBConnection().newId();
    }

    public LocationEntity() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return x == that.x && Float.compare(z, that.z) == 0 && Objects.equals(y, that.y) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, id);
    }

    public void setX(long x) {
        if (x >= 625L) {
            throw new IncorrectDataInput();
        }
        this.x = x;
    }

    public void setY(Long y) {
        if (y < -354F) {
            throw new IncorrectDataInput();
        }
        this.y = y;

    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
