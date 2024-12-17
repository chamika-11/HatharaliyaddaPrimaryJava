package student;

import DBConnector.DBConnection;
import com.sun.jdi.connect.spi.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Teacher {

    java.sql.Connection con = DBConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("Select Max(teacher_id) from teacher");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return id + 1;
    }

    //Insert data
    public void insert(int id, String name, String subject, String grade, String clas, String tphone) {

        String sql = "insert into teacher values(?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, subject);
            ps.setString(4, grade);
            ps.setString(5, clas);
            ps.setString(6, tphone);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "NEW TEACHER ADDED SUCCESSFULLY!");
            } else {
                System.out.print("No rows were inserted!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //View Data
    public void getTeacherValue(JTable table2, String searchValue) {
        String sql = "select * from teacher where concat(teacher_id)like ? order by teacher_id desc";
        try {
            ps = con.prepareCall(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table2.getModel();
            model.setRowCount(0); // Clear existing rows
            Object[] row;

            while (rs.next()) {
                row = new Object[6];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);

                model.addRow(row);

            }

        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update 
    public void update(int id, String name, String subject, String grade, String clas, String tphone) {

        String sql = "update teacher set Name=?,Subject=?,Grade=?,Class=?,Telephone_Number=? where teacher_id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, subject);
            ps.setString(3, grade);
            ps.setString(4, clas);
            ps.setString(5, tphone);
            ps.setInt(6, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Teacher data update Successfully");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Check the id already existing
    public boolean isIdExist(int id) {
        try {
            ps = con.prepareStatement("SELECT * FROM teacher WHERE teacher_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Teacher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
