package com.example.demo.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.impl.FileWrapperDaoImpl;
import com.example.demo.model.FileWrapper;

@Service
public class FileWrapperServiceImpl {

	@Autowired
	FileWrapperDaoImpl fileWrapperDaoImpl;
	
//	FileWrapperDaoImpl fileWrapperDaoImpl = new FileWrapperDaoImpl();
	
	 public  void saveFileMetadata(File file, String path, String fileName, long length) {
		 
		 fileWrapperDaoImpl.saveFileMetadata(file, path, fileName, length);
	 }
	
	 public  List<FileWrapper> selectFileMetadata() {
		 return fileWrapperDaoImpl.selectFileMetadata();
	 }	 
	 
	 public  List<FileWrapper> selectFileMetadataWhere(String fileName) {
		 return fileWrapperDaoImpl.selectFileMetadataWhere(fileName);
	 }
	 public  void deleteAllExistFilesFromDB() {
		 fileWrapperDaoImpl.deleteAllExistFilesFromDB();
	 }	 
}