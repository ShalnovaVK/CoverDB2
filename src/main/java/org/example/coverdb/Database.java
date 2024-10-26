package org.example.coverdb;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class Database {
private  Connection con;

    public Database() {
        con=connect_to_db("mybaskets","postgres","vika");
    }
    //подключение к базе данных
    public Connection connect_to_db(String dbname, String user, String pass)
    {
        Connection con_obj=null;
        String url="jdbc:postgresql://localhost:5432/";

        try
        {
            con_obj= DriverManager.getConnection(url+dbname,user,pass);
            if(con_obj!=null)
            {
                System.out.println("Connection established successfully !");
            }
            else
            {
                System.out.println("Connection failed !!");
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return con_obj;
    }
    //вставка значений
    public  void insertRow(String tName, List<String> columns, List<String> values) {
        if (columns.size() != values.size()) {
            throw new IllegalArgumentException("Количество столбцов и значений должно совпадать!");
        }
        Statement stmt;
        String columnNames = String.join(", ", columns);
        String insvalues = String.join(", ", values);
        String placeholders = String.join(", ", Collections.nCopies(values.size(), "%s")); // создаёт "?, ?, ?" для подготовленного выражения

        String query = "INSERT INTO "+tName+" ("+columnNames+ ") VALUES (" + insvalues + ");";

        try {

            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void createTable(String tableName, List<String> columns, List<String> typeData)
    {
        Statement stmt;

        try {

            String query="create table "+tableName+" (";
            if (columns.size() != typeData.size()) {
                throw new IllegalArgumentException("Количество столбцов и значений должно совпадать!");
            }

            for (int i = 0; i < columns.size() - 1; i++) {
                query += columns.get(i) + " " + typeData.get(i) + ", ";
            }
            query += columns.get(columns.size() - 1) + " " + typeData.get(columns.size() - 1)+");";
            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Table has been created successfully !!");
        }
        catch (Exception e)
        {
            System.out.println("Exception caught");
        }
    }
    public void readTable(String tName)
    {
        Statement stmt;
        ResultSet rs;
        try {
            stmt=con.createStatement();
            String query="select * from "+tName+";";
            rs=stmt.executeQuery(query);
            System.out.println("buyer_id\t\tfull_name\t\tlogin\t\tpassword\t\tcontacts\t\tphone_number");
            System.out.println("---------------------------");
            while(rs.next())
            {
                System.out.print(rs.getString("buyer_id")+"\t\t");
                System.out.print(rs.getString("full_name")+"\t\t\t");
                System.out.print(rs.getString("login")+"\t\t\t\t");
                System.out.print(rs.getString("password")+"\t\t\t\t\t");
                System.out.print(rs.getString("contacts")+"\t\t\t\t\t\t");
                System.out.println(rs.getString("phone_number")+"\t\t\t\t\t\t\t");

            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public  void UpdateRow(String tName, String nameCol, String valueCol, String conditionName, String value) {
        Statement stmt;


        try {
            String query = "update "+tName+" set "+nameCol+ " = " + valueCol + "where " + conditionName + " = " + value + ";";
            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Inserted successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void deleteRow(String tName, String nameCol, String valueCol, String conditionName, String value)
    {
        Statement stmt;

        try {
            String query= "delete "+tName+" set "+nameCol+ " = " + valueCol + " where " + conditionName + " = " + value + ";";
            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Row deleted successfully !");

        }
        catch (Exception e)
        {
            System.out.println("Exception caught in deleteTable");
        }
    }
}
