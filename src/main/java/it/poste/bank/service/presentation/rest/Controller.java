package it.poste.bank.service.presentation.rest;

import io.opentracing.Tracer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.poste.bank.service.application.BankService;
import it.poste.bank.service.application.model.Account;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${service.api.path}")
@Slf4j
@Api(tags="Bank service Controller")
public class Controller {

    @Value("${server.port}")
    int serverPort;

    @Value("${server.address}")
    String serverAddress;

    @Value("${bank.service.version}")
    String version;

    @Autowired
    private Tracer tracer;

    @Autowired
    private BankService bankService;

    private static final String REQUEST_TYPE = "request_type";

    /**
     * ******************** API *****************************
     */
    
    
    /**
     * Get app version
     * @return app version
     */
    @ApiOperation(value = "App version")
    @GetMapping("/info")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK")
            }
    )
    @ResponseStatus(code = HttpStatus.OK)
    public String version() {
        return version;
    }    
    
    /**
     * Readiness probe
     * @throws java.lang.Exception if any err occurs
     */
    @ApiOperation(value = "Retrieve all bank accounts")
    @GetMapping("/readiness")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 500, message = "Internal server error")}
    )
    @ResponseStatus(code = HttpStatus.OK)
    public void readiness() throws Exception {
        bankService.readiness();
    }    
    
    
    
    /**
     * Retrieve all bank accounts
     * @return accounts list
     * @throws java.lang.Exception if any err occurs
     */
    @ApiOperation(value = "Retrieve all bank accounts")
    @GetMapping("/accounts")
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 500, message = "Internal server error")}
    )
    @ResponseStatus(code = HttpStatus.OK)
    public List<Account> getAccounts() throws Exception {

        // set Jaeger tag
        tracer.activeSpan().setTag(REQUEST_TYPE, "getAccounts");
                
        return bankService.getAccounts();
    }    
    
    
    
    /**
     * Create new bank account
     * @param account account data
     * @throws java.lang.Exception
     */
    @ApiOperation(value = "Create new bank account")
    @PostMapping(path = "/create-account", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "OK"),
                @ApiResponse(code = 400, message = "Invalid ID supplied"),
                @ApiResponse(code = 500, message = "Internal server error")}
    )
    @ResponseStatus(code = HttpStatus.OK)
    public void createAccount(
            @ApiParam("Account client data") @RequestBody Account account
    ) throws Exception {
        // set Jaeger tag
        tracer.activeSpan().setTag(REQUEST_TYPE, "createAccount");
        bankService.createAccount(account);
    }
}
