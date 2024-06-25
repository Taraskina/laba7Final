package com.dar.server.data.entities;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.dar.common.utilites.CheckingReader.readValidType;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Entity
@Builder
@Table(name = "spacemarines")
@DynamicInsert

/**
 * основной хранимый entity-дата-класс
 */
public class PersonEntity implements Comparable<PersonEntity>, Serializable {

    /**
     * id {@link PersonEntity}Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence_generator")
    @SequenceGenerator(name = "my_sequence_generator", sequenceName = "my_sequence_name", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountEntity owner;

    /**
     * Имя {@link PersonEntity}.
     * Поле не может быть null и не может быть пустым.
     */
    @Column(name = "name")
    @NotNull
    private String name;

    /**
     * Координаты {@link PersonEntity}.
     * Поле не может быть null.
     */
    @OneToOne
    @JoinColumn(name = "Coordinate_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    @NotNull
    private CoordinatesEntity coordinatesEntity;

    /**
     * Дата создания {@link PersonEntity}.
     * Поле не может быть null и его значение должно генерироваться автоматически.
     * Формат даты: "dd-MM-yyyy HH:mm"
     */
    @NotNull
    @Column
    private LocalDateTime creationDate;

    /**
     * Уровень здоровья {@link PersonEntity}.
     * Значение поля должно быть больше 0.
     */
    @Column(name = "health")
    private long weight;

    /**
     * Рост {@link PersonEntity}.
     */
    @Column
    private float height;

    /**
     * Вид оружия {@link PersonEntity}.
     * Поле может быть null.
     */
    @Column
    @NotNull
    private Color color;

    /**
     * Локация {@link PersonEntity}.
     * Поле не может быть null.
     */
    @OneToOne
    @JoinColumn(name = "Location_id", referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    @NotNull
    private LocationEntity locationEntity;

    PersonEntity(AccountEntity acc, java.lang.String name, CoordinatesEntity coordinatesEntity, java.time.LocalDateTime creationDate, long weight, float height, Color color, LocationEntity locationEntity) {
        this.weight = weight;
        this.coordinatesEntity = coordinatesEntity;
        this.owner = acc;
        this.color = color;
        this.name = name;
        this.height = height;
        this.locationEntity = locationEntity;
        this.creationDate = now();
    }

    PersonEntity(long id, AccountEntity owner, String name, CoordinatesEntity coordinatesEntity, LocalDateTime localDateTime, long health, Boolean loyal, float height, Color weaponType, LocationEntity locationEntity) {
        this.id = id;
        this.weight = health;
        this.locationEntity = locationEntity;
        this.coordinatesEntity = coordinatesEntity;
        this.owner = owner;
        this.color = weaponType;
        this.name = name;
        this.height = height;
        this.creationDate = localDateTime;
    }

    public PersonEntity(String n, CoordinatesEntity c, long h, float height, Color gun, LocationEntity ch) {
        super();
        this.name = n;
        this.weight = h;
        this.coordinatesEntity = c;
        this.color = gun;
        this.locationEntity = ch;
        this.height = height;
    }

    public PersonEntity() {
        this.creationDate = now();
    }

    public static PersonEntity newInstance(String[] args) {


        PersonEntityBuilder spmBuilder = PersonEntity.builder();
        spmBuilder.name = ((String) readValidType("s", args[0]));
        spmBuilder.coordinatesEntity = (new CoordinatesEntity(
                (Long) readValidType("l", args[1]),
                (Long) readValidType("l", args[2])));

        spmBuilder.weight = ((Long) readValidType("l", args[3]));
        spmBuilder.height = ((Float) readValidType("f", args[4]));
        spmBuilder.color = (Color.choose((String) readValidType("s", args[5])));
        spmBuilder.locationEntity = (new LocationEntity(
                (long) readValidType("l", args[6]),
                (Long) readValidType("l", args[7]),
                (float) readValidType("f", args[8])));
        //spmBuilder.id = Math.toIntExact(DBConnection.getDBConnection().newId());
        return spmBuilder.build();
    }

    public PersonEntity update(String[] args) {
        return newInstance(args);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity that)) return false;
        return id == that.id && weight == that.weight && Float.compare(height, that.height) == 0 && Objects.equals(name, that.name) && Objects.equals(coordinatesEntity, that.coordinatesEntity) && Objects.equals(creationDate, that.creationDate)  && color == that.color && Objects.equals(locationEntity, that.locationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinatesEntity, creationDate, weight, height, color, locationEntity);
    }

    @Override
    public String toString() {

        return "***** " + this.getClass() + " Details *****\n" +
                "ID=" + getId() + "\n" +
                "Name=" + getName() + "\n" +
                "health=" + getWeight() + "\n" +
                "Coordinates=" + getCoordinatesEntity() + "\n" +
                "chapter=" + getLocationEntity() + "\n" +
                "weapoonType=" + getColor() + "\n" +
                "height=" + getHeight() + "\n" +
                "creationDate=" + getCreationDate() + "\n" +
                "*****************************";
    }

    @Override
    public int compareTo(PersonEntity other) {
        return this.name.compareTo(other.name);
    }


}
