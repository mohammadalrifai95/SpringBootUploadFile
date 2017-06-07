package com.example.demo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FileWrapper {

	private int id;
	private File file;
	private List<File>files;

	private String filename;
	private String absolutePath;
	private long length;
	long lastModified;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public FileWrapper() {
	}

	public FileWrapper(String filename) throws IOException {
		this.filename = filename;
		init();
		length = file.length();
		lastModified = file.lastModified();
	}

	private void init() throws IOException {
		file = new File(filename);
		if (!file.exists())
			throw new FileNotFoundException("File not found " + filename);
		if (!file.canRead())
			throw new IOException("File not readable");
	}

	public String getFilename() {
		return filename;
	}

	public long getLength() {
		return length;
	}

	public long getLastModified() {
		return lastModified;
	}

}