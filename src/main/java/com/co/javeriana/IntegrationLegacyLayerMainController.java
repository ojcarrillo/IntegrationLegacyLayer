package com.co.javeriana;

import java.io.File;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.co.javeriana.ftp.exceptions.FTPErrors;
import com.co.javeriana.ftp.ftpclient.FTPService;
import com.co.javeriana.integration.layer.IntegrationQueueBDImpl;

@Component
public class IntegrationLegacyLayerMainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/* ruta del directorio compartido */
	private static final String PATH_LOCAL = File.separator + "ftp" + File.separator + "touresbalon" + File.separator;
	private static final String EXT = ".txt";

	@Autowired
	private Environment env;

	@Autowired
	private FTPService ftpService;
	
	@Autowired
	private IntegrationQueueBDImpl integrationService;

	@Scheduled(initialDelay=0, fixedRateString="${ftp.cronjob.fixedDelay}")
	public void procesarArchivosFTP() {
		log.info("ejecucion:: "+new Date());
		try {
			/* se conecta al ftp */
			ftpService.connectToFTP(env.getProperty("ftp.host"), Integer.parseInt(env.getProperty("ftp.port")),
					env.getProperty("ftp.username"), env.getProperty("ftp.password"));
			/* descargmos los archivos a un directorio local */
			ftpService.downloadAndDeleteFilesFromFTPDir(env.getProperty("ftp.dirpath"), PATH_LOCAL);
			/* se desconecta del ftp */
			ftpService.disconnectFTP();
			
			/* creamos un hilo por cada archivo para que corran de forma paralela */
			for(String filename : env.getProperty("account.files").split(",")) {
				integrationService.procesar(PATH_LOCAL + filename + EXT);
			}
			
		} catch (FTPErrors e) {
			e.printStackTrace();
		}
	}

}
