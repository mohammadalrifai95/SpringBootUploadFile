package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
 






import scala.annotation.meta.setter;

import com.example.demo.model.FileWrapper;
import com.example.demo.service.impl.FileWrapperServiceImpl;
import com.example.demo.storage.StorageService;
 
@Controller
public class UploadController {
 
    @Autowired
    StorageService storageService;
 
    @Autowired
    FileWrapperServiceImpl fileWrapperServiceImpl; 
    
    FileWrapper fileWrapper;
    
    List<String> files = new ArrayList<String>();
 
    @GetMapping("/")
    public String listUploadedFiles(Model model) {
    	
    	fileWrapperServiceImpl.deleteAllExistFilesFromDB();
    	
        return "uploadForm";
    }

    @GetMapping("/backtouploadform")
    public String listUploadedFilesAgain(Model model) {
    	return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
        
			fileWrapper = new FileWrapper();

			File convFile = new File(file.getOriginalFilename());
        	
            storageService.store(file);
            model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
            files.add(file.getOriginalFilename());
            
            
            fileWrapperServiceImpl.saveFileMetadata(convFile,convFile.getAbsolutePath(),
					convFile.getName(), convFile.length());
            
        } catch (Exception e) {
            model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
        }
        return "uploadForm";
    }
 
    @GetMapping("/gellallfiles")
    public String getListFiles(Model model) {
    	model.addAttribute("files",
    			files.stream()
    			.map(fileName -> MvcUriComponentsBuilder
    					.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
    					.collect(Collectors.toList()));
    	model.addAttribute("totalFiles", "TotalFiles: " + files.size());
    	return "listFiles";
    }

    @GetMapping("/backtoListOfFiles")
    public String backToListFiles(Model model) {
    	model.addAttribute("files",
    			files.stream()
    			.map(fileName -> MvcUriComponentsBuilder
    					.fromMethodName(UploadController.class, "getFile", fileName).build().toString())
    					.collect(Collectors.toList()));
    	model.addAttribute("totalFiles", "TotalFiles: " + files.size());
    	return "listFiles";
    }
    @GetMapping("/SearchMetaDataForspecificfile")
    public String SearchMetaDataForspecificfile(Model model) {
    	return "metadataforspecificfile";
    }
    @GetMapping("/searchmetadataforcertinfile")
    public String SearchMetaDataForCirtenfile(Model model) {
    	return "metadataforcertinfile";
    }

    @GetMapping("/gellallfilesFromDB")
    public String getListFilesFromDB(Model model) {
    	
    	List<FileWrapper> listFileWrappers = fileWrapperServiceImpl.selectFileMetadata();
    	
    	model.addAttribute("listFileWrappers", listFileWrappers);
    	
    	return "fileMetadata";
    }

    @GetMapping("/serchforfilemetadata")
    public String getListFilesFromDB(@RequestParam("fileName") String fileName, Model model) {
    	
    	List<FileWrapper> listFileWrappers = fileWrapperServiceImpl.selectFileMetadataWhere(fileName);
    	
    	model.addAttribute("listFileWrappers", listFileWrappers);
    	
    	return "metadataforspecificfile";
    }
    
    @GetMapping("//getallfilesFromDB")
    public String getFilesFromDB(Model model) {
    	
    	List<FileWrapper> listFileWrappers = fileWrapperServiceImpl.selectFileMetadata();
    	
    	List<String> fileNames = new ArrayList<String>();
    	
    	for(FileWrapper fileWrapper : listFileWrappers){
    		fileNames.add(fileWrapper.getFilename());
    	}
    	
    	model.addAttribute("listFileWrappers", listFileWrappers);
    	model.addAttribute("fileNames", fileNames);
    	
    	return "metadata";
    }
 
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    	    	
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    
    @GetMapping("/filemetadata/{filename:.+}")
    @ResponseBody
    public String getFileMetaData(@PathVariable String filename) {
    	
    	
    	System.out.println("Momo");
    	System.out.println("Momo");
    	return "fileMetadata";
    }
}