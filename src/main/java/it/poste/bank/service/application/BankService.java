package it.poste.bank.service.application;


import org.springframework.stereotype.Component;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import it.poste.bank.service.application.model.Account;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Bank features
 */
@Component("bankService")
public class BankService {

    final Counter counter;
    final Timer timer;
    
    @Value("${bank.commands.url.readiness}")
    String commandReadinessUrl;
    
    @Value("${bank.query.url.readiness}")
    String queryReadinessUrl;
    
    @Value("${bank.commands.url.create.account}")
    String createAccountUrl;
    
    @Value("${bank.query.url.list.account}")
    String listAccountsUrl;
    
    @Autowired
    private RestTemplate restTemplate;

    public BankService(MeterRegistry registry) {
        this.counter = Counter.builder("poste.bank.service.create.account.counter").register(registry);
        this.timer = Timer.builder("poste.bank.service.create.account.timer").register(registry);
    }

    /**
     * Readiness method, check readiness on sub modules
     * @throws Exception if any error occurs
     */
    public void readiness() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        // check query module url
        restTemplate.exchange(queryReadinessUrl, HttpMethod.GET, entity, Void.class).getBody();        
        
        // check command module url
        restTemplate.exchange(commandReadinessUrl, HttpMethod.GET, entity, Void.class).getBody();        
    }

    /**
     * Get all accounts list
     * @return all accounts list
     * @throws Exception if any error occurs
     */
    @Timed(value = "poste.bank.service.get.account.timer1", description = "getAccounts method", extraTags = {
        "account", "list"
    })
    public List<Account> getAccounts() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        List<Account> result = restTemplate.exchange(listAccountsUrl, HttpMethod.GET, entity, List.class).getBody();        
        return result;
    }
    
    /**
     * Create new account
     * @param account account info
     * @throws Exception if any error occurs
     */
    @Timed(value = "poste.bank.service.create.account.timer1", description = "createAccount method", extraTags = {
        "account", "create"
    })
    public void createAccount(Account account) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Account> requestEntity = new HttpEntity<>(account, headers);
        restTemplate.exchange(createAccountUrl, HttpMethod.POST, requestEntity, String.class);
        counter.increment();
    }

}
