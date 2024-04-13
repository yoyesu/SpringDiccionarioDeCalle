package steps;

import com.mdlsf.springdiccionariodecalle.controller.EntryController;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class CucumberSteps {
    @MockBean
    EntryRepository entryRepository;

    TestContext testContext = new TestContext();

    @Given("there are entries in the database")
    public void thereAreEntriesInDatabase() {
        List<Entry> entries = new ArrayList<>();
        for (int i=0; i < 20; i++) {
            entries.add(Entry.builder().term("term " + i).def("def " + i).build());
        }
        given(this.entryRepository.findAll()).willReturn(entries);

    }

    @When("a request to get 10 entries is received")
    public void aGetRequestIsReceived(){
        EntryController controller = new EntryController(entryRepository);
        ResponseEntity<List<Entry>> response = controller.getAllEntries(10);
        testContext.setGetMethodResponse(response);
    }

    @Then("10 entries are returned")
    public void entriesAreReturned() {
        System.out.println(testContext.getGetMethodResponse());
        Assertions.assertEquals(testContext.getGetMethodResponse().getBody().size(), 10);
    }
}
