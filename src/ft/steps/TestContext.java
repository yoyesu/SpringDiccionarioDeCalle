package steps;

import com.mdlsf.springdiccionariodecalle.entities.Entry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
public class TestContext {

    private ResponseEntity<List<Entry>> getMethodResponse;
}
