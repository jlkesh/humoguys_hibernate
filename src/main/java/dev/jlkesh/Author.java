package dev.jlkesh;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Author {
    private String id;
    private String fullName;
    private int age;
}
