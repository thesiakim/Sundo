package business.biz;

public class Constants {
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//	Request Result에서 사용하는 상수
	//
	/////////////////////////////////////////////////////////////////////////////////////////////////////

	/** 결과 키 */
	public final static String KEY_RESULT = "RESULT";
	
	/** 메시지 키 */
	public final static String KEY_MSG = "MSG";
	
	/** 리스트 키 */
	public final static String KEY_LIST = "LIST";
	
	/** 데이터 키 */
	public final static String KEY_DATA = "DATA";
	
	/** 개수 키 */
	public final static String KEY_COUNT = "COUNT";
	
	/** 페이지 키 */
	public final static String KEY_PAGE = "PAGE";
	
	/** 페이지 컬럼 키 */
	public final static String KEY_PAGE_ROW = "PAGEROW";
	
	/** 총 개수 키 */
	public final static String KEY_TOTAL_COUNT = "TOTAL_COUNT";
	
	/** 메뉴 권한 체크 키 */
	public final static String KEY_MENU_AUTH = "MENU_AUTH";
	
	
	
	/** 결과 - 성공 */
	public final static String VALUE_RESULT_SUCCESS = "SUCCESS";
	
	/** 결과 - 실패 */
	public final static String VALUE_RESULT_FAILURE = "FAILURE";
	
	/** 결과 - 파일 업로드 실패 */
	public final static String VALUE_RESULT_UPLOAD_FAILRUE = "UPLOAD_FAILRUE";
	
	/** 메세지 - 성공 */
	public final static String VALUE_MSG_SUCEESS = "SUCCESS";
	
	/** 메세지 - 실패 */
	public final static String VALUE_MSG_FAILURE = "FAILURE";
	
	/** 메세지 - 파일 사이즈 체크 */
	public final static String VALUE_MSG_UPLOAD_SIZE_CHECK = "UPLOAD_SIZE_CHECK";
	
	/** 메세지 - 파일 불허용  */
	public final static String VALUE_MSG_NOT_ALLOW_FILE_EXT = "UPLOAD_NOT_ALLOW_FILE_EXT";
	
	/** 메세지 - 잘못된 연결 */
	public final static String VALUE_MSG_WRONG_ACCESS = "WRONG_ACCESS";
	
	/** 메뉴 권한 - 성공 */
	public final static String VALUE_MENU_AUTH_SUCCESS = "SUCCESS";
	
	/** 메뉴 권한 - 실패 */
	public final static String VALUE_MENU_AUTH_FAILURE = "FAILURE";
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	사용자 계정 관련 상수들
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
	/** 사용자 권한 - 관리자 */
	public final static String ROLE_ADMIN = "ROLE_ADMIN";
	
	/** 사용자 권한 - 사용자 */
	public final static String ROLE_USER = "ROLE_USER";
	
	/** 사용자 권한 - 사이트 접근 제한자 */
	public final static String ROLE_FAILURE = "ROLE_FAILURE";
	
	/** 세션 - 다른 사람의 로그인 함 */
	public final static String SESSION_ANOTHER = "SESSION_ANOTHER";
	
	/** 세션 - 다른 사람이 로그인 하여 로그아웃 됨 */
	public final static String SESSION_ANOTHER_OUT = "SESSION_ANOTHER_OUT";
	
	/** 세션 - 세션이 종료되었음 */
	public final static String SESSION_DELETE = "SESSION_DELETE";
	
	/** 세션 - 접근이 제한된 요청 */
	public final static String ACCESS_FAULT = "ACCESS_FAULT";
	
	
	/** 로그인 - 사용자 정보 없음 */
	public final static String LOGIN_FAILURE_NO_USER = "NO_USER";
	
	/** 로그인 - 패스워드 틀림 */
	public final static String LOGIN_FAILURE_NO_PASSWORD = "NO_PASSWORD";
	
	/** 로그인 - 미승인 사용자 */
	public final static String LOGIN_FAILURE_NO_ACCEPT = "NO_ACCEPT";	
	
	/** 사용자 비밀번호 변경주기(단위:일) */
	public final static int USER_PASSWORD_CHANGE_PRIOD = 180;	
	
	/** 삭제된 사용자 키*/
	public final static String DELETE_USER_KEY = "DELETE_USER";
	/** 삭제된 사용자 값*/
	public final static String DELETE_USER_VALUE = "삭제계정";
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//
//	알람 관련 상수들
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/** 알람 - 우측 하단 최대 보여줄 개수 */
	public final static int ALARM_MAX_COUNT = 3;
	
	/** 기상정보 - 레이더 영상 이미지 시간 간격(분) */
	public final static Integer RADAR_IMAGE_TIME = 10;
	
	/** 기상정보 - 위성 영상 이미지 시간 간격(분) */
	public final static Integer SATELLITE_IMAGE_TIME = 15;
	
	/** 기상정보 - 영상 이미지 체크 개수 */
	public final static Integer IMAGE_CHECK_COUNT = 3;
	
/////////////////////////////////////////////////////////////////////////////////////////////////////
//
//메시지, 사용자, 사용자 그룹, 문자채팅, 영상통화 사용하는  상수
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public final static int LIST_DEFAULT_LIMIT = 30;
	public final static int MESSAGE_LIST_DEFAULT_LIMIT = 10;		// communication_normal.js 에서도 수정해 주어야 함.
	public final static int VIDEO_LIST_DEFAULT_LIMIT = 30;
	public final static int LETTER_LIST_DEFAULT_LIMIT = 10;
	
