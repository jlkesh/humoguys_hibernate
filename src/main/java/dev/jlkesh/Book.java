package dev.jlkesh;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
/*@ToString*/
@Entity
public class Book {
    @Id
    /*@UuidGenerator(style = UuidGenerator.Style.TIME)*/
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String title;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Author author;

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author=" + getAuthor() +
                '}';
    }
}
