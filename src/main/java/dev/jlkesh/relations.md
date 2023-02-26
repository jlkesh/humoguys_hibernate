# [Documentation](https://docs.jboss.org/hibernate/orm/6.1/userguide/html_single/Hibernate_User_Guide.html)

# Two Types Of Relations
## Unidirectional 
* @OneToOne 
## Bidirectional 
* @ManyToOne
* @OneToMany
* @ManyToMany

Example 215. @Any mapping usage
@Entity
@Table(name = "property_holder")
public class PropertyHolder2 {

    @Id
    private Long id;

    @Any
    @PropertyDiscriminationDef
    @Column(name = "property_type")
    @JoinColumn(name = "property_id")
    private Property property;

    //Getters and setters are omitted for brevity

}
Though the mapping has been "simplified", the mapping works exactly as shown in @Any mapping usage.

@ManyToAny mapping
While the @Any mapping is useful to emulate a @ManyToOne association when there can be multiple target entities, to emulate a @OneToMany association, the @ManyToAny annotation must be used.

The mapping details are the same between @Any and @ManyToAny except for:

The use of @ManyToAny instead of @Any

The use of @JoinTable, @JoinTable#joinColumns and @JoinTable#inverseJoinColumns instead of just @JoinColumn

In the following example, the PropertyRepository entity has a collection of Property entities.

The repository_properties link table holds the associations between PropertyRepository and Property entities.

Example 216. @ManyToAny mapping usage
@Entity
@Table(name = "property_repository")
public class PropertyRepository {

    @Id
    private Long id;

    @ManyToAny
    @AnyDiscriminator(DiscriminatorType.STRING)
    @Column(name = "property_type")
    @AnyKeyJavaClass(Long.class)
    @AnyDiscriminatorValue(discriminator = "S", entity = StringProperty.class)
    @AnyDiscriminatorValue(discriminator = "I", entity = IntegerProperty.class)
    @Cascade({ org.hibernate.annotations.CascadeType.ALL })
    @JoinTable(name = "repository_properties",
            joinColumns = @JoinColumn(name = "repository_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id")
)
private List<Property<?>> properties = new ArrayList<>();

    //Getters and setters are omitted for brevity

}
CREATE TABLE property_repository (
id BIGINT NOT NULL,
PRIMARY KEY ( id )
)

CREATE TABLE repository_properties (
repository_id BIGINT NOT NULL,
property_type VARCHAR(255),
property_id BIGINT NOT NULL
)
To see the @ManyToAny annotation in action, consider the next examples.

If we persist an IntegerProperty as well as a StringProperty entity, and associate both of them with a PropertyRepository parent entity, Hibernate will generate the following SQL queries:

Example 217. @ManyToAny mapping persist example
IntegerProperty ageProperty = new IntegerProperty();
ageProperty.setId(1L);
ageProperty.setName("age");
ageProperty.setValue(23);

session.persist(ageProperty);

StringProperty nameProperty = new StringProperty();
nameProperty.setId(1L);
nameProperty.setName("name");
nameProperty.setValue("John Doe");

session.persist(nameProperty);

PropertyRepository propertyRepository = new PropertyRepository();
propertyRepository.setId(1L);

propertyRepository.getProperties().add(ageProperty);
propertyRepository.getProperties().add(nameProperty);

session.persist(propertyRepository);
INSERT INTO integer_property
( "name", "value", id )
VALUES ( 'age', 23, 1 )

INSERT INTO string_property
( "name", "value", id )
VALUES ( 'name', 'John Doe', 1 )

INSERT INTO property_repository ( id )
VALUES ( 1 )

INSERT INTO repository_properties
( repository_id , property_type , property_id )
VALUES
( 1 , 'I' , 1 )
When fetching the PropertyRepository entity and navigating its properties association, Hibernate will fetch the associated IntegerProperty and StringProperty entities like this:

Example 218. @ManyToAny mapping query example
PropertyRepository propertyRepository = session.get(PropertyRepository.class, 1L);

assertEquals(2, propertyRepository.getProperties().size());

for(Property property : propertyRepository.getProperties()) {
assertNotNull(property.getValue());
}
SELECT pr.id AS id1_1_0_
FROM   property_repository pr
WHERE  pr.id = 1

