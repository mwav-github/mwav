
package net.mwav.common.module;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * <pre>description : Utility Class for Mathematical operations </pre>
 * @class name : MathLib.java
 * @author : (정) 남동희
            (부) 김주성
 * @since : 2019. 12. 02.
 * @version : v1.0
 * @see
 * @history :
   ----------------------------------------
   * Modification Information(개정이력)
   ----------------------------------------
           수정일          수정자     		수정내용
   --------    --------    ----------------
   2019. 12. 02.    남동희	최초 생성    
 */
public class MathLib {

	private MathLib() {
	}

	private static class MathLibHolder {
		private static final MathLib mathLib = new MathLib();
	}

	/**
	 * 
	 * @method name : getInstance
	 * @author : (정) 남동희
	             (부)
	 * @since  : 2019. 12. 02.
	 * @version : v1.0
	 * @see :
	 * @description : Instance method of MathLib Class
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
	     수정일     		수정자        	수정내용
	   --------    --------    ----------------
	   2019. 12. 02. 남동희		최초 생성     
	 * @param :
	 * @return : MathLib Instance
	 * @throws : 
	 <pre>
	 * {@code : 
	 * MathLib mathLib = MathLib.getInstance();
	 * } 
	 </pre>
	 */
	public static MathLib getInstance() {
		return MathLibHolder.mathLib;
	}

	public String GetFileSizeUnit(long filesize) {
		float tmp = filesize; //Int32.Parse(filesize);

		String tmstr = "", tmphead = "", tmptail = "";

		if (tmp > 1024000) {
			tmstr = String.valueOf(tmp / 1024000);

			if (tmstr.lastIndexOf(".") > 0) {
				tmphead = tmstr.substring(0, tmstr.lastIndexOf("."));

				if (tmstr.substring(tmstr.lastIndexOf(".")).length() > 3) {
					tmptail = tmstr.substring(tmstr.lastIndexOf("."), 3) + "MB";
				} else {
					tmptail = tmstr.substring(tmstr.lastIndexOf(".")) + "MB";
				}
				return tmphead + tmptail;
			} else {
				return tmstr + "MB";
			}
		} else if (tmp > 1024) {
			tmstr = String.valueOf(tmp / 1024);

			if (tmstr.lastIndexOf(".") > 0) {
				tmphead = tmstr.substring(0, tmstr.lastIndexOf("."));

				if (tmstr.substring(tmstr.lastIndexOf(".")).length() > 3) {
					tmptail = tmstr.substring(tmstr.lastIndexOf("."), 3) + "KB";
				} else {
					tmptail = tmstr.substring(tmstr.lastIndexOf(".")) + "KB";
				}
				return tmphead + tmptail;
			} else {
				return tmstr + "KB";
			}
		} else {
			return filesize + "byte";
		}
	}

	public int generateRandomNumber(int lowNumber, int highNumber) {
		int returnValue;
		Random r = new Random();
		returnValue = r.nextInt(highNumber - lowNumber + 1) + lowNumber;
		return returnValue;
	}

	public int generateRandomNumber(int highNumber) {
		int returnValue;
		returnValue = generateRandomNumber(0, highNumber);
		return returnValue;
	}

	/**
	 * 특정숫자 집합에서 랜덤 숫자를 구하는 기능 시작숫자와 종료숫자 사이에서 구한 랜덤 숫자를 반환한다
	 *
	 * @param startNum - 시작숫자
	 * @param endNum - 종료숫자
	 * @return 랜덤숫자
	 * @see
	 */
	public int getRandomNum(int startNum, int endNum) {
		int randomNum = 0;

		// 랜덤 객체 생성
		SecureRandom rnd = new SecureRandom();

		do {
			// 종료숫자내에서 랜덤 숫자를 발생시킨다.
			randomNum = rnd.nextInt(endNum + 1);
		} while (randomNum < startNum); // 랜덤 숫자가 시작숫자보다 작을경우 다시 랜덤숫자를 발생시킨다.

		return randomNum;
	}

