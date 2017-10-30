package se.rosscom.shopper.business.authentication.boundary;

import se.rosscom.shopper.business.account.boundary.AccountService;
import se.rosscom.shopper.business.account.entity.Account;
import se.rosscom.shopper.business.authentication.entity.Token;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.Base64;

@Path("auth")
public class AuthenticationResource {

    @Inject
    private AccountService accountService;

    @Inject
    private TokenService tokenService;

    @Inject
    private StringHelper stringHelper;

    @GET
    @Path("login")
    public Response login(@Context ContainerRequestContext context) {

        try {
            String decoded = getDecodedAuthorizationHeaderFromRequest(context);

            Account account = accountService.findByUser(stringHelper.getStringBeforeSeparator(decoded, ":"));

            authenticate(account, stringHelper.getStringAfterSeparator(decoded, ":"));

            String token = generateToken(account);

            return Response.ok(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private String getDecodedAuthorizationHeaderFromRequest(ContainerRequestContext context) throws Exception{
        String authString = context.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authString == null ){
            throw new Exception("No basic auth header");
        } else {
            return new String(Base64.getDecoder().decode(authString.substring(6)));
        }
    }

    private void authenticate(Account account, String password) throws Exception {
        if (account != null && account.getPassword().equals(password)) {
            return;
        }
        throw new Exception("Invalid user credentials");
    }

    private String generateToken(Account account) {
        Token token = tokenService.createToken(account);
        return "Auth-shopper " + account.getUserName() +":" + token.getToken();
    }
}
