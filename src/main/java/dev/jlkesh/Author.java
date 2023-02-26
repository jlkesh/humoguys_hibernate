package dev.jlkesh;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Author {

    @Id
    /*@UuidGenerator(style = UuidGenerator.Style.TIME)*/
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String fullName;
    private int age;

    @OneToOne(mappedBy = "author",
            cascade = CascadeType.REMOVE)
    private Book book;


    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                '}';
    }
}