	/**
	 * 특정 숫자 집합에서 특정 숫자가 있는지 체크하는 기능 12345678에서 7이 있는지 없는지 체크하는 기능을 제공함
	 *
	 * @param sourceInt - 특정숫자집합
	 * @param searchInt - 검색숫자
	 * @return 존재여부
	 * @see
	 */
	public Boolean getNumSearchCheck(int sourceInt, int searchInt) {
		String sourceStr = String.valueOf(sourceInt);
		String searchStr = String.valueOf(searchInt);

		// 특정숫자가 존재하는지 하여 위치값을 리턴한다. 없을 시 -1
		if (sourceStr.indexOf(searchStr) == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 숫자타입을 문자열로 변환하는 기능 숫자 20081212를 문자열 '20081212'로 변환하는 기능
	 *
	 * @param srcNumber - 숫자
	 * @return 문자열
	 * @see
	 */
	public String getNumToStrCnvr(int srcNumber) {
		String rtnStr = null;

		rtnStr = String.valueOf(srcNumber);

		return rtnStr;
	}

	/**
	 * 숫자타입을 데이트 타입으로 변환하는 기능
	 * 숫자 20081212를 데이트타입  '2008-12-12'로 변환하는 기능
	 * @param srcNumber - 숫자
	 * @return String
	 * @see
	 */
	public String getNumToDateCnvr(int srcNumber) {

		String pattern = null;
		String cnvrStr = null;

		String srcStr = String.valueOf(srcNumber);

		// Date 형태인 8자리 및 14자리만 정상처리
		if (srcStr.length() != 8 && srcStr.length() != 14) {
			throw new IllegalArgumentException("Invalid Number: " + srcStr + " Length=" + srcStr.trim().length());
		}

		if (srcStr.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (srcStr.length() == 14) {
			pattern = "yyyyMMddhhmmss";
		}

		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern, Locale.KOREA);

		Date cnvrDate = null;

		try {
			cnvrDate = dateFormatter.parse(srcStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		cnvrStr = String.format("%1$tY-%1$tm-%1$td", cnvrDate);

		return cnvrStr;

	}

	/**
	 * 체크할 숫자 중에서 숫자인지 아닌지 체크하는 기능
	 * 숫자이면 True, 아니면 False를 반환한다
	 * @param checkStr - 체크문자열
	 * @return 숫자여부
	 * @see
	 */
	public Boolean getNumberValidCheck(String checkStr) {

		int i;
		//String sourceStr = String.valueOf(sourceInt);

		int checkStrLt = checkStr.length();

		for (i = 0; i < checkStrLt; i++) {

			// 아스키코드값( '0'-> 48, '9' -> 57)
			if (checkStr.charAt(i) > 47 && checkStr.charAt(i) < 58) {
				continue;
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * 특정숫자를 다른 숫자로 치환하는 기능 숫자 12345678에서 123를 999로 변환하는 기능을 제공(99945678)
	 *
	 * @param srcNumber - 숫자집합
	 * @param cnvrSrcNumber - 원래숫자
	 * @param cnvrTrgtNumber - 치환숫자
	 * @return 치환숫자
	 * @see
	 */
	public int getNumberCnvr(int srcNumber, int cnvrSrcNumber, int cnvrTrgtNumber) {

		// 입력받은 숫자를 문자열로 변환
		String source = String.valueOf(srcNumber);
		String subject = String.valueOf(cnvrSrcNumber);
		String object = String.valueOf(cnvrTrgtNumber);

		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;

		// 원본숫자에서 변환대상숫자의 위치를  찾는다.
		while (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject)); // 변환대상숫자 위치까지 숫자를 잘라낸다
			nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			source = nextStr;
			rtnStr.append(preStr).append(object); // 변환대상위치 숫자에 변환할 숫자를 붙여준다.
		}
		rtnStr.append(nextStr); // 변환대상 숫자 이후 숫자를 붙여준다.

		return Integer.parseInt(rtnStr.toString());
	}

	/**
	 * 특정숫자가 실수인지, 정수인지, 음수인지 체크하는 기능 123이 실수인지, 정수인지, 음수인지 체크하는 기능을 제공함
	 *
	 * @param srcNumber - 숫자집합
	 * @return -1(음수), 0(정수), 1(실수)
	 * @see
	 */
	public int checkRlnoInteger(double srcNumber) {

		// byte 1바이트 		▶소수점이 없는 숫자로, 범위 -2^7 ~ 2^7 -1
		// short 2바이트		▶소수점이 없는 숫자로, 범위 -2^15 ~ 2^15 -1
		// int 4바이트 		▶소수점이 없는 숫자로, 범위 -2^31 ~ 2^31 - 1
		// long 8바이트 		▶소수점이 없는 숫자로, 범위 -2^63 ~ 2^63-1

		// float 4바이트		▶소수점이 있는 숫자로, 끝에 F 또는 f 가 붙는 숫자 (예:3.14f)
		// double 8바이트	▶소수점이 있는 숫자로, 끝에 아무것도 붙지 않는 숫자 (예:3.14)
		//							▶소수점이 있는 숫자로, 끝에 D 또는 d 가 붙는 숫자(예:3.14d)

		String cnvrString = null;

		if (srcNumber < 0) {
			return -1;
		} else {
			cnvrString = String.valueOf(srcNumber);

			if (cnvrString.indexOf(".") == -1) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	/** 
	 * <pre>description : returns random Integer value </pre>
	 * @method name : getRandomInt
	 * @author : (정) 남동희
	             (부)
	 * @since  : 2019. 12. 02.
	 * @version : v1.0
	 * @see : java.util.Random 
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
		  수정일 	          수정자    		        수정내용
	   --------    --------    ----------------
	   2019. 12. 02.  남동희		최초 생성
	 * @param : A boundary
	 * @return : Random Integer value in boundary
	 * @throws : java.lang.IllegalStateException
	 <pre>
	 * {@code : 예제 코드 작성
	 * mathLib.getRandomInt(2,5) -> 2 || 3 || 4 || 5
	 * } 
	 </pre>
	*/
	public int getRandomInt(int min, int max) throws Exception {
		if (min > max) {
			throw new IllegalStateException();
		}
		return new Random().nextInt((max - min) + 1) + min;
	}

	/** 
	 * <pre>description : returns random Integer value </pre>
	 * @method name : getRandomInt
	 * @author : (정) 남동희
	             (부)
	 * @since  : 2019. 12. 02.
	 * @version : v1.0
	 * @see : java.util.Random 
	 * @history :
	   ----------------------------------------
	   * Modification Information(개정이력)
	   ----------------------------------------
		  수정일 	          수정자    		        수정내용
	   --------    --------    ----------------
	   2019. 12. 02.  남동희		최초 생성
	 * @param : A boundary
	 * @return : Random Integer value in boundary
	 * @throws : java.lang.IllegalStateException
	 <pre>
	 * {@code : 예제 코드 작성
	 * mathLib.getRandomInt(2) -> 0 || 1 || 2
	 * mathLib.getRandomInt(0) -> 0
	 * } 
	 </pre>
	*/
	public int getRandomInt(int boundary) throws Exception {
		return (boundary > 0 ? 1 : -1) * new Random().nextInt(Math.abs(boundary) + 1);
	}
}
