package commf.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import common.util.FileUtil;
import common.util.properties.ApplicationProperty;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 2010-12-02 File Download View Resolver
 * @author ntarget
 *
 */

@SuppressWarnings("all")
public class ExcelTempView extends AbstractView {
	/** Logger for this class */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

		String tempPath		= ApplicationProperty.get("excel.templete.dir");
		String pcFilename	= (String)model.get("pcFilename");

		if (pcFilename == null || "".equals(pcFilename))
			pcFilename	= (String)model.get("filename");

		// ** [sbb2.0-fix findbugs, cslee] Predictable Pseudo Random Number Generantor - 18.08.01
		// [조치] - java.security.SecureRandom 로 대체
//		Random random = new Random(System.currentTimeMillis());
		SecureRandom random = new SecureRandom( String.valueOf(System.currentTimeMillis()).getBytes() );
		
		StringBuffer sb = null;
		sb = new StringBuffer();
		sb.append(String.valueOf(System.currentTimeMillis()));
		sb.append(String.valueOf(random.nextLong()));

		String fileName		= sb.toString() +"_"+pcFilename+".xls";

		String realPcFileName	= pcFilename+".xls";

		String tempFileName = (String)model.get("filename")+"_Template.xls";
		List   excelList	= (List)model.get("excelList");
		Map    paramMap		= (HashMap)model.get("paramMap");

		// Excel File Create
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("excelList",  excelList);
		beans.put("paramMap", 	paramMap);

		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(tempPath +"/"+ tempFileName, beans, tempPath +"/"+ fileName);

		// File Download
		// ** [sbb2.0-fix findbugs, cslee] Potential Path Traversal (File Read) - 18.07.31
		// 파일경로를 파라미터로 전달받지 않기 때문에 외부 공격으로 인한 문제 없음
		// -> FileUtil.newFile(...)로 대체
//		File f = new File(tempPath +"/"+ fileName);
		File f = FileUtil.newFile(tempPath +"/"+ fileName);

		if (f.exists()) {
			logger.info("response charset : " + response.getCharacterEncoding());
			// 파일명 인코딩 처리
			// ** [sbb2.0-fix findbugs, cslee] Untrusted User-Agent Header - 18.08.02
			// [조치] - 다운로드되는 파일명의 인코딩 처리를 위해 User-Agent를 활용하여 브라우저 구별을 하는 것으로 보안상 문제되지 않음
			//        - 그대로 유지
			String downFilename = (request.getHeader("User-Agent").indexOf("MSIE") == -1) ?
					new String(realPcFileName.getBytes(), "8859_1") : // FF
						URLEncoder.encode(realPcFileName, "UTF-8");		// IE
			logger.info("disposition filename : " + downFilename);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + downFilename + "\"");

			byte[] buffer = new byte[1024];
			BufferedInputStream ins = new BufferedInputStream(new FileInputStream(f));
			BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

			try {
				int read = 0;
				while ((read = ins.read(buffer)) != -1) {
					outs.write(buffer, 0, read);
				}
				outs.close();
				ins.close();
			} catch (IOException e) {
				logger.info("EXCEL DOWNLOAD ERROR : FILE NAME = "+tempPath +"/"+  fileName+" ");
			} finally {
				// Download Excel : File Remove
				FileUtil.deleteFile(tempPath +"/"+ fileName);
				if(outs!=null) outs.close();
				if(ins!=null) ins.close();
			}
		}


	}

}
