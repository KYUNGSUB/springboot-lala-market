package kr.talanton.lala.common.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Common {
	public static final String PC_LIST_TYPE = "2";
	public static final String PC_MAIN_TYPE = "3";
	public static final String PC_EXPOSE_TYPE = "4";
	public static final String MOBILE_LIST_TYPE = "5";
	public static final String MOBILE_MAIN_TYPE = "6";
	public static final String MOBILE_EXPOSE_TYPE = "7";
	public static final String STYLE_SHOP_CATEGORY = "0020000";

	public static String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator); // File.separator : OS에 따라 다르므로 이렇게 사용
	}
	
	public static boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			if (contentType != null) // modified by ksseo
				return contentType.startsWith("image"); // image/jpg, image/png, image/gif
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}