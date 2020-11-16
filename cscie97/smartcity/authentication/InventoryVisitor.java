package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.Resource;
import cscie97.smartcity.authentication.domain.User;


public class InventoryVisitor implements Visitor {
    @Override
    public void visit(User user) {
            AuthenticationService authenticationService = AuthenticationService.getInstance();
            authenticationService.getUsersMap().put(user.getId(), user);
    }

    @Override
    public void visit(Entitlement entitlement) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getEntitlementsMap().put(entitlement.getId(),entitlement);
    }

    @Override
    public void visit(AuthToken authToken) {
        AuthenticationService authenticationService = AuthenticationService.getInstance();
        authenticationService.getAuthTokensMap().put(authToken.getId(), authToken);
    }

    @Override
    public void visit(Resource resource) {

    }

    @Override
    public boolean isValidStatus() {
        return false;
    }

    @Override
    public boolean isValidExpTime() {
        return false;
    }

    @Override
    public boolean isHasPermissions() {
        return false;
    }

    @Override
    public boolean isHasResources() {
        return false;
    }

    @Override
    public User getUserUnderValidation() {
        return null;
    }


}
