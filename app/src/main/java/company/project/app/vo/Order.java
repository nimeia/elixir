package company.project.app.vo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
@NoArgsConstructor
@Setter
@Getter
public class Order implements Serializable {

    private Long id;

    void xx(){
//        log.info("===");
//        getId();
    }
}
