package kr.nanoit.dto.messsage_Structure;

import java.io.Serializable;


/**
* Serializable 사용이유 
 * 생성한 객체를 파일로 저장할 경우, 저장한 객체를 읽을 경우, 다른 서버에서 생성한 객체를 받을 경우
*/

public interface MessageService extends Serializable {

    String getMessageServiceType();


    String getProtocol();
}
