package se.rosscom.shopper.business.ping;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

public class PingService {

    @PersistenceContext
    private EntityManager em;

    public JsonObject ping() {
        return Json.createObjectBuilder()
                .add("environment", "prd")  //TODO should add logic to find out which environment is running
                .add("systemTime", LocalDateTime.now().toString())
                .add("databaseConnection", checkDb())
                .build();
    }

    private boolean checkDb() {
        return em.isOpen();

    }
}
