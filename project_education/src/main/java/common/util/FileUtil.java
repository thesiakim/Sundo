package common.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import business.biz.Constants;

/**
 * 파일 처리 관련된 유틸리티 API.
 *
 * @author Administrator
 *
 */
public class FileUtil {

    /**
     * Logging output for this class.
     */
    protected static final Log log = LogFactory.getLog(FileUtil.class);

    public static final int BUFFER_SIZE = 4096;

    /**
	 * path 파라미터의 파일이나 디렉터리가 없으면, 해당 디렉터리를 생성한다.
	 * 만일 파일이나 디렉터리가 존재한다면 false를 반환한다.
	 *
	 * @param path
	 *         	생성할 디렉터리 위치
	 * @return boolean
	 * 			<code>true</code> 성공적으로 디렉터리를 생성한 경우.
	 *         	<code>false</code> 디렉터리를 생성하지 않은 경우.
	 */
    public static boolean makeDirectories(String path) {
        if( StringUtils.isBlank(path)) {
        	throw new RuntimeException("Given path parameter is blank. Thus can't make directory.");
        }

        // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read) - 18.08.01
        // [조치] - 해당 함수를 사용하는 모든 소스부분에서 시스템 내에 정의해 놓은 상수 정보를 이용하여 파일 경로를 구성하기 떼문에 문제 없음
        //        - FileUtil.newFile(...)로 대체
//        File f = new File(path);
        File f = newFile(path);

        if (f.exists()) {
        	return false;
        } else {
        	if (log.isDebugEnabled()) {
        		log.debug(" Path does not exist on the file system. Creating folders...");
        	}
        	f.mkdirs();
        	return true;
        }
    }

