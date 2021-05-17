package test.xyz.srclab.spring.boot.web.message;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xyz.srclab.spring.boot.web.message.HttpReqMessage;
import xyz.srclab.spring.boot.web.servlet.WebServlets;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("test")
@RestController
public class MessageController {

    @RequestMapping("internal/message")
    public RespBody testMessage(HttpReqMessage<ReqBody> httpReqMessage) {
        RespBody respBody = new RespBody();
        respBody.setResp1(httpReqMessage.getBody().getReq1());
        respBody.setResp2(httpReqMessage.getBody().getReq2());
        return respBody;
    }

    @RequestMapping("message")
    public ModelAndView testMessage(ReqBody reqBody, HttpServletRequest servletRequest) {
        HttpReqMessage<ReqBody> httpReqMessage = HttpReqMessage.newHttpReqMessage();
        httpReqMessage.setMetadata(WebServlets.toHttpHeaders(servletRequest));
        httpReqMessage.setBody(reqBody);
        servletRequest.setAttribute("httpReqMessage", httpReqMessage);
        return new ModelAndView("internal/message");
    }
}
