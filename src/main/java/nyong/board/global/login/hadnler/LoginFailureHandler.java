package nyong.board.global.login.hadnler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 오류 핸들러이지만 보안을 위해 200(Success) 반환
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        response.getWriter().write("fail");
        log.info("로그인에 실패했습니다");
    }
}
