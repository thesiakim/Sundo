package commf.message;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

@SuppressWarnings("all")
public class Message {
	private static MessageSourceAccessor messageAcc = null;

	public void setMessageSourceAccessor(MessageSourceAccessor messageAcc) {
		this.messageAcc = messageAcc;
	}

	/**
	 * KEY에 해당하는 메세지 반환
	 *
	 * @param  key
	 * @return
	 */
	public static String getMessage(String key) {
		return messageAcc.getMessage(key, Locale.getDefault());
	}

	/**
	 * KEY에 해당하는 메세지 반환
	 *
	 * @param  key
	 * @return
	 */
	public static String getMessage(String key, Object[] objs) {
		return messageAcc.getMessage(key, objs, Locale.getDefault());
	}
}
