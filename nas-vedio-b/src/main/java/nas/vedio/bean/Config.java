package nas.vedio.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "vedio",ignoreUnknownFields=true)
public class Config {
    public String baseImagePath;
    public String baseVedioPath;
    public String idbeg;
    public String idend;
}
