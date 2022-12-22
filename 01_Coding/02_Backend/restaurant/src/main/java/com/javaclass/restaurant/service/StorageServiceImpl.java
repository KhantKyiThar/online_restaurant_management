package com.javaclass.restaurant.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class StorageServiceImpl implements StorageService {

	private final Path storagePath;
	
	@Autowired
	public StorageServiceImpl() throws IOException {
		Path storagePath = Paths.get("").resolve("src").resolve("main").resolve("resources").resolve("static")
				.resolve("images");
		if (!Files.exists(storagePath)) {
			Files.createDirectories(storagePath);
		}
		this.storagePath = storagePath;
	}
	
	@Override
	public String create(MultipartFile file, String fileType) {
		String filePath = null;
		try {
			String fileName = Instant.now().getEpochSecond() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
			Files.copy(file.getInputStream(), this.storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			switch (fileType) {
			case "image/jpg":
			case "image/jpeg":
				filePath = "/images/jpg/" + fileName;
				break;
			case "image/png":
			default:
				filePath = "/images/png/" + fileName;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	@Override
	public byte[] load(String fileName) {
		byte[] retBytes = null;
		try {
			Path filePath = this.storagePath.resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists() && resource.isReadable()) {
				retBytes = org.springframework.util.StreamUtils.copyToByteArray(resource.getInputStream());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retBytes;
	}

	@Override
	public boolean delete(String filePath) {
		filePath = filePath.replace("/images/jpg/", "");
		filePath = filePath.replace("/images/png/", "");
		if (filePath != null && filePath != "") {
			try {
				Files.delete(this.storagePath.resolve(filePath));
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	@Override
	public String update(MultipartFile file, String fileType, String filePath) {
		String retfilePath = null;

		filePath = filePath.replace("/images/jpg/", "");
		filePath = filePath.replace("/images/png/", "");
		try {
			if (filePath != null && filePath != "") {
				try {
					Files.delete(this.storagePath.resolve(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String fileName = Instant.now().getEpochSecond() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
			Files.copy(file.getInputStream(), this.storagePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
			switch (fileType) {
			case "image/jpg":
			case "image/jpeg":
				retfilePath = "/images/jpg/" + fileName;
				break;
			case "image/png":
			default:
				retfilePath = "/images/png/" + fileName;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retfilePath;
	}

	@Override
	public void clearAll() {
		try {
			Files.walk(this.storagePath).sorted().forEach(file -> {
				try {
					if (!Files.isDirectory(file)) {
						Files.deleteIfExists(file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean check(String filePath) {
		filePath = filePath.replace("/images/jpg/", "");
		filePath = filePath.replace("/images/png/", "");
		if (filePath != null && filePath != "") {
			Path filePathPath = this.storagePath.resolve(filePath);
			return Files.exists(filePathPath) && !Files.isDirectory(filePathPath) && Files.isReadable(filePathPath);
		}
		return false;
	}

	

}
