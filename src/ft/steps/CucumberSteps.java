package steps;

import com.mdlsf.springdiccionariodecalle.controller.EntryController;
import com.mdlsf.springdiccionariodecalle.entities.Entry;
import com.mdlsf.springdiccionariodecalle.repos.EntryRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

public class CucumberSteps {
    @MockBean(EntryRepository.class)
    EntryRepository entryRepository;

    @Autowired
    EntryController controller;

    TestContext testContext = new TestContext();
    List<Entry> entries = new ArrayList<>();

    @Given("there are entries in the database")
    public void thereAreEntriesInDatabase() {

        for (int i=0; i < 20; i++) {
            entries.add(Entry.builder().term("term " + i).def("def " + i).build());
        }


    }

    @When("a request to get 10 entries is received")
    public void aGetRequestIsReceived(){
        given(entryRepository.findAll()).willReturn(entries);
        ResponseEntity<List<Entry>> response = controller.getAllEntries(10);
        testContext.setGetMethodResponse(response);
    }

    @Then("10 entries are returned")
    public void entriesAreReturned() {
        System.out.println(testContext.getGetMethodResponse());
        Assertions.assertEquals(testContext.getGetMethodResponse().getBody().size(), 10);
    }
}
