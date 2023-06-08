package ToDoList;

import java.io.Serializable;

public class CantfindImageException extends Throwable implements Serializable {
    private String message=null;
    CantfindImageException(){
        message="图片加载失败,图片不存在或者路径错误!";
    }
    public String getMessage(){
        return message;
    }
}
