package model.utils;
import java.math.BigDecimal;  
import java.util.Calendar;  
import java.util.GregorianCalendar;  
import java.util.LinkedHashSet;  
import java.util.Random;  
import java.util.Set;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
import model.utils.StringConstants;  
  
  
  
/** 
  * <pre> 
  * 功能描述： 字符串操作类 
  *           把一些String相关的常用的方法进行了封装，方便以后直接使用 
  * </pre> 
  * @author 方方   <p> 
  *                 Blog:  http://myclover.iteye.com <p> 
  *                 日   期：  2010-08-30 <p> 
  * @version 1.0 <p> 
  * {@code com.myclover.utils.string.StringOperateUtils.java com.myclover.utils.constant.StringConstants.java} 
  * 
 */  
public class StringOperateUtils {  
      
    // 身份证加权因子  
    private static final int[] weight = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6,  
            3, 7, 9, 10, 5, 8, 4, 2, 1 };  
    // 身份证校验码  
    private static final int[] checkDigit = new int[] { 1, 0, 'X', 9, 8, 7, 6,  
            5, 4, 3, 2 };  
      
    /** 
     * 功能描述：验证字符串为null或空字符串 
     * @param  strTemp  需要验证的字符串 
     * @return    返回：如果验证成功，则返回true，否则返回false 
     */  
    public static boolean validateNull(String strTemp) {  
        if (strTemp == null || strTemp.length() == 0) {  
            return true;  
        }  
        return false;  
    }  
  
      
    /** 
     * 功能描述：过滤Html特殊字符 
     * @param strTemp  需要过滤的字符串 
     * @return   返回：返回过滤后的新字符串 
     *  
     */  
    public static String filterHtml(String strTemp) {  
        if (strTemp == null) {  
            return null;  
        }  
        if (strTemp.length() == 0) {  
            return strTemp;  
        }  
  
        String temp = null;  
        temp = strTemp.replaceAll("&", "&amp;");  
        temp = temp.replaceAll("<", "&lt;");  
        temp = temp.replaceAll(">", "&gt;");  
        temp = temp.replaceAll(" ", "&nbsp;");  
        temp = temp.replaceAll("'", "&#39;");  
        temp = temp.replaceAll("\"", "&quot;");  
        temp = temp.replaceAll("\n", "<br>");  
        return temp;  
    }  
      
      
    /** 
     * 功能描述：验证E-mail地址的合法性 
     * @param email  需要验证的email地址 
     * @return 返回：如果是合法的email地址，则返回true，否则返回false 
     *  
     */  
    public static boolean validateEmail(String email) {  
        Pattern p = Pattern.compile(StringConstants.EMAIL_REGEXP);  
        Matcher m = p.matcher(email);  
        if (m.matches()) {  
            return true;  
        }  
        return false;  
    }  
  
      
    /** 
     * 功能描述：验证电话号码的合法性 
     * @param  phone  需要验证的电话号码 
     * @return  返回：合法的电话号码则返回true，否则返回false 
     *  
     */  
    public static boolean validatePhone(String phone) {  
        Pattern p = Pattern.compile(StringConstants.PHONE_REGEXP);  
        Matcher m = p.matcher(phone);  
        if (m.matches()) {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证邮政编码的合法性 
     * @param zip  需要验证的邮政编码 
     * @return 返回：合法的邮政编码则返回true，否则返回false 
     */  
    public static boolean validateZIP(String zip){  
        Pattern p = Pattern.compile(StringConstants.ZIP_REGEXP);  
        Matcher m = p.matcher(zip);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证URL的合法性 
     * @param url  需要验证的URL 
     * @return 返回：合法的URL则返回true，否则返回false 
     */  
    public static boolean validateURL(String url){  
        Pattern p = Pattern.compile(StringConstants.URL_REGEXP);  
        Matcher m = p.matcher(url);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证HTTP的合法性 
     * @param http  需要验证的HTTP 
     * @return 返回：合法的HTTP则返回true，否则返回false 
     */  
    public static boolean validateHTTP(String http){  
        Pattern p = Pattern.compile(StringConstants.HTTP_REGEXP);  
        Matcher m = p.matcher(http);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证中文字符的合法性 
     * @param zh  需要验证的中文字符 
     * @return 返回：合法的中文字符则返回true，否则返回false 
     */  
    public static boolean validateZH(String zh){  
        Pattern p = Pattern.compile(StringConstants.ZH_REGEXP);  
        Matcher m = p.matcher(zh);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证手机号码的合法性 
     * @param mobile  需要验证的手机号码 
     * @return 返回：合法的手机号码则返回true，否则返回false 
     */  
    public static boolean validateMobile(String mobile){  
        Pattern p = Pattern.compile(StringConstants.MOBILE_REGEXP);  
        Matcher m = p.matcher(mobile);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：验证图片格式的合法性 
     * @param img  需要验证的图片格式 
     * @return 返回：合法的图片格式则返回true，否则返回false 
     */  
    public static boolean validateImg(String img){  
        Pattern p = Pattern.compile(StringConstants.ICON_REGEXP);  
        Matcher m = p.matcher(img);  
        if(m.matches())  
        {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：判断字符串是否是合法的Java标识符 
     * @param java.lang.String s  待判断的字符串参数 
     * @return  返回：如果符合Java标识符，则返回true 
     *                否则返回false 
     */  
    public static boolean isJavaIdentifier(String s){  
        //如果字符串为空或者长度为0，返回false  
        if ((s == null) || (s.length() == 0)) {  
            return false;  
        }  
        //字符串中每一个字符都必须是Java标识符的一部分  
        for (int i=0; i<s.length(); i++) {  
            if (!Character.isJavaIdentifierPart(s.charAt(i))) {  
                return false;  
            }  
        }  
        return true;  
    }  
      
      
    /** 
     * 功能描述：将字符串第一个字母转为大写 
     * @param java.lang.String  str   需要转换的字符串参数 
     * @return   返回：返回转换后的新字符串 
     */  
    public static String convertFirstToUpper(String str){  
        String str1 = str.substring(0,1).toUpperCase();  
        String str2 = str.substring(1,str.length());  
        return str1+str2;  
    }  
      
      
    /** 
     * 功能描述：替换掉特殊字符 
     * @param java.lang.String  strFileContent  原字符串 
     * @return   返回：替换后的新字符串 
     */  
    public static String replaceSpecialChar(String strFileContent){         
      Pattern pattern = Pattern.compile("[<>*?|\\/:\"]+");  
      Matcher m = pattern.matcher(strFileContent);  
      strFileContent = m.replaceAll("");  
      return strFileContent;  
    }  
      
    /** 
     * 功能描述：如果原字符串不为空，则返回原字符串，否则返回给定的字串str2 
     * @param java.lang.String  str  原字符串 
     * @param java.lang.String  str2 给定的字符 
     * @return  返回：如果原字符串不为空，则返回原字符串，否则返回给定的字串str2 
     */  
    public static String nullString(String str,String str2){            
      str = (str==null)?str2:str;  
      str = (str.equals("null"))?str2:str;  
      str = (str.equals(""))?str2:str;  
      return str;  
    }  
      
    /** 
     * 功能描述：空判断，如果为空，返回给定的字串str2 
     * @param strFileContent 
     * @param str 原字符串 
     * @param str2 给定的字符 
     * @return 
     */  
    public static String emptyString(String str,String str2){           
      str = nullString(str,str2);  
      str = (str.equals(""))?str2:str;  
      return str;  
    }  
      
/** 
 * 功能描述： 对指定字符串的截取 
 *  
 * @param java.lang.String strFile 
 *            给定的字符串，从1开始计算 
 * @param iStart 
 *            起始位置， 
 * @param iLen 
 *            截取的字串长度 
 * @return 
 *        返回：返回截取后的新子串   
 */  
    public static String getStringByLocation(String strFile, int iStart,int iLen) {  
    String strLocate = "";  
    try {  
        if (strFile.length() >= (iStart + iLen)) {  
            strLocate = strFile.substring(iStart - 1, iStart + iLen - 1);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart - 1);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim();// strFile2  
}  
      
    /** 
     * 功能描述：截取指定字符之前的字串 
     * @param java.lang.String strFile  
     *           给定的字符串 
     * @param java.lang.String loc   
     *           指定字符,默认为一个空格 
     * @return 
     *        返回：截取后的字符串 
     */  
    public static String getStringByLocation(String strFile, String loc) {  
    if ("".equals(loc)) {  
        loc = " ";  
    }  
    //  如果不存在指定的字符串loc  
    // strFile = strFile.substring(0,strFile.indexOf(loc));这一句会报错  
    if (strFile.indexOf(loc) == -1)  
        return strFile;  
    try {  
        if (!"".equals(strFile)) {  
            strFile = strFile.trim();  
            strFile = strFile.substring(0, strFile.indexOf(loc));  
        } else {  
            return "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strFile;// strFile2  
}  
      
    /** 
     * 功能描述：根据指定的位置截取字符串，并在字符串后面追加给定的子串 
     * @param java.lang.String strFile  
     *          需要截取的字符串，从1开始计算的 
     * @param iStart  起始位置， 
     * @param iLen    截取的字串长度 
     * @param java.lang.String strAdd   
     *          后面增加指定字符 
     * @return 
     *         返回：截取后的新字符串 
     */  
    public static String getStringByLocation(String strFile, int iStart,int iLen,String strAdd) {  
    String strLocate = "";  
    try {  
        if (strFile.length() >= (iStart + iLen)) {  
            strLocate = strFile.substring(iStart - 1, iStart + iLen - 1);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart - 1);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim()+strAdd;// strFile2  
}  
      
    /** 
     * 功能描述：从指定的起始和截止位置对指定的字符串进行截取 
     * @param java.lang.String strFile  
     *                给定的字符串 
     * @param iStart  起始位置， 
     * @param iEnd    结束位置 
     * @return 
     *        返回： 截取后的新串 
     */  
    public static String getBeginEndLocation(String strFile, int iStart,int iEnd) {  
    String strLocate = "";  
    if (iEnd < 0 || iEnd < iStart) {  
        return "";  
    }  
    try {  
        if (strFile.length() >= (iEnd)) {  
            strLocate = strFile.substring(iStart, iEnd);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim();// strFile2  
}  
      
    /** 
     * 功能描述：根据指定的位置截取字符串，并在字符串后面追加给定的子串 
     * @param java.lang.String strFile  
     *                给定字串 
     * @param iStart   
     *                起始位置， 
     * @param iEnd     
     *                结束位置 
     * @param java.lang.String strAdd   
     *                后面增加指定字符 
     * @return 
     *        返回：截取后的新字符串 
     */  
    public static String getBeginEndLocation(String strFile, int iStart,int iEnd,String strAdd) {  
    String strLocate = "";  
    try {  
        if (strFile.length() >= (iEnd)) {  
            strLocate = strFile.substring(iStart, iEnd);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim() + strAdd;// strFile2  
}  
      
    /** 
     * 功能描述：根据指定的位置截取子字符串 
     * @param java.lang.String strFile  
     *                给定字串 从1开始计算的 
     * @param iStart   
     *                起始位置， 
     * @param iLen     
     *               截取的字串长度 
     * @return 
     *        返回：截取后的子串 
     */  
    public static String getBeginEnd1Location(String strFile, int iStart,int iLen) {  
    String strLocate = "";  
    try {  
        if (strFile.length() >= (iStart + iLen)) {  
            strLocate = strFile.substring(iStart - 1, (iStart + iLen) - 1);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart - 1);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim();// strFile2  
}  
      
    /** 
     * 功能描述：根据指定的位置截取子串，并在后面进行追加指定的字符串 
     * @param java.lang.String  strFile 
     *                给定字串 从1开始计算的 
     * @param iStart   
     *                起始位置， 
     * @param iLen     
     *                截取的字串长度 
     * @param java.lang.String  strAdd   
     *                后面增加指定字符 
     * @return 
     *        返回：   截取并且追加后的子串 
     */  
    public static String getBeginEnd1Location(String strFile, int iStart,int iLen,String strAdd) {  
    String strLocate = "";  
    try {  
        if (strFile.length() >= (iStart + iLen)) {  
            strLocate = strFile.substring(iStart - 1, (iStart + iLen) - 1);  
        } else if (strFile.length() >= iStart) {  
            strLocate = strFile.substring(iStart - 1);  
        } else {  
            strLocate = "";  
        }  
    } catch (Exception e) {  
        System.err.println(e);  
    }  
    return strLocate.trim() + strAdd;// strFile2  
}  
      
    /** 
     * 功能描述: 返回指定长度的空字符串 
     * @param iLen 
     * @return 
     *        返回：指定长度的空串 
     */  
    public static String getSpace(int iLen){  
        StringBuffer str = new StringBuffer("");  
        for(int i = 0;i<iLen;i++){  
            str.append(" ");  
        }  
        return str.toString();  
    }  
      
      
    /** 
     * 功能描述：将字符串转换成整型数据 
     * @param str  字符串 
     * @return  返回：返回转换后的整型数据 
     */  
    public static int stringToInt(String str){  
        str = nullString(str,"0");  
        if(StringOperateUtils.isNumeric(str))  
        {  
            return Integer.parseInt(str);  
        }  
        return 0;  
    }  
      
      
/** 
 * 功能描述: 返回指定个数的指定字符串 
 * @param iLen  
 *              指定个数 
 * @param java.lang.String str2   
 *              指定字符串 
 * @return 
 *        返回：新字符串 
 */  
    public static String getSpace(int iLen,String str2){  
        StringBuffer str = new StringBuffer("");  
        for(int i = 0;i<iLen;i++){  
            str.append(str2);  
        }  
        return str.toString();  
    }  
      
/** 
 *  
 * 功能描述: 返回指定长度的指定字符串        
 * @param java.lang.String str 
 *             原字符串 
 * @param iLen 
 *             增加子串的长度数量，其中iLen-str.getBytes().length 为str2增加的个数 
 * @param java.lang.String str2 
 *             增加的子串 
 * @param java.lang.String strLR 
 *             子串增加的位置，其中strLR为H或h表示在头增加子串，否则在尾部增加 
 * @return 
 *        返回：新的字符串 
 */  
    public static String parseString(String str,int iLen,String str2,String strLR){  
        str = StringOperateUtils.emptyString(str, "");  
    String strTmp = "";  
    int istrLen = str.getBytes().length;  
    for (int i = istrLen; i < iLen; i++) {  
        strTmp += str2;  
    }  
    if ("H".equalsIgnoreCase(strLR)) {  
        return strTmp + str;  
    } else {  
        return str + strTmp;  
    }  
    }  
      
    /** 
     * 功能描述：特殊字符，全角转半角 
     * @param java.lang.String str 
     *          含有全角的字符串 
     * @return 
     *        返回：只有半角的字符串 
     */  
    public static String replaceRN(String str){  
        if (str != null) {  
        str = str.replaceAll("’", "'");  
        str = str.replaceAll("，", ",");  
        str = str.replaceAll("。", ".");  
        str = str.replaceAll("？", "?");  
        str = str.replaceAll("`", "");  
        str = str.replaceAll("；", ";");  
        str = str.replaceAll("：", ":");  
        str = str.replaceAll("！", "!");  
    }  
        return str;  
    }  
      
      
/** 
 * 根据指定长度，给字串补齐,若超过指定长度，则进行截取处理 
 *  
 * @param str 
 *            给定字串 
 * @param iLen 
 *            指定长度 
 * @param type 
 *            保留变量 
 * @param filterFlg 
 *            是否过滤 true :过滤；false：不过滤 
 * @return 
 */  
    public static String getSpaceString(String str,int iLen,String type,boolean filterFlg){           
        if (str == null || str.equals("") || str.equals("null")) {  
        str = " ";  
    }  
    // 过滤全角字符  
    if (filterFlg) {  
        str = replaceRN(str);  
    }  
    // 得到字符长度  
    int iStrLen = str.length();  
    if (iLen > iStrLen) {  
        str += StringOperateUtils.getSpace(iLen - str.getBytes().length);  
    } else {  
        // IF Pos(is_errortext,as_type) = 0 Then is_errortext=is_errortext +  
        // as_type +"~r~n"  
        str = str.substring(0, iLen);  
    }  
    str.replaceAll("\n", "");  
    return str;  
    }  
      
    /** 
     * 功能描述：返回四舍五入的double 
     * @param unroundedValue  
     *           接收的输入参数 
     * @param ai_decimal  
     *           小数位长度 
     * @return 
     *        返回：四舍五入后的double数据 
     */   
public static Double getRoundedDouble(double unroundedValue, int ai_decimal) {  
    int aa=1;          
    for(int i = 0;i<ai_decimal;i++)  
    {  
        aa = aa*10;  
    }  
    double bb = Double.parseDouble("1"+StringOperateUtils.getSpace(ai_decimal, "0") + "." + StringOperateUtils.getSpace(ai_decimal, "0"));    
      
    return Double.valueOf(Math.round(unroundedValue*aa)/bb);  
}  
  
/** 
 * 功能描述：返回四舍五入的double 
 * @param unroundedValue  
 *           接收的输入参数 
 * @param ai_decimal  
 *           小数位长度 
 * @return 
 *        返回：四舍五入后的double数据 
 */  
public static Double getRoundedSimDouble(double unroundedValue, int ai_decimal){  
    double total =  new BigDecimal( unroundedValue ).setScale( ai_decimal, BigDecimal.ROUND_HALF_UP ).doubleValue( );     
    return new Double(total);  
}  
  
  
  
    /** 
     * 功能描述：判断是否是数字 
     * @param java.lang.String str 
     *           接收的字符串参数 
     * @return 
     *         返回：如果是数字，则返回true，否则返回false 
     */  
    public static boolean isNumeric(String str)  
    {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if( !isNum.matches() )  
        {  
            return false;  
        }  
        return true;  
    }   
  
    /** 
     * 功能描述：把\n符号转换为</br> 
     * @param str 
     * @return 
     */  
    public static String formatHtmlStr(String str){  
        if(str == null)  
            return "";  
        return str.replaceAll("\n", "</br>");  
    }  
  
    /** 
     * 功能描述：截取得到从后往前的ilen位非字母的数字 
     *  
     * @param java 
     *            .lang.String str 接收的字符串 
     * @param iLen 
     *            非字母数字的长度，如果数字的位数比这个长度小，则以空格补齐 
     * @return 返回：非字母的数字字符串 
     */  
    public static String getShipId(String str, int iLen) {  
        String strNumber = "";  
        for (int i = str.length(); i > 0; i--) {  
            String strTmp = str.substring(i - 1, i);  
            if ("0123456789".indexOf(strTmp) != -1) {  
                strNumber = strTmp + strNumber;  
            }  
            if (strNumber.length() > (iLen - 1))  
                break;  
        }  
        return strNumber;  
    }  
  
    /** 
     * 功能描述：截取得到从前往后的iLen位非字母的数字 
     *  
     * @param java 
     *            .lang.String str 接收的字符串 
     * @param iLen 
     *            非字母数字的长度，如果数字的位数比这个长度小，则以空格补齐 
     * @return 返回：非字母的数字字符串 
     */  
    public static String getShipIdHead(String str, int iLen) {  
        String strNum = "";  
        for (int i = 0; i < str.length(); i++) {  
            String strTmp = str.substring(i, i + 1);  
            if ("0123456789".indexOf(strTmp) != -1) {  
                strNum = strNum + strTmp;  
            }  
            if (strNum.length() > (iLen - 1)) {  
                break;  
            }  
  
        }  
        return strNum;  
    }  
  
  
    /** 
     * 判断任意一个整数是否素数 
     *  
     * @param n 
     * @return boolean 
     */  
    public static boolean isPrimes(int n) {  
        for (int i = 2; i <= Math.sqrt(n); i++) {  
            if (n % i == 0) {  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /** 
     * 获得任意一个整数的阶乘，递归 
     *  
     * @param n 
     * @return n! 
     */  
    public static int factorial(int n) {  
        if (n == 1) {  
            return 1;  
        }  
        return n * factorial(n - 1);  
    }  
  
    /** 
     * 获得任意一个整数的阶乘，递归 
     *  
     * @param n 
     * @return n! 
     */  
    public static long factorial(long n) {  
        if (n == 1) {  
            return 1;  
        }  
        return n * factorial(n - 1);  
    }  
  
  
    /** 
     * 功能描述：人民币转成大写 
     * @param value    需要转换的金额 
     * @return String  转换后的字符串 
     */  
    public static String rmbToBigString(double value) {  
        char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示  
        char[] vunit = { '万', '亿' }; // 段名表示  
        char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示  
        long midVal = (long) (value * 100); // 转化成整形  
        String valStr = String.valueOf(midVal); // 转化成字符串  
  
        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分  
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分  
  
        String prefix = ""; // 整数部分转化的结果  
        String suffix = ""; // 小数部分转化的结果  
        // 处理小数点后面的数  
        if (rail.equals("00")) { // 如果小数部分为0  
            suffix = "整";  
        } else {  
            suffix = digit[rail.charAt(0) - '0'] + "角"  
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来  
        }  
        // 处理小数点前面的数  
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组  
        char zero = '0'; // 标志'0'表示出现过0  
        byte zeroSerNum = 0; // 连续出现0的次数  
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字  
            int idx = (chDig.length - i - 1) % 4; // 取段内位置  
            int vidx = (chDig.length - i - 1) / 4; // 取段位置  
            if (chDig[i] == '0') { // 如果当前字符是0  
                zeroSerNum++; // 连续0次数递增  
                if (zero == '0') { // 标志  
                    zero = digit[0];  
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {  
                    prefix += vunit[vidx - 1];  
                    zero = '0';  
                }  
                continue;  
            }  
            zeroSerNum = 0; // 连续0次数清零  
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的  
                prefix += zero;  
                zero = '0';  
            }  
            prefix += digit[chDig[i] - '0']; // 转化该数字表示  
            if (idx > 0)  
                prefix += hunit[idx - 1];  
            if (idx == 0 && vidx > 0) {  
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿  
            }  
        }  
  
        if (prefix.length() > 0)  
            prefix += '圆'; // 如果整数部分存在,则有圆的字样  
        return prefix + suffix; // 返回正确表示  
    }  
  
    /** 
     * 功能描述：全角字符转半角字符 
     *  
     * @param QJStr   全角字符 
     * @return String 转换后的半角字符 
     */  
    public static final String QJToBJChange(String qjStr) {  
        char[] chr = qjStr.toCharArray();  
        String str = "";  
        for (int i = 0; i < chr.length; i++) {  
            chr[i] = (char) ((int) chr[i] - 65248);  
            str += chr[i];  
        }  
        return str;  
    }  
  
    /** 
     * 功能描述：去掉字符串中重复的子字符串 
     * @param str    原字符串 
     * @param match  字符串分隔符 
     * @return String  返回分隔后不重复的新字符串 
     */  
    public static String removeSameString(String str , String match) {  
        Set mLinkedSet = new LinkedHashSet();  
        if(match == null || "".equals(match.trim()))  
        {  
            match = " ";  
        }  
        String[] strArray = str.split(match);  
        StringBuffer sb = new StringBuffer();  
  
        for (int i = 0; i < strArray.length; i++) {  
            if (!mLinkedSet.contains(strArray[i])) {  
                mLinkedSet.add(strArray[i]);  
                sb.append(strArray[i] + " ");  
            }  
        }  
        return sb.toString().substring(0, sb.toString().length() - 1);  
    }  
  
  
    /** 
     * 功能描述：返回给定的两个字符串中相同的最大的子串 
     * @param a  字符串 
     * @param b  字符串 
     * @return   返回：返回两个字符串中相同的最大的子串 
     */  
    public static String getMaxMatch(String a, String b) {  
        StringBuffer tmp = new StringBuffer();  
        String maxString = "";  
        int max = 0;  
        int len = 0;  
        char[] aArray = a.toCharArray();  
        char[] bArray = b.toCharArray();  
        int posA = 0;  
        int posB = 0;  
        while (posA < aArray.length - max) {  
            posB = 0;  
            while (posB < (bArray.length - max)) {  
                if (aArray[posA] == bArray[posB]) {  
                    len = 1;  
                    tmp = new StringBuffer();  
                    tmp.append(aArray[posA]);  
                    while ((posA + len < aArray.length)  
                            && (posB + len < bArray.length)  
                            && (aArray[posA + len] == bArray[posB + len])) {  
                        tmp.append(aArray[posA + len]);  
                        len++;  
                    }  
                    if (len > max) {  
                        max = len;  
                        maxString = tmp.toString();  
                    }  
                }  
                posB++;  
            }  
            posA++;  
        }  
        return maxString;  
    }  
  
  
    /** 
     * 功能描述：判断是不是合法字符  
     * @param ch 要判断的字符 
     * @return 返回：如果是合法字符，则返回true，否则返回false 
     */  
    public static boolean isLetter(char ch) {  
          
        String c = String.valueOf(ch);  
          
        if (c == null || c.length() < 0) {  
            return false;  
        }  
        // a-z  
        if (c.compareToIgnoreCase("a") >= 0 && c.compareToIgnoreCase("z") <= 0) {  
            return true;  
        }  
        // 0-9  
        if (c.compareToIgnoreCase("0") >= 0 && c.compareToIgnoreCase("9") <= 0) {  
            return true;  
        }  
        // . - _  
        if (c.equals(".") || c.equals("-") || c.equals("_")) {  
            return true;  
        }  
        return false;  
    }  
      
      
    /** 
     * 功能描述：判断是不是由合法的字符组成的字符串 
     * @param str   需要判断的字符串 
     * @return   返回：如果是合法的则返回true，否则返回false 
     */  
    public static boolean isRealLetter(String str)  
    {  
        if(str == null || str.length() < 0)  
        {  
            return false;  
        }  
        for(int i = 0 ; i < str.length() ; i++)  
        {  
            if(!StringOperateUtils.isLetter(str.charAt(i)))  
            {  
                return false;  
            }  
        }  
            return true;  
    }  
      
      
    /** 
     * 功能描述：通过时间戳和三位随机数生成20位长度的UUID 
     * @return  返回：20位长度的UUID 
     */  
    public static String generateUUID()  
    {  
        Calendar cal = new GregorianCalendar();  
        Random r = new Random();  
        StringBuffer sb = new StringBuffer();  
        sb.append(cal.get(Calendar.YEAR));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.MONTH) + 1 , 2));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.DAY_OF_MONTH) , 2));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.HOUR_OF_DAY) , 2));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.MINUTE) , 2));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.SECOND) , 2));  
        sb.append(StringOperateUtils.addZero(cal.get(Calendar.MILLISECOND) , 3));  
        sb.append(r.nextInt(10));  
        sb.append(r.nextInt(10));  
        sb.append(r.nextInt(10));  
          
        return sb.toString();  
    }  
      
      
    /** 
     * 功能描述：对给定的数字长度不够的在其前面补0 
     * @param date  给定的数字 
     * @param len   需要的长度 
     * @return      返回：返回给定长度的字符串 
     */  
    public static String addZero(int date , int len)  
    {  
        String str = String.valueOf(date);  
        StringBuffer sb = new StringBuffer("");  
        while(str.length() < len)  
        {  
            sb.append("0");  
            len--;  
        }  
        sb.append(str);  
        return sb.toString();  
    }  
      
      
    /** 
     * <pre> 
     * 公民身份号码是特征组合码,由十七位数字本体码和一位数字校验码组成.排列顺序从左至右依次为: 
     * 六位数字地址码,八位数字出生日期码,三位数字顺序码和一位数字校验码。 
     * 1、地址码：表示编码对象常住户口所在县（市、旗、区）的行政区划代码，按 GB/T 2260 的规定执行。  
     * 2、出生日期码：表示编码对象出生的年、月、日，按 * GB/T 7408 的规定执行。年、月、日代码之间不用分隔符。  
     * 例：某人出生日期为 1966年10月26日，其出生日期码为 19661026。 
     * 3、顺序码：表示在同一地址码所标识的区域范围内， 
     * 对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数千分配给女性。  
     * 4、校验码：校验码采用ISO 7064：1983，MOD 11-2 校验码系统。  
     * （1）十七位数字本体码加权求和公式  
     * S = Sum(Ai * Wi), i = * 0, ... , 16 ，先对前17位数字的权求和  
     * Ai:表示第i位置上的身份证号码数字值  
     * Wi:表示第i位置上的加权因子  
     * Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1 
     * （2）计算模 Y = mod(S, 11)  
     * （3）通过模得到对应的校验码  
     * Y: 0 1 2 3 4 5 6 7 8 9 10  
     * 校验码: 1 0 X 9 8 7 6 5 4 3 2 
     * </pre> 
     * 功能描述：验证身份证是否合法 
     * @param java.lang.String idcard 传入身份证参数 
     * @return   
     * 如果验证成功则返回：true   
     * 否则返回：false 
     */  
    public static boolean verifyIDCard(String idcard) {  
        if (idcard.length() == 15) {  
            idcard = StringOperateUtils.updateToeighteen(idcard);  
        }  
        if (idcard.length() != 18) {  
            return false;  
        }  
        //获取输入身份证上的最后一位，它是校验码  
        String checkDigit = idcard.substring(17, 18);  
        //比较获取的校验码与本方法生成的校验码是否相等  
        if (checkDigit.equals(StringOperateUtils.getCheckDigit(idcard))) {  
            return true;  
        }  
        return false;  
    }  
  
    /** 
     * 功能描述：计算18位身份证的校验码 
     * @param java.lang.String eighteenCardID   18位身份证参数 
     * @return 返回18位的身份证字符串 
     */  
    private static String getCheckDigit(String eighteenCardID) {  
        int remaining = 0;  
        if (eighteenCardID.length() == 18) {  
            eighteenCardID = eighteenCardID.substring(0, 17);  
        }  
  
        if (eighteenCardID.length() == 17) {  
            int sum = 0;  
            int[] a = new int[17];  
            //先对前17位数字的权求和  
            for (int i = 0; i < 17; i++) {  
                String k = eighteenCardID.substring(i, i + 1);  
                a[i] = Integer.parseInt(k);  
            }  
            for (int i = 0; i < 17; i++) {  
                sum = sum + weight[i] * a[i];  
            }  
            //再与11取模  
            remaining = sum % 11;  
        }  
        return remaining == 2 ? "X" : String.valueOf(checkDigit[remaining]);  
    }  
  
      
    /** 
     * 功能描述：将15位身份证升级成18位身份证号码 
     * @param java.lang.String fifteenCardID 15位身份证参数 
     * @return  返回：把15位转换为18位的身份证字符串 
     */  
    private static String updateToeighteen(String fifteenCardID) {  
        //15位身份证上的生日中的年份没有19，要加上  
        String eighteenCardID = fifteenCardID.substring(0, 6);  
        eighteenCardID = eighteenCardID + "19";  
        eighteenCardID = eighteenCardID + fifteenCardID.substring(6, 15);  
        eighteenCardID = eighteenCardID + StringOperateUtils.getCheckDigit(eighteenCardID);  
        return eighteenCardID;  
    }  
      
    /** 
     * 功能描述：判断ip地址是否合法 
     * @param strIP  需要判断的ip地址 
     * @return  返回：如果ip地址合法，则返回true，否则返回false 
     */  
    public static boolean isRealIP(String strIP){  
        String[] s = strIP.split("\\.");  
        if(s.length != 4){  
            return false;  
        }  
        for(int i=0;i<s.length;i++){  
            if(Integer.parseInt(s[i]) > 255 || Integer.parseInt(s[i]) < 0)  
                return false;  
        }  
        return true;  
    }  
      
  
    /** 
     * 功能描述：将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理 
     * @param strIP  ip地址 
     * @return  返回：返回转换后的整型数据 
     */  
    public static long ipToLong(String strIP){  
        if(!isRealIP(strIP)){  
            return -1;  
        }  
         long[] ip=new long[4];  
         //先找到IP地址字符串中.的位置  
         int position1=strIP.indexOf(".");  
         int position2=strIP.indexOf(".",position1+1);  
         int position3=strIP.indexOf(".",position2+1);  
         //将每个.之间的字符串转换成整型  
         ip[0]=Long.parseLong(strIP.substring(0,position1));  
         ip[1]=Long.parseLong(strIP.substring(position1+1,position2));  
         ip[2]=Long.parseLong(strIP.substring(position2+1,position3));  
         ip[3]=Long.parseLong(strIP.substring(position3+1));  
         return (ip[0]<<24)+(ip[1]<<16)+(ip[2]<<8)+ip[3];   
    }  
  
      
    /** 
     * 功能描述：将10进制整数形式转换成127.0.0.1形式的IP地址 
     * @param longIP  整型类型的ip 
     * @return    返回：返回转换后的真正的ip地址 
     */  
    public static String longToIP(long longIP){  
        if(longIP > 4294967295l){  
            return "输入的整数太大，目前还没有这样的IP地址!";  
        }  
         StringBuffer sb=new StringBuffer("");  
         //直接右移24位  
         sb.append(String.valueOf(longIP>>>24));  
         sb.append(".");            
         //将高8位置0，然后右移16位  
         sb.append(String.valueOf((longIP&0x00FFFFFF)>>>16));   
         sb.append(".");  
         sb.append(String.valueOf((longIP&0x0000FFFF)>>>8));  
         sb.append(".");  
         sb.append(String.valueOf(longIP&0x000000FF));  
         return sb.toString();   
    }   
      
      
}  
