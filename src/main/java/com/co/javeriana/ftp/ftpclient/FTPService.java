package com.co.javeriana.ftp.ftpclient;

import java.io.File;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.co.javeriana.ftp.exceptions.FTPErrors;

/**
 * Created by Yoandy Pérez Villazón on 13/09/17.
 */
public interface FTPService {

	void connectToFTP(String host, Integer port, String user, String pass) throws FTPErrors;

	void uploadFileToFTP(File file, String ftpHostDir, String serverFilename) throws FTPErrors;

	void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors;

	void disconnectFTP() throws FTPErrors;

	String getFtpWorkingDir() throws FTPErrors;

	FTPFile[] getFilesFromFTPDir(String remoteDir) throws FTPErrors;

	void deleteFileFromFTPDir(String fileName) throws FTPErrors;

	void downloadAndDeleteFilesFromFTPDir(String remoteDirPath, String localDirPath) throws FTPErrors;

}
