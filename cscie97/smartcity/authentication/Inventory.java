package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.User;


public class Inventory implements Visitor {
    @Override
    public void visit(User user) {
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            authenticationService.getUserList().put(user.getId(), user);
    }

    @Override
    public void visit(Entitlement entitlement) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getEntitlementList().put(entitlement.getId(),entitlement);
    }

    @Override
    public void visit(AuthToken authToken) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getAuthTokenList().put(authToken.getId(), authToken);
    }


}
