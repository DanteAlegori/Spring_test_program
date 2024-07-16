package Joker.dao;

import org.springframework.stereotype.Component;
import Joker.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Component
public class

PersonDAO {

    private static final String URL = "jdbc:postgresql://localhost:5432/Test";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "3696";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> getAll() {
        List<Person> persons = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
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
        Person person =null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM  Person WHERE id=?");

        preparedStatement.setInt(1,id);

       ResultSet resultSet = preparedStatement.executeQuery();

       resultSet.next();

       person =new Person();
       person.setId(resultSet.getInt("id"));
       person.setName(resultSet.getString("name"));
       person.setAge(resultSet.getInt("age"));
        person.setEmail(resultSet.getString("email"));



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;

    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Person VALUES (?,?,?,?)");
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4, person.getEmail());


           preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person updatedPerson) {
        try {
            String sql = "UPDATE Person SET name=?, age=?, email=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id) {
        try {
            String sql = "DELETE FROM Person WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getNextId() {
        int nextId = 0;
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT MAX(id) FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1;
            } else {
                nextId = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nextId;
    }

}
