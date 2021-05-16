package test.xyz.srclab.spring.boot.web.message;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.srclab.spring.boot.web.message.HttpReqMessage;

@RequestMapping("test")
@RestController
public class MessageController {

    @RequestMapping("message")
    public RespBody testMessage(HttpReqMessage<ReqBody> reqMessage) {
        RespBody respBody = new RespBody();
        respBody.setResp1(reqMessage.getBody().getReq1());
        respBody.setResp2(reqMessage.getBody().getReq2());
        return respBody;
    }

    //@RequestMapping("message")
    public RespBody testMessage(ReqBody reqBody) {
        RespBody respBody = new RespBody();
        respBody.setResp1(reqBody.getReq1());
        respBody.setResp2(reqBody.getReq2());
        return respBody;
    }
}
