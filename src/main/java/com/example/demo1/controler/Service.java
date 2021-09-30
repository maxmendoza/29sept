package com.example.demo1.controler;
import com.example.demo1.database.MysqlCon;
import com.example.demo1.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    @Path("/employee")
    public class Service {

        Connection con;
        PreparedStatement pstm;
        ResultSet rs;
        Statement statement;

        @GET
        @Path("/")
        @Produces(MediaType.APPLICATION_JSON)

        public List<Customer> getCustomer(){
            List<Customer> employees = new ArrayList<>();

            Customer Customer = new Customer();
            Customer.setCustomerNumber(1);
            Customer.setCustomerName("juan");
            employees.add(Customer);

            Customer = new Customer();
            Customer.setCustomerNumber(2);
            Customer.setCustomerName("lopez");
            employees.add(Customer);
            return employees;
        }

        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Customer getEmployee(@PathParam("id") int employeeNumber){
            Customer employee = new Customer();
            if(employeeNumber != 0)
                employee.setCustomerName("juan");
            return employee;
        }

        //employees   employeeNumber , lastName , firstName , extension , email , officeCode , reportsTo , jobTitle

        @POST
        @Path("/createemp")
        @Produces(MediaType.APPLICATION_JSON)
        public boolean createUser (Customer emp){
            boolean state = false;
            try {
                con = MysqlCon.getConnection();
                String query = "INSERT INTO `customers`( `customerName`, `contactLastName`, `contactFirstName`, `phone`, `addressLine1`, `addressLine2`, `city`, `state`, `postalCode`, `country`, `salesRepEmployeeNumber`, `creditLimit`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
                pstm = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                pstm.setString(1,emp.getCustomerName());
                pstm.setString(2,emp.getContactLastName());
                pstm.setString(3,emp.getContactFirstName());
                pstm.setString(4,emp.getPhone());
                pstm.setString(5,emp.getAddressLine1());
                pstm.setString(6,emp.getCreditLimit());
                pstm.setString(6,emp.getAddressLine2());
                pstm.setString(8,emp.getCity());
                pstm.setString(9,emp.getState());
                pstm.setString(10,emp.getPostalCode());
                pstm.setString(11,emp.getCountry());
                pstm.setInt(12,emp.getSalesRepEmployeeNumber());
                state = pstm.executeUpdate() == 1;
                rs = pstm.getGeneratedKeys();
            }catch (SQLException ex){
                ex.printStackTrace();
            }finally {
                closeConnection();
            }
            return state;
        }
        @PUT
        @Path("/modifyemp")
        @Produces(MediaType.APPLICATION_JSON)
        public boolean modifyPerson (Customer emp){
            boolean state = false;
            try {
                con = MysqlCon.getConnection();
                String query = "UPDATE `customers` SET `customerName`=?,`contactLastName`=?,`contactFirstName`=?,`phone`=?,`addressLine1`=?,`addressLine2`=?,`city`=?,`state`=?,`postalCode`=?,`country`=?,`salesRepEmployeeNumber`=?,`creditLimit`=? WHERE `customerNumber`=?";
                pstm = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                pstm.setString(1,emp.getCustomerName());
                pstm.setString(2,emp.getContactLastName());
                pstm.setString(3,emp.getContactFirstName());
                pstm.setString(4,emp.getPhone());
                pstm.setString(5,emp.getAddressLine1());
                pstm.setString(6,emp.getCreditLimit());
                pstm.setString(6,emp.getAddressLine2());
                pstm.setString(8,emp.getCity());
                pstm.setString(9,emp.getState());
                pstm.setString(10,emp.getPostalCode());
                pstm.setString(11,emp.getCountry());
                pstm.setInt(12,emp.getSalesRepEmployeeNumber());
                pstm.setInt(12,emp.getCustomerNumber());
                state = pstm.executeUpdate() == 1;
                rs = pstm.getGeneratedKeys();

            }catch (SQLException ex){
                ex.printStackTrace();
            }finally {
                closeConnection();
            }
            return state;
        }
        @DELETE
        @Path("/deleteemp")
        @Produces(MediaType.APPLICATION_JSON)
        public boolean deletePerson (int id){
            boolean state = false;
            try {
                con = MysqlCon.getConnection();
                String query = "DELETE FROM `customers` WHERE `customerNumber`=?";
                pstm = con.prepareStatement(query);
                pstm.setInt(1,id);
                state = pstm.executeUpdate() == 1;

            }catch (SQLException ex){
                ex.printStackTrace();
            }finally {
                closeConnection();
            }
            return state;
        }

        public void closeConnection() {
            try {
                if (con != null) {
                    con.close();
                }
                if (pstm != null) {
                    pstm.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {

            }
        }
}