SELECT ip.id AS id1_0_0_ ,
ip."name" AS name2_0_0_ ,
ip."value" AS value3_0_0_
FROM   integer_property ip
WHERE  ip.id = 1

SELECT sp.id AS id1_3_0_ ,
sp."name" AS name2_3_0_ ,
sp."value" AS value3_3_0_
FROM   string_property sp
WHERE  sp.id = 1
2.8.8. @JoinFormula mapping
The @JoinFormula annotation is used to customize the join between a child Foreign Key and a parent row Primary Key.

Example 219. @JoinFormula mapping usage
@Entity(name = "User")
@Table(name = "users")
public static class User {

	@Id
	private Long id;

	private String firstName;

	private String lastName;

	private String phoneNumber;

	@ManyToOne
	@JoinFormula("REGEXP_REPLACE(phoneNumber, '\\+(\\d+)-.*', '\\1')::int")
	private Country country;

	//Getters and setters omitted for brevity

}

@Entity(name = "Country")
@Table(name = "countries")
public static class Country {

	@Id
	private Integer id;

	private String name;

	//Getters and setters, equals and hashCode methods omitted for brevity

}
CREATE TABLE countries (
id int4 NOT NULL,
name VARCHAR(255),
PRIMARY KEY ( id )
)

CREATE TABLE users (
id int8 NOT NULL,
firstName VARCHAR(255),
lastName VARCHAR(255),
phoneNumber VARCHAR(255),
PRIMARY KEY ( id )
)
The country association in the User entity is mapped by the country identifier provided by the phoneNumber property.

Considering we have the following entities:

Example 220. @JoinFormula mapping usage
Country US = new Country();
US.setId(1);
US.setName("United States");

Country Romania = new Country();
Romania.setId(40);
Romania.setName("Romania");

doInJPA(this::entityManagerFactory, entityManager -> {
entityManager.persist(US);
entityManager.persist(Romania);
});

doInJPA(this::entityManagerFactory, entityManager -> {
User user1 = new User();
user1.setId(1L);
user1.setFirstName("John");
user1.setLastName("Doe");
user1.setPhoneNumber("+1-234-5678");
entityManager.persist(user1);

	User user2 = new User();
	user2.setId(2L);
	user2.setFirstName("Vlad");
	user2.setLastName("Mihalcea");
	user2.setPhoneNumber("+40-123-4567");
	entityManager.persist(user2);
});
When fetching the User entities, the country property is mapped by the @JoinFormula expression:

Example 221. @JoinFormula mapping usage
doInJPA(this::entityManagerFactory, entityManager -> {
log.info("Fetch User entities");

	User john = entityManager.find(User.class, 1L);
	assertEquals(US, john.getCountry());

	User vlad = entityManager.find(User.class, 2L);
	assertEquals(Romania, vlad.getCountry());
});
-- Fetch User entities

SELECT
u.id as id1_1_0_,
u.firstName as firstNam2_1_0_,
u.lastName as lastName3_1_0_,
u.phoneNumber as phoneNum4_1_0_,
REGEXP_REPLACE(u.phoneNumber, '\+(\d+)-.*', '\1')::int as formula1_0_,
c.id as id1_0_1_,
c.name as name2_0_1_
FROM
users u
LEFT OUTER JOIN
countries c
ON REGEXP_REPLACE(u.phoneNumber, '\+(\d+)-.*', '\1')::int = c.id
WHERE
u.id=?

-- binding parameter [1] as [BIGINT] - [1]

SELECT
u.id as id1_1_0_,
u.firstName as firstNam2_1_0_,
u.lastName as lastName3_1_0_,
u.phoneNumber as phoneNum4_1_0_,
REGEXP_REPLACE(u.phoneNumber, '\+(\d+)-.*', '\1')::int as formula1_0_,
c.id as id1_0_1_,
c.name as name2_0_1_
FROM
users u
LEFT OUTER JOIN
countries c
ON REGEXP_REPLACE(u.phoneNumber, '\+(\d+)-.*', '\1')::int = c.id
WHERE
u.id=?

-- binding parameter [1] as [BIGINT] - [2]
Therefore, the @JoinFormula annotation is used to define a custom join association between the parent-child association.