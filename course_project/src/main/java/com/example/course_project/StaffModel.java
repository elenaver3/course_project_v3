package com.example.course_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StaffModel {

    private Connection connection;
    private ObservableList<Staff> staffList;

    public StaffModel() throws Exception {
        this.connection = ConnectionTry.getConnection();

        staffList = FXCollections.observableArrayList();

        Statement statement = connection.createStatement();
        String query = "SELECT * FROM staff";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String last_name = resultSet.getString("last_name");
            String first_name = resultSet.getString("first_name");
            String second_name = resultSet.getString("second_name");
            String address = resultSet.getString("address");
            String phone_number = resultSet.getString("phone_number");
            Staff staff = new Staff(id, last_name, first_name, second_name, address, phone_number);
            staffList.add(staff);
        }
    }

    private int getNewStaffId() throws SQLException {
        String query = "SELECT MAX(id) AS id FROM staff";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private void updateStaffArray(int id_staff, int action) throws SQLException {
        String query = " ";
        PreparedStatement statement;
        ResultSet resultSet;
        switch (action) {
            case 1:
                query = "SELECT * FROM staff WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_staff);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    String address = resultSet.getString("address");
                    String phone_number = resultSet.getString("phone_number");
                    Staff staff = new Staff(id, last_name, first_name, second_name, address, phone_number);
                    staffList.add(staff);
                }

                statement.close();
                break;
            case 2:
                query = "SELECT * FROM staff WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id_staff);

                resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String last_name = resultSet.getString("last_name");
                    String first_name = resultSet.getString("first_name");
                    String second_name = resultSet.getString("second_name");
                    String address = resultSet.getString("address");
                    String phone_number = resultSet.getString("phone_number");
                    for (int i = 0; i < staffList.size(); i++) {
                        if (staffList.get(i).getId() == id) {
                            staffList.set(i, new Staff(id, last_name, first_name, second_name, address, phone_number));
                        }
                    }
                }

                statement.close();
                break;
            case 3:
                for (int i = 0; i < staffList.size(); i++) {
                    if (staffList.get(i).getId() == id_staff) {
                        Staff staff = staffList.get(i);
                        staffList.remove(staff);
                        break;
                    }
                }
                break;
        }

    }

    public Staff getStaffMember(int id_staff) throws SQLException {
        Staff staff = new Staff();
        for (Staff one_staff: staffList) {
            if (one_staff.getId() == id_staff) {
                staff = one_staff;
                break;
            }
        }
        return staff;
    }

    public ObservableList<Staff> getStaff() {
        return staffList;
    }


    public void updateStaff(int staff_id, String new_last, String new_first, String new_second, String new_address, String new_phone) throws SQLException {
        String sql = "UPDATE staff SET last_name = ?, first_name = ?, second_name = ?, address = ?, phone_number = ?";
        sql += " WHERE id = ?";

        Staff staff = getStaffMember(staff_id);

        PreparedStatement statement = connection.prepareStatement(sql);

        if (staff.getId() != -1) {
            if (new_last.isBlank())
                statement.setString(1, staff.getLast_name());
            else
                statement.setString(1, new_last);
            if (new_first.isBlank())
                statement.setString(2, staff.getFirst_name());
            else
                statement.setString(2, new_first);
            if (new_second.isBlank())
                statement.setString(3, staff.getSecond_name());
            else
                statement.setString(3, new_second);
            if (new_address.isBlank())
                statement.setString(4, staff.getAddress());
            else
                statement.setString(4, new_address);
            if (new_phone.isBlank() || new_phone.length() > 11)
                statement.setString(5, staff.getPhone_number());
            else {
                statement.setString(5, new_phone);
            }
            statement.setInt(6, staff_id);

            statement.executeUpdate();
            statement.close();
            this.updateStaffArray(staff_id, 2);
        }
    }

    public void deleteStaff(int staff_id) throws SQLException {
        String sql = "DELETE FROM staff where id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, staff_id);

        statement.executeUpdate();
        statement.close();
        this.updateStaffArray(staff_id, 3);

    }

    public void insertStaff(String first_name, String last_name, String second_name, String address, String phone_number) throws SQLException {

        String sql = "INSERT INTO staff (last_name, first_name, second_name, address, phone_number) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, last_name);
        statement.setString(2, first_name);
        statement.setString(3, second_name);
        statement.setString(4, address);
        statement.setString(5, phone_number);

        statement.executeUpdate();
        statement.close();
        this.updateStaffArray(getNewStaffId(), 1);
    }


}
