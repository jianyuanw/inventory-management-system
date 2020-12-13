package sg.edu.iss.ims.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/security/user")
    public String user() {
        return "<h1>Welcome User</h1>";
    }

    @GetMapping("/security/user/test")
    public String userTest() {
        return "<h1>Welcome User</h1>";
    }

    @GetMapping("/security/admin")
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }

    @GetMapping("/security/admin/test")
    public String adminTest() {
        return "<h1>Welcome Admin</h1>";
    }
}
