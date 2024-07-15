package Joker.dao;

import org.springframework.stereotype.Component;
import Joker.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/Test";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "3696";

    private  static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAll() {
List<Person> persons = new ArrayList<>();
        try {
            Statement statement =connection.createStatement();
            String SQL = "SELECT * FROM person";
          ResultSet resultSet = statement.executeQuery(SQL);
          while (resultSet.next()) {
              Person person = new Person();
              person.setId(resultSet.getInt("id"));
              person.setName(resultSet.getString("name"));
              person.setEmail(resultSet.getString("Email"));
              person.setAge(resultSet.getInt("Age"));
                persons.add(person);
          }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    public Person get(int id) {
return null;
        //return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO person VALUES ('"+person.getId()+"','"+person.getName()+"','"+person.getAge()+"','"+person.getEmail()+"')";


            statement.executeUpdate(SQL);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated = get(id);
//        personToBeUpdated.setName(updatedPerson.getName());
//        personToBeUpdated.setAge(updatedPerson.getAge());
//        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }

    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
    }

    private int getNextId() {

//        return people.stream().mapToInt(Person::getId).max().orElse(0) + 1;
        return 0;
    }
}
