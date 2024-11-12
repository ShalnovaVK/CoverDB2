package org.example.coverdb;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
private  Connection con;
    // Метод для форматирования значений
    private String formatValue(String value) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return value; // Если булевое значение, возвращаем без кавычек
        }

        try {
            // Проверка, является ли значение числом
            Double.parseDouble(value);
            return value; // Если это число, возвращаем без кавычек
        } catch (NumberFormatException e) {
            // Не число, возвращаем со строковыми кавычками
            return "'" + value + "'";
        }
    }


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
        //String insvalues = String.join(", ", values);
        String insvalues = values.stream().map(this::formatValue) // обернуть  значение в одинарные кавычки в зависимости от типа
                .collect(Collectors.joining(", ")); // объединить в строку с запятыми

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
    public ResultSet readTable(String tName)
    {
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt=con.createStatement();
            String query="select * from "+tName+";";
            rs=stmt.executeQuery(query);
            return rs;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return rs;
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
    public void deleteRow(String tName, String conditionName, String value)
    {
        Statement stmt;

        try {
            String query= "delete from "+tName+" where " + conditionName + " = " + formatValue(value) + ";";
            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Row deleted successfully !");

        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public void deleteRowByNumber(String tName,String firstEl, String value){
        Statement stmt;
        try {
            String query= "WITH RankedElem AS (SELECT " +tName+".*, ROW_NUMBER() OVER () AS rnum FROM " +
                    tName + ") DELETE FROM "+tName+" WHERE "+firstEl+" IN (SELECT "+firstEl+" FROM RankedElem" +
                    " WHERE rnum = "+formatValue(value)+");";
            stmt=con.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Row deleted successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