    /**
	 * 파일시스템에서 지정된 디렉터리를 삭제한다.
	 *
	 * @param path
	 *            삭제한 디렉터리
	 * @todo implement flag
	 */
    public static void removeDirectories(String path, boolean removeWithContents) {
        if (log.isDebugEnabled()) {
            log.debug(" Attempting to remove folders for path: " + path);
        }
        if (StringUtils.isBlank(path)) {
        	throw new RuntimeException("Given path is blank.");
        }

        // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
        // [조치] - 현재 해당 함수를 사용하는 곳이 없음
        //        - FileUtil.newFile(...)로 대체
//        File f = new File(path);
        File f = FileUtil.newFile(path);

    	try {
			FileUtils.deleteDirectory(f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	/**
	 * 지정된 파일의 위치를 옮긴다.
	 *
	 * @param fromFile 원본 위치
	 * @param toFile 대상 위치
	 * @throws IOException
	 *
	 */
    public static void moveFile(String fromFile, String toFile) {
        try {
        	if( StringUtils.isNotBlank(toFile)){
        		String replacedPath = replacePathToSlash(toFile);
        		makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));
        	} else {
        		throw new RuntimeException("Given target file path is blank. Thus can't move source file.");
        	}

        	copyFile(fromFile, toFile);
			deleteFile(fromFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 파일을 복사한다. 대상 파일이 이미 존재하는 경우, 런타임 예외를 발생시킨다.
     *
     * @param fromFile 원본 파일
     * @param toFile 대상 파일
     * @throws IOException
     */
    public static void copyFile(String fromFile, String toFile) throws IOException {
    	FileInputStream fis 	= null;
    	FileOutputStream fos 	= null;

        try {
            // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
            // [조치] - 현재 해당 함수를 사용하는 곳이 없음
            //        - FileUtil.newFile(...)로 대체
//            if(new File(toFile).exists()){ // 대상 파일이 이미 존재하면 예외 처리
        	if(FileUtil.newFile(toFile).exists()){ // 대상 파일이 이미 존재하면 예외 처리
        		throw new RuntimeException("Given target file exist already. : " + toFile);
        	}

        	//retrieve the file data
        	// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
            // [조치] - 현재 해당 함수를 사용하는 곳이 없음
            //        - FileUtil.newFileInputStream(...)로 대체
//        	fis = new FileInputStream(fromFile);
        	fis = FileUtil.newFileInputStream(fromFile);
        	// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Write)
            // [조치] - 현재 해당 함수를 사용하는 곳이 없음
            //        - FileUtil.newFileOutputStream(...)로 대체
//        	fos = new FileOutputStream(toFile);
        	fos = FileUtil.newFileOutputStream(toFile);

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;

            while ((bytesRead = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        } catch (Exception ioe) {
        	throw new RuntimeException(ioe);
        } finally {
	        fis.close();
	        fos.close();
        }
    }

    /**
     * 파일을 복사한다.
     *
     * @param in byte[] 복사할 원본의 바이너리
     * @param outPathName String 목표 파일명
     * @throws IOException
     */
	public static void copyFile(byte[] in, String outPathName) throws IOException {
		Assert.notNull(in, "No input byte array specified");
		
		// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
        // [조치] - 현재 해당 함수를 사용하는 곳이 없음
        //        - FileUtil.newFile(...)로 대체
//		File out = new File(outPathName);
		File out = FileUtil.newFile(outPathName);
		
		if( out.exists() ){
			throw new RuntimeException("Given target file exist already. : " + outPathName);
		}
		copyFile(in, out);
	}

	/**
	 * 파일을 복사한다.
	 *
	 * @param in byte[]
	 * @param out File
	 * @throws IOException
	 */
	public static void copyFile(byte[] in, File out) throws IOException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No output File specified");
		ByteArrayInputStream inStream = new ByteArrayInputStream(in);

		String replacedPath = replacePathToSlash(out.getPath());
		makeDirectories(replacedPath.substring(0, replacedPath.lastIndexOf("/")));

		OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
		copyFile(inStream, outStream);
	}

	/**
	 * 파일을 복사한다.
	 *
	 * @param in InputStream
	 * @param out OutputStream
	 * @return
	 * @throws IOException
	 */
	public static int copyFile(InputStream in, OutputStream out) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");
		try {
			int byteCount = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ex) {
				log.warn("Could not close InputStream", ex);
			}
			try {
				out.close();
			}
			catch (IOException ex) {
				log.warn("Could not close OutputStream", ex);
			}
		}
	}

	// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read) - 18.08.01
    // [조치] - 사용하지 않는 소스 제거
//	/**
//	 * 지정된 위치의 파일을 byte[]로 반환한다.
//	 *
//	 * @param fullFilePath
//	 * @return
//	 * @throws IOException
//	 */
//	public static byte[] getFileToByteArray(String fullFilePath) throws IOException {
//		Assert.notNull(fullFilePath, "No input byte array specified");
//
//		FileInputStream fis = null;
//		byte[] out = null;
//		try {
//			fis = new FileInputStream(fullFilePath);
//			out = new byte[fis.available()];
//
//			fis.read(out);
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			fis.close();
//		}
//		return out;
//	}

	/**
	 * 지정된 파일을 삭제한다.
	 *
	 * @param fullFilePath 파일 위치 문자열
	 */
    public static void deleteFile(String fullFilePath) {
        
        // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
        // [조치] - 해당 함수를 사용하는 부분에서 savePath를 소스내 정해진 상수를 통해 구성하기 때문에 문제되지 않음
        //        - FileUtil.newFile(..)로 대체
//        File file = new File(fullFilePath);
        File file = FileUtil.newFile(fullFilePath);

        try {
            if (file.exists()) {
                file.delete();
            }
            else{
            	log.debug("Given path's file do not exist. : " + fullFilePath);
            }
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 파일이나 디렉터리 명을 "/"(슬래시)기반으로 변경하여 반환.
     *
     * @param path 변경할 패스 문자열
     * @return
     */
    public static String replacePathToSlash(String path){
    	return (StringUtils.isBlank(path)) ?
    			path :
    			path.replaceAll("[\\\\]+", "/").replaceAll("[/]{2,}", "/");
    }

    // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
    // [조치] - 사용하지 않는 소스 제거
//    /**
//     * Zip 압축파일 풀기
//     * [JAVA UTIL ZIP로   압축풀기용]
//     * ntarget - 2011-03-04
//     * @param string
//     * @param dmMonthPath
//     */
//	@SuppressWarnings("rawtypes")
//	public static void unzipFile(String fromFile, String toPath) {
//
//		//디렉토리 생성
//		FileUtil.makeDirectories(toPath);
//
//		ZipFile zipFile 			= null;
//        FileOutputStream fos 		= null;
//        ByteArrayOutputStream baos 	= null;
//
//		try {
//            zipFile 		= new ZipFile(fromFile);
//            Enumeration e 	= zipFile.entries();
//
//            while( e.hasMoreElements() ) {
//                ZipEntry zipEntry 	= (ZipEntry)e.nextElement();
//                String strEntry 	= zipEntry.getName();
//                int startIndex 		= 0;
//                int endIndex 		= 0;
//                boolean isDirectory = false;
//
//                // Directory가 있을경우 생성함.
//                while(true) {
//                    endIndex = strEntry.indexOf("/", startIndex);
//
//                    if(endIndex != -1 ) {
//                        String strDirectory	= strEntry.substring(0, endIndex);
//                        File fileDirectory 	= new File(toPath + strDirectory);
//
//                        if( fileDirectory.exists() == false ) {
//                            fileDirectory.mkdir();
//                        }
//                        startIndex = endIndex+1;
//                    } else {
//                        break;
//                    }
//
//                    if( endIndex+1 == strEntry.length() ) {
//                        isDirectory = true;
//                    }
//                }
//
//                // Directory가 아니면 파일생성.
//                if( isDirectory == false ) {
//                    InputStream is = zipFile.getInputStream(zipFile.getEntry(strEntry));
//                    baos = new ByteArrayOutputStream();
//
//                    byte[] byteBuffer = new byte[1024];
//                    byte[] byteData = null;
//                    int nLength = 0;
//
//                    while ((nLength=is.read(byteBuffer)) > 0 ) {
//                        baos.write(byteBuffer, 0, nLength);
//                    }
//                    is.close();
//
//                    byteData = baos.toByteArray();
//                    fos = new FileOutputStream(toPath + strEntry);
//                    fos.write(byteData);
//
//                }
//            }
//        } catch(IOException e) {
//        	e.printStackTrace();
//        } finally {
//            if( zipFile != null ) {
//            	try {
//					zipFile.close();
//					zipFile = null;
//					baos.close();
//					fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        }
//	}

	// ** [sbb2.0-fix findbugs, cslee] Portential Command Injection - 18.08.01
	// --> 사용하지 않는 소스 제거
//    /**
//     * Zip 압축파일 풀기
//     * [UNIX, LINUX 명령어로 압축풀기용]
//     * ntarget - 2011-03-04
//     * @param string
//     * @param dmMonthPath
//     */
//	public static void unzipFileCmd(String fromFile, String toPath) {
//		String cmd = "";
//
//		//디렉토리 생성
//		FileUtil.makeDirectories(toPath);
//
//		if (!"".equals(toPath)) {
//			cmd = "unzip -o "+ fromFile + " -d " + toPath;
//		} else {
//			cmd	= "unzip -o "+ fromFile;
//		}
//
//		try {
//			Runtime rt = Runtime.getRuntime();
//			Process procs = rt.exec(cmd);
//			procs.waitFor();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
    * 파일 확장자를 구한다
    * @param fileName
    * @return 확장자
    */
    public static String getExt(String fileName){
    	int pathPoint = fileName.lastIndexOf(".");
    	String ext = fileName.substring(pathPoint + 1, fileName.length()).toLowerCase();
    	return ext;
    }
	    
    /**
	 * 파일명 나노타임으로 자동생성하여 파일을 저장한다.
	 * @param request
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String,Object>> multiFileUploadAutoFileName(HttpServletRequest request, String savePath) throws Exception
	{
		String METHOD_NM = ".fileUploadAutoFileName()";
		log.debug(FileUtil.class.getName() + METHOD_NM);
		
		// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
        // [조치] - 해당 함수를 아직 사용하는 곳이 없음
        //        - FileUtil.newFile(..)로 대체
//		File uFile = new File(savePath);
		File uFile = FileUtil.newFile(savePath);
		
		boolean uFileExists = uFile.exists();
		
		// 업로드 디렉토리
		if(!uFileExists){ 
			uFile.mkdirs(); 
		} 
		
		List<Map<String,Object>> fileDataList = new ArrayList<Map<String,Object>> ();
		
        if(request instanceof MultipartHttpServletRequest)
        {
            MultipartHttpServletRequest multi = (MultipartHttpServletRequest)request;
            
            MultiValueMap<String, MultipartFile> fileMap = multi.getMultiFileMap();
            if(!fileMap.isEmpty()){
	            for (int i = 0,cnt = fileMap.get("file[]").size(); i < cnt; i++) {
	            	CommonsMultipartFile mf = (CommonsMultipartFile) fileMap.get("file[]").get(i); 
	
	            	if( !"".equals( mf.getOriginalFilename())  ){
	            	    
	            	    // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
	                    // [조치] - 해당 함수를 아직 사용하는 곳이 없음
	                    //        - FileUtil.newFile(..)로 대체
//	            	    File cf = new File(savePath,mf.getOriginalFilename());
				    	File cf = FileUtil.newFile(savePath,mf.getOriginalFilename());
				    	
			            System.out.println(mf.getOriginalFilename());    
		                if ( cf.exists() ) {}
		                	//cf.delete();
		                	
	                	String temp[] = mf.getOriginalFilename().split("[.]");
	     				String fileName = FileUtil.getUnique();  
	     				
	                     if(temp.length != 0)
	     				{
	                     	fileName += "."+temp[temp.length-1];
	     				}
	                     
    	                 // ** [sbb2.0-fix findbugs, cslee] Potential Path Traversal (File Read) - 18.07.31
    	                 // 파일경로를 파라미터로 전달받지 않기 때문에 외부 공격으로 인한 문제 없음
    	                 // -> FileUtil.newFile(...)로 대체
//	                     File f = new File(savePath, fileName);
	                     File f = FileUtil.newFile(savePath, fileName);
	                     mf.transferTo(f);                                              
	                     Map<String,Object> fileDataMap = new HashMap<String,Object>();
	                     
	                     fileDataMap.put("fileName", fileName);
	                     fileDataMap.put("filePath", savePath);
	                     fileDataMap.put("fileSize", f.length());
	                     fileDataMap.put("orgFileName", mf.getOriginalFilename());
	                     ///Na - fileType
	                     ///ex)mp3, mp4, jpg, pdf ...
	                     fileDataMap.put("fileType", temp[temp.length-1]);
	                     
	                     fileDataList.add(fileDataMap);
				    }
				}
            }
        }
	    return fileDataList;
	}
	
	/**
     *  
     * @Method Name : getUnique
     * @변경이력 :
     * @Method 설명 : 파입 업로드를 위한 파일 이름 생성
     * @return
     */
    public static String getUnique()
    {
    	String METHOD_NM = ".getUnique()";
		log.debug(FileUtil.class.getName() + METHOD_NM);
		
    	return String.valueOf(System.nanoTime());
    }
    
    /**
	 * 파일명 나노타임으로 자동생성하여 파일을 저장한다.
	 * @param request
	 * @param savePath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map<String,Object>> fileUploadAutoFileName(HttpServletRequest request, String savePath) throws Exception
	{
		String METHOD_NM = ".fileUploadAutoFileName()";
		log.debug(FileUtil.class.getName() + METHOD_NM + " " + savePath);
		
		// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read) - 18.08.01
		// [조치] - 해당 함수를 사용하는 소스 부분에서 저장 path를 소스내 정해진 상수를 통해 구성하기 때문에 문제되지 않음
		//        - FileUtil.newFile(...) 대체
//		File uFile = new File(savePath);
		File uFile = FileUtil.newFile(savePath);
		
		boolean uFileExists = uFile.exists();
		
		// 업로드 디렉토리
		if(!uFileExists){ 
			uFile.mkdirs(); 
		} 
		
		List<Map<String,Object>> fileDataList = new ArrayList<Map<String,Object>> ();
		
        if(request instanceof MultipartHttpServletRequest)
        {
            MultipartHttpServletRequest multi = (MultipartHttpServletRequest)request;
            
            Map fileMap = multi.getFileMap();
            
            Iterator itr = fileMap.keySet().iterator();
            
            while(itr.hasNext())
            { 
                Object key = itr.next();
                
                CommonsMultipartFile mf = (CommonsMultipartFile) fileMap.get(key); 
                
			    if( !"".equals( mf.getOriginalFilename())  ){
			        
			        // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
			        // [조치] - 해당 함수를 사용하는 부분에서 savePath를 소스내 정해진 상수를 통해 구성하기 때문에 문제되지 않음
			        //        - FileUtil.newFile(..)로 대체
//			        File cf = new File(savePath,mf.getOriginalFilename());
			    	File cf = FileUtil.newFile(savePath,mf.getOriginalFilename());
		                
	                if ( cf.exists() ) {}
	                	//cf.delete();
	                	
                	String temp[] = mf.getOriginalFilename().split("[.]");
     				String fileName = FileUtil.getUnique();  
     				
                     if(temp.length != 0) {
                     	fileName += "."+temp[temp.length-1];
     				 }
                     
                     // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
                     // [조치] - 해당 함수를 사용하는 부분에서 savePath를 소스내 정해진 상수를 통해 구성하기 때문에 문제되지 않음
                     //        - FileUtil.newFile(..)로 대체
//                     File f = new File(savePath, fileName);
                     File f = FileUtil.newFile(savePath, fileName);
                     
                     mf.transferTo(f);                                              
                     Map<String,Object> fileDataMap = new HashMap<String,Object>();
                     
                     fileDataMap.put("fileName", fileName);
                     fileDataMap.put("filePath", savePath);
                     fileDataMap.put("fileSize", f.length());
                     fileDataMap.put("orgFileName", mf.getOriginalFilename().replace(" ", ""));
                     ///Na - fileType
                     ///ex)mp3, mp4, jpg, pdf ...
                     fileDataMap.put("fileType", temp[temp.length-1]);
                     
                     fileDataList.add(fileDataMap);
			    }
            }
        }
	    return fileDataList;
	}
	
	/**
     * 파일 레파지토리정보를 조회한다.
     * @param path
     * @return
     */
    public static String getRepository (String path){
    	
		
    	String METHOD_NM = ".getRepository()";
		log.debug(FileUtil.class.getName() + METHOD_NM + " " + path);
		String rtnStr = "";
		
		String osName = System.getProperty("os.name").toLowerCase();
		
		if(osName.contains("windows")) {
			if("CMTMSG".equals(path)){
				rtnStr = Constants.CMTMSG_FILE_PATH;
			}else if("ROUTE_PATH".equals(path)){
				rtnStr = Constants.ROUTE_FILE_PATH;
			}else if("KML_PATH".equals(path)){
				rtnStr = Constants.KML_FILE_PATH;
			}else{
				rtnStr = Constants.ETC_FILE_PATH;
			}
		} else {
			if("CMTMSG".equals(path)){			
				rtnStr = Constants.CMTMSG_SERVER_FILE_PATH;
			}else if("ROUTE_PATH".equals(path)){
				rtnStr = Constants.ROUTE_SERVER_FILE_PATH;
			}else if("KML_PATH".equals(path)){
				rtnStr = Constants.KML_SERVER_FILE_PATH;
			}else{
				rtnStr = Constants.ETC_SERVER_FILE_PATH;;
			}
		}
		return rtnStr;
		
	}
    
    /**
	 * 파일 Remove
	 * @param file, path, fileName
	 */
	public static void removeFile(String filePath) {
		try {
			if(filePath != null) {
				
			    // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
		        // [조치] - 해당 함수를 사용하는 부분에서 파일경로를 소스내 정해진 상수정보를 이용해 구성하기 때문에 문제 없음
		        //        - FileUtil.newFile(..)로 대체
//			    File f = new File(filePath);
				File f = FileUtil.newFile(filePath);
				
				if(f.exists()) {
					f.delete();
				}
			}
		} catch(Exception e) {}
	}
	
	/**
     * // ** [sbb2.0-fix findbugs, cslee] Potential Path Traversal (File Read) - 18.07.31
     * 추가
     * 파일 경로 자체(전체)가 파라미터로 전달되지 않고 내부 소스 상수나 DB 검색을 통해 경로가 
     * 구성되기 때문에 외부 공격에 의해 경로가 변경될 수 없는 대상
     * @param pathname
     * @return
     */
    public static File newFile(String pathname) {
        return new File(pathname);
    }
    
    /**
     * // ** [sbb2.0-fix findbugs, cslee] Potential Path Traversal (File Read) - 18.07.31
     * 추가
     * 파일 경로 자체(전체)가 파라미터로 전달되지 않고 내부 소스 상수나 DB 검색을 통해 경로가 
     * 구성되기 때문에 외부 공격에 의해 경로가 변경될 수 없는 대상
     * @param parent
     * @param child
     * @return
     */
    public static File newFile(String parent, String child) {
        return new File(parent, child);
    }

    /**
     * // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read) - 18.08.01
     * 추가
     * 파일 경로 자체(전체)가 파라미터로 전달되지 않고 내부 소스 상수나 DB 검색을 통해 경로가 
     * 구성되기 때문에 외부 공격에 의해 경로가 변경될 수 없는 대상
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static FileInputStream newFileInputStream(String name) throws FileNotFoundException {
        return new FileInputStream(name);
    }
    /**
     * // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read) - 18.08.01
     * 추가
     * 파일 경로 자체(전체)가 파라미터로 전달되지 않고 내부 소스 상수나 DB 검색을 통해 경로가 
     * 구성되기 때문에 외부 공격에 의해 경로가 변경될 수 없는 대상
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    public static FileOutputStream newFileOutputStream(String name) throws FileNotFoundException {
        return new FileOutputStream(name);
    }
}
