package com.example.demo.dao.impl;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.FileWrapper;
/*import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;*/


@Repository("ProfilesDAO")
public class FileWrapperDaoImpl extends FileWrapperDao{
	
	/*@Resource(name = "SessionFactory")
	private SessionFactory sessionFactory;*/
	
	 public  void saveFileMetadata(File file, String path, String fileName, long length){
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      Class.forName(JDBC_DRIVER);
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      stmt = conn.createStatement();
		      String sql = "INSERT INTO filewrapper (name, absolutePath, file, length)" +
		                   "VALUES ('"+fileName+"', '"+path+"', '"+file+"', "+length+")";
		      
		      
		      stmt.executeUpdate(sql);

		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		}//end method

	
	
	
	 public  List<FileWrapper> selectFileMetadata() {
		 
		 List<FileWrapper> llistFileWrappers = new ArrayList<FileWrapper>();  
		 Connection conn = null;
		 Statement stmt = null;
		   try{
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      stmt = conn.createStatement();
//		      String sql = "SELECT id, name, absolutePath, file, length FROM filewrapper";
		      String sql = "SELECT  id, file, absolutePath, name, length FROM filewrapper";
		      ResultSet rs = stmt.executeQuery(sql);
		      
		      
		      FileWrapper fileWrapper;
		      while(rs.next()){
		    	  fileWrapper = new FileWrapper();
		    	  
		    	  fileWrapper.setId(rs.getInt("id"));
		    	  fileWrapper.setFilename(rs.getString("name"));
		    	  fileWrapper.setLength(rs.getLong("length"));
		    	  fileWrapper.setAbsolutePath(rs.getString("absolutePath"));
		    	  
		    	  llistFileWrappers.add(fileWrapper);		         
		      }
		      rs.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try

		   return llistFileWrappers;
	 }
	 public  List<FileWrapper> selectFileMetadataWhere(String fileName) {
		 
		 List<FileWrapper> llistFileWrappers = new ArrayList<FileWrapper>();  
		 Connection conn = null;
		 Statement stmt = null;
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
			 conn = DriverManager.getConnection(DB_URL, USER, PASS);
			 stmt = conn.createStatement();
//		      String sql = "SELECT id, name, absolutePath, file, length FROM filewrapper";
			 String sql = "SELECT  id, file, absolutePath, name, length FROM filewrapper where "
			 		+ " name = '"+fileName+"' ";
			 ResultSet rs = stmt.executeQuery(sql);
			 
			 
			 FileWrapper fileWrapper;
			 while(rs.next()){
				 fileWrapper = new FileWrapper();
				 
				 fileWrapper.setId(rs.getInt("id"));
				 fileWrapper.setFilename(rs.getString("name"));
				 fileWrapper.setLength(rs.getLong("length"));
				 fileWrapper.setAbsolutePath(rs.getString("absolutePath"));
				 
				 llistFileWrappers.add(fileWrapper);		         
			 }
			 rs.close();
		 }catch(SQLException se){
			 se.printStackTrace();
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally{
			 try{
				 if(stmt!=null)
					 conn.close();
			 }catch(SQLException se){
			 }
			 try{
				 if(conn!=null)
					 conn.close();
			 }catch(SQLException se){
				 se.printStackTrace();
			 }//end finally try
		 }//end try
		 
		 return llistFileWrappers;
	 }

	 public void deleteAllExistFilesFromDB(){
		 Connection conn = null;
		   Statement stmt = null;
		   try{
		      Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      stmt = conn.createStatement();
		      String sql = "DELETE FROM filewrapper ";
		      stmt.executeUpdate(sql);

		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            conn.close();
		      }catch(SQLException se){
		      }try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		 
	 }
	 
}
