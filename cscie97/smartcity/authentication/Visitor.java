package cscie97.smartcity.authentication;

import cscie97.smartcity.authentication.domain.AuthToken;
import cscie97.smartcity.authentication.domain.Entitlement;
import cscie97.smartcity.authentication.domain.Resource;
import cscie97.smartcity.authentication.domain.User;

public interface Visitor {

    void visit(User user);
    void visit(Entitlement entitlement);
    void visit(AuthToken authToken);
    void visit(Resource resource);

    boolean isValidStatus();
    boolean isValidExpTime();
    boolean isHasPermissions();
    boolean isHasResources();
    User getUserUnderValidation();

}
