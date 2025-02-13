package kr.hhplus.be.server.api.mock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockDataPlatformService {

    public void sendMessage(String message) {
        try {
            log.info("Success: {}", message);
        } catch(Exception e) {
            log.error("Fail", e);
        }
    }

}
