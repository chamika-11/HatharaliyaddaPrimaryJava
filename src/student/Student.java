
package student;

import DBConnector.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Student {
    Connection con = DBConnection.getConnection();
    PreparedStatement ps;
    
    public int getMax(){
        int id=0;
        Statement st;
        try{
            st=con.createStatement();
            ResultSet rs=st.executeQuery("Select Max(Student_id) from student");
            while(rs.next()){
                id=rs.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return id+1;
    }
    
    public void insert(int id,String fname,String lname,String address,String gender,String grade,String clas,String tname,String gname,String gphone){
        
        String sql ="insert into student values(?,?,?,?,?,?,?,?,?,?)";
        
        try {
            ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2, fname);
            ps.setString(3, lname);
            ps.setString(4, address);
            ps.setString(5, gender);
            ps.setString(6, grade);
            ps.setString(7, clas);
            ps.setString(8, tname);
            ps.setString(9, gname);
            ps.setString(10, gphone);
            
            if(ps.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "NEW STUDENT ADDED SUCCESSFULLY!");
            }
            else{
                System.out.print("No rows were inserted!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //GET ALL STUDENT DETAILS
    public void getStudentValue(JTable table,String searchValue){
        String sql="select * from student where concat(Student_id)like ? order by Student_id desc";
        try {
            ps=con.prepareCall(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs=ps.executeQuery();
            DefaultTableModel model=(DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows
            Object[] row;
            
            while(rs.next()){
                row=new Object[11];
                row[0]=rs.getInt(1);
                row[1]=rs.getString(2);
                row[2]=rs.getString(3);
                row[3]=rs.getString(4);
                row[4]=rs.getString(5);
                row[5]=rs.getString(6);
                row[6]=rs.getString(7);
                row[7]=rs.getString(8);
                row[8]=rs.getString(9);
                row[9]=rs.getString(10);
                model.addRow(row);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //update 
    public void update(int id,String fname,String lname,String address,String gender,String grade,String clas,String tname,String gname,String gphone){
        
        String sql="update student set First_Name=?,Last_Name=?,Address=?,Gender=?,Grade=?,Class=?,Class_Teacher_Name=?,Guardian_Name=?,Guardian_Phone_Number=? where Student_id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, address);
            ps.setString(4, gender);
            ps.setString(5, grade);
            ps.setString(6, clas);
            ps.setString(7, tname);
            ps.setString(8, gname);
            ps.setString(9, gphone);
            ps.setInt(10,id);
            
            if(ps.executeUpdate()>0){
            JOptionPane.showMessageDialog(null, "Student data update Successfully");
        }
        
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Check the id already existing
   public boolean isIdExist(int id) {
    try {
        ps = con.prepareStatement("SELECT * FROM student WHERE Student_id = ?");
        ps.setInt(1, id); 
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return true; 
        }
    } catch (SQLException ex) {
        Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;  
}
   
   //delete data
   public void delete(int id){

      int yesOrNo=JOptionPane.showConfirmDialog(null, "Course and score records will also deleted","Student Delete",JOptionPane.OK_CANCEL_OPTION, 
        JOptionPane.WARNING_MESSAGE);
       if(yesOrNo==JOptionPane.OK_OPTION){
          try {
              ps=con.prepareStatement("Delete from student where Student_id=?");
              ps.setInt(1, id);
              if(ps.executeUpdate()>0){
                  JOptionPane.showMessageDialog(null, "Student data deleted Successfully");
              }
          } catch (SQLException ex) {
              Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
          }
           
       }
   }
   
}
