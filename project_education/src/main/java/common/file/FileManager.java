package common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import commf.message.Message;
import common.util.CommUtils;
import common.util.FileUtil;
import common.util.properties.ApplicationProperty;
import egovframework.rte.fdl.cmmn.exception.EgovBizException;

/**
 * Program Name    : File Manager
 * Description     : Common File Management
 * Programmer Name : ntarget
 * Creation Date   : 2017-01-09
 * Used Table      :
 */

@SuppressWarnings("all")
public class FileManager {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 빈 파일 객체를 호함하여 리스트를 리턴한다.
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping
	public List multiFileUpload(HttpServletRequest request) throws Exception {
	    // 빈 file정보는 포함하지 않고 리턴.
	    return multiFileUploadDetail(request, false);
	}

    /*입력정보 파일*/
    @RequestMapping
    private List multiFileUploadDetail(HttpServletRequest request, boolean isInsertEmptyFileInfo) throws Exception {

        List listFile = new ArrayList();

        // ** [sbb2.0-fix findbugs, cslee] Untrusted Content-Type Header - 18.08.02
        // [조치] - 해당 method를 사용하지 않고 있고 아래 content-type을 체크하는 부분이 크게 필요하지 않기 때문에 제거함
//        if (-1 >= request.getContentType().indexOf("multipart/form-data")) {
//            return null;
//        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> files = multipartRequest.getFileMap();

        String tempDir = ApplicationProperty.get("upload.temp.dir");

        logger.debug("File Directory Create !!!");

        FileUtil.makeDirectories(tempDir);

        for (int i = 0; i < files.size(); i++) {
            String upfileName = "upfile" + i;

            MultipartFile inFile = files.get(upfileName);
            String saveFileName = getFileName(tempDir, inFile.getOriginalFilename());

			// 가능 확장자 체크
			if (!CommUtils.isAtthAllowedFileType(inFile.getOriginalFilename(), ApplicationProperty.get("file.all.allow.exts")))
				throw new EgovBizException("["+inFile.getOriginalFilename()+"]"+(String)Message.getMessage("fail.common.notExtFile"));

            if (!saveFileName.equals("")) {
            	FileOutputStream fos = null;

	            try {
	                logger.debug("File Upload !!!");
	                // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Write) - 18.08.01
                    // [조치] - 시스템 내에 정의해 놓은 상수 정보를 이용하여 파일 경로를 구성하기 때문에 문제 없음
                    //        - FileUtil.newFileOutputStream(...)로 대체
//	                fos = new FileOutputStream(tempDir + saveFileName);
	                fos = FileUtil.newFileOutputStream(tempDir + saveFileName);
	                
					FileUtil.copyFile(inFile.getInputStream(), fos);

                    HashMap fmap = new HashMap();
                    fmap.put("fileSvrNm", saveFileName);
                    fmap.put("fileOrgNm", CommUtils.nvlTrim(inFile.getOriginalFilename()) );
                    fmap.put("tempDir"  , tempDir);
                    fmap.put("fileSize" , inFile.getSize());
                    fmap.put("idx"      , String.valueOf(i));

                    listFile.add(fmap);

	            } catch (FileNotFoundException e) {
	                throw new RuntimeException(e);
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            } catch (Exception e) {
	                throw new RuntimeException(e);
	            } finally {
	            	fos.close();
	            }
            } else {
                // isInsertEmptyFileInfo이 true이면 빈 파일정보를 empty hashmap으로 추가.
                if(isInsertEmptyFileInfo) {
                    listFile.add(new HashMap());
                }
            }
        }

        logger.debug("File Upload End !!!");

        return listFile;
    }



	/**
	 * File Upload
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws Exception
	 */
	public Map makeFileMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map fmap = new HashMap();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = multipartRequest.getFileMap();

		String excelDir = ApplicationProperty.get("upload.excel.dir");

		logger.debug("File Directory Create !!!");

		FileUtil.makeDirectories(excelDir);

		String upfileName = "upfile0";
		MultipartFile inFile = files.get(upfileName);

		String saveFileName = getFileName(excelDir, inFile.getOriginalFilename());

		// 가능 확장자 체크
		if (!CommUtils.isAtthAllowedFileType(inFile.getOriginalFilename(), ApplicationProperty.get("file.all.allow.exts")))
			throw new EgovBizException("["+inFile.getOriginalFilename()+"]"+(String)Message.getMessage("fail.common.notExtFile"));

		FileOutputStream fos = null;

		try {
		    // ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Write) - 18.08.01
            // [조치] - 시스템 내에 정의해 놓은 상수 정보를 이용하여 파일 경로를 구성하기 때문에 문제 없음
            //        - FileUtil.newFileOutputStream(...)로 대체
//		    fos = new FileOutputStream(excelDir + saveFileName);
			fos = FileUtil.newFileOutputStream(excelDir + saveFileName);
			FileUtil.copyFile(inFile.getInputStream(), fos);

			fmap.put("inFile", 		inFile.getInputStream());
			fmap.put("fileNm", 		saveFileName);
			fmap.put("excelDir", 	excelDir);

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			fos.close();
		}

		return fmap;
	}

	/**
	 * @param dir
	 * @param originalFileName
	 * @return
	 * @author purple
	 */
	public String getFileName(String dir, String originalFileName) {
		if (CommUtils.nvlTrim(originalFileName).equals(""))
			return "";

		String dotextension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		// ** [sbb2.0-fix findbugs, cslee] Portential Path Traversal (File Read)
		// [조치] - 해당 함수를 사용하는 부분에서 파일경로를 소스내 정해진 상수정보를 이용해 구성하기 때문에 문제 없음
		//        - FileUtil.newFile(..)로 대체
//		java.io.File currentPath = new java.io.File(dir);
		File currentPath = FileUtil.newFile(dir);
		
		String[] fileList = null;

		// ** [sbb2.0-fix findbugs, cslee] Predictable Pseudo Random Number Generantor - 18.08.01
        // [조치] - java.security.SecureRandom 로 대체
//      Random random = new Random(System.currentTimeMillis());
        SecureRandom random = new SecureRandom( String.valueOf(System.currentTimeMillis()).getBytes() );

        FileNameFilter fileNameFilter = new FileNameFilter();

		StringBuffer sb = null;
		do {
			sb = new StringBuffer();
			sb.append(String.valueOf(System.currentTimeMillis()));
			sb.append(String.valueOf(random.nextLong()));
			sb.append(dotextension);
			fileNameFilter.setFileName(sb.toString());

			fileList = currentPath.list(fileNameFilter);
		} while (fileList.length > 0);

		return sb.toString();
	}

	/**
	 * @author purple
	 */
	static class FileNameFilter implements FilenameFilter {
		String sFileName = null;

		public void setFileName(String sFileName) {
			this.sFileName = sFileName;
		}

		public boolean accept(java.io.File directory, String name) {
			if (name.equals(sFileName)) {
				return true;
			}
			return false;
		}
	}
	
	/**
	* 파일을 byte로 변환
	* @param file
	* @return byte[]
	* @exception UserException 
	*/
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        // Get the size of the file
        long length = file.length();
        /*
         * You cannot create an array using a long type. It needs to be an int
         * type. Before converting to an int type, check to ensure that file is
         * not loarger than Integer.MAX_VALUE;
         */
        if (length > Integer.MAX_VALUE) {
        	is.close();
        	
            return null;
        }
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ( (offset < bytes.length)
                &&
                ( (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) ) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
        	is.close();
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }
}

