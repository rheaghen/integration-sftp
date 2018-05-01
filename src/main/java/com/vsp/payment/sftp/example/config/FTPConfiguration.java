//package com.vsp.payment.sftp.example.config;
//
//import java.io.File;
//import java.util.List;
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.expression.common.LiteralExpression;
//import org.springframework.integration.annotation.Gateway;
//import org.springframework.integration.annotation.IntegrationComponentScan;
//import org.springframework.integration.annotation.MessagingGateway;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.config.EnableIntegration;
//import org.springframework.integration.file.FileNameGenerator;
//import org.springframework.integration.file.remote.FileInfo;
//import org.springframework.integration.file.remote.session.SessionFactory;
//import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
//import org.springframework.integration.sftp.outbound.SftpMessageHandler;
//import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//
//import com.jcraft.jsch.ChannelSftp.LsEntry;
//
//@Configuration
//@EnableIntegration
//@IntegrationComponentScan
//public class FTPConfiguration {
//
//	@Autowired
//	@Qualifier("ftpProperties")
//	private Properties ftpProperties;
//	
//	
//	@Bean
//	public SessionFactory<LsEntry> sftpSessionFactory(){
//		DefaultSftpSessionFactory sessionFactory = new DefaultSftpSessionFactory();
//		sessionFactory.setHost(ftpProperties.getProperty("ftp.payment.dev.host"));
//		sessionFactory.setPort(22);
//		sessionFactory.setUser(ftpProperties.getProperty("ftp.payment.dev.username"));
//		sessionFactory.setPassword(ftpProperties.getProperty("ftp.payment.dev.password"));
//		sessionFactory.setAllowUnknownKeys(true);
//		return sessionFactory;
//	}
//	
//	@Bean
//	@ServiceActivator(inputChannel = "toSftpChannel", outputChannel = "nullChannel")
//	public MessageHandler handler(){
//		SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
//		handler.setRemoteDirectoryExpression(new LiteralExpression(ftpProperties.getProperty("ftp.payment.dev.remoteDir")));
//		handler.setFileNameGenerator(new FileNameGenerator(){
//
//			@Override
//			public String generateFileName(Message<?> message) {
//				return ftpProperties.getProperty("ftp.payment.dev.remoteFile");
//			}
//			
//		});
//		return handler;
//	}
//
//	@MessagingGateway
//	public interface TheGateway {
//		
//		@Gateway(requestChannel = "toSftpChannel")
//		void sendToSftp(File file);
//	}
//
//	@MessagingGateway
//	public interface LsGateway {
//		
//		@Gateway(requestChannel = "lsFtpChannel")
//		void viewDirectory(String path);
//	}
//	
//	@Bean
//	public DirectChannel textOutputChannel(){
//		return new DirectChannel();
//	}
//	
//	@Bean
//	public DirectChannel lsFtpChannel(){
//		return new DirectChannel();
//	}
//	
//	@ServiceActivator(inputChannel = "textOutputChannel")
//	public void logText(Message<List<FileInfo<LsEntry>>> output){
//		List<FileInfo<LsEntry>> payload = output.getPayload();
//		for(FileInfo<LsEntry> fi : payload){
//			LsEntry infoObject = fi.getFileInfo();
//			
//			System.out.println(fi.getFilename());
//		}
//	}
//	
//	@ServiceActivator(inputChannel = "lsFtpChannel",outputChannel = "textOutputChannel")
//	public SftpOutboundGateway sftpOutputGateway(){
//		return new SftpOutboundGateway(sftpSessionFactory(), "ls", "'" + ftpProperties.getProperty("ftp.payment.dev.remoteDir") + "'");
//	}
//	
//}