	//메시지 첨부파일 저장경로
	public final static String CMTMSG_FILE_KEY= "CMTMSG"; // PATH 정보 KEY
	public final static String CMTMSG_FILE_PATH= "C:\\MOSPA_SAVE\\CMTMSG\\"; // PATH 정보 KEY
	public final static String CMTMSG_SERVER_FILE_PATH = "/data/ndmi/data/cmtmsg/"; //레파지토리 절대경로
	
	public final static String ETC_FILE_PATH= "C:\\MOSPA_SAVE\\ETC\\"; // PATH 정보 KEY
	public final static String ETC_SERVER_FILE_PATH = "/data/ndmi/data/etc/"; //레파지토리 절대경로
	
	//문자채팅 첨부파일 저장경로
	public final static String LETTERCHAT_FILE_KEY= "LETTERCHAT";// PATH 정보 KEY
	public final static String LETTERCHAT_FILE_PATH = "c://test";//레파지토리 절대경로
	
	//사용자 생성 공간정보 첨부파일 저장 경로
	public final static String ROUTE_FILE_PATH= "C:\\MOSPA_SAVE\\ROUTE\\"; // PATH 정보 KEY
	public final static String ROUTE_SERVER_FILE_PATH = "/data/ndmi/data/route/"; // 레파지토리 절대 경로

	//사용자 생성 공간정보 KML 출력 경로
	public final static String KML_FILE_PATH= "C:\\MOSPA_SAVE\\KML\\"; // PATH 정보 KEY
	public final static String KML_SERVER_FILE_PATH = "/data/ndmi/data/kml/"; // 레파지토리 절대 경로


/////////////////////////////////////////////////////////////////////////////////////////////////////
//
// Cloud Message 상수
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public final static String GCM_KEY_TYPE = "RECEIVE_TYPE";
	public final static Integer GCM_TYPE_MESSAGE = 1;
	public final static Integer GCM_TYPE_ALARM = 2;
	public final static Integer GCM_TYPE_REPASS = 3;
	public final static Integer GCM_TYPE_LOGOUT = 4;
	public final static Integer GCM_TYPE_INVITE = 5;
	
	public final static String GCM_KEY_MESSAGE = "MSG";
	public final static String GCM_KEY_DATA_ID = "DATA_ID";
	

	// WebSocket Port
	public final static Integer WEBSOCKET_PORT = 9000;

	// 시스템 모니터링 관련 상수
	// 주기유형코드 - CRON
	public final static String CD_PERIOD_TYPE_CRON = "SBB-CD-051-1";
	// 주기유형코드 - Task Scheduler
	public final static String CD_PERIOD_TYPE_TASK_SCHEDULER = "SBB-CD-051-2";
	// 작업실행결과 코드 - 정상
	public final static String CD_JOB_EXEC_SUCCESS = "SBB-CD-052-1";
	// 작업실행결과 코드 - 실패
	public final static String CD_JOB_EXEC_FAILURE = "SBB-CD-052-2";
	// 알람코드 - 디스크 저장공간 부족
	public final static String CD_ALARM_DISK = "SBB-CD-053-1";
	// 알람코드 - CPU 처리용량 부족
	public final static String CD_ALARM_CPU = "SBB-CD-053-2";
	// 알람코드 - 메모리 용량 부족
	public final static String CD_ALARM_MEM = "SBB-CD-053-3";
	// 알람코드 - 모듈동작 실패
	public final static String CD_ALARM_FAILURE = "SBB-CD-053-4";
	// 알람코드 - 모듈동작 지연
	public final static String CD_ALARM_DELAY = "SBB-CD-053-5";
	// 스케줄링 작업유형 - 서버 알람
	public final static String CD_JOB_TYPE_SERVER = "SBB-CD-054-1";
	// 스케줄링 작업유형 - 연계 모듈
	public final static String CD_JOB_TYPE_LINK = "SBB-CD-054-2";
	// 스케줄링 작업유형 - 데이터베이스 알람
	public final static String CD_JOB_TYPE_DB = "SBB-CD-054-3";

	// 모바일 알람 타입
	// 서버 알람 (서버 부하율 - 디스크, 메모리, CPU 관련 알람)
	public final static String CD_MOBILE_ALARM_TYPE_S = "S";
	// 연계모듈 알람 (실패, 지연)
	public final static String CD_MOBILE_ALARM_TYPE_M = "M";
	// 로그인 알람
	public final static String CD_MOBILE_ALARM_TYPE_L = "L";

	// 모바일 알람 인증 3회 실패
	public static final String MOBILE_AUTH_3_FAILS_MSG = "MOBILE_AUTH_3_FAILS_MSG";

	// 사용자 비밀번호 체크용
	public static final String PASSWORD_CHECK_UPPERCASES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String PASSWORD_CHECK_LOWERCASES = "abcdefghijklmnopqrstuvwxyz";
	public static final String PASSWORD_CHECK_NUMBERS = "0123456789";
	public static final String PASSWORD_CHECK_CHARACTERS = "!@#$%^&*?_~";
	public static final Integer PASSWORD_CHECK_SERIAL_LIMIT = 3;

	// 서버 사용률 수치 - 평균 구간
	public static final String USAGE_AVERAGE_PERIOD = "10 min 1 sec";

	// 과거재난발생이력 팝업 차트 유형 - 발생건수
	public static final String CD_DISASTER_HISTORY_CHART_TYPE_OCCUR = "occur";
	// 과거재난발생이력 팝업 차트 유형 - 인명/재산피해
	public static final String CD_DISASTER_HISTORY_CHART_TYPE_DAMAGE = "damage";
	// 레이더 이미지 종류
	public static final String RADAR_IMAGE_TYPE = "KMA";
}